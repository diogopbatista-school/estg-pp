package PickingManagement;

import com.estg.core.AidBox;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.Vehicle;
import Core.AidBoxImp;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.exceptions.RouteException;

/*
* Nome: Diogo Pereira Batista
* Número: 8230367
* Turma: LSIRC T1
*
* Nome: Rúben da Silva Uth
* Número: 8210481
* Turma: LSIRC T2
 */
/**
 *
 * The Route class represents a route for picking management in a logistics
 * system. It manages a sequence of aid boxes to be visited by a vehicle during
 * a picking operation. The class provides methods to add, remove, replace, and
 * insert aid boxes into the route. It also computes various properties of the
 * route, such as total distance, duration and also does a detailed report for
 * the route
 *
 *
 * @author Diogo e Ruben
 */
public class RouteImp implements Route {

    /**
     * The value to start arrays
     */
    private final static int INICIAL_SIZE = 5;
    
     /**
     * The value for expanding arrays dynamically
     */
    private final static int EXPAND_ARRAY = 2;

    /**
     * The string reference for the institution base
     */
    private static String CODE_FOR_BASE = "Base";
    
    /**
     * The array of aidboxes of the route
     */
    private AidBox[] aidBoxes;
    
    /**
     * The number of aidboxes of the route
     */
    private int nAidBoxes;
    
    /**
     * The unique vehicle of the route
     */
    private Vehicle vehicle;
    
    /**
     * The report of the route
     */
    private Report report;

    /**
     * Constructor for the route
     *
     * @param vehicle - The vehicle for the route
     */
    public RouteImp(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.aidBoxes = new AidBox[INICIAL_SIZE];
        this.nAidBoxes = 0;
    }

    /**
     * This method expands the size of the array of objects
     *
     * @param object - The object array i want to expand.
     * @param size - The size of the array object .
     * @param newObject - The new array with new size
     * @return - The expanded array with all previous informations inside
     */
    private Object[] expandArray(Object[] object, int size, Object[] newObject) {
        for (int i = 0; i < size; i++) {
            newObject[i] = object[i];
        }
        object = newObject;
        return object;
    }

    /**
     * This method expands the size of the array of aidboxes
     */
    private void expandAidBoxArray() {
        Object[] aux = new AidBox[this.nAidBoxes * EXPAND_ARRAY];
        aux = expandArray(this.aidBoxes, this.nAidBoxes, aux);
        this.aidBoxes = (AidBox[]) aux;
    }

    /**
     * This method checks if the object i want to compare is in the array or not
     *
     * @param array - The array of object i wanna check for
     * @param element - The object I want to check whether or not it is in the
     * array
     * @param length - The number of objects I will check
     * @return - True if the exact same object is in the array . False otherwise
     */
    private boolean containsElement(Object[] array, Object element, int length) {
        for (int i = 0; i < length; i++) {
            if (element.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the aidbox is already at the route
     *
     * @param aidbox - The aidbox i wanna check if it already exists in the
     * aidbox
     * @return - True if the exact same aidbox is in the array . False otherwise
     */
    @Override
    public boolean containsAidBox(AidBox aidbox) {
        return containsElement(this.aidBoxes, aidbox, this.nAidBoxes);
    }

    /**
     * This method checks if the object i want to compare is in the array or not
     *and if so , gets me a index where is locared in the array
     * 
     * @param array - The array of object i wanna check for
     * @param element - The object I want to check whether or not it is in the
     * array
     * @param size - The number of objects I will check
     * @return - The position in the array . -1 if doesnt find at the array
     */
    private int getObjectPosition(Object[] array, Object element, int size) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Getter for the index of the aidbox that i want to get
     *
     * @param aidbox - The aidbox to search
     * @return - The position in the array
     */
    private int getAidBoxPosition(AidBox aidbox) {
        return getObjectPosition(this.aidBoxes, aidbox, this.nAidBoxes);
    }

    /**
     * Getter for an array of AidBox objects representing the route.
     *
     * @return an array of AidBox objects, where each object is a clone of the
     * corresponding AidBox in the original array
     */
    @Override
    public AidBox[] getRoute() {

        AidBox[] aux = new AidBoxImp[this.nAidBoxes];
        try {
            for (int i = 0; i < this.nAidBoxes; i++) {
                if (this.aidBoxes[i] != null) {
                    aux[i] = ((AidBoxImp) this.aidBoxes[i]).clone();
                }

            }
        } catch (CloneNotSupportedException e) {
            e.getMessage();
        }
        return aux;
    }

    /**
     * Getter for the vehicle of the route
     *
     * @return - The vehicle of the route
     */
    @Override
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    /**
     * Setter for the report
     *
     * @param report - The report i want to insert
     */
    public void setReport(Report report) {
        if (report != null) {
            this.report = report;
        }
    }

    /**
     * Getter for the report
     *
     * @return - The report of this route
     */
    @Override
    public Report getReport() {
        return this.report;
    }

    /**
     * Getter for the total distance of the route . From base to all aidboxes
     * and back to the base
     *
     * @return - The total distance
     */
    @Override
    public double getTotalDistance() {
        if (this.nAidBoxes != 0) {
            if (((AidBoxImp) this.aidBoxes[0]).getDistaceBase(CODE_FOR_BASE) != -1
                    && ((AidBoxImp) this.aidBoxes[this.nAidBoxes - 1]).getDistaceBase(CODE_FOR_BASE) != -1) {

                double totalDistance = ((AidBoxImp) this.aidBoxes[0]).getDistaceBase(CODE_FOR_BASE)
                        + ((AidBoxImp) this.aidBoxes[this.nAidBoxes - 1]).getDistaceBase(CODE_FOR_BASE);
                try {
                    for (int i = 0; i < this.nAidBoxes - 1; i++) {
                        totalDistance += this.aidBoxes[i].getDistance(this.aidBoxes[i + 1]);
                    }
                } catch (AidBoxException e) {
                }
                return totalDistance;
            }
            return -1.0;
        }
        return -1.0;
    }

    /**
     * Getter for the total duration of the route . From base to all aidboxes
     * and back to the base
     *
     * @return - The total duration
     */
    @Override
    public double getTotalDuration() {
        if (this.nAidBoxes != 0) {
            if (((AidBoxImp) this.aidBoxes[0]).getDurationBase(CODE_FOR_BASE) != -1
                    && ((AidBoxImp) this.aidBoxes[this.nAidBoxes - 1]).getDurationBase(CODE_FOR_BASE) != -1) {

                double totalDuration = ((AidBoxImp) this.aidBoxes[0]).getDurationBase(CODE_FOR_BASE)
                        + ((AidBoxImp) this.aidBoxes[this.nAidBoxes - 1]).getDurationBase(CODE_FOR_BASE);
                try {
                    for (int i = 0; i < this.nAidBoxes - 1; i++) {
                        totalDuration += this.aidBoxes[i].getDistance(this.aidBoxes[i + 1]);
                    }
                } catch (AidBoxException e) {
                }
                return totalDuration;
            }
            return -1.0;
        }
        return -1.0;
    }

    /**
     * This method checks all vehicle types and see if its compatible with the 
     * all the containers of an specific aidbox
     * 
     * @param containers - All the containers of a specific aidbox
     * @param vehicleTypes - All the types a vehicle has
     * @return - True if the aidbox has an type compatible with the vehicle's types
     */
    private boolean compatibleWithVehicle(Container[] containers, ContainerType[] vehicleTypes) {
        boolean check = false;

        for (int i = 0; i < vehicleTypes.length; i++) {
            for (int j = 0; j < containers.length; j++) {
                if (vehicleTypes[i].equals(containers[j].getType())) {
                    check = true;
                    break;
                }
            }
            if (check) {
                break;
            }
        }
        return check;
    }
    
    /**
     * This method adds a aidbox to the route
     * 
     * @param aidbox - The aidbox to add
     * @throws RouteException - if the Aid Box is null.
     * if the Aid Box is already in the route.
     * if the Aid Box is not compatible (doesn't have a container that can be 
     * picked by the vehicle) with the Vehicle of the route.
     */
    @Override
    public void addAidBox(AidBox aidbox) throws RouteException {
        if (aidbox == null) {
            throw new RouteException("This AidBox is null");
        }
        if (containsAidBox(aidbox)) {
            throw new RouteException("This AidBox is already in the route");
        }

        if (!compatibleWithVehicle(aidbox.getContainers(), ((VehicleImp) this.vehicle).getContainersTypes())) {
            throw new RouteException("The Aid Box is not compatible with the Vehicle of the route");
        }

        if (this.nAidBoxes == this.aidBoxes.length) {
            expandAidBoxArray();
        }

        this.aidBoxes[this.nAidBoxes++] = aidbox;
    }
    
    /**
     * This method removes a aidbox from the array of aidboxes 
     * 
     * @param aidbox - The aidbox to remove
     * @return - The removed aidbox
     * @throws RouteException - if the Aid Box parameter is null or
     * if the Aid Box is not in the route
     */
    @Override
    public AidBox removeAidBox(AidBox aidbox) throws RouteException {
        if (aidbox == null) {
            throw new RouteException("This AidBox is null");
        }

        if (!containsAidBox(aidbox)) {
            throw new RouteException("This AidBox is not into the route");
        }

        this.aidBoxes[getAidBoxPosition(aidbox)] = this.aidBoxes[this.nAidBoxes];
        this.aidBoxes[this.nAidBoxes--] = null;
        return aidbox;

    }

    /**
     * Replaces an Aid Box from the route
     *
     * @param aidbox - the Aid Box to replace
     * @param aidbox1 - the Aid Box to replace with
     * @throws RouteException - if any Aid Box is null 
     * if the Aid Box to replace is not in the route .
     * if the Aid Box to insert is already in the route.
     * if the Aid Box to insert is not compatible (doesn't have a container that
     * can be picked by the vehicle) with the Vehicle of the route
     */
    @Override
    public void replaceAidBox(AidBox aidbox, AidBox aidbox1) throws RouteException {
        if (aidbox == null || aidbox1 == null) {
            throw new RouteException("Aidbox cannot be null");
        }

        if (!containsAidBox(aidbox)) {
            throw new RouteException("The AidBox to be replaced is not in the route");
        }

        if (this.containsAidBox(aidbox1)) {
            throw new RouteException("The AidBox to replace with is already in the route");
        }

        if (!compatibleWithVehicle(aidbox1.getContainers(), ((VehicleImp) this.vehicle).getContainersTypes())) {
            throw new RouteException("The Aid Box to insert is not compatible with the Vehicle of the route");
        }

        this.aidBoxes[getAidBoxPosition(aidbox)] = aidbox1;
    }

    /**
     * Inserts an Aid Box before another Aid Box in the route
     *
     * @param aidbox - the Aid Box to insert before
     * @param aidbox1 - the Aid Box to insert
     * @throws RouteException - if any Aid Box is null
     * if the Aid Box to replace is not in the route
     * if the Aid Box to insert is already in the route
     * if the Aid Box to insert is not compatible (doesn't have a container 
     * that can be picked by the vehicle) with the Vehicle of the route
     */
    @Override
    public void insertAfter(AidBox aidbox, AidBox aidbox1) throws RouteException {
        if (aidbox == null || aidbox1 == null) {
            throw new RouteException("Aidbox cannot be null");
        }

        if (!containsAidBox(aidbox)) {
            throw new RouteException("The AidBox to be replaced is not in the route");
        }

        if (this.containsAidBox(aidbox1)) {
            throw new RouteException("The AidBox to replace with is already in the route");
        }

        if (!compatibleWithVehicle(aidbox1.getContainers(), ((VehicleImp) this.vehicle).getContainersTypes())) {
            throw new RouteException("The Aid Box to insert is not compatible with the Vehicle of the route");
        }

        expandAidBoxArray();

        if (getAidBoxPosition(aidbox) == this.nAidBoxes) {
            for (int i = this.nAidBoxes; i > 0; i--) {
                this.aidBoxes[i] = this.aidBoxes[i - 1];
            }
            this.aidBoxes[0] = aidbox1;
        }

        this.nAidBoxes++;
    }
}
