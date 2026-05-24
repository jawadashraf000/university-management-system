package model.service;

import model.base.Notifiable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SecurityService extends ServiceUnit implements Notifiable {

    private int totalCameras;
    private boolean isLockdownActive;
    private List<String> incidentLog     = new ArrayList<>();
    private List<String> notificationLog = new ArrayList<>();

    public SecurityService(String entityID, String name, String location,
                           int staffCount, int totalCameras) {
        super(entityID, name, location, 24.0, staffCount, 250.0);
        this.totalCameras = totalCameras;
    }

    public void logIncident(String incident) {
        String entry = "[" + LocalDateTime.now().toLocalTime() + "] " + incident;
        incidentLog.add(entry);
    }

    public void activateLockdown(String reason) {
        isLockdownActive = true;
        logIncident("LOCKDOWN: " + reason);
    }

    public void deactivateLockdown() {
        isLockdownActive = false;
        logIncident("Lockdown lifted.");
    }

    @Override
    public void sendNotification(String message) {
        String entry = "[" + LocalDateTime.now().toLocalTime() + "] SECURITY: " + message;
        notificationLog.add(entry);
        System.out.println("SECURITY: " + entry);
    }

    public List<String> getNotificationLog() {
        return new ArrayList<>(notificationLog);
    }

    public int     getTotalCameras()     { return totalCameras; }
    public boolean isLockdownActive()    { return isLockdownActive; }
    public List<String> getIncidentLog() { return new ArrayList<>(incidentLog); }
    public void setTotalCameras(int c)   { this.totalCameras = c; }

    @Override
    public String toString() {
        return "[" + getEntityID() + "] " + getName() + " | Staff: " + getStaffCount() +
               " | Cameras: " + totalCameras + " | Lockdown: " + (isLockdownActive ? "ACTIVE" : "Normal") +
               " | Incidents: " + incidentLog.size() + " | Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }
}
