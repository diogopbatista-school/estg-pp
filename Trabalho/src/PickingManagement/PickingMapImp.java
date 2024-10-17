
package PickingManagement;

import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Route;
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
 * The PickingMapImp class provides functionalities to manage an PickingMap's
 * date,and Routes.
 *
 * @author Diogo e Ruben
 */

public class PickingMapImp implements PickingMap{
    /**
     * The date of this picking map
     */
    private LocalDateTime date;
    
    /**
     * The array of routes
     */
    private Route[] routes;

    /**
     * Constructor for this picking map
     * @param routes - The array of routes i want to insert inside this 
     * picking map
     */
    public PickingMapImp(Route[] routes) {
        this.date = LocalDateTime.now();
        this.routes = routes;
    }

    /**
     * Getter for the date of the PickingMap
     * 
     * @return the date of the PickingMap
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Getter for the routes of the PickingMap
     * 
     * @return the routes of the PickingMap
     */
    @Override
    public Route[] getRoutes() {
        return this.routes;
    }
    
}
