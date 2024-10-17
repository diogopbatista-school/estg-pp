/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import Exceptions.LocationException;
import Exceptions.ReportException;
import PickingManagement.VehicleImp;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Institution;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;
import java.time.LocalDateTime;

/**
 *
 * @author diogo
 */
public class InstitutionImp implements Institution, Cloneable {

    private static final int INICIALIZE_ARRAY = 2;

    /**
     * The expand array constant
     */
    private static final int EXPAND_ARRAY = 2;

    private static String CODE_FOR_BASE = "Base";
    /**
     * The name of the institution
     */
    private String name;
    private int nAidBoxes;
    private int nVehicles;
    private int nContainers;
    private int nPickingMaps;
    private int nReports;
    private Vehicle[] vehicles;
    private Container[] containers;
    private AidBox[] aidBoxes;
    private PickingMap[] pickingMaps;
    private Report[] reports;
    private Container[] stockContainers;
    private int nStockContainers;
    private Measurement[] errorMeasurements;
    private int nErrorMeasurements;

    public InstitutionImp(String name) {
        this.name = name;
        this.aidBoxes = new AidBox[INICIALIZE_ARRAY];
        this.nAidBoxes = 0;
        this.pickingMaps = new PickingMap[INICIALIZE_ARRAY];
        this.nVehicles = 0;
        this.vehicles = new Vehicle[INICIALIZE_ARRAY];
        this.nVehicles = 0;
        this.reports = new Report[INICIALIZE_ARRAY];
        this.nReports = 0;
        this.containers = new Container[INICIALIZE_ARRAY];
        this.nContainers = 0;
        this.stockContainers = new Container[INICIALIZE_ARRAY];
        this.nStockContainers = 0;
        this.errorMeasurements = new Measurement[INICIALIZE_ARRAY];
        this.nErrorMeasurements = 0;
    }

    public void setLocations(String code, Location[] locations) throws LocationException {
        int position = getAidBoxPosition(code);
        if (position != -1) {
            ((AidBoxImp) this.aidBoxes[position]).setLocations(locations);
        }

    }

    private int getAidBoxPosition(String code) {
        for (int i = 0; i < this.nAidBoxes; i++) {
            if (code.equals(this.aidBoxes[i].getCode())) {
                return i;
            }
        }
        return -1;
    }

    private Object[] getObjectArray(Object[] object, int size, Object[] newObject) {
        for (int i = 0; i < size; i++) {
            newObject[i] = object[i];
        }
        object = newObject;
        return object;
    }

    public Container[] getContainers() {
        Container[] aux = new Container[this.nContainers];
        return (Container[]) getObjectArray(this.containers, this.nContainers, aux);
    }

    @Override
    public AidBox[] getAidBoxes() {
        AidBox[] aux = new AidBox[this.nAidBoxes];
        return (AidBox[]) getObjectArray(this.aidBoxes, this.nAidBoxes, aux);
    }

    public Report[] getReports() {
        Report[] aux = new Report[this.nReports];
        return (Report[]) getObjectArray(this.reports, this.nReports, aux);
    }

    @Override
    public Vehicle[] getVehicles() {
        Vehicle[] aux = new Vehicle[this.nVehicles];
        for (int i = 0; i < this.nVehicles; i++) {
            try {
                Vehicle clone = ((VehicleImp) this.vehicles[i]).clone();
                aux[i] = clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e.getMessage());
            }
        }
        return aux;

    }

    @Override
    public PickingMap[] getPickingMaps() {
        PickingMap[] aux = new PickingMap[this.nPickingMaps];
        return (PickingMap[]) getObjectArray(this.pickingMaps, this.nPickingMaps, aux);
    }

    private int searchPickingMap(LocalDateTime ldt) {
        for (int i = 0; i < this.nPickingMaps; i++) {
            if (ldt.isEqual(this.pickingMaps[i].getDate())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public PickingMap[] getPickingMaps(LocalDateTime ldt, LocalDateTime ldt1) {
        int first = searchPickingMap(ldt);
        int last = searchPickingMap(ldt1);
        int size = first - last;

        PickingMap[] pickingMaps = new PickingMap[size];
        int count = 0;

        for (int i = first; i <= last; i++) {
            pickingMaps[count++] = this.pickingMaps[i];
        }

        return pickingMaps;
    }

    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {
        if (this.nPickingMaps == 0) {
            throw new PickingMapException("There are no picking maps in the institution");
        }

        return this.pickingMaps[this.nPickingMaps - 1];
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("Aidbox cannot be null");
        }

        if (!hasSameAidbox(aidbox)) {
            throw new AidBoxException("Aidbox doesnt exist");
        }

        return ((AidBoxImp) aidbox).getDistaceBase(CODE_FOR_BASE);
    }

    @Override
    public Container getContainer(AidBox aidbox, ContainerType ct) throws ContainerException {
        Container containerCopy;

        if (aidbox == null || ct == null) {
            throw new ContainerException("Aidbox/ContainerType cannot be null");
        }

        if (!hasSameAidbox(aidbox) || aidbox.getContainer(ct) != null) {
            throw new ContainerException("The aidbox does not exist or a container with the given item type does not exist!");
        }

        containerCopy = aidbox.getContainer(ct);
        return containerCopy;
    }

    private boolean containsElement(Object[] array, Object object, int size) {
        for (int i = 0; i < size; i++) {
            if (object.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean hasSameAidbox(AidBox aidbox) {
        return containsElement(this.aidBoxes, aidbox, this.nAidBoxes);
    }

    private boolean hasSameVehicle(Vehicle vhcl) {
        return containsElement(this.vehicles, vhcl, this.nVehicles);
    }

    private boolean hasSamePickingMap(PickingMap pm) {
        return containsElement(this.pickingMaps, pm, this.nPickingMaps);
    }

    private boolean hasSameContainer(Container cntr) {
        return containsElement(this.containers, cntr, this.nContainers);
    }

    private int getObjectPosition(Object[] array, Object element, int size) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    private int getVehiclePosition(Vehicle vhcl) {
        return getObjectPosition(this.vehicles, vhcl, this.nVehicles);
    }

    private Object[] expandArray(Object[] object, int size, Object[] newObject) {
        for (int i = 0; i < size; i++) {
            newObject[i] = object[i];
        }
        object = newObject;
        return object;
    }

    private void expandContainerArray() {
        Object[] aux = new Container[this.nContainers * EXPAND_ARRAY];
        aux = expandArray(this.containers, this.nContainers, aux);
        this.containers = (Container[]) aux;
    }

    private void expandVehicleArray() {
        Object[] aux = new Vehicle[this.nVehicles * EXPAND_ARRAY];
        aux = expandArray(this.vehicles, this.nVehicles, aux);
        this.vehicles = (Vehicle[]) aux;
    }

    private void expandPickingMapArray() {
        Object[] aux = new PickingMap[this.nPickingMaps * EXPAND_ARRAY];
        aux = expandArray(this.pickingMaps, this.nPickingMaps, aux);
        this.pickingMaps = (PickingMap[]) aux;
    }

    private void expandAidBoxArray() {
        Object[] aux = new AidBox[this.nAidBoxes * EXPAND_ARRAY];
        aux = expandArray(this.aidBoxes, this.nAidBoxes, aux);
        this.aidBoxes = (AidBox[]) aux;
    }

    private void expandReportsArray() {
        Object[] aux = new Report[this.nReports * EXPAND_ARRAY];
        aux = expandArray(this.reports, this.nReports, aux);
        this.reports = (Report[]) aux;
    }

    private void expandStockContainersArray() {
        Object[] aux = new Container[this.nStockContainers * EXPAND_ARRAY];
        aux = expandArray(this.stockContainers, this.nStockContainers, aux);
        this.stockContainers = (Container[]) aux;
    }

    private void expandErrorMeasurementArray() {
        Object[] aux = new Measurement[this.nErrorMeasurements * EXPAND_ARRAY];
        aux = expandArray(this.errorMeasurements, this.nErrorMeasurements, aux);
        this.errorMeasurements = (Measurement[]) aux;
    }

    @Override
    public void enableVehicle(Vehicle vhcl) throws VehicleException {
        if (vhcl == null) {
            throw new VehicleException("The vehicle is null");
        }

        if (getVehiclePosition(vhcl) == -1) {
            throw new VehicleException("The vehicle is not in the institution");
        }
        if (((VehicleImp) vhcl).getStatus()) {
            throw new VehicleException("The vehicle is already enabled");
        }

        ((VehicleImp) this.vehicles[getVehiclePosition(vhcl)]).setStatus(true);
    }

    @Override
    public void disableVehicle(Vehicle vhcl) throws VehicleException {
        if (vhcl == null) {
            throw new VehicleException("The vehicle is null");
        }

        if (getVehiclePosition(vhcl) == -1) {
            throw new VehicleException("The vehicle is not in the institution");
        }
        if (((VehicleImp) vhcl).getStatus()) {
            throw new VehicleException("The vehicle is already disabled");
        }

        ((VehicleImp) this.vehicles[getVehiclePosition(vhcl)]).setStatus(false);
    }

    @Override
    public boolean addVehicle(Vehicle vhcl) throws VehicleException {
        if (vhcl == null) {
            throw new VehicleException("The vehicle is null");
        }

        if (hasSameVehicle(vhcl)) {
            return false;
        }

        if (this.nVehicles == this.vehicles.length) {
            expandVehicleArray();
        }

        this.vehicles[this.nVehicles++] = vhcl;
        return true;
    }

    @Override
    public boolean addPickingMap(PickingMap pm) throws PickingMapException {
        if (pm == null) {
            throw new PickingMapException("The picking map is null");
        }

        if (hasSamePickingMap(pm)) {
            return false;
        }

        if (this.nPickingMaps == this.pickingMaps.length) {
            expandPickingMapArray();
        }

        this.pickingMaps[this.nPickingMaps++] = pm;
        return true;
    }

    @Override
    public boolean addAidBox(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("This aidbox is null");
        }

        if (hasSameAidbox(aidbox)) {
            return false;
        }

        if (this.nAidBoxes == this.aidBoxes.length) {
            expandAidBoxArray();
        }

        this.aidBoxes[this.nAidBoxes++] = aidbox;
        return true;
    }

    public boolean addContainer(Container ctnr) throws ContainerException {
        if (ctnr == null) {
            throw new ContainerException("This ctnr is null");
        }

        if (hasSameContainer(ctnr)) {
            return false;
        }

        if (this.nContainers == this.containers.length) {
            expandContainerArray();
        }

        this.containers[this.nContainers++] = ctnr;
        return true;
    }

    public void addReport(Report report) throws ReportException {
        if (report == null) {
            throw new ReportException("Report cannot be null");
        }

        if (this.nReports == this.reports.length) {
            expandReportsArray();
        }

        this.reports[this.nReports++] = report;

    }

    public boolean checkForContainer(Container cntnr) {
        for (int i = 0; i < this.nAidBoxes; i++) {
            if (cntnr.equals(this.aidBoxes[i].getContainer(cntnr.getType()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addMeasurement(Measurement msrmnt, Container cntnr) throws ContainerException, MeasurementException {
        if (cntnr == null || !checkForContainer(cntnr)) {
            throw new ContainerException("The container does not exist/cannot be null");
        }

        if (msrmnt.getValue() > cntnr.getCapacity() || msrmnt.getValue() < 0) {
            throw new MeasurementException("The measurement value is invalid");

        }

        for (int i = 0; i < this.nAidBoxes; i++) {
            Container[] aux = this.aidBoxes[i].getContainers();
            for (int j = 0; j < aux.length; j++) {
                if (aux[j].getCode().equals(cntnr.getCode())) {
                    String code = cntnr.getCode();
                    AidBox aidbox = this.aidBoxes[i];
                    ((AidBoxImp) aidbox).getContainer(code).addMeasurement(msrmnt);
                    return true;
                }
            }
        }
        return false;
    }

    public void addMeasurementToContainerArray(Measurement msrmnt, Container cntnr) throws ContainerException, MeasurementException {
        if (cntnr == null || !checkForContainer(cntnr)) {
            throw new ContainerException("The container does not exist/cannot be null");
        }

        if (msrmnt.getValue() > cntnr.getCapacity() || msrmnt.getValue() < 0) {
            throw new MeasurementException("The measurement value is invalid");

        }

        for (int i = 0; i < this.nContainers; i++) {
            if(this.containers[i].getCode().equals(cntnr.getCode())){
                this.containers[i].addMeasurement(msrmnt);
            }
        }

    }

    public void addStockContainer(ContainerImp cntnr) throws ContainerException {
        if (cntnr == null) {
            throw new ContainerException("Container cannot be null");
        }

        if (this.nStockContainers == this.stockContainers.length) {
            expandStockContainersArray();
        }

        this.stockContainers[this.nStockContainers++] = cntnr;
    }

    public void addErrorMeasurements(Measurement measurement) throws MeasurementException {
        if (measurement == null) {
            throw new MeasurementException("Measurement cannot be null");
        }

        if (this.nErrorMeasurements == this.errorMeasurements.length) {
            expandErrorMeasurementArray();
        }

        this.errorMeasurements[this.nErrorMeasurements++] = measurement;
    }

    public int searchAidBox(String code) {
        for (int i = 0; i < this.nAidBoxes; i++) {
            if (this.aidBoxes[i].getCode().equals(code)) {
                return i;
            }
        }
        return -1;
    }

    public void addContainer(String aidboxCode, Container container) throws AidBoxException, ContainerException {
        int position = this.searchAidBox(aidboxCode);

        if (position == -1) {
            System.out.println("AidBox does not exist");
            return;
        }

        for (int i = 0; i < this.aidBoxes[position].getContainers().length; i++) {
            ContainerType type = this.aidBoxes[position].getContainers()[i].getType();
            String code = this.aidBoxes[position].getContainers()[i].getCode();
            if (type.equals(container.getType()) || code.equals(container.getCode())) {
                throw new ContainerException("This containerType/CODE already exists in this aidbox. Operation not performed ");

            }
        }

        this.aidBoxes[position].addContainer(container);

    }

    private int searchContainer(Container cntnr) {
        for (int i = 0; 0 < this.nContainers; i++) {
            if (cntnr.equals(this.containers[i])) {
                return i;
            }
        }
        return -1;
    }

    public void removeContainer(Container cntnr) throws InstitutionException {
        if (cntnr == null) {
            throw new InstitutionException("This container cannot be null");
        }

        if (!hasSameContainer(cntnr)) {
            throw new InstitutionException("The container to remove doesnt exit");
        }

        this.containers[searchContainer(cntnr)] = this.containers[--this.nContainers];
        this.containers[this.nContainers] = null;
    }

    public AidBox[] getAidBoxesClone() {
        AidBox[] aidboxesCopy = new AidBox[this.nAidBoxes];
        for (int i = 0; i < this.nAidBoxes; i++) {
            try {
                aidboxesCopy[i] = ((AidBoxImp) this.aidBoxes[i]).clone();
            } catch (CloneNotSupportedException e) {

            }
        }
        return aidboxesCopy;
    }

    public Vehicle[] getVehiclesClone() {
        Vehicle[] vehicleCopy = new Vehicle[this.nVehicles];
        for (int i = 0; i < this.nVehicles; i++) {
            try {
                vehicleCopy[i] = ((VehicleImp) this.vehicles[i]).clone();
            } catch (CloneNotSupportedException e) {

            }
        }
        return vehicleCopy;
    }

}
