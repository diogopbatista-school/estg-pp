
package Core;

import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.PickingMapException;
import menu.Menu;

/**
 *
 * @author diogo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PickingMapException, MeasurementException {
        Menu menu = new Menu();
        menu.mainMenu();
        
    }
    
}
