package model.academic;

import java.io.Serializable;

public class Assignment implements Serializable {

    private String assignmentID;
    private String title;
    private String courseID;
    private String deadline;
    private double totalMarks;

    public Assignment(String assignmentID, String title, String courseID, String deadline, double totalMarks) {
        this.assignmentID = assignmentID;
        this.title = title;
        this.courseID = courseID;
        this.deadline = deadline;
        this.totalMarks = totalMarks;
    }

    public String getAssignmentID() { return assignmentID; }
    public String getTitle()        { return title; }
    public String getCourseID()     { return courseID; }
    public String getDeadline()     { return deadline; }
    public double getTotalMarks()   { return totalMarks; }

    public void setTitle(String title)       { this.title = title; }
    public void setDeadline(String deadline) { this.deadline = deadline; }
    public void setTotalMarks(double marks)  { this.totalMarks = marks; }

    @Override
    public String toString() {
        return "[" + assignmentID + "] " + title + " | Course: " + courseID + " | Deadline: " + deadline + " | Marks: " + totalMarks;
    }
}
