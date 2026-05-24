package model.academic;

import model.base.CampusEntity;

public abstract class AcademicUnit extends CampusEntity {

    private int totalStudents;
    private double costPerStudent;
    private double equipmentCost;
    private int capacity;

    public AcademicUnit(String entityID, String name, String location, int capacity, double costPerStudent) {
        super(entityID, name, location);
        this.capacity = capacity;
        this.costPerStudent = costPerStudent;
    }

    @Override
    public double calculateOperationalCost() {
        return (totalStudents * costPerStudent) + equipmentCost;
    }

    public void addEquipmentCost(double cost) { this.equipmentCost += cost; }

    public int getTotalStudents()     { return totalStudents; }
    public double getCostPerStudent() { return costPerStudent; }
    public double getEquipmentCost()  { return equipmentCost; }
    public int getCapacity()          { return capacity; }

    public void setTotalStudents(int totalStudents) { this.totalStudents = totalStudents; }
    public void setEquipmentCost(double equipmentCost) { this.equipmentCost = equipmentCost; }
    public void setCapacity(int capacity)      { this.capacity = capacity; }

    @Override
    public String toString() {
        return super.toString() + " | Students: " + totalStudents + "/" + capacity;
    }
}
