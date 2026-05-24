package model.facility;

import model.base.CampusEntity;
import model.util.UniversityStats;

// Base Abstract class extended by Cafeteria, Hostel, Library.

public abstract class Facility extends CampusEntity {

    private double maintenanceCost;
    private int usageFrequency;
    private double usageRate;

    public Facility(String entityID, String name, String location, double maintenanceCost, double usageRate) {
        super(entityID, name, location);
        this.maintenanceCost = maintenanceCost;
        this.usageRate = usageRate;
        UniversityStats.incrementTotalFacilityUsage();
    }

    @Override
    public double calculateOperationalCost() {
        return maintenanceCost + (usageFrequency * usageRate);
    }

    public void recordVisit() { usageFrequency++; }

    public double getMaintenanceCost() { return maintenanceCost; }
    public int    getUsageFrequency()  { return usageFrequency; }

    public void setMaintenanceCost(double maintenanceCost) { this.maintenanceCost = maintenanceCost; }
    public void setUsageFrequency(int usageFrequency)     { this.usageFrequency = usageFrequency; }

    @Override
    public String toString() {
        return super.toString() + " | Visitors/day: " + usageFrequency;
    }
}
