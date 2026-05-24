package model.base;

import java.io.Serializable;

// Base Abstract class extended by AcademicUnit, Facility, ServiceUnit.

public abstract class CampusEntity implements Serializable {

    private String entityID;
    private String name;
    private String location;

    public CampusEntity(String entityID, String name, String location) {
        this.entityID = entityID;
        this.name = name;
        this.location = location;
    }

    public abstract double calculateOperationalCost();

    public String getEntityID() { return entityID; }
    public String getName()     { return name; }
    public String getLocation() { return location; }

    public void setEntityID(String entityID) { this.entityID = entityID; }
    public void setName(String name)         { this.name = name; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public String toString() {
        return "[" + entityID + "] " + name + " | " + location + " | Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CampusEntity)) return false;
        return entityID.equals(((CampusEntity) obj).entityID);
    }
}
