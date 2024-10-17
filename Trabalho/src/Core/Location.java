
package Core;

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
 * The Location class provides functionalities to manage an location's code , 
 * distance and duration
 *
 * @author Diogo e Ruben
 */
public class Location {
    
    /**
     * The from location code
     */
    private final String code;
    
    /**
     * The distance to
     */
    private double distance;
    
    /**
     * The duration to
     */
    private double duration;

    /**
     * Constructs a new Location with the specified code,duration and duration.
     *
     * @param code - the code correspond to a an aid box
     * @param distance - the distance
     * @param duration - the duration
     *
     */
    public Location(String code, double distance, double duration) {
        this.code = code;
        this.distance = distance;
        this.duration = duration;
    }

    /**
     * Getter for the code
     *
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Getter for the distance
     *
     * @return the distance
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * Getter for the duration
     *
     * @return the duration
     */
    public double getDuration() {
        return this.duration;
    }
}
