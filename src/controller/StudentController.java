package controller;

import model.academic.Course;
import model.people.Student;
import model.util.UniversityStats;
import persistence.AppState;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    private AppState state;

    public StudentController(AppState state) {
        this.state = state;
    }

    public boolean addStudent(Student student) {
        if (state.findStudent(student.getStudentID()) != null)
            return false;

        state.getStudents().add(student);
        return true;
    }

    public boolean removeStudent(String studentID) {
        Student s = state.findStudent(studentID);
        if (s == null) return false;

        for (Course c : state.getAllCourses())
            c.dropStudent(studentID);

        state.getStudents().remove(s);
        UniversityStats.decrementTotalStudents();
        return true;
    }

    public boolean updateStudent(String studentID, String name, String email, String program) {
        Student s = state.findStudent(studentID);
        if (s == null) return false;

        s.setName(name);
        s.setEmail(email);
        s.setProgram(program);
        return true;
    }

    public boolean enrollInCourse(String studentID, String courseID) {
        Student student = state.findStudent(studentID);
        Course course = state.findCourse(courseID);

        if (student == null || course == null) return false;

        if (student.isEnrolledIn(courseID)) return false;

        if (course.enrollStudent(studentID)) {
            student.enrollInCourse(courseID);
            return true;
        }

        return false;
    }

    public boolean dropFromCourse(String studentID, String courseID) {
        Student student = state.findStudent(studentID);
        Course course = state.findCourse(courseID);

        if (student == null || course == null) return false;

        if (!course.dropStudent(studentID)) return false;

        student.dropCourse(courseID);
        return true;
    }

    public Student findStudent(String id) {
        return state.findStudent(id);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(state.getStudents());
    }

    public List<Student> searchStudents(String keyword) {
        List<Student> result = new ArrayList<>();
        String kw = keyword.toLowerCase();

        for (Student s : state.getStudents())
            if (s.getName().toLowerCase().contains(kw)
                    || s.getStudentID().toLowerCase().contains(kw))
                result.add(s);

        return result;
    }

    public int getTotalStudents() {
        return state.getStudents().size();
    }
}