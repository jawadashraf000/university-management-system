package model.academic;

public class Lab extends AcademicUnit {

    private String labName;
    private int numberOfComputers;

    public Lab(String entityID, String name, String location, int capacity,
               String labName, int numberOfComputers) {
        super(entityID, name, location, capacity, 25.0);
        this.labName = labName;
        this.numberOfComputers = numberOfComputers;
    }

    @Override
    public double calculateOperationalCost() {
        return super.calculateOperationalCost() + (numberOfComputers * 50.0);
    }

    public String getLabName()        { return labName; }
    public int getNumberOfComputers() { return numberOfComputers; }

    public void setLabName(String labName) { this.labName = labName; }
    public void setNumberOfComputers(int numberOfComputers) { this.numberOfComputers = numberOfComputers; }

    @Override
    public String toString() {
        return super.toString() + " (" + labName + " Lab) | PCs: " + numberOfComputers;
    }
}
