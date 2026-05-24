package persistence;

import model.academic.*;
import model.facility.*;
import model.people.*;
import model.service.*;
import model.base.CampusZone;
import model.repository.CampusRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// This class contains collections of Objects of all classes in a single class.
// Benefit: Help to manage operations and read and write object in file.
// It is helpful in File Handling so it is placed in persistence package.

public class AppState implements Serializable {

    // Collections of Objects of all classes:

    private List<Student> students = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Admin> admins = new ArrayList<>();

    private CampusRepository<Department> departmentRepository = new CampusRepository<>("Departments");
    private CampusRepository<Classroom>  classroomRepository  = new CampusRepository<>("Classrooms");
    private CampusRepository<Lab> labRepository = new CampusRepository<>("Labs");

    private List<Course> allCourses = new ArrayList<>();

    private CampusRepository<Library> libraryRepository = new CampusRepository<>("Libraries");
    private CampusRepository<Cafeteria> cafeteriaRepository = new CampusRepository<>("Cafeterias");
    private CampusRepository<Hostel> hostelRepository = new CampusRepository<>("Hostels");

    private List<Book> allBooks = new ArrayList<>();

    private List<TransportService> transportServices = new ArrayList<>();
    private List<SecurityService> securityServices = new ArrayList<>();
    private List<HealthCenter> healthCenters = new ArrayList<>();

    private List<CampusZone> campusZones = new ArrayList<>();

    public AppState() {
        // Its state is defined through setters.
    }

    public Student findStudent(String id) {
        for (Student s : students) if (s.getStudentID().equals(id)) return s;
        return null;
    }

    public Teacher findTeacher(String id) {
        for (Teacher t : teachers) if (t.getTeacherID().equals(id)) return t;
        return null;
    }

    public Admin findAdmin(String id) {
        for (Admin a : admins) if (a.getAdminID().equals(id)) return a;
        return null;
    }

    public Course findCourse(String id) {
        for (Course c : allCourses) if (c.getCourseID().equals(id)) return c;
        return null;
    }

    public List<Student> getStudents() { return students; }
    public List<Teacher> getTeachers() { return teachers; }
    public List<Admin> getAdmins()     { return admins; }

    public CampusRepository<Department> getDepartmentRepository() { return departmentRepository; }
    public CampusRepository<Classroom> getClassroomRepository()   { return classroomRepository; }
    public CampusRepository<Lab> getLabRepository() { return labRepository; }
    public List<Course> getAllCourses()             { return allCourses; }

    public CampusRepository<Library> getLibraryRepository()     { return libraryRepository; }
    public CampusRepository<Cafeteria> getCafeteriaRepository() { return cafeteriaRepository; }
    public CampusRepository<Hostel> getHostelRepository()       { return hostelRepository; }
    public List<Book> getAllBooks() { return allBooks; }

    public List<TransportService> getTransportServices() { return transportServices; }
    public List<SecurityService> getSecurityServices()   { return securityServices; }
    public List<HealthCenter> getHealthCenters() { return healthCenters; }
    public List<CampusZone> getCampusZones()     { return campusZones; }

    @Override
    public String toString() {
        return "AppState | Saved. | Students: " + students.size() +
               " | Courses: " + allCourses.size() + " | Depts: " + departmentRepository.size();
    }
}
