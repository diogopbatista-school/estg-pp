/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PickingManagement;

import Core.ContainerImp;
import Core.ContainerTypeImp;
import Core.InstitutionImp;
import Core.MeasurementImp;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.exceptions.MeasurementException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteGenerator;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author diogo
 */
public class RouteGeneratorImp implements RouteGenerator {

    private static final ContainerTypeImp PRIORITY_N1 = new ContainerTypeImp("perishable food");
    private static final int ONE_ROUTE_ONE_VEHICLE = 1;
    private int[] numberOfEmptyContainers = new int[ContainerTypeImp.getTypes().length];
    private AidBox[] allAidBoxes;
    private int nAidBoxes;
    private Vehicle[] allVehicles;
    private int nVehicles;

    public RouteGeneratorImp() {
    }

    @Override
    public Route[] generateRoutes(Institution instn) {

        this.allAidBoxes = ((InstitutionImp)instn).getAidBoxes();
        this.nAidBoxes = this.allAidBoxes.length;
        this.allVehicles = ((InstitutionImp)instn).getVehiclesClone();
        this.nVehicles = this.allVehicles.length;
        this.setNumberEmptyContainers(instn);

        if (this.nAidBoxes == 0 || this.nVehicles == 0) {
            return null;
        }

        Route[] routes = new Route[this.nVehicles];
        try {
            for (int i = 0; i < this.nVehicles; i++) {
                if (this.nAidBoxes == 0) {
                    break;
                }
                Vehicle vehicle = this.allVehicles[i];
                if (!((VehicleImp) vehicle).getStatus()) {
                    continue;
                }
                this.setVehicleEmptyContainers(vehicle);
                routes[i] = new RouteImp(vehicle);
                this.addAidBoxToRoute(routes[i]);
                ((RouteImp) routes[i]).setReport(generateReport(routes[i]));
                removePickedAidBoxes();
            }
        } catch (RouteException e) {
            //Fazer alguma cena com o throw;
        }
        return removeNulls(routes);
    }

    //Adiciona aidboxes à rota
    private void addAidBoxToRoute(Route route) throws RouteException {
        Vehicle vehicle = route.getVehicle();
        for (int i = 0; i < this.nAidBoxes; i++) {
            AidBox aidbox = this.allAidBoxes[i];

            if (!canPick(((VehicleImp) vehicle))) {
                ((VehicleImp) vehicle).setStatus(false);
                break;
            }

            if (aidbox == null) {
                continue;
            }

            if (this.pickContainers(aidbox, vehicle)) {
                route.addAidBox(aidbox);
            }

        }
    }

    //Verifica se existem contentores que podem ser coletados, se sim reseta o contentor e coloca o no veiculo
    private boolean pickContainers(AidBox aidbox, Vehicle vehicle) {
        Container[] aux = aidbox.getContainers();
        if (aux != null) {
            for (int i = 0; i < aux.length; i++) {
                if (this.checkContainer(aux[i]) && canPickUp(((VehicleImp) vehicle), aux[i].getType())) {
                    Capacity vehicleCapacity = ((VehicleImp) vehicle).getContainerType(aux[i].getType());
                    vehicleCapacity.setCurrentCapacity();
                    try {
                        ((ContainerImp) aux[i]).addMeasurement(new MeasurementImp(0, LocalDateTime.now()));
                    } catch (MeasurementException ex) {
                        Logger.getLogger(RouteGeneratorImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    //Verifica se existem contentores que podem ser coletados
    private boolean checkContainers(AidBox aidbox) {
        Container[] aux = aidbox.getContainers();
        if (aux != null) {

            for (int i = 0; i < aux.length; i++) {
                if (this.checkContainer(aux[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    //Verifica se um contentor pode ser coleatado
    private boolean checkContainer(Container container) {
        if (container == null) {
            return false;
        }
        if (container.getType().equals(PRIORITY_N1)
                && ((ContainerImp) container).getLastMeasurement() > 0) {
            return true;
        }
        if (isAbove80(container.getCapacity(), ((ContainerImp) container).getLastMeasurement())) {
            return true;
        }
        return false;
    }

    //Remove AidBoxes que já foram coletadas
    private void removePickedAidBoxes() {
        for (int i = 0; i < this.nAidBoxes; i++) {
            if (!this.checkContainers(this.allAidBoxes[i])) {
                this.allAidBoxes[i] = this.allAidBoxes[--this.nAidBoxes];
                this.allAidBoxes[this.nAidBoxes] = null;
            }
        }
    }

    private boolean canPick(VehicleImp vehicle) {
        ContainerType[] types = vehicle.getContainersTypes();
        boolean check = false;
        for (int i = 0; i < types.length; i++) {
            if (canPickUp(vehicle, types[i])) {
                check = true;
            }
        }
        return check;
    }

    private boolean canPickUp(VehicleImp vhcl, ContainerType type) {
        Capacity aux = vhcl.getContainerType(type);

        double currentCapacity = aux.getCurrentCapacity();
        double maxCapacity = aux.getEmptyContainers();
        if (currentCapacity >= maxCapacity) {
            return false;
        }
        return true;
    }

    private boolean isAbove80(double maxCapacity, double msrmt) {
        if ((maxCapacity >= 0) || (msrmt >= 0)) {
            Double maxCapacityPercent = ((80 * maxCapacity) / 100);
            if (msrmt > maxCapacityPercent) {
                return true;
            }
        }
        return false;
    }

    private Route[] removeNulls(Route[] routes) {
        int count = 0;
        for (int i = 0; i < routes.length; i++) {
            if (routes[i] != null) {
                count++;
            }
        }

        Route[] aux = new RouteImp[count];
        count = 0;

        for (int i = 0; i < routes.length; i++) {
            if (routes[i] != null) {
                aux[count] = routes[i];
                count++;
            }
        }
        return aux;
    }

    private Report generateReport(Route route) {
        int pickedContainers = getPickedContainers(route);
        int nonPickedContainers = getTotalContainers(route) - pickedContainers;
        double totalDistance = ((RouteImp) route).getTotalDistance();
        double totalDuration = ((RouteImp) route).getTotalDuration();
        return new ReportImp(ONE_ROUTE_ONE_VEHICLE, pickedContainers,
                totalDistance, totalDuration, nonPickedContainers, this.nVehicles - 1, LocalDateTime.now());
    }

    private int getPickedContainers(Route route) {
        Vehicle vehicle = route.getVehicle();
        ContainerType[] types = ((VehicleImp) vehicle).getContainersTypes();
        int totalPickedContainers = 0;
        for (int i = 0; i < types.length; i++) {
            Capacity capacity = ((VehicleImp) vehicle).getContainerType(types[i]);
            totalPickedContainers += capacity.getCurrentCapacity();
        }
        return totalPickedContainers;
    }

    private int getTotalContainers(Route route) {
        AidBox[] aidboxes = route.getRoute();
        int totalContainers = 0;
        for (int i = 0; i < aidboxes.length; i++) {
            totalContainers += aidboxes[i].getContainers().length;
        }
        return totalContainers;
    }

    private void setNumberEmptyContainers(Institution instn) {
        Container[] stockContainers = ((InstitutionImp) instn).getContainers();
        for (int i = 0; i < this.numberOfEmptyContainers.length; i++) {
            this.numberOfEmptyContainers[i] = 0;
        }

        for (int i = 0; i < stockContainers.length; i++) {
            for (int j = 0; j < this.numberOfEmptyContainers.length; j++) {
                if (((ContainerTypeImp) stockContainers[i].getType()).getType().equals(ContainerTypeImp.getTypes()[j])
                        && ((ContainerImp) stockContainers[i]).getLastMeasurement() <= 0) {
                    this.numberOfEmptyContainers[j]++;
                }
            }
        }
    }

    private void setVehicleEmptyContainers(Vehicle vehicle) {
        String[] types = ContainerTypeImp.getTypes();
        for (int i = 0; i < this.numberOfEmptyContainers.length; i++) {
            if (this.numberOfEmptyContainers[i] <= 0) {
                continue;
            }
            Capacity capacity = ((VehicleImp) vehicle).getContainerType(types[i]);
            if(capacity == null){
                continue;
            }
            if (this.numberOfEmptyContainers[i] >= capacity.getMaxCapacity()) {
                ((VehicleImp)vehicle).setEmptyContainers(types[i], capacity.getMaxCapacity());
            } else {
                ((VehicleImp)vehicle).setEmptyContainers(types[i], this.numberOfEmptyContainers[i]);
            }
            this.numberOfEmptyContainers[i] -= capacity.getEmptyContainers();
        }
    }
}
