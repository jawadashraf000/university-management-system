package model.academic;

import model.base.Reportable;
import model.util.UniversityStats;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Department extends AcademicUnit implements Reportable {

    private String departmentCode;
    private int totalStudents;
    private List<Course> courses = new ArrayList<>();

    public Department(String entityID, String name, String location, String departmentCode) {
        super(entityID, name, location, 500, 50.0);
        this.departmentCode = departmentCode;
        UniversityStats.incrementTotalDepartments();
        totalStudents = 0;
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            setTotalStudents(getTotalStudents());
            UniversityStats.incrementTotalCourses();
        }
    }

    public void removeCourse(String courseID) {
        for (int i = 0; i < courses.size(); i++) {
            Course c = courses.get(i);
            if (c.getCourseID().equals(courseID)) {
                courses.remove(c);
                setTotalStudents(getTotalStudents());
                UniversityStats.decrementTotalCourses();
            }
        }
    }

    public Course findCourse(String courseID) {
        for (Course c : courses) {
            if (c.getCourseID().equals(courseID)) {
                return c;
            }
        }
        return null;
    }

//    public int getTotalStudents() {
//        List<String> uniqueStudents = new ArrayList<>();
//        for (Course c : courses)
//            for (String id : c.getEnrolledStudentIDs())
//                if (!uniqueStudents.contains(id)) uniqueStudents.add(id);
//        return uniqueStudents.size();
//    }


    @Override
    public String generateReport() {
        return "--- DEPARTMENT PERFORMANCE REPORT ---\n" +
                "Department  : " + getName() + " (" + departmentCode + ")\n" +
                "Location    : " + getLocation() + "\n" +
                "Total Courses  : " + courses.size() + "\n" +
                "Total Students : " + getTotalStudents() + "\n" +
                "Operational Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }

    public String getDepartmentCode() { return departmentCode; }
    public List<Course> getCourses()  { return new ArrayList<>(courses); }
    public int getTotalStudents() {return totalStudents; }

    public void setDepartmentCode(String c)  { this.departmentCode = c; }
    public void incrementTotalStudents() { totalStudents++; }
    public void decrementTotalStudents() { totalStudents--; }

    @Override
    public String toString() {
        return "[" + getEntityID() + "] " + getName() + " (" + departmentCode + " | Courses: " + courses.size() +
               " | Students: " + getTotalStudents() + " | Cost: Rs." + String.format("%.0f", calculateOperationalCost());
    }
}
