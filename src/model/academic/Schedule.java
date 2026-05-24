package model.academic;

import java.io.Serializable;

public class Schedule implements Serializable {

    private String scheduleID;
    private String ownerID;
    private String ownerName;
    private String day;
    private String startTime;
    private String endTime;
    private String room;
    private boolean isActive;

    public Schedule(String scheduleID, String ownerID, String ownerName,
                    String day, String startTime, String endTime, String room) {
        this.scheduleID  = scheduleID;
        this.ownerID     = ownerID;
        this.ownerName   = ownerName;
        this.day         = day;
        this.startTime   = startTime;
        this.endTime     = endTime;
        this.room  = room;
        this.isActive    = true;
    }

    public boolean conflictsWith(Schedule other) {
        if (!this.day.equals(other.day)) return false;
        int thisStart = toMinutes(this.startTime);
        int thisEnd = toMinutes(this.endTime);
        int otherStart = toMinutes(other.startTime);
        int otherEnd = toMinutes(other.endTime);
        return thisStart < otherEnd && otherStart < thisEnd;
    }

    private int toMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    public String  getScheduleID() { return scheduleID; }
    public String  getOwnerID()    { return ownerID; }
    public String  getOwnerName()  { return ownerName; }
    public String  getDay()        { return day; }
    public String  getStartTime()  { return startTime; }
    public String  getEndTime()    { return endTime; }
    public String  getRoomOrStop() { return room; }
    public boolean isActive()      { return isActive; }

    public void setDay(String day)             { this.day = day; }
    public void setStartTime(String startTime)      { this.startTime = startTime; }
    public void setEndTime(String endTime)        { this.endTime = endTime; }
    public void setRoom(String room)     { this.room = room; }
    public void setActive(boolean isActive)        { this.isActive = isActive; }

    @Override
    public String toString() {
        return ownerName + " | " + day + " | " + startTime + "-" + endTime + " | Room: " + room;
    }
}
