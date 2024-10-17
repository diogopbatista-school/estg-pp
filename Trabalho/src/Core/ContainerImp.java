/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Core;

import com.estg.core.Container;
import com.estg.core.ContainerType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDate;
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
 *
 * The ContainerImp class provides functionalities to manage a container's code,
 * max capacity, type, and measuremets.
 *
 * @author Diogo e Ruben
 */
public class ContainerImp implements Container {

    /**
     * The value to start arrays
     */
    private static int INICIALIZE_ARRAY = 2;

    /**
     * The value for expanding arrays dynamically
     */
    private static int EXPAND_ARRAY = 2;

    /**
     * The container's code
     */
    private String code;

    /**
     * The container´s max capacity
     */
    private double maxCapacity;

    /**
     * The container's type
     */
    private ContainerType type;

    /**
     * The container's measurements
     */
    private Measurement[] measurements;

    /**
     * The container's number of measurements
     */
    private int nMeasurements;

    /**
     * The date of the object's creation
     */
    private LocalDate creationDate;

    /**
     * Constructs a new container with the specified code, max capacity and
     * type.
     *
     * @param code - the code of the container
     * @param maxCapacity - the capacity of the container
     * @param type - the type of the container
     */
    public ContainerImp(String code, double maxCapacity, ContainerTypeImp type) {
        this.code = code;
        this.maxCapacity = maxCapacity;
        this.type = type;
        this.measurements = new Measurement[INICIALIZE_ARRAY];
        this.nMeasurements = 0;
        this.creationDate = LocalDate.now();
    }

    /**
     * Getter for the last measurement of this container
     *
     * @return - The last measurement . -1 if container doesnt have measurements
     */
    public double getLastMeasurement() {
        if (this.nMeasurements != 0) {
            return this.measurements[this.nMeasurements - 1].getValue();
        }
        return -1;
    }

    /**
     * Getter for the container's code
     *
     * @return - The container's code
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Getter for the max capacity of the container
     *
     * @return - the max capacity
     */
    @Override
    public double getCapacity() {
        return this.maxCapacity;
    }

    /**
     * Getter for container's type
     *
     * @return - The container's type
     */
    @Override
    public ContainerType getType() {
        return this.type;
    }

    /**
     * Getter of the container's all measurements.
     *
     * @return - All the measurements of the container
     */
    @Override
    public Measurement[] getMeasurements() {
        Measurement[] aux = new Measurement[this.nMeasurements];

        for (int i = 0; i < this.nMeasurements; i++) {
            aux[i] = this.measurements[i];
        }

        return aux;
    }

    /**
     * Getter of one specific measurement based on a date
     *
     * @param ld - The LocalDate to search
     * @return - The measurement with a specific LocalDate
     */
    @Override
    public Measurement[] getMeasurements(LocalDate ld) {

        int count = 0;
        for (int i = 0; i < this.nMeasurements; i++) {
            if (this.measurements[i].getDate().toLocalDate().equals(ld)) {
                count++;
            }
        }

        Measurement[] newMeasurements = new Measurement[count];
        int index = 0;
        for (int i = 0; i < this.nMeasurements; i++) {
            if (this.measurements[i].getDate().toLocalDate().equals(ld)) {
                newMeasurements[index++] = this.measurements[i];
            }
        }
        return newMeasurements;
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
     * This method expands the size of the array of measurements.
     */
    private void expandMeasurements() {
        Object[] aux = new Measurement[this.nMeasurements * EXPAND_ARRAY];
        aux = expandArray(this.measurements, this.nMeasurements, aux);
        this.measurements = (Measurement[]) aux;
    }

    /**
     * This method adds a new measurement to the container
     *
     * @param msrmnt - The measurement to add
     * @return - True the addition was successful,fase if already exists for a
     * given date
     * @throws MeasurementException - if the measurement is null if the value is
     * lesser than 0 if the date is before the existing last measurement date if
     * the for a given date the measurement already exists but the values are
     * different
     */
    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {

        if (msrmnt == null) {
            throw new MeasurementException("Measurement cannot be null");
        }
        if (msrmnt.getValue() < 0) {
            throw new MeasurementException("The measurement value is less then 0");
        }

        for (int i = 0; i < this.nMeasurements; i++) {
            if (this.nMeasurements > 0 && msrmnt.getDate().isBefore(this.measurements[i].getDate())) {
                throw new MeasurementException("Measurement cannot be before the last measurement");
            }

            if (msrmnt.getDate().toLocalDate().isEqual(this.measurements[i].getDate().toLocalDate())
                    && msrmnt.getValue() != this.measurements[i].getValue()) {
                throw new MeasurementException("There's already a measurement for this measurement's date");
            }

            if (this.measurements[i].getDate().equals(msrmnt.getDate())) {
                return false;
            }
        }

        if (this.nMeasurements == this.measurements.length) {
            expandMeasurements();
        }

        this.measurements[this.nMeasurements++] = msrmnt;

        return true;

    }

    /**
     * Compares this object to the specified object for equality.
     *
     * @param obj the object to be compared for equality with this object
     * @return {@code true} if the specified object is equal to this object;
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
        final ContainerImp other = (ContainerImp) obj;
        return Objects.equals(this.code, other.code);
    }

    /**
     * Creates and returns a copy of this object.
     *
     * @return a clone of this instance
     * @throws CloneNotSupportedException if the object's class does not support
     * the Cloneable interface
     */
    @Override
    public Container clone() throws CloneNotSupportedException {
        try {
            return (ContainerImp) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e.getMessage());
        }
    }

}
