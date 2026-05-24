package model.people;

import model.academic.Department;
import model.util.UniversityStats;
import persistence.AppState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student implements Serializable {

    private String studentID;
    private String name;
    private String email;
    private String program;
    private String departmentID;
    private String role;
    private String username;
    private String password;
    private static final String secretCode = "0000";
    private List<String> enrolledCourseIDs = new ArrayList<>();

    public Student(String studentID, String name, String email,
                   String program, String departmentID, String username, String password) {
        this.studentID = studentID;
        this.name = name;
        this.email = email;
        this.program = program;
        this.departmentID = departmentID;
        this.role  = "STUDENT";
        this.username  = username;
        this.password = password;
        UniversityStats.incrementTotalStudents();
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void enrollInCourse(String courseID)  {
        if (!enrolledCourseIDs.contains(courseID)) {
            enrolledCourseIDs.add(courseID);
        }
    }

    public void dropCourse(String courseID) {
        enrolledCourseIDs.remove(courseID);
    }

    public boolean isEnrolledIn(String courseID) {
        return enrolledCourseIDs.contains(courseID);
    }

    public String getStudentID()    { return studentID; }
    public String getName()         { return name; }
    public String getEmail()        { return email; }
    public String getProgram()      { return program; }
    public String getDepartmentID() { return departmentID; }
    public String getRole()         { return role; }
    public List<String> getEnrolledCourseIDs() { return new ArrayList<>(enrolledCourseIDs); }

    public void setName(String name)       { this.name = name; }
    public void setEmail(String email)     { this.email = email; }
    public void setProgram(String program) { this.program = program; }
    public void setDepartmentID(String id) { this.departmentID = id; }
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
        return "[" + studentID + "] " + name + " | " + program + " | Dept: " + departmentID +
               " | Email: " + email + " | Courses: " + enrolledCourseIDs.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) return false;
        return studentID.equals(((Student) obj).studentID);
    }
}
