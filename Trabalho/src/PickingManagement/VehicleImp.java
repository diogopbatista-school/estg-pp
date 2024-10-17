
package PickingManagement;

import com.estg.core.ContainerType;
import com.estg.pickingManagement.Vehicle;
import java.util.Arrays;
import java.util.Objects;

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
 * The class VehicleImp provides functionality to define the code,capacity and 
 * status of the vehicle
 * 
 * @author Diogo e Ruben
 */

public class VehicleImp implements Vehicle,Cloneable{
    
    /**
     * The vehicle's code
     */
    private String code;
    
    /**
     * All capacity of the vehicle
     */
    private Capacity[] capacity;
    
    /**
     * The number of capacitys
     */
    private int nCapacity;
    
    /**
     * The status of the vehicle
     */
    private boolean status;

    /**
     * Constructor for the vehicle 
     * 
     * @param code - The code of the vehicle
     * @param capacity - The capacitys of the vehicle
     */
    public VehicleImp(String code, Capacity[] capacity) {
        this.code = code;
        this.capacity = capacity;
        this.nCapacity = capacity.length;
        this.status = true;
    }

    /**
     * Getter for the vehicle's code
     * 
     * @return - The vehicle's code
     */
    @Override
    public String getCode() {
        return code;
    }
    
    /**
     * Getter for all vehicle's capacitys.
     * 
     * @return - All capacitys of the vehicle
     */
    public Capacity[] getCapacitys(){
        Capacity[] aux = new Capacity[this.nCapacity];
        for ( int i = 0 ; i < this.nCapacity;i++){
            aux[i] = this.capacity[i];
        }
        return aux;
    }
    
    /**
     * Getter for the capacity from a specific ContainerType
     * 
     * @param ct - The containerType i want to get
     * @return - The capacity with the argument ContainerType.
     * Null if the vehicle doesnt have that containerType
     */
    public Capacity getContainerType(ContainerType ct){
        for ( int i = 0 ; i < this.nCapacity; i++){
            if(this.capacity[i].getType().equals(ct)){
                return this.capacity[i];
            }
        }
        return null;
    }
    
    /**
     * Getter for the capacity from a specific string
     * 
     * @param type - The string i want to get
     * @return - The capacity with the argument ContainerType.
     * Null if the vehicle doesnt have that containerType
     */
    public Capacity getContainerType(String type){
        for ( int i = 0 ; i < this.nCapacity; i++){
            if(this.capacity[i].getType().getType().equals(type)){
                return this.capacity[i];
            }
        }
        return null;
    }
    
    /**
     * Getter for all ContainerTypes inside the vehicle
     * 
     * @return - All ContainerTypes inside the vehicle
     */
    public ContainerType[] getContainersTypes(){
        ContainerType[] aux = new ContainerType[this.nCapacity];
        for ( int i = 0 ; i < this.nCapacity;i++){
            aux[i] = this.capacity[i].getType();
        }
        return aux;
    }
    
    /**
     * Getter for a max capacity of a prioritary ContainerType with a specific 
     * string
     * 
     * @param prio - The string priority i wanna get
     * @return - The max capacity of the priority capacity
     */
    public double getCapacity(String prio){
        for ( int i = 0 ; i < this.nCapacity; i++){
            if(this.capacity[i].getType().getType().equals(prio)){
                return this.capacity[i].getMaxCapacity();
            }
        }
        return -1.0;
    }

    /**
     * Getter for a capacity from a specific ContainerType
     * 
     * @param ct - The containerType reference
     * @return - The max capacity for that ContainerType
     */
    @Override
    public double getCapacity(ContainerType ct){
        for ( int i = 0 ; i < this.nCapacity; i++){
            if(this.capacity[i].getType().equals(ct)){
                return this.capacity[i].getMaxCapacity();
            }
        }
        return -1.0;
    }
    
    /**
     * Getter for the vehicle's status
     * 
     * @return - the vehicle's Status
     */
    public boolean getStatus(){
        return this.status;
    }
    
    /**
     * Setter for a vehicle's status
     * 
     * @param st - the status i wanna change for the vehicle
     */
    public void setStatus(boolean st) {
        this.status = st;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VehicleImp other = (VehicleImp) obj;
        if (this.nCapacity != other.nCapacity) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return Arrays.deepEquals(this.capacity, other.capacity);
    }
    
    
    
    @Override
    public Vehicle clone() throws CloneNotSupportedException{
        try{
            Vehicle cloned = (Vehicle) super.clone();
            return cloned;
        }
        catch(CloneNotSupportedException e){
            throw new AssertionError(e.getMessage());
        }
    }
    
    public void setEmptyContainers(String type, double number){
        for ( int i = 0 ; i < this.nCapacity; i++){
            if(this.capacity[i].getType().getType().equals(type)){
                this.capacity[i].setEmptyContainers(number);
            }
        }
        
    }
}
