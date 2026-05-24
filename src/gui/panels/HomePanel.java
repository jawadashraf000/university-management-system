package gui.panels;

import gui.components.DashboardCard;
import persistence.AppState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HomePanel extends JPanel {
    public HomePanel(AppState state) {

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(240, 244, 247));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel cardsPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        cardsPanel.setOpaque(false);

        cardsPanel.add(new DashboardCard("Students", String.valueOf(state.getStudents().size())));
        cardsPanel.add(new DashboardCard("Departments", String.valueOf(state.getDepartmentRepository().size())));
        cardsPanel.add(new DashboardCard("Teachers", String.valueOf(state.getTeachers().size())));
        cardsPanel.add(new DashboardCard("Courses", String.valueOf(state.getAllCourses().size())));

        int facilities = state.getLibraryRepository().size() + state.getHostelRepository().size() + state.getCafeteriaRepository().size();
        cardsPanel.add(new DashboardCard("Facilities", String.valueOf(facilities)));
        cardsPanel.add(new DashboardCard("Libraries", String.valueOf(state.getLibraryRepository().size())));
        cardsPanel.add(new DashboardCard("Cafeterias", String.valueOf(state.getCafeteriaRepository().size())));
        cardsPanel.add(new DashboardCard("Hostels", String.valueOf(state.getHostelRepository().size())));

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(new CampusMapPanel(state));
        centerPanel.add(new TimetablePanel(state));

        add(cardsPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
}