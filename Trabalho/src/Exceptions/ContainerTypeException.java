
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


public class ContainerTypeException extends Exception {
    public ContainerTypeException() {
    }

    /**
     * Constructor with message
     *
     * @param message custom message
     */
    public ContainerTypeException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     *
     * @param message custom message
     * @param cause cause
     */
    public ContainerTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
