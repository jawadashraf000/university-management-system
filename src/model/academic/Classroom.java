package model.academic;

public class Classroom extends AcademicUnit {

    private boolean hasProjector;
    private boolean hasAC;
    private String currentCourseID;
    private boolean available;

    public Classroom(String entityID, String name, String location,
                     int capacity, boolean hasProjector, boolean hasAC) {
        super(entityID, name, location, capacity, 15.0);
        this.hasProjector = hasProjector;
        this.hasAC = hasAC;
        this.available = true;

        if (hasProjector) {
            addEquipmentCost(200.0);
        }
        if (hasAC) {
            addEquipmentCost(800.0);
        }
    }

    public void occupy(String courseID) {
        if (available) {
            this.currentCourseID = courseID;
            available = false;
        }
    }

    public void vacate() {
        available = true;
        if (currentCourseID != null) {
            System.out.println("Classroom freed. Course " + currentCourseID + " should be rescheduled.");
        }

        this.currentCourseID = null;
    }

    public boolean hasProjector() { return hasProjector; }
    public boolean hasAC()        { return hasAC; }
    public boolean isAvailable()  { return available; }
    public String  getCurrentCourseID() { return currentCourseID; }

    public void setAvailable(boolean available) { this.available = available; }
    public void setHasProjector(boolean hasProjector) { this.hasProjector = hasProjector; }
    public void setHasAC(boolean hasAC)        { this.hasAC = hasAC; }

    @Override
    public String toString() {
        return super.toString() + " | AC: " + (hasAC ? "Yes" : "No") + " | Projector: "
                + (hasProjector ? "Yes" : "No") + " | Status: " + isAvailable();
    }
}