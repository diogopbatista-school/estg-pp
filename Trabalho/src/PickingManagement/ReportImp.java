/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PickingManagement;

import com.estg.pickingManagement.Report;
import java.time.LocalDateTime;

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
 * Represents a report containing statistics and details about picking
 * operations. Provides methods to retrieve and set various attributes of the
 * report, such as the number of used vehicles, picked containers, total
 * distance , total duration, non-picked containers, not used vehicles, and date
 * of the report.
 *
 * @author Diogo e Ruben
 */
public class ReportImp implements Report {

    /**
     * The amount of used vehicles
     */
    private int usedVehicles;
    
    /**
     * The amount of picked containers
     */
    private int pickedContainers;
    
    /**
     * The total distance
     */
    private double totalDistance;
    
    /**
     * The total duratin
     */
    private double totalDuration;
    
    /**
     * The amount of non picked containers
     */
    private int nonPickedContainers;
    
    /**
     * The amount of not used vehicles
     */
    private int notUsedVehicles;
    
    /**
     * The date of the report
     */
    private LocalDateTime date;

    /**
     * Constructs a new ReportImp instance with the specified parameters.
     *
     * @param usedVehicles - the number of vehicles that were used
     * @param pickedContainers - the number of containers that were picked
     * @param totalDistance - the total distance covered
     * @param totalDuration - the total duration of the operation
     * @param nonPickedContainers - the number of containers that were not picked
     * @param notUsedVehicles - the number of vehicles that were not used
     * @param date - the date of the report
     */
    public ReportImp(int usedVehicles, int pickedContainers, double totalDistance, double totalDuration, int nonPickedContainers, int notUsedVehicles, LocalDateTime date) {
        this.usedVehicles = usedVehicles;
        this.pickedContainers = pickedContainers;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
        this.nonPickedContainers = nonPickedContainers;
        this.notUsedVehicles = notUsedVehicles;
        this.date = date;
    }

    /**
     * Getter for the number of used vehicles
     *
     * @return the number of used vehicles
     */
    @Override
    public int getUsedVehicles() {
        return this.usedVehicles;
    }

    /**
     * Setter for the number of used vehicles
     *
     * @param usedVehicles - the number of used vehicles
     */
    public void setUsedVehicles(long usedVehicles) {

        this.usedVehicles = (int) usedVehicles;
    }

    /**
     * Getter for the number of picked containers
     *
     * @return the number of picked containers
     */
    @Override
    public int getPickedContainers() {
        return this.pickedContainers;
    }

    /**
     * Setter for the number of picked containers
     *
     * @param pickedContainers - the number of picked containers
     */
    public void setPickedContainers(long pickedContainers) {
        this.pickedContainers = (int) pickedContainers;
    }

    /**
     * Getter for the total distance covered by the vehicles
     *
     * @return the total distance covered by the vehicles
     */
    @Override
    public double getTotalDistance() {
        return this.totalDistance;
    }

    /**
     * Setter for the total distance covered by the vehicles
     *
     * @param totalDistance - the total distance covered by the vehicles
     */
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * Getter for the total duration of the routes
     *
     * @return the total duration of the routes
     */
    @Override
    public double getTotalDuration() {
        return this.totalDuration;
    }

    /**
     * Setter for the total duration of the routes
     *
     * @param totalDuration -the total duartion of the routes
     */
    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;

    }

    /**
     * Getter for the number of non-picked containers
     *
     * @return the number of non-picked containers
     */
    @Override
    public int getNonPickedContainers() {
        return this.nonPickedContainers;
    }

    /**
     * Setter for the number of non-picked containers
     *
     * @param nonPickedContainers the number of non-picked containers
     */
    public void setNonPickedContainers(long nonPickedContainers) {
        this.nonPickedContainers = (int) nonPickedContainers;
    }

    /**
     * Getter for the number of not used vehicles
     *
     * @return the number of not used vehicles
     */
    @Override
    public int getNotUsedVehicles() {
        return this.notUsedVehicles;
    }

    /**
     * Getter for the number of not used vehicles
     *
     * @param notUsedVehicles the number of not used vehicles
     */
    public void setNotUsedVehicles(long notUsedVehicles) {
        this.notUsedVehicles = (int) notUsedVehicles;
    }

    /**
     * Getter for the date of the report
     *
     * @return the date of the report
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Getter for the date of the report
     *
     * @param date the date of the report
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
