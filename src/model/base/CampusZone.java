package model.base;

import model.facility.Facility;
import model.service.ServiceUnit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CampusZone implements Serializable {

    private String zoneID;
    private String zoneName;
    private String location;
    private List<Facility> facilities   = new ArrayList<>();
    private List<ServiceUnit> serviceUnits = new ArrayList<>();

    public CampusZone(String zoneID, String zoneName, String location) {
        this.zoneID = zoneID;
        this.zoneName = zoneName;
        this.location = location;
    }

    public void addFacility(Facility f) {
        facilities.add(f);
    }
    public void addServiceUnit(ServiceUnit s) {
        serviceUnits.add(s);
    }

    public String getZoneID()   { return zoneID; }
    public String getZoneName() { return zoneName; }
    public String getLocation() { return location; }
    public List<Facility> getFacilities() { return facilities; }
    public List<ServiceUnit> getServiceUnits() { return serviceUnits; }

    public void setZoneID(String zoneID) { this.zoneID = zoneID; }
    public void setZoneName(String zoneName) { this.zoneName = zoneName; }

    @Override
    public String toString() {
        return "[" + zoneID + "] " + zoneName + " | Facilities: " + facilities.size() + " | Services: " + serviceUnits.size();
    }
}
