package controller;

import model.people.*;
import persistence.AppState;

public class AuthenticationController {

    private AppState state;
    private Object currentUser;
    private String currentRole;

    public AuthenticationController(AppState state) {
        this.state = state;
    }

    public boolean login(String username, String password) {
        for (Admin a : state.getAdmins()) {
            if (a.authenticate(username, password)) {
                currentUser = a;
                currentRole = "ADMIN";
                return true;
            }
        }

        for (Teacher t : state.getTeachers()) {
            if (t.authenticate(username, password)) {
                currentUser = t;
                currentRole = "TEACHER";
                return true;
            }
        }

        for (Student s : state.getStudents()) {
            if (s.authenticate(username, password)) {
                currentUser = s;
                currentRole = "STUDENT";
                return true;
            }
        }

        currentUser = null;
        currentRole = null;
        return false;
    }

    public void logout() {
        currentUser = null;
        currentRole = null;
    }

    public boolean isLoggedIn()    { return currentUser != null; }
    public String getCurrentRole() { return currentRole; }
    public Object getCurrentUser() { return currentUser; }
    public String getCurrentUserName() {
        if (currentUser instanceof Admin) return ((Admin) currentUser).getName();
        if (currentUser instanceof Teacher) return ((Teacher) currentUser).getName();
        if (currentUser instanceof Student) return ((Student) currentUser).getName();
        return "USER";
    }
}