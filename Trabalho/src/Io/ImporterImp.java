/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Io;

import Core.AidBoxImp;
import Core.ContainerImp;
import Core.ContainerTypeImp;
import Core.InstitutionImp;
import Core.Location;
import Core.MeasurementImp;
import Exceptions.ContainerTypeException;
import Exceptions.LocationException;
import Exceptions.ReportException;
import PickingManagement.Capacity;
import PickingManagement.ReportImp;
import PickingManagement.VehicleImp;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.VehicleException;
import com.estg.io.HTTPProvider;
import com.estg.io.Importer;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author diogo
 */
public class ImporterImp implements Importer {

    private static final String PATH_INSTITUTION = "institution.json";
    private static final String PATH_VEHICLES = "vehicles.json";
    private static final String PATH_REPORTS = "reports.json";
    private static final String PATH_AIDBOXES = "aidBoxes.json";
    private static final String PATH_DISTANCES = "distances.json";
    private static final String PATH_READINGS = "readings.json";
    private static final JSONParser PARSER = new JSONParser();
    private static final HTTPProvider HTTP_PROVIDER = new HTTPProvider();
    private final String importType;
    private JSONObject jsonInstitution;
    private JSONArray jsonVehicles;
    private JSONArray jsonReports;
    private JSONArray jsonAidBoxes;
    private JSONArray jsonLocations;
    private JSONArray jsonReadings;
    private JSONArray jsonContainerTypes;
    private JSONArray jsonContainers;

    public ImporterImp(String type) {
        this.importType = type;
    }

    @Override
    public void importData(Institution instn) throws FileNotFoundException, IOException, InstitutionException {
        if (instn == null) {
            throw new InstitutionException("The institution in null");
        }
        try {
            switch (importType) {
                case "WEB":
                    importContainerTypesWebApi();
                    importVehicleWebApi(instn);
                    importContainerWebApi(instn);
                        try {
                            importReadingsWebApi(instn);
                        } catch (MeasurementException ex) {
                            Logger.getLogger(ImporterImp.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    importAidBoxesWebApi(instn);
                    importLocationsWebApi(instn);
                    importStockContainers(instn);
                    break;

                case "FILE": {
                    try {
                        this.importaFiles(instn);
                    } catch (MeasurementException ex) {
                        Logger.getLogger(ImporterImp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            }
        } catch (org.json.simple.parser.ParseException ex) {
            throw new IOException("The file could not be read");
        } catch (ContainerException ex) {
            Logger.getLogger(ImporterImp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Institution importInstitution() throws IOException, InstitutionException, org.json.simple.parser.ParseException, MeasurementException {
        this.jsonInstitution = (JSONObject) PARSER.parse(new String(Files.readAllBytes(Paths.get(PATH_INSTITUTION))));
        Institution institution = new InstitutionImp((String) jsonInstitution.get("Nome"));
        this.importaFiles(institution);
        this.importVehicles(institution);
        this.importReports(institution);
        return institution;
    }

    private void importaFiles(Institution instn) throws IOException, InstitutionException, org.json.simple.parser.ParseException, MeasurementException {
        this.jsonAidBoxes = (JSONArray) PARSER.parse(new String(Files.readAllBytes(Paths.get(PATH_AIDBOXES))));
        this.addAidBoxes(instn);
        this.jsonLocations = (JSONArray) PARSER.parse(new String(Files.readAllBytes(Paths.get(PATH_DISTANCES))));
        this.addLocations(instn);
        this.jsonReadings = (JSONArray) PARSER.parse(new String(Files.readAllBytes(Paths.get(PATH_READINGS))));
        this.addReadings(instn);
    }

    public void importVehicles(Institution instn) throws IOException, org.json.simple.parser.ParseException {
        this.jsonVehicles = (JSONArray) PARSER.parse(new String(Files.readAllBytes(Paths.get(PATH_VEHICLES))));
        this.addVehicles(instn);
    }

    public void importReports(Institution instn) throws IOException, org.json.simple.parser.ParseException {
        this.jsonReports = (JSONArray) PARSER.parse(new String(Files.readAllBytes(Paths.get(PATH_REPORTS))));
        this.addReports(instn);
    }

    public void importStockContainers(Institution instn) {
        Container[] allContainers = ((InstitutionImp) instn).getContainers();

        for (int i = 0; i < allContainers.length; i++) {
            Container aux = allContainers[i];
            if (((ContainerImp) aux).getLastMeasurement() == -1) {

            }
        }
    }

    private void addReports(Institution instn) throws org.json.simple.parser.ParseException {
        try {
            for (Object obj : this.jsonReports) {
                JSONObject jsonVehicle = (JSONObject) obj;

                ((InstitutionImp) instn).addReport(createReport(jsonVehicle));
            }
        } catch (ReportException e) {

        }
    }

    private Report createReport(JSONObject jsonReport) throws org.json.simple.parser.ParseException {
        LocalDateTime date = (LocalDateTime) jsonReport.get("date");
        int usedVhcl = (int) jsonReport.get("Used Vehicles");
        int nonUsedVhcl = (int) jsonReport.get("Non Used Vehicles");
        int pickedCntnr = (int) jsonReport.get("Picked Containers");
        int nonPickedCntnr = (int) jsonReport.get("Non Picked Containers");
        double totalDistance = (long) jsonReport.get("Total Distance");
        double totalDuration = (long) jsonReport.get("Total Duration");

        Report report = new ReportImp(usedVhcl, pickedCntnr, totalDistance, totalDuration, nonPickedCntnr, nonUsedVhcl, date);
        return report;
    }

    private Vehicle createVehicle(JSONObject jsonVehicle) throws InstitutionException, org.json.simple.parser.ParseException {
        String code = (String) jsonVehicle.get("code");

        JSONObject jsonCapacity = (JSONObject) jsonVehicle.get("capacity");
        String[] types = ContainerTypeImp.getTypes();
        Capacity[] capacity = new Capacity[jsonCapacity.size()];
        for (int i = 0; i < capacity.length; i++) {
            capacity[i] = new Capacity(new ContainerTypeImp(types[i]), (long) jsonCapacity.get(types[i]));
        }

        return (Vehicle) new VehicleImp(code, capacity);
    }

    private void importVehicleWebApi(Institution instn) throws org.json.simple.parser.ParseException {
        this.jsonVehicles = (JSONArray) PARSER.parse(HTTP_PROVIDER.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/vehicles"));
        this.addVehicles(instn);
    }

    private void addVehicles(Institution institution) throws org.json.simple.parser.ParseException {
        try {
            for (Object obj : this.jsonVehicles) {
                JSONObject jsonVehicle = (JSONObject) obj;

                institution.addVehicle(createVehicle(jsonVehicle));
            }
        } catch (InstitutionException | VehicleException e) {

        }
    }

    private void importAidBoxesWebApi(Institution instn) throws InstitutionException, org.json.simple.parser.ParseException {
        this.jsonAidBoxes = (JSONArray) PARSER.parse(HTTP_PROVIDER.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxes"));
        this.addAidBoxes(instn);
    }

    private void addAidBoxes(Institution instn) {
        try {
            for (Object obj : this.jsonAidBoxes) {
                JSONObject jsonAidBox = (JSONObject) obj;
                instn.addAidBox(createAidBox(jsonAidBox, instn));

            }
        } catch (InstitutionException | AidBoxException e) {

        }
    }

    private AidBox createAidBox(JSONObject jsonAidBox, Institution instn) throws InstitutionException {
        AidBox aidbox = new AidBoxImp((String) jsonAidBox.get("code"),
                (String) jsonAidBox.get("Zona"));
        JSONArray jsonContainers = (JSONArray) jsonAidBox.get("containers");
        try {
            for (Object objContainer : jsonContainers) {
                String container = (String) objContainer;
                boolean containerFound = false;
                Container[] aux = ((InstitutionImp) instn).getContainers();
                for (int i = 0; i < aux.length; i++) {
                    if (aux[i].getCode().equals(container)) {
                        aidbox.addContainer(aux[i]);
                        ((InstitutionImp)instn).removeContainer(aux[i]);
                    }
                }      
            }
        } catch (ContainerException e) {
            throw new InstitutionException("The container could not be added to the aidbox" + e.getMessage());
        }
        return aidbox;
    }

    private Container getContainer(AidBox[] aidboxes, String containerCode) throws org.json.simple.parser.ParseException {
        for (AidBox aidbox : aidboxes) {
            Container container = ((AidBoxImp) aidbox).getContainer(containerCode);
            if (container != null) {
                return container;
            }
        }
        return null;
    }

    private void importReadingsWebApi(Institution instn) throws org.json.simple.parser.ParseException, MeasurementException {
        this.jsonReadings = (JSONArray) PARSER.parse(HTTP_PROVIDER.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/readings"));
        this.addReadings(instn);
    }

    private void addReadings(Institution instn) throws org.json.simple.parser.ParseException, MeasurementException {
        for (Object obj : this.jsonReadings) {
            JSONObject jsonObject = (JSONObject) obj;

            ZonedDateTime zdt = ZonedDateTime.parse((String) jsonObject.get("data"),
                    DateTimeFormatter.ISO_DATE_TIME);

            Measurement msrmnt = new MeasurementImp((long) jsonObject.get("valor"), zdt.toLocalDateTime());
            Container add = this.getContainer(instn.getAidBoxes(), (String) jsonObject.get("contentor"));
            try {

                ((InstitutionImp)instn).addMeasurementToContainerArray(msrmnt, add);

            } catch (ContainerException | MeasurementException e) {
                ((InstitutionImp) instn).addErrorMeasurements(msrmnt);
            }
        }
    }

    public void importContainerTypesWebApi() throws org.json.simple.parser.ParseException {
        this.jsonContainerTypes = (JSONArray) PARSER.parse(HTTP_PROVIDER.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/types"));
        this.addContainerTypes();
    }

    private void addContainerTypes() {
        try {
            for (Object obj : this.jsonContainerTypes) {
                JSONObject jsonObject = (JSONObject) obj;
                JSONArray jsonTypes = (JSONArray) jsonObject.get("types");
                for (Object obj1 : jsonTypes) {
                    String type = (String) obj1;
                    ContainerTypeImp.addType(type);
                }
            }
        } catch (ContainerTypeException e) {

        }
    }

    private void importLocationsWebApi(Institution instn) throws org.json.simple.parser.ParseException {
        this.jsonLocations = (JSONArray) PARSER.parse(HTTP_PROVIDER.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=%7bcodigoOrigem%7d&to=%7bcodigoDestino%7d"));
        addLocations(instn);
    }

    private void addLocations(Institution instn) {
        for (Object obj : this.jsonLocations) {
            JSONObject jsonObject = (JSONObject) obj;
            String code = (String) jsonObject.get("from");
            JSONArray jsLocation = (JSONArray) jsonObject.get("to");
            try {
                ((InstitutionImp) instn).setLocations(code, createLocation(jsLocation));
            } catch (LocationException e) {

            }
        }
    }

    private Location[] createLocation(JSONArray jsonLocations) {
        Location[] locations = new Location[jsonLocations.size()];

        int i = 0;
        for (Object obj : jsonLocations) {
            JSONObject jsonLocation = (JSONObject) obj;
            if (jsonLocation != null) {
                locations[i] = new Location((String) jsonLocation.get("name"),
                        (long) jsonLocation.get("distance"), (long) jsonLocation.get("duration"));
            } else {
                locations[i] = null;
            }
            i++;
        }
        return locations;
    }

    private void importContainerWebApi(Institution instn) throws org.json.simple.parser.ParseException, ContainerException {
        this.jsonContainers = (JSONArray) PARSER.parse(HTTP_PROVIDER.getFromURL("https://data.mongodb-api.com/app/data-docuz/endpoint/containers"));
        addContainer(instn);
    }

    private Container createContainer(JSONObject jsonContainers) throws org.json.simple.parser.ParseException {
        String code = (String) jsonContainers.get("code");
        double maxCapacity = (long) jsonContainers.get("capacity");
        String type = (String) jsonContainers.get("type");

        Container container = new ContainerImp(code, maxCapacity, new ContainerTypeImp(type));

        return container;

    }

    private void addContainer(Institution instn) throws org.json.simple.parser.ParseException, ContainerException {
        for (Object obj : this.jsonContainers) {
            JSONObject jsonContainers = (JSONObject) obj;

            ((InstitutionImp) instn).addContainer(createContainer(jsonContainers));
        }
    }

}
