/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import Core.AidBoxImp;
import Core.ContainerImp;
import Core.ContainerTypeImp;
import Core.InstitutionImp;
import Exceptions.LocationException;
import Io.Exporter;
import Io.ImporterImp;
import PickingManagement.Capacity;
import PickingManagement.PickingMapImp;
import PickingManagement.RouteGeneratorImp;
import PickingManagement.VehicleImp;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.core.exceptions.VehicleException;
import java.util.Scanner;
import com.estg.io.Importer;
import com.estg.pickingManagement.RouteGenerator;
import com.estg.pickingManagement.Vehicle;
import java.io.IOException;
import org.json.simple.parser.ParseException;



public class Menu {

    private Institution instn = null;
    private Scanner scanner;

    public Menu() {

    }

    public Menu(Institution instn) {
        this.instn = instn;
    }

    /**
     * This is the main menu
     */
    public void mainMenu() throws PickingMapException, MeasurementException {
        int option;
        this.scanner = new Scanner(System.in);
        do {
            if (this.instn == null) {
                option = this.institutionMenu();
            } else {
                option = this.generalMenu();
            }
        } while (option != 0);
        this.scanner.close();
    }

    /**
     * Displays the institution creation menu and handles user input for
     * creating or importing an institution.
     *
     * @return The chosen option. 0 to exit, 1 to create an institution, 2 to
     * import an institution.
     *
     */
    private int institutionMenu() throws MeasurementException {
        int option;
        
        System.out.println("Institution creation menu:");
        System.out.println("1. Create institution");
        System.out.println("2. Import institution");
        System.out.println("0. Exit");
        System.out.print("Choose a option: ");
        option = this.scanner.nextInt();

        switch (option) {
            case 1:
                System.out.println("You chose option 1");
                this.createInstitution();
                break;
            case 2:
                System.out.println("You chose option 2");
                try {
                    this.importInstitution();
                } catch (IOException e) {
                    System.out.println("The files were not found: " + e.getMessage());
                    return 2;
                } catch (ParseException e) {
                    System.out.println("The files could not be read: " + e.getMessage());
                    return 2;
                } catch (InstitutionException e) {
                    System.out.println("The instution couldnot be created: " + e.getMessage());
                }
                System.out.println("The institution was successfully imported");
                break;

            case 0:
                System.out.println("Leaving...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
                break;
        }
        return option;
    }

    /**
     * Prompts the user to input the name of the institution and creates a new
     * Institution object with the provided name.
     */
    private void createInstitution() {
        String name;

        System.out.print("Insert the institution's name: ");
        name = this.scanner.next();
        this.instn = new InstitutionImp(name);
    }

    /**
     *
     * Imports an institution from a file and assigns it to the current
     * instance.
     *
     * @throws IOException if there is an issue reading the file.
     * @throws ParseException if there is an issue parsing the file content.
     * @throws InstitutionException if there is an issue creating the
     * Institution object.
     */
    private void importInstitution() throws IOException, ParseException, InstitutionException, MeasurementException {
        Importer imp = new ImporterImp("FILE");
        this.instn = ((ImporterImp)imp).importInstitution();
    }

    /**
     * Displays and handles the general menu options for the application.
     *
     * @return the selected menu option
     */
    private int generalMenu() throws PickingMapException {
        Scanner scanner = new Scanner(System.in);

        int option;

        System.out.println("General menu:");
        System.out.println("1. Create");
        System.out.println("2. Generate routes");
        System.out.println("3. Import");
        System.out.println("4. Save");
        System.out.println("0. Exit");
        System.out.print("Escolha uma opção: ");


        option = this.scanner.nextInt();


        switch (option) {
            case 1:
                System.out.println("You chose option 1");
                this.creationMenu();
                break;
            case 2:
                System.out.println("You chose option 2");
                this.generateRoutes();
                break;
            case 3:
                System.out.println("You chose option 4");
                this.importDataMenu();
                break;
            case 4:
                System.out.println("You chose option 5");
                this.saveData();
                break;
            case 0:
                System.out.println("Leaving...");
                break;
            default:
                System.out.println("Invalid option. Try again.");
                break;
        }

        return option;
    }

    private void generateRoutes() throws PickingMapException {
        RouteGenerator routeGenerator = new RouteGeneratorImp();
        PickingMapImp pkmp = new PickingMapImp (routeGenerator.generateRoutes(this.instn));
        this.instn.addPickingMap(pkmp);
    }
    /**
     * Displays and handles the creation menu options for the application.
     * Allows the user to create AidBoxes, Containers, and Vehicles.
     */
    private void creationMenu() {
        int option;

        do {
            System.out.println("Creation menu:");
            System.out.println("1. AidBox");
            System.out.println("2. Container");
            System.out.println("3. Vehicle");
            System.out.println("0. Exit");
            System.out.print("Choose a option: ");
            option = this.scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("You chose option 1");
                    try {
                        this.createAidBox();
                    } catch (AidBoxException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("You chose option 2");
                    try {
                        this.createContainer();
                    } catch (AidBoxException | ContainerException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("You chose option 3");
                {
                    try {
                        createVehicle();
                    } catch (VehicleException ex) { 
                    }
                }
                    break;

                case 0:
                    System.out.println("Leaving...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        } while (option != 0);
    }

    /**
     *
     * @Creates an AidBox with the provided information and adds it to the
     * institution.
     *
     * @throws AidBoxException if the AidBox with the given code already exists.
     */
    private void createAidBox() throws AidBoxException {
        String code;
        String zone;
;

        System.out.print("Insert aidbox's code: ");
        code = this.scanner.next();
        System.out.print("Insert aidbox's zone: ");
        zone = this.scanner.next();


        if (!this.instn.addAidBox(new AidBoxImp(code, zone))) {
            throw new AidBoxException("This aidbox already exists");
        }

        // this.createDistances(code);
    }


    /**
     *
     * Creates a distance between two specified AidBoxes and adds it to the
     * institution's distances.
     *
     * @param code the code of the first AidBox.
     * @param code1 the code of the second AidBox.
     *
     * @throws AidBoxException if there is an issue with the AidBox.
     * @throws DistanceException if there is an issue with the distance.
     */
    private void createDistance(String code, String code1) throws AidBoxException, LocationException {

        System.out.print("Insert the distance to the aidbox " + code1 + ": ");
        double distance = this.scanner.nextDouble();
        System.out.print("Insert the duration to the aidbox " + code1 + ": ");
        double duration = this.scanner.nextDouble();

        //((InstitutionImp)this.instn).addDistance(code, new Location(code1, distance, duration));
        //this.instn.addDistance(code1, new Location(code, distance, duration));

    }

    /**
     *
     * Creates a container with the specified code and capacity and adds it to
     * the institution's containers.
     *
     * @throws ContainerException if the container already exists.
     * @throws AidBoxException if there is an issue with the AidBox.
     */
    

    private void createContainer() throws ContainerException, AidBoxException {
        String aidboxCode;
        String code;
        String type;
        double maxCapacity;

        System.out.println("Insert the container's code: ");
        code = this.scanner.next();
        System.out.println("Insert the container's max capacity: ");
        maxCapacity = this.scanner.nextDouble();
        System.out.println("AVAILABLES TYPES:");
        for(int i = 0; i < ContainerTypeImp.getTypes().length; i++){
            System.out.println(ContainerTypeImp.getTypes()[i]);
        }
          System.out.println("Write the one of the types that exists. If you type it wrong , it "
                  + "will get a default type Unknown");
          type = this.scanner.next();
        Container container = new ContainerImp(code, maxCapacity, new ContainerTypeImp(type)); /// CORRIGIRIEWASFEAfdaf


        System.out.print("Insert the aibox code to insert the container: ");
        aidboxCode = this.scanner.next();
        ((InstitutionImp)this.instn).addContainer(aidboxCode, container);
    }
    
    
    /**
     * Creates a vehicle with the specified maximum capacity and adds it to the
     * institution's vehicles.
     *
     * @throws VehicleException if there is an issue with the vehicle.
     */
    private void createVehicle() throws VehicleException {
        String code;
        int nTypes;
        
        

        System.out.println("Insert the container's code: ");
        code = this.scanner.next();  
        
        System.out.println("How many types you want to insert? MAX:" + ContainerTypeImp.getTypes().length );
        System.out.println("If number above of max , DEFAULT MAX SET TO" + ContainerTypeImp.getTypes().length);
        nTypes = this.scanner.nextInt();
        if ( nTypes > ContainerTypeImp.getTypes().length){
            nTypes = ContainerTypeImp.getTypes().length;
        }
        
        System.out.println("Select the following types:");
        for(int i = 0; i < ContainerTypeImp.getTypes().length; i++){
            System.out.println(ContainerTypeImp.getTypes()[i]);
        }
        
        Capacity[] capacity = new Capacity[nTypes];
        
        for(int i = 0; i < nTypes ; i++){
            String type = "";
            double maxCapacity;
            System.out.println("Write the one of the types that exists. If you type it wrong (CASE-SENSITIVE) , it "
                  + "will get a default type Unknown");
            type = this.scanner.next();
            System.out.println("Select the max capacity for that type");
            maxCapacity = this.scanner.nextDouble();
            capacity[i] = new Capacity(new ContainerTypeImp(type), maxCapacity);
        }
        

        Vehicle vehicle = new VehicleImp(code, capacity); 
        this.instn.addVehicle(vehicle); 

    }

    /**
     * Allows the user to choose between creating a regular vehicle or a
     * refrigerated vehicle.
     *
     * @throws VehicleException if there is an issue with creating the vehicle.
     */
    private void chooseVehicle() throws VehicleException {

        int option;

        do {
            System.out.println("Choose vehicle");
            System.out.println("1. Vehicle");
            System.out.println("0. Exit");
            System.out.print("Choose a option: ");
            option = this.scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("You chose option 1");
                    this.createVehicle();
                    break;
                case 0:
                    System.out.println("Leaving...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        } while (option != 0);
    }

    /**
     * Imports data into the institution using the provided importer.
     *
     * @param importer The importer object used to import data.
     */
    private void importData(Importer importer) {
        try {
            importer.importData(this.instn);
            System.out.println("Data imported successfully.");
        } catch (IOException | InstitutionException e) {
            System.out.println("Failed to import data: " + e.getMessage());
        }

    }

    /**
     * Displays a menu for importing data into the institution. Allows importing
     * from file, web API, importing vehicles, or importing reports. The user
     * can choose an option from the menu until choosing to exit.
     */
    private void importDataMenu() {
        int option;

        do {
            System.out.println("Import data menu:");
            System.out.println("1. Import from file");
            System.out.println("2. Import from Web API");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            option = this.scanner.nextInt();

            Importer importer;
            switch (option) {
                case 1:
                    importer = new ImporterImp("FILE");
                    System.out.println("You choose to import from file");
                    this.importData(importer);
                    break;
                case 2:
                    importer = new ImporterImp("WEB");
                    System.out.println("You choose to import from the web API");
                    this.importData(importer);
                    break;
                case 0:
                    System.out.println("Leaving...");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }

        } while (option != 0);
    }

    /**
     * Saves the institution data to a file using an Exporter object. Displays a
     * success message if the save operation is successful.
     */
    private void saveData() {
        Exporter exporter = new Exporter(this.instn);

        try {
            exporter.save();
            System.out.println("The institution was saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save the institution: " + e.getMessage());
        }
    }


}
