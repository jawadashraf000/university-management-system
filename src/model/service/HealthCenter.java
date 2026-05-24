package model.service;

import model.base.Notifiable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HealthCenter extends ServiceUnit implements Notifiable {

    private int totalBeds;
    private int occupiedBeds;
    private int totalDoctors;
    private List<String> notificationLog = new ArrayList<>();

    public HealthCenter(String entityID, String name, String location,
                        int staffCount, int totalBeds, int totalDoctors) {
        super(entityID, name, location, 12.0, staffCount, 300.0);
        this.totalBeds    = totalBeds;
        this.totalDoctors = totalDoctors;
    }

    public List<String> declareMedicalEmergency(String location, String details,
                                                 SecurityService security) {
        List<String> log = new ArrayList<>();
        String msg = "MEDICAL EMERGENCY at " + location + ": " + details;

        sendNotification(msg);
        log.add("[HealthCenter: " + getName() + "] Alerted.");

        security.sendNotification(msg);
        security.logIncident(msg);
        log.add("[Security: " + security.getName() + "] Alerted.");

        return log;
    }

    public boolean admitPatient()    {
        if (occupiedBeds >= totalBeds) return false;
        occupiedBeds++;
        return true;
    }

    public boolean dischargePatient() {
        if (occupiedBeds <= 0) return false;
        occupiedBeds--;
        return true;
    }

    @Override
    public void sendNotification(String message) {
        String entry = "[" + LocalDateTime.now().toLocalTime() + "] HEALTH: " + message;
        notificationLog.add(entry);
        System.out.println("HEALTH: " + entry);
    }

    public List<String> getNotificationLog() {
        return new ArrayList<>(notificationLog);
    }

    public int getTotalBeds()     { return totalBeds; }
    public int getOccupiedBeds()  { return occupiedBeds; }
    public int getAvailableBeds() { return totalBeds - occupiedBeds; }
    public int getTotalDoctors()  { return totalDoctors; }

    public void setTotalBeds(int totalBeds)       { this.totalBeds = totalBeds; }
    public void setTotalDoctors(int totalDoctors) { this.totalDoctors = totalDoctors; }

    @Override
    public String toString() {
        return "[" + getEntityID() + "] " + getName() + " | Beds: " + occupiedBeds + "/" + totalBeds +
               " | Doctors: " + totalDoctors + " | Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }
}
