package model.util;

// University Constants.

public class UniversityStats {

    private static int totalStudents = 0;
    private static int totalCourses = 0;
    private static int totalFacilityUsage = 0;
    private static int totalDepartments = 0;

    private UniversityStats() {}

    public static void incrementTotalStudents() { totalStudents++; }
    public static void decrementTotalStudents() { if (totalStudents > 0) totalStudents--; }
    public static void incrementTotalCourses()  { totalCourses++; }
    public static void decrementTotalCourses()  { if (totalCourses > 0) totalCourses--; }
    public static void incrementTotalFacilityUsage() { totalFacilityUsage++; }
    public static void incrementTotalDepartments()   { totalDepartments++; }
    public static void decrementTotalDepartments()   { if (totalDepartments > 0) totalDepartments--; }

    public static int getTotalStudents()      { return totalStudents; }
    public static int getTotalCourses()       { return totalCourses; }
    public static int getTotalFacilityUsage() { return totalFacilityUsage; }
    public static int getTotalDepartments()   { return totalDepartments; }

    public static void reset() {
        totalStudents = 0;
        totalCourses = 0;
        totalFacilityUsage = 0;
        totalDepartments = 0;
    }

    public static String getSummary() {
        return "Total Students: " + totalStudents + "\n" +
               "Total Courses: "  + totalCourses  + "\n" +
               "Total Departments: " + totalDepartments + "\n" +
               "Total Facility Usage: " + totalFacilityUsage;
    }
}
