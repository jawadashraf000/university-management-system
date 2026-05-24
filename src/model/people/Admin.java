package model.people;

import model.base.Notifiable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Admin implements Serializable, Notifiable {

    private String adminID;
    private String name;
    private String email;
    private String role;
    private String username;
    private String password;
    private List<String> notificationLog = new ArrayList<>();
    private static final String secretCode = "0000";

    public Admin(String adminID, String name, String email, String username, String password) {
        this.adminID = adminID;
        this.name = name;
        this.email = email;
        this.role = "ADMIN";
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public void sendNotification(String message) {
        String entry = "[" + LocalDateTime.now().toLocalTime() + "] ADMIN: " + message;
        notificationLog.add(entry);
        System.out.println(entry);
    }

    public List<String> getNotificationLog() { return new ArrayList<>(notificationLog); }

    public String getAdminID() { return adminID; }
    public String getName()    { return name; }
    public String getEmail()   { return email; }
    public String getRole()    { return role; }

    public void setName(String name)   { this.name = name; }
    public void setEmail(String email) { this.email = email; }

    public void setUsername(String username, String code) {
        if (code.equals(secretCode)) {
            this.username = username;
        } else {
            System.out.println("Invalid Secret Code!");
        }
    }

    public void setPassword(String password, String code) {
        if (code.equals(secretCode)) {
            this.password = password;
        } else {
            System.out.println("Invalid Secret Code!");
        }
    }

    @Override
    public String toString() {
        return "[" + adminID + "] ADMIN: " + name + " | " + email;
    }
}
