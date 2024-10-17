/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Io;

import Core.AidBoxImp;
import Core.InstitutionImp;
import Core.Location;
import PickingManagement.Capacity;
import PickingManagement.VehicleImp;
import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Vehicle;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author diogo
 */
public class Exporter {
    
    private Institution instn;

    public Exporter(Institution instn) {
        this.instn = instn;
    }
    
    
    public void save() throws IOException {
        this.saveInstitution();
        this.saveVehicles();
        this.saveReports();
        this.saveAidBoxes();
        this.saveDistances();
    }
    
    
    private void saveInstitution() throws IOException {
        JSONObject institution = new JSONObject();
        institution.put("Nome", this.instn.getName());

        try (FileWriter file = new FileWriter("Institution.json")) {
            file.write(institution.toJSONString());
        }
    }
    
    private void saveVehicles() throws IOException {
        JSONArray vehicles = new JSONArray();

        for (Vehicle vehicle : this.instn.getVehicles()) {
            vehicles.add(vehicleToJsonObject(vehicle));
        }

        try (FileWriter file = new FileWriter("vehicles.json")) {
            file.write(vehicles.toJSONString());
        }
    }
    
    private JSONObject vehicleToJsonObject(Vehicle vehicle) {
        VehicleImp vhcl = (VehicleImp) vehicle;
        JSONObject jsonVehicle = new JSONObject();
        jsonVehicle.put("code", (String) vhcl.getCode());
        
        JSONArray capacitys = new JSONArray();
                
        for (Capacity capacity : vhcl.getCapacitys() ) {
            capacitys.add(capacityJsonObject(capacity));
        }       
        return jsonVehicle;
    }
    
    
    private JSONObject capacityJsonObject(Capacity capacity) {
        JSONObject jsonCapacity = new JSONObject();
        String type = capacity.getType().getType();
        jsonCapacity.put( type, capacity.getMaxCapacity());
        return jsonCapacity;
    }
    
    private void saveReports() throws IOException {
        
        JSONArray reports = new JSONArray();

        for (Report report : ((InstitutionImp)this.instn).getReports()) {
            reports.add(reportToJsonObject(report));
        }

        try (FileWriter file = new FileWriter("Reports.json")) {
            file.write(reports.toJSONString());
        }
    }
    
    private JSONObject reportToJsonObject(Report report) {
        JSONObject jsonReport = new JSONObject();
        jsonReport.put("date", report.getDate().toString());
        jsonReport.put("Used Vehicles", (int) report.getUsedVehicles());
        jsonReport.put("Picked Containers", (int) report.getPickedContainers());
        jsonReport.put("Non Picked Containers", (int) report.getNonPickedContainers());
        jsonReport.put("Total Distance", report.getTotalDistance());
        jsonReport.put("Total Duration", report.getTotalDuration());
        jsonReport.put("Non Used Vehicles", (int) report.getNotUsedVehicles());
        return jsonReport;
    }
    
    private void saveAidBoxes() throws IOException {
        JSONArray aidboxes = new JSONArray();
        for (AidBox aidbox : this.instn.getAidBoxes()) {
            aidboxes.add(aidBoxToJsonObject(aidbox));
        }
        try (FileWriter file = new FileWriter("aidBoxes.json")) {
            file.write(aidboxes.toJSONString());
        }
    }
    
    private JSONObject aidBoxToJsonObject(AidBox aidbox) {
        JSONObject jsonAidbox = new JSONObject();

        jsonAidbox.put("code", aidbox.getCode());
        jsonAidbox.put("Zona", aidbox.getZone());

        JSONArray containers = new JSONArray();

        for (Container container : aidbox.getContainers()) {
            containers.add(containerToJsonObject(container));
        }

        jsonAidbox.put("containers", containers);

        return jsonAidbox;
    }
    
    private JSONObject containerToJsonObject(Container container) {
        JSONObject jsonContainer = new JSONObject();
        jsonContainer.put("code", container.getCode());
        return jsonContainer;
    }
    
    
    private void saveDistances() throws IOException {
        JSONArray distances = new JSONArray();

        for (AidBox aidbox : this.instn.getAidBoxes()) {
            distances.add(locationJsonObject(aidbox));
        }

        try (FileWriter file = new FileWriter("distances.json")) {
            file.write(distances.toJSONString());
        }
    }
    
    private JSONObject locationJsonObject(AidBox aidbox) {
        JSONObject distances = new JSONObject();
        distances.put("from", aidbox.getCode());

        JSONArray jsonDistances = new JSONArray();
        for (Location location : ((AidBoxImp)aidbox).getLocations()) {
            jsonDistances.add(locationJsonObject(location));
        }
        distances.put("to", jsonDistances);

        return distances;
    }
    
    private JSONObject locationJsonObject(Location location) {
        JSONObject jsonDistance = new JSONObject();

        jsonDistance.put("name", location.getCode());
        jsonDistance.put("distance", (long) location.getDistance());
        jsonDistance.put("duration", (long) location.getDuration());

        return jsonDistance;
    }
}
