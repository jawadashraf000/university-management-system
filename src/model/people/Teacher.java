package model.people;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Teacher implements Serializable {

    private String teacherID;
    private String name;
    private String email;
    private String departmentID;
    private String designation;
    private String role;
    private String username;
    private String password;
    private List<String> courseIDs = new ArrayList<>();
    private static final String secretCode = "0000";

    public Teacher(String teacherID, String name, String email, String departmentID, String designation,
                   String username, String password) {
        this.teacherID = teacherID;
        this.name = name;
        this.email = email;
        this.departmentID = departmentID;
        this.designation = designation;
        this.role = "TEACHER";
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void assignCourse(String courseID)  {
        if (!courseIDs.contains(courseID)) {
            courseIDs.add(courseID);
        }
    }

    public void removeCourse(String courseID) {
        courseIDs.remove(courseID);
    }

    public String getTeacherID()               { return teacherID; }
    public String getName()                    { return name; }
    public String getEmail()                   { return email; }
    public String getDepartmentID()            { return departmentID; }
    public String getDesignation()             { return designation; }
    public String getRole()                    { return role; }
    public List<String> getCourseIDs()         { return new ArrayList<>(courseIDs); }

    public void setName(String name)           { this.name = name; }
    public void setEmail(String email)         { this.email = email; }
    public void setDepartmentID(String id)     { this.departmentID = id; }
    public void setDesignation(String d)       { this.designation = d; }

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
        return "[" + teacherID + "] " + name + " | " + designation + " | Dept: " + departmentID +
               " | Courses: " + courseIDs.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Teacher)) return false;
        return teacherID.equals(((Teacher) obj).teacherID);
    }

    @Override
    public int hashCode() { return teacherID.hashCode(); }
}
