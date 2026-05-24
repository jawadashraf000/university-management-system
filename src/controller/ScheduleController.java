package controller;

import model.academic.*;
import persistence.AppState;
import java.util.ArrayList;
import java.util.List;

public class ScheduleController {

    private AppState state;

    public ScheduleController(AppState state) {
        this.state = state;
    }

    public boolean addSchedule(String courseID, Schedule schedule) {
        Course course = state.findCourse(courseID);

        if (course == null) return false;

        if (isRoomConflict(schedule)) return false;

        if (isTeacherConflict(course.getTeacherID(), schedule)) return false;

        course.addSchedule(schedule);
        return true;
    }

    public List<String> handleClassroomUnavailable(String classroomID) {
        List<String> log = new ArrayList<>();

        Classroom room = state.getClassroomRepository().findById(classroomID);
        if (room == null) return log;

        room.vacate();
        room.setAvailable(false);

        for (Course course : state.getAllCourses()) {
            if (!classroomID.equals(course.getClassroomID())) continue;

            String newRoomID = findAvailableRoom(
                    course.getCurrentEnrollment(),
                    classroomID
            );

            if (newRoomID != null) {
                Classroom newRoom = state.getClassroomRepository().findById(newRoomID);

                course.setClassroomID(newRoomID);
                newRoom.occupy(course.getCourseID());

                for (Schedule s : course.getAllSchedules()) {
                    if (s.isActive() && s.getRoomOrStop().equals(classroomID)) {
                        s.setRoom(newRoomID);
                    }
                }

                log.add(course.getCourseName() + " moved -> " + newRoomID);
            } else {
                course.setClassroomID(null);
                log.add(course.getCourseName() + " unassigned room");
            }
        }

        return log;
    }

    public List<Schedule> getFullTimetable() {
        List<Schedule> all = new ArrayList<>();

        for (Course c : state.getAllCourses())
            for (Schedule s : c.getAllSchedules())
                if (s.isActive()) all.add(s);

        return all;
    }

    public List<Schedule> getTimetableByDay(String day) {
        List<Schedule> result = new ArrayList<>();

        for (Schedule s : getFullTimetable())
            if (s.getDay().equalsIgnoreCase(day))
                result.add(s);

        return result;
    }

    public List<Schedule> getTimetableForStudent(String studentID) {
        List<Schedule> result = new ArrayList<>();

        for (Course c : state.getAllCourses())
            if (c.isEnrolled(studentID))
                result.addAll(c.getAllSchedules());

        return result;
    }

    public List<Schedule> getTransportSchedules() {
        List<Schedule> result = new ArrayList<>();

        for (var ts : state.getTransportServices())
            result.addAll(ts.getAllSchedules());

        return result;
    }

    private boolean isRoomConflict(Schedule incoming) {
        for (Course c : state.getAllCourses())
            for (Schedule s : c.getAllSchedules())
                if (s.isActive()
                        && s.getRoomOrStop().equals(incoming.getRoomOrStop())
                        && s.conflictsWith(incoming))
                    return true;

        return false;
    }

    private boolean isTeacherConflict(String teacherID, Schedule incoming) {
        if (teacherID == null) return false;

        for (Course c : state.getAllCourses())
            if (teacherID.equals(c.getTeacherID()))
                for (Schedule s : c.getAllSchedules())
                    if (s.isActive() && s.conflictsWith(incoming))
                        return true;

        return false;
    }

    private String findAvailableRoom(int capacity, String excludeID) {
        for (Classroom r : state.getClassroomRepository().getAll())
            if (!r.getEntityID().equals(excludeID)
                    && r.isAvailable()
                    && r.getCapacity() >= capacity)
                return r.getEntityID();

        return null;
    }
}