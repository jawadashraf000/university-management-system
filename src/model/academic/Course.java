package model.academic;

import model.base.Reportable;
import model.base.Schedulable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Schedulable, Serializable, Reportable {

    private String courseID;
    private String courseName;
    private String departmentID;
    private String teacherID;
    private String teacherName;
    private String classroomID;
    private int creditHours;
    private int maxEnrollment;
    private List<Assignment> assignments = new ArrayList<>();
    private List<String> enrolledStudentIDs = new ArrayList<>();
    private List<Schedule> schedules = new ArrayList<>();

    public Course(String courseID, String courseName,
                  String departmentID, int creditHours, int maxEnrollment) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.departmentID = departmentID;
        this.creditHours = creditHours;
        this.maxEnrollment = maxEnrollment;
    }

    public boolean enrollStudent(String studentID) {
        if (enrolledStudentIDs.size() >= maxEnrollment) return false;
        if (enrolledStudentIDs.contains(studentID)) return false;
        enrolledStudentIDs.add(studentID);
        return true;
    }

    public boolean dropStudent(String studentID) {
        return enrolledStudentIDs.remove(studentID);
    }

    public boolean isEnrolled(String studentID) {
        return enrolledStudentIDs.contains(studentID);
    }

    public void addAssignment(Assignment a) {
        assignments.add(a);
    }

    public boolean removeAssignment(String id) {
        for (int i = 0; i < assignments.size(); i++) {
            if (assignments.get(i).getAssignmentID().equals(id)) {
                assignments.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public Schedule generateSchedule() {
        return new Schedule("SCH-" + courseID, courseID, courseName,
                "Monday", "08:00", "09:30", classroomID != null ? classroomID : "TBD");
    }

    public boolean hasConflict(Schedule other) {
        for (Schedule s : schedules)
            if (s.isActive() && s.conflictsWith(other)) return true;
        return false;
    }

    public void updateSchedule(Schedule newSchedule) {
        for (int i = 0; i < schedules.size(); i++) {
            Schedule s = schedules.get(i);
            if (s.getDay().equals(newSchedule.getDay()) && s.getOwnerID().equals(courseID)) {
                schedules.remove(i);
                i--;
            }
        }
        schedules.add(newSchedule);
    }

    public List<Schedule> getAllSchedules() { return new ArrayList<>(schedules); }

    public void addSchedule(Schedule s) {
        schedules.add(s);
    }

    public String getCourseID()     { return courseID; }
    public String getCourseName()   { return courseName; }
    public String getDepartmentID() { return departmentID; }
    public String getTeacherID()    { return teacherID; }
    public String getTeacherName()  { return teacherName; }
    public String getClassroomID()  { return classroomID; }
    public int getCreditHours()     { return creditHours; }
    public int getMaxEnrollment()   { return maxEnrollment; }
    public int getAvailableSeats()   { return maxEnrollment - enrolledStudentIDs.size(); }
    public int getCurrentEnrollment() { return enrolledStudentIDs.size(); }
    public List<String> getEnrolledStudentIDs() { return new ArrayList<>(enrolledStudentIDs); }
    public List<Assignment> getAssignments()    { return new ArrayList<>(assignments); }

    public void setTeacherID(String id)     { this.teacherID = id; }
    public void setTeacherName(String name) { this.teacherName = name; }
    public void setClassroomID(String id)   { this.classroomID = id; }
    public void setDepartmentID(String departmentID) { this.departmentID = departmentID; }
    public void setCourseName(String name)  { this.courseName = name; }
    public void setCreditHours(int ch)      { this.creditHours = ch; }
    public void setMaxEnrollment(int max)   { this.maxEnrollment = max; }

    @Override
    public String toString() {
        return "[" + courseID + "] " + courseName +
               " | Dept: " + departmentID + " | Credits: " + creditHours +
               " | Enrolled: " + enrolledStudentIDs.size() + "/" + maxEnrollment +
               " | Teacher: " + (teacherName != null ? teacherName : "Unassigned");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Course)) return false;
        return courseID.equals(((Course) obj).courseID);
    }

    @Override
    public String generateReport() {
        return "--- COURSE REPORT ---\n" +
                "Course ID     : " + getCourseID() + "\n" +
                "Course Name   : " + getCourseName() + "\n" +
                "Department ID : " + getDepartmentID() + "\n" +
                "Credit Hours  : " + getCreditHours() + "\n" +
                "Total Enrollments : " + getCurrentEnrollment() + "\n" +
                "Max Enrollments   : " + getMaxEnrollment();
    }
}
