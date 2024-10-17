/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import Exceptions.ContainerTypeException;
import Exceptions.LocationException;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;

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
* The AidBox class provides functionalities to manage an aid box's code, zone, 
* and containers, as well as methods to manage locations 
* to other aid boxes and calculate distances and durations.
*
* @author Diogo e Ruben
*
 */
public class AidBoxImp implements AidBox {

    /**
     * The value for expanding arrays dynamically
     */
    private static int EXPAND_ARRAY = 2;

    /**
     * The value to start arrays
     */
    private static int INICIALIZE_ARRAY = 6;

    /**
     * The aidbox's code
     */
    private String code;

    /**
     * The zone where the aidbox is located
     */
    private String zone;

    /**
     * The containers inside an aidbox
     */
    private Container[] containers;

    /**
     * The number of containers of each aidbox
     */
    private int nContainers;

    /**
     * All the locations from the aidbox to other destination
     */
    private Location[] locations;

    /**
     * The number of locations
     */
    private int nLocations;

    /**
     * Constructs a new aidbox with the specified code,zone and locations.
     *
     * @param code - the code for the aidbox
     * @param zone - the zone for the aid box
     */
    public AidBoxImp(String code, String zone) {
        this.code = code;
        this.zone = zone;
        this.containers = new Container[INICIALIZE_ARRAY];
        this.locations = new Location[INICIALIZE_ARRAY];
        this.nContainers = 0;
        this.nLocations = 0;
    }

    /**
     * Getter Aid Box code.
     *
     * @return Aid Box code
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Getter for Aid Box zone
     *
     * @return Aid Box zone
     */
    @Override
    public String getZone() {
        return this.zone;
    }

    /**
     * Setter for the aidbox locations
     *
     * @param loc - The array of locations . Aidbox location reference for other
     * destinations
     * @throws LocationException - if the location is null || or if already
     * exist already that location
     *
     */
    public void setLocations(Location[] loc) throws LocationException {
        for (int i = 0; i < loc.length; i++) {
            this.addLocation(loc[i]);
        }
    }

    /**
     * Getter for a specific container with a specific code.
     *
     * @param code - The code of the container
     * @return The container with the specific code . Null if cannot find
     * otherwise
     */
    public Container getContainer(String code) {
        for (int i = 0; i < this.nContainers; i++) {
            if (this.containers[i].getCode().equals(code)) {
                return this.containers[i];
            }
        }
        return null;
    }

    /**
     * Getter for a specific container with a specific container type.
     *
     * @param ct - The container type of the container
     * @return The container with the specific Container Type . Null if cannot
     * find otherwise
     */
    @Override
    public Container getContainer(ContainerType ct) {
        for (int i = 0; i < this.nContainers; i++) {
            if (this.containers[i].getType() == ct) {
                return this.containers[i];
            }
        }
        return null;
    }

    /**
     * Getter for any array without nulls.
     *
     * @param object - The array object that already exists with some nulls.
     * @param size - The size of the array's length.
     * @param newObject - The new array object with the exact length without
     * nulls
     * @return - The array with objects without nulls.
     */
    private Object[] getObjectArray(Object[] object, int size, Object[] newObject) {
        for (int i = 0; i < size; i++) {
            newObject[i] = object[i];
        }
        object = newObject;
        return object;
    }

    /**
     * Getter for the array locations without nulls.
     *
     * @return - The array of locations without nulls
     */
    public Location[] getLocations() {
        Location[] aux = new Location[this.nLocations];
        return (Location[]) getObjectArray(this.locations, this.nLocations, aux);
    }

    /**
     * Getter for the array containers without nulls.
     *
     * @return - The array of containers without nulls
     */
    @Override
    public Container[] getContainers() {
        Container[] aux = new Container[this.nContainers];
        return (Container[]) getObjectArray(this.containers, this.nContainers, aux);
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
     * This method expands the size of the array of containers
     */
    private void expandContainerArray() {
        Object[] aux = new Container[this.nContainers * EXPAND_ARRAY];
        aux = expandArray(this.containers, this.nContainers, aux);
        this.containers = (Container[]) aux;
    }

    /**
     * This method expands the size of the array of locations
     */
    private void expandLocationArray() {
        Object[] aux = new Location[this.nLocations * EXPAND_ARRAY];
        aux = expandArray(this.locations, this.nLocations, aux);
        this.locations = (Location[]) aux;
    }

    /**
     * This method checks if the object i want to compare is in the array or not
     *
     * @param array - The array of object i wanna check for
     * @param element - The object I want to check whether or not it is in the
     * array
     * @param size - The number of objects I will check
     * @return - True if the exact same object is in the array . False otherwise
     */
    private boolean containsElement(Object[] array, Object element, int size) {
        for (int i = 0; i < size; i++) {
            if (element.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the container is already at the aidbox
     *
     * @param cntnr - The container i wanna check if it already exists in the
     * aidbox
     * @return - True if the exact same container is in the array . False
     * otherwise
     */
    private boolean hasSameContainer(Container cntnr) {
        return (containsElement(this.containers, cntnr, this.nContainers));
    }

    /**
     * This method checks if the location is already at the aidbox
     *
     * @param loc - The location i wanna check if it already exists in the
     * aidbox
     * @return - True if the exact same location is in the array . False
     * otherwise
     */
    private boolean hasSameLocation(Location loc) {
        return (containsElement(this.locations, loc, this.nLocations));
    }

    /**
     * This method searchs in the array the location and gives the reference's
     * number inside the array
     *
     * @param code - The reference of the location i wanna find inside the
     * location's array
     * @return - The position of the location inside the array. -1 if it didnt
     * find
     */
    private int searchLocation(String code) {
        for (int i = 0; i < this.nLocations; i++) {
            if (code.equals(this.locations[i].getCode())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method searchs in the array the containers and gives the reference's
     * number inside the array
     *
     * @param cntnr - The reference of the container i wanna find inside the
     * container's array
     * @return - The position of the container inside the array. -1 if it didnt
     * find
     */
    private int searchContainer(Container cntnr) {
        for (int i = 0; 0 < this.nContainers; i++) {
            if (cntnr.equals(this.containers[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method gives me the distance between current aidbox to the parameter
     * aidbox
     *
     * @param aidbox - The aidbox reference to obtain the distance between the
     * current one to the parameter aidbox
     * @return - The distance between current aidbox to the parameter aidbox
     * @throws AidBoxException - If the aidbox is null
     */
    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {

        if (aidbox == null) {
            throw new AidBoxException("Aidbox cannot be null");
        }

        return this.locations[searchLocation(aidbox.getCode())].getDistance();

    }

    /**
     * This method gives me the duration between current aidbox to the parameter
     * aidbox
     *
     * @param aidbox - The aidbox reference to obtain the duration between the
     * current one to the parameter aidbox
     * @return - The duration between current aidbox to the parameter aidbox
     * @throws AidBoxException - If the aidbox is null
     */
    @Override
    public double getDuration(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("Aidbox cannot be null");
        }

        return this.locations[searchLocation(aidbox.getCode())].getDuration();
    }

    /**
     * Getter for the distance between current aidbox to the base
     *
     * @param name - The name of the base
     * @return - the distance between current aidbox to the base
     * @throws NullPointerException - if the string is null
     */
    public double getDistaceBase(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException("String is null");
        }

        for (int i = 0; i < this.nLocations; i++) {
            if (name.equals(this.locations[i].getCode())) {
                return this.locations[i].getDistance();
            }
        }
        return -1.0;
    }

    /**
     * Getter for the duration between current aidbox to the base
     *
     * @param name - The name of the base
     * @return - the duration between current aidbox to the base
     * @throws NullPointerException - if the string is null
     */
    public double getDurationBase(String name) throws NullPointerException {
        if (name == null) {
            throw new NullPointerException("String is null");
        }

        for (int i = 0; i < this.nLocations; i++) {
            if (name.equals(this.locations[i].getCode())) {
                return this.locations[i].getDuration();
            }
        }
        return -1.0;
    }

    /**
     * This method adds a new container to the aidbox.
     *
     * @param cntnr - The container to add to the aidbox
     * @return - True the addition was successful, False if the aidbox already
     * has that container
     * @throws ContainerException - if the container is null or if container
     * type already exist in the aidbox
     */
    @Override
    public boolean addContainer(Container cntnr) throws ContainerException {

        if (cntnr == null) {
            throw new ContainerException("This container is null");
        }

        for (int i = 0; i < this.nContainers; i++) {
            for (int j = i + 1; j < this.nContainers; j++) {
                if (this.containers[i].getType().equals(this.containers[j].getType())) {
                    throw new ContainerException("Container type already exist in the aidbox");
                }
            }
        }

        if (hasSameContainer(cntnr)) {
            return false;
        }

        if (this.nContainers == this.containers.length) {
            expandContainerArray();
        }

        this.containers[this.nContainers++] = cntnr;

        return true;
    }

    /**
     * This method adds a new location to the aidbox.
     *
     * @param loc - The location i want to add
     * @throws LocationException - If the location is null or if already exist a
     * location exactly like that
     */
    public void addLocation(Location loc) throws LocationException {
        if (loc == null) {
            throw new LocationException("Location cannot be null");
        }

        if (hasSameLocation(loc)) {
            throw new LocationException("Already exist a location exactly like that");
        }

        if (this.nLocations == this.locations.length) {
            expandLocationArray();
        }

        this.locations[this.nLocations++] = loc;
    }

    /**
     * This method removes a container from the current aidbox
     *
     * @param cntnr - The container to remove
     * @throws AidBoxException - If the to container does not exists . If to or
     * from parameter is null
     */
    @Override
    public void removeContainer(Container cntnr) throws AidBoxException {
        if (cntnr == null) {
            throw new AidBoxException("This container cannot be null");
        }

        if (this.locations == null) {
            throw new AidBoxException("FROM cannot be null");
        }

        for (int i = 0; i < this.nLocations; i++) {
            if (this.containers[i] == null) {
                throw new AidBoxException("to number : " + i + "is null");
            }
        }

        if (!hasSameContainer(cntnr)) {
            throw new AidBoxException("The container to remove doesnt exit");
        }

        this.containers[searchContainer(cntnr)] = this.containers[this.nContainers];
        this.containers[this.nContainers--] = null;
    }

    /**
     * Creates and returns a copy of this AidBox.
     *
     * This method performs a deep copy of the AidBox by cloning the containers
     * array.
     *
     * @return A cloned instance of this AidBox.
     * @throws CloneNotSupportedException If the object's class does not support
     * the Cloneable interface.
     */
    @Override
    public AidBox clone() throws CloneNotSupportedException {
        try {
            AidBoxImp cloned = (AidBoxImp) super.clone();
            cloned.containers = this.containers.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e.getMessage());
        }
    }

}
