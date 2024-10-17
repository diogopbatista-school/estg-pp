
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

public class ReportException extends Exception {

    public ReportException() {
    }

    /**
     * Constructor with message
     *
     * @param message custom message
     */
    public ReportException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause
     *
     * @param message custom message
     * @param cause cause
     */
    public ReportException(String message, Throwable cause) {
        super(message, cause);
    }
}
