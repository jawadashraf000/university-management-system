package model.service;

import model.academic.Schedule;
import model.base.Schedulable;
import java.util.ArrayList;
import java.util.List;

public class TransportService extends ServiceUnit implements Schedulable {

    private int totalVehicles;
    private boolean isPeakHour;
    private List<String> routeNames = new ArrayList<>();
    private List<Schedule> schedules  = new ArrayList<>();

    public TransportService(String entityID, String name, String location,
                            int staffCount, int totalVehicles) {
        super(entityID, name, location, 14.0, staffCount, 200.0);
        this.totalVehicles = totalVehicles;
    }

    public void addRoute(String routeName) {
        if (!routeNames.contains(routeName)) {
            routeNames.add(routeName);
        }
    }

    public boolean removeRoute(String routeName) {
        return routeNames.remove(routeName);
    }

    public void setPeakHour(boolean isPeak) {
        this.isPeakHour = isPeak;
        regenerateSchedules();
        System.out.println("[Transport] Peak hour " + (isPeak ? "ON" : "OFF") + ". Schedules updated.");
    }

    private void regenerateSchedules() {
        schedules.clear();
        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
        int index = 0;
        for (String route : routeNames) {
            for (String day : days) {
                String start = isPeakHour ? "07:00" : "07:30";
                String end   = "09:00";
                schedules.add(new Schedule("TS-" + index++, getEntityID(), route, day, start, end, route));
            }
        }
    }

    @Override
    public Schedule generateSchedule() {
        if (routeNames.isEmpty()) return null;
        return new Schedule("TS-DEFAULT", getEntityID(), routeNames.get(0),
                "Monday", isPeakHour ? "07:00" : "07:30", "09:00", routeNames.getFirst());
    }

    public boolean hasConflict(Schedule other) {
        for (Schedule s : schedules)
            if (s.isActive() && s.conflictsWith(other)) return true;
        return false;
    }

    public void updateSchedule(Schedule newSchedule) {
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            if (s.getOwnerID().equals(newSchedule.getOwnerID())
                && s.getDay().equals(newSchedule.getDay())) {
                schedules.remove(s);
            }
        }
        schedules.add(newSchedule);
    }

    public List<Schedule> getAllSchedules() { return new ArrayList<>(schedules); }

    public int getTotalVehicles() { return totalVehicles; }
    public boolean isPeakHour()   { return isPeakHour; }
    public List<String> getRouteNames() { return new ArrayList<>(routeNames); }

    public void setTotalVehicles(int v) { this.totalVehicles = v; }

    @Override
    public String toString() {
        return "[" + getEntityID() + "] " + getName() + " | Vehicles: " + totalVehicles +
               " | Routes: " + routeNames.size() + " | Peak: " + (isPeakHour ? "ON" : "OFF") +
               " | Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }
}
