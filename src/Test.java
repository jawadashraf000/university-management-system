import model.academic.*;
import model.base.CampusZone;
import model.facility.Book;
import model.facility.Cafeteria;
import model.facility.Hostel;
import model.facility.Library;
import model.people.Admin;
import model.people.Student;
import model.people.Teacher;
import model.service.HealthCenter;
import model.service.SecurityService;
import model.service.TransportService;
import persistence.AppState;
import persistence.DataManager;

public class Test {
    public static void sampleData () {
        AppState state = new AppState();

        Admin admin = new Admin("A001", "Dr. Khalid Mehmood", "admin@university.edu", "admin", "admin");
        state.getAdmins().add(admin);

        Department cs = new Department("D001", "Computer Science", "Block A", "CS");
        Department ee = new Department("D002", "Electrical Engineering", "Block B", "EE");
        Department bba = new Department("D003", "Physics", "Block C", "PHY");
        Department math = new Department("D004", "Mathematics", "Block D", "MATH");
        cs.addEquipmentCost(50000);
        ee.addEquipmentCost(75000);
        bba.addEquipmentCost(30000);
        math.addEquipmentCost(20000);

        state.getDepartmentRepository().add(cs);
        state.getDepartmentRepository().add(ee);
        state.getDepartmentRepository().add(bba);
        state.getDepartmentRepository().add(math);

        Classroom cr1 = new Classroom("CR001", "Room 101", "Block A", 50, true, true);
        Classroom cr2 = new Classroom("CR002", "Room 202", "Block B", 60, true, false);
        Classroom cr3 = new Classroom("CR003", "Seminar Hall", "Block C", 100, true, true);
        Classroom cr4 = new Classroom("CR004", "Room 305", "Block D", 40, false, true);
        Classroom cr5 = new Classroom("CR005", "Auditorium", "Main", 200, true, true);

        state.getClassroomRepository().add(cr1);
        state.getClassroomRepository().add(cr2);
        state.getClassroomRepository().add(cr3);
        state.getClassroomRepository().add(cr4);
        state.getClassroomRepository().add(cr5);

        Lab csLab = new Lab("L001", "CS Lab 1", "Block A", 40, "Computer", 1);
        csLab.setNumberOfComputers(40);
        Lab phyLab = new Lab("L002", "Physics Lab", "Block B", 30, "Physics", 1);
        state.getLabRepository().add(csLab);
        state.getLabRepository().add(phyLab);

        Teacher t1 = new Teacher("T001", "Prof. Ahmed Ali", "ahmed@uni.edu", "D001", "Professor", "teacher", "teacher");
        Teacher t2 = new Teacher("T002", "Dr. Sara Khan", "sara@uni.edu", "D001", "Asst. Prof", "teacher1", "teacher1");
        Teacher t3 = new Teacher("T003", "Prof. Usman Raza", "usman@uni.edu", "D002", "Professor", "teacher2", "teacher2");
        Teacher t4 = new Teacher("T004", "Dr. Fatima Shah", "fatima@uni.edu", "D002", "Lecturer", "teacher3", "teacher3");
        Teacher t5 = new Teacher("T005", "Prof. Bilal Hassan", "bilal@uni.edu", "D003", "Professor", "teacher4", "teacher4");
        Teacher t6 = new Teacher("T006", "Prof. Kamran Malik", "kamran@uni.edu", "D004", "Professor", "teacher5", "teacher5");

        state.getTeachers().add(t1);
        state.getTeachers().add(t2);
        state.getTeachers().add(t3);
        state.getTeachers().add(t4);
        state.getTeachers().add(t5);
        state.getTeachers().add(t6);

        Course c1 = new Course("CRS001", "OOP in Java","D001", 3, 40);
        Course c2 = new Course("CRS002", "Data Structures", "D001",3, 35);
        Course c3 = new Course("CRS003", "Database Systems","D001", 3, 40);
        Course c4 = new Course("CRS004", "Circuit Analysis","D002", 3, 30);
        Course c5 = new Course("CRS005", "Applied Physics", "D003",3, 50);
        Course c6 = new Course("CRS006", "Calculus II", "D004",3, 45);

        c1.setTeacherID("T001");
        c1.setTeacherName(t1.getName());
        t1.assignCourse("CRS001");
        c2.setTeacherID("T002");
        c2.setTeacherName(t2.getName());
        t2.assignCourse("CRS002");
        c3.setTeacherID("T001");
        c3.setTeacherName(t1.getName());
        t1.assignCourse("CRS003");
        c4.setTeacherID("T003");
        c4.setTeacherName(t3.getName());
        t3.assignCourse("CRS004");
        c5.setTeacherID("T005");
        c5.setTeacherName(t5.getName());
        t5.assignCourse("CRS005");
        c6.setTeacherID("T006");
        c6.setTeacherName(t6.getName());
        t6.assignCourse("CRS006");

        c1.setClassroomID("CR001");
        cr1.occupy("CRS001");
        c2.setClassroomID("CR002");
        cr2.occupy("CRS002");
        c3.setClassroomID("CR003");
        cr3.occupy("CRS003");
        c4.setClassroomID("CR004");
        cr4.occupy("CRS004");
        c5.setClassroomID("CR003");
        c6.setClassroomID("CR002");

        c1.addSchedule(new Schedule("SCH001", "CRS001", "CS-301 OOP", "MONDAY", "08:00", "09:30", "CR001"));
        c1.addSchedule(new Schedule("SCH001", "CRS001", "CS-301 OOP", "MONDAY", "01:00", "02:30", "CR002"));
        c2.addSchedule(new Schedule("SCH002", "CRS002", "CS-201 DS", "TUESDAY", "09:30", "11:30", "CR003"));
        c3.addSchedule(new Schedule("SCH003", "CRS003", "CS-401 DB", "WEDNESDAY", "08:00", "10:00", "CR001"));
        c3.addSchedule(new Schedule("SCH003", "CRS003", "CS-401 DB", "WEDNESDAY", "12:00", "13:00", "CR001"));
        c3.addSchedule(new Schedule("SCH003", "CRS003", "CS-401 DB", "WEDNESDAY", "03:00", "05:00", "CR003"));
        c4.addSchedule(new Schedule("SCH004", "CRS004", "EE-201 Circuits", "THURSDAY", "08:00", "09:30", "CR004"));
        c4.addSchedule(new Schedule("SCH004", "CRS004", "EE-201 Circuits", "THURSDAY", "10:00", "11:30", "CR002"));
        c5.addSchedule(new Schedule("SCH005", "CRS005", "PHY-101 Applied Physics", "FRIDAY", "08:00", "11:00", "CR003"));
        c6.addSchedule(new Schedule("SCH006", "CRS006", "MATH-201 Calculus", "FRIDAY", "10:00", "11:30", "CR002"));

        c1.addAssignment(new Assignment("A001", "Assignment 1 - Inheritance", "CRS001", "2024-12-01", 20));
        c1.addAssignment(new Assignment("A002", "Lab Task - Polymorphism", "CRS001", "2024-12-10", 15));
        c2.addAssignment(new Assignment("A003", "Assignment 1 - Arrays", "CRS002", "2024-12-05", 20));
        c3.addAssignment(new Assignment("A004", "DB Design Project", "CRS003", "2024-12-15", 30));

        cs.addCourse(c1);
        cs.addCourse(c2);
        cs.addCourse(c3);
        ee.addCourse(c4);
        bba.addCourse(c5);
        math.addCourse(c6);

        state.getAllCourses().add(c1);
        state.getAllCourses().add(c2);
        state.getAllCourses().add(c3);
        state.getAllCourses().add(c4);
        state.getAllCourses().add(c5);
        state.getAllCourses().add(c6);

        String[][] stuData = {
                {"S001", "Ali Hassan", "ali@student.edu", "BSCS", "D001", "student", "student"},
                {"S002", "Sara Malik", "sara@student.edu", "BSCS", "D001", "student1", "student1"},
                {"S003", "Usman Tariq", "usman@student.edu", "BSCS", "D001", "student2", "student2"},
                {"S004", "Ayesha Noor", "ayesha@student.edu", "BSEE", "D002", "student3", "student3"},
                {"S005", "Bilal Ahmed", "bilal@student.edu", "BSCS", "D001", "student4", "student4"},
                {"S006", "Fatima Zahra", "fatima@student.edu", "BSEE", "D002", "student5", "student5"},
                {"S007", "Hassan Raza", "hassan@student.edu", "BSPHY", "D003", "student6", "student6"},
                {"S008", "Zainab Ali", "zainab@student.edu", "BSPHY", "D003", "student7", "student7"},
                {"S009", "Kamran Sheikh", "kamran@student.edu", "BSCS", "D001", "student8", "student8"},
                {"S010", "Nadia Iqbal", "nadia@student.edu", "BSMATH", "D004", "student9", "student9"},
                {"S011", "Tariq Butt", "tariq@student.edu", "BSCS", "D001", "student10", "student10"},
                {"S012", "Maryam Ali", "maryam@student.edu", "BSEE", "D002", "student11", "student11"},
        };

        String[][] enroll = {
                {"CRS001", "CRS002"}, {"CRS001", "CRS003"}, {"CRS001", "CRS002", "CRS003"},
                {"CRS004"}, {"CRS001"}, {"CRS004"},
                {"CRS005"}, {"CRS005"}, {"CRS001", "CRS002"},
                {"CRS006"}, {"CRS001", "CRS003"}, {"CRS004"},
        };

        for (int i = 0; i < stuData.length; i++) {
            Student st = new Student(stuData[i][0], stuData[i][1], stuData[i][2], stuData[i][3], stuData[i][4], stuData[i][5], stuData[i][6]);

            for (String cid : enroll[i]) {
                for (Course c : state.getAllCourses()) {
                    if (c.getCourseID().equals(cid)) {
                        c.enrollStudent(st.getStudentID());
                        st.enrollInCourse(cid);
                        break;
                    }
                }
            }
            state.getStudents().add(st);

            Department d = state.getDepartmentRepository().findById((st.getDepartmentID()));
            d.incrementTotalStudents();
        }
        state.getDepartmentRepository().getAll().forEach(d -> d.getTotalStudents());

        Library lib = new Library("LIB001", "Central Library", "Block C", 200, 15000.0);
        lib.setUsageFrequency(280);
        String[][] books = {
                {"B001", "Clean Code", "Robert Martin"},
                {"B002", "Design Patterns", "Gang of Four"},
                {"B003", "OOP in Java", "Herbert Schildt"},
                {"B004", "Data Structures", "Mark Weiss"},
                {"B005", "Computer Networks", "Tanenbaum"},
                {"B006", "Operating Systems", "Silberschatz"},
                {"B007", "Database Concepts", "Korth"},
                {"B008", "Discrete Mathematics", "Rosen"},
        };
        for (String[] b : books) {
            Book bk = new Book(b[0], b[1], b[2]);
            lib.addBook(bk);
            state.getAllBooks().add(bk);
        }
        lib.checkoutBook("B001", "S001");
        lib.checkoutBook("B003", "S002");
        state.getLibraryRepository().add(lib);

        Cafeteria caf = new Cafeteria("CAF001", "Main Cafeteria", "Ground Floor", 150, 8000.0);
        caf.setUsageFrequency(420);
        state.getCafeteriaRepository().add(caf);

        Hostel boys = new Hostel("H001", "Boys Hostel A", "North Campus", 100, 8000.0, "Boys");
        Hostel girls = new Hostel("H002", "Girls Hostel B", "East Campus", 80, 8000.0, "Girls");
        boys.allocateRoom("S001");
        boys.allocateRoom("S003");
        boys.allocateRoom("S005");
        girls.allocateRoom("S002");
        girls.allocateRoom("S004");
        state.getHostelRepository().add(boys);
        state.getHostelRepository().add(girls);

        TransportService ts = new TransportService("TS001", "Campus Transport", "Main Gate", 10, 5);
        ts.addRoute("Route A - City Center");
        ts.addRoute("Route B - Railway Station");
        ts.addRoute("Route C - Gulberg");
        ts.addRoute("Route D - DHA Phase 5");
        state.getTransportServices().add(ts);

        SecurityService sec = new SecurityService("SEC001", "Campus Security", "Main Gate", 20, 30);
        sec.logIncident("Morning patrol — all clear.");
        sec.logIncident("Visitor check completed at Gate 2.");
        state.getSecurityServices().add(sec);

        HealthCenter hc = new HealthCenter("HC001", "Campus Health Center", "Block D", 15, 20, 5);
        state.getHealthCenters().add(hc);

        CampusZone zone = new CampusZone("Z001", "Main Campus Zone", "Central Campus");
        zone.addFacility(lib);
        zone.addFacility(caf);
        zone.addServiceUnit(sec);
        zone.addServiceUnit(ts);
        state.getCampusZones().add(zone);

        try {
            DataManager.save(state);
            System.out.println("[Main] Sample data seeded.");
        } catch (Exception e) {
            System.out.println("[Main] Could not save seed data: " + e.getMessage());
        }
    }
}
