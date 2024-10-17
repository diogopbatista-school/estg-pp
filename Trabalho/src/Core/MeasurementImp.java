package Core;

import com.estg.core.Measurement;
import java.time.LocalDate;
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
 * The MeasurementImp class provides functionalities to manage an Measurement's
 * date,value.
 *
 * @author Diogo e Ruben
 */
public class MeasurementImp implements Measurement, Cloneable {

    /**
     * The value in kilos
     */
    private final double value;

    /**
     * The date of the measurement
     */
    private final LocalDateTime date;

    /**
     * The date of the object's creation
     */
    private final LocalDate creationDate;

    /**
     * Constructor for the measurement
     *
     * @param value value in kgs
     * @param date the date
     */
    public MeasurementImp(double value, LocalDateTime date) {
        this.value = value;
        this.date = date;
        this.creationDate = LocalDate.now();
    }

    /**
     * Gets the date
     *
     * @return LocalDateTime - the date
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Gets the value in kilos
     *
     * @return double - the value
     */
    @Override
    public double getValue() {
        return this.value;
    }

    /**
     * Creates and returns a copy (clone) of this object.
     *
     * @return a clone of this instance
     * @throws CloneNotSupportedException if the object's class does not support
     * the Cloneable interface
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
