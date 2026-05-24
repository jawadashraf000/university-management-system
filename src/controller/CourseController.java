package controller;

import model.academic.*;
import model.people.Teacher;
import persistence.AppState;
import java.util.ArrayList;
import java.util.List;

public class CourseController {

    private AppState state;

    public CourseController(AppState state) {
        this.state = state;
    }

    public boolean addCourse(Course course, String departmentID) {
        Department dept = state.getDepartmentRepository().findById(departmentID);

        if (dept == null || state.findCourse(course.getCourseID()) != null) {
            return false;
        }

        dept.addCourse(course);
        state.getAllCourses().add(course);
        return true;
    }

    public boolean removeCourse(String courseID) {
        Course course = state.findCourse(courseID);

        if (course == null) return false;

        Department dept = state.getDepartmentRepository().findById(course.getDepartmentID());

        if (dept != null) dept.removeCourse(courseID);

        if (course.getTeacherID() != null) {
            Teacher t = state.findTeacher(course.getTeacherID());
            if (t != null) t.removeCourse(courseID);
        }

        return state.getAllCourses()
                .removeIf(c -> c.getCourseID().equals(courseID));
    }

    public boolean updateCourse(String courseID, String name, int credits, int maxEnroll) {
        Course c = state.findCourse(courseID);

        if (c == null) return false;

        c.setCourseName(name);
        c.setCreditHours(credits);
        c.setMaxEnrollment(maxEnroll);
        return true;
    }

    public boolean assignTeacher(String courseID, String teacherID) {
        Course course = state.findCourse(courseID);
        Teacher teacher = state.findTeacher(teacherID);

        if (course == null || teacher == null) return false;

        if (course.getTeacherID() != null) {
            Teacher old = state.findTeacher(course.getTeacherID());
            if (old != null) old.removeCourse(courseID);
        }

        course.setTeacherID(teacherID);
        course.setTeacherName(teacher.getName());
        teacher.assignCourse(courseID);
        return true;
    }

    public boolean assignClassroom(String courseID, String classroomID) {
        Course course = state.findCourse(courseID);
        Classroom room = state.getClassroomRepository().findById(classroomID);

        if (course == null || room == null || !room.isAvailable()) return false;

        if (course.getClassroomID() != null) {
            Classroom old = state.getClassroomRepository()
                    .findById(course.getClassroomID());
            if (old != null) old.vacate();
        }

        course.setClassroomID(classroomID);
        room.occupy(courseID);
        return true;
    }

    public Course findCourse(String id) {
        return state.findCourse(id);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(state.getAllCourses());
    }

    public List<Course> searchCourses(String keyword) {
        List<Course> result = new ArrayList<>();
        String kw = keyword.toLowerCase();

        for (Course c : state.getAllCourses()) {
            if (c.getCourseName().toLowerCase().contains(kw)
                    || c.getCourseID().toLowerCase().contains(kw)) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Course> getCoursesByDepartment(String deptID) {
        List<Course> result = new ArrayList<>();

        for (Course c : state.getAllCourses()) {
            if (deptID.equals(c.getDepartmentID())) {
                result.add(c);
            }
        }
        return result;
    }

    public int getTotalCourses() {
        return state.getAllCourses().size();
    }
}