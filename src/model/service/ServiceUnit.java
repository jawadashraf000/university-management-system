package model.service;

import model.base.CampusEntity;

// Base Abstract class extended by HealthCanter, SecurityService and TransportService.

public abstract class ServiceUnit extends CampusEntity {

    private double serviceHours;
    private int staffCount;
    private double hourlyRate;

    public ServiceUnit(String entityID, String name, String location,
                       double serviceHours, int staffCount, double hourlyRate) {
        super(entityID, name, location);
        this.serviceHours = serviceHours;
        this.staffCount   = staffCount;
        this.hourlyRate   = hourlyRate;
    }

    @Override
    public double calculateOperationalCost() {
        return serviceHours * staffCount * hourlyRate * 30;
    }

    public double getServiceHours() { return serviceHours; }
    public int getStaffCount()      { return staffCount; }
    public double getHourlyRate()   { return hourlyRate; }

    public void setServiceHours(double h) { this.serviceHours = h; }
    public void setStaffCount(int s)      { this.staffCount = s; }
    public void setHourlyRate(double r)   { this.hourlyRate = r; }

    @Override
    public String toString() {
        return "[" + getEntityID() + "] " + getName() + " | Staff: " + staffCount +
               " | Hours/day: " + serviceHours + " | Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }
}
