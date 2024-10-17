package PickingManagement;

import Core.ContainerTypeImp;
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
 * The Capacity class provides functionalities to manage an Capacity's
 * type,maxCapacity and currentCapacity;
 *
 * @author Diogo e Ruben
 */
public class Capacity {

    /**
     * The capacity type
     */
    private ContainerTypeImp type;

    /**
     * The capacity max limit
     */
    private double maxCapacity;
    
    private double emptyContainers;

    /**
     * The current capacity
     */
    private double currentCapacity;

    /**
     * Constuctor for the capacity
     *
     * @param type - the container type of the capacity
     * @param number - the amount of max limit
     */
    public Capacity(ContainerTypeImp type, double number) {
        this.type = type;
        this.maxCapacity = number;
        this.currentCapacity = 0;
        this.emptyContainers = 0;
    }

    /**
     * Getter for the type of the capacity
     *
     * @return - the container type of the capacity
     */
    public ContainerTypeImp getType() {
        return this.type;
    }

    /**
     * Getter for the max limit of the capacity
     *
     * @return - The max capacity
     */
    public double getMaxCapacity() {
        return this.maxCapacity;
    }

    /**
     * Getter for the current capacity
     *
     * @return - The current capacity
     */
    public double getCurrentCapacity() {
        return this.currentCapacity;
    }

    /**
     * This method adds one to the current capacity and cannot surpass the limit
     * for the capacity
     */
    public void setCurrentCapacity() {
        if (this.currentCapacity < this.maxCapacity) {
            this.currentCapacity++;
        }
    }
    
    public double getEmptyContainers(){
        return this.emptyContainers;
    }

    public void setEmptyContainers(double number){
        if(number <= this.maxCapacity && number >= 0){
            this.emptyContainers = number;
        }
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument;
     * {@code false} otherwise
     */
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
        final Capacity other = (Capacity) obj;
        if (Double.doubleToLongBits(this.maxCapacity) != Double.doubleToLongBits(other.maxCapacity)) {
            return false;
        }
        return Objects.equals(this.type, other.type);
    }

}
