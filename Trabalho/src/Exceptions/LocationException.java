
package Exceptions;

/*
* Nome: Diogo Pereira Batista
* Número: 8230367
* Turma: LSIRC T1
*
* Nome: Rúben da Silva Uth
* Número: 8210481
* Turma: LSIRC T2
 */

public class LocationException extends Exception{

    public LocationException() {
    }

    /**
     * Constructor with message
     *
     * @param message custom message
     */
    public LocationException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     *
     * @param message custom message
     * @param cause cause
     */
    public LocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
