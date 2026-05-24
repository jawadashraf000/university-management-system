package model.facility;

import java.util.ArrayList;

public class Hostel extends Facility {

    private int totalRooms;
    private int occupiedRooms;
    private double feePerMonth;
    private String hostelType;
    private ArrayList<String> roomNumbers = new ArrayList<>();
    private ArrayList<String> studentIDs = new ArrayList<>();

    public Hostel(String entityID, String name, String location,
                  int totalRooms, double feePerMonth, String hostelType) {
        super(entityID, name, location, totalRooms * 500.0, 3.0);
        this.totalRooms = totalRooms;
        this.feePerMonth = feePerMonth;
        this.hostelType = hostelType;
    }

    public void allocateRoom(String studentID) {
        if (occupiedRooms >= totalRooms) {
            System.out.println("No room available!");
            return;
        }

        roomNumbers.add("R" + (occupiedRooms + 1));
        studentIDs.add(studentID);
        occupiedRooms++;
        recordVisit();
    }

    public boolean vacateRoom(String roomNumber) {
        for (int i = 0; i < roomNumbers.size(); i++) {
            if (roomNumbers.get(i).equals(roomNumber)) {
                roomNumbers.remove(i);
                studentIDs.remove(i);
                occupiedRooms = Math.max(0, occupiedRooms - 1);
                return true;
            }
        }
        return false;
    }

    public int getTotalRooms()     { return totalRooms; }
    public int getOccupiedRooms()  { return occupiedRooms; }
    public int getAvailableRooms() { return totalRooms - occupiedRooms; }
    public double getFeePerMonth() { return feePerMonth; }
    public String getHostelType()  { return hostelType; }
    public ArrayList<String> getRoomAllocations() { return new ArrayList<>(roomNumbers); }

    public void setFeePerMonth(double feePerMonth) { this.feePerMonth = feePerMonth; }

    @Override
    public String toString() {
        return "[" + getEntityID() + "] " + getName() + " (" + hostelType + ") | Rooms: " +
                occupiedRooms + "/" + totalRooms + " | Fee: Rs." + feePerMonth +
                "/month | Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }
}