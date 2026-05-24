package model.facility;

public class Cafeteria extends Facility {

    private int totalSeats;
    private int occupiedSeats;
    private double averageMealCost;

    public Cafeteria(String entityID, String name, String location,
                     int totalSeats, double maintenanceCost) {
        super(entityID, name, location, maintenanceCost, 1.5);
        this.totalSeats = totalSeats;
        this.averageMealCost = 150.0;
    }

    public void occupySeat() {
        if (occupiedSeats >= totalSeats) {
            System.out.println("No seats available.");
            return;
        }
        occupiedSeats++;
        recordVisit();
        System.out.println("Successfully occupied a seat.");
    }

    public void vacateSeat() {
        if (occupiedSeats > 0) occupiedSeats--;
    }

    public int getTotalSeats()     { return totalSeats; }
    public int getOccupiedSeats()  { return occupiedSeats; }
    public int getAvailableSeats() { return totalSeats - occupiedSeats; }
    public double getAverageMealCost() { return averageMealCost; }

    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }
    public void setAverageMealCost(double averageMealCost){ this.averageMealCost = averageMealCost; }

    @Override
    public String toString() {
        return "[" + getEntityID() + "] " + getName() + " | Seating: " + occupiedSeats + "/" + totalSeats +
               " | Meal: Rs." + averageMealCost +
               " | Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }
}
