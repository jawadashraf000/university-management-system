package gui;

import controller.AuthenticationController;
import gui.components.SidebarButton;
import gui.panels.*;
import persistence.*;
import javax.swing.*;
import java.awt.*;

public class MainDashboard extends JFrame {

    private JPanel contentPanel;

    public MainDashboard(AppState state, AuthenticationController auth) {

        AutoSaveTimer autoSave = new AutoSaveTimer(state, 5);

        setTitle("University Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel sidebar = new JPanel();

        sidebar.setBackground(new Color(0, 0, 128));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new GridLayout(10, 1, 0, 5));

        SidebarButton dashboardBtn = new SidebarButton("Dashboard");
        SidebarButton studentBtn = new SidebarButton("Students");
        SidebarButton teacherBtn = new SidebarButton("Teachers");
        SidebarButton departmentBtn = new SidebarButton("Departments");
        SidebarButton courseBtn = new SidebarButton("Courses");
        SidebarButton facilityBtn = new SidebarButton("Facilities");
        SidebarButton servicesBtn = new SidebarButton("Services");
        SidebarButton timetableBtn = new SidebarButton("Timetable");
        SidebarButton reportBtn = new SidebarButton("Reports");
        SidebarButton logoutBtn = new SidebarButton("Logout");

        if (auth.getCurrentRole().equals("STUDENT")) {
            sidebar.add(dashboardBtn);
            sidebar.add(timetableBtn);
            sidebar.add(reportBtn);
            sidebar.add(logoutBtn);
        } else if (auth.getCurrentRole().equals("TEACHER")) {
            sidebar.add(dashboardBtn);
            sidebar.add(studentBtn);
            sidebar.add(courseBtn);
            sidebar.add(timetableBtn);
            sidebar.add(reportBtn);
            sidebar.add(logoutBtn);
        } else if (auth.getCurrentRole().equals("ADMIN")) {
            sidebar.add(dashboardBtn);
            sidebar.add(studentBtn);
            sidebar.add(teacherBtn);
            sidebar.add(departmentBtn);
            sidebar.add(courseBtn);
            sidebar.add(facilityBtn);
            sidebar.add(servicesBtn);
            sidebar.add(timetableBtn);
            sidebar.add(reportBtn);
            sidebar.add(logoutBtn);
        }

        JScrollPane sideScroll = new JScrollPane(sidebar);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(new HomePanel(state), BorderLayout.CENTER);

        add(sideScroll, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        dashboardBtn.addActionListener(e -> switchPanel(new HomePanel(state)));
        studentBtn.addActionListener(e -> switchPanel(new StudentPanel(state)));
        teacherBtn.addActionListener(e -> switchPanel(new TeacherPanel(state)));
        departmentBtn.addActionListener(e -> switchPanel(new DepartmentPanel(state)));
        courseBtn.addActionListener(e -> switchPanel(new CoursePanel(state)));
        facilityBtn.addActionListener(e -> switchPanel(new FacilityPanel(state)));
        servicesBtn.addActionListener(e -> switchPanel(new ServicePanel(state)));
        timetableBtn.addActionListener(e -> switchPanel(new TimetablePanel(state)));
        reportBtn.addActionListener(e -> switchPanel(new ReportPanel(state)));
        logoutBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Save changes before logout?", "Save", JOptionPane.YES_NO_CANCEL_OPTION);

            if (choice == JOptionPane.CANCEL_OPTION) return;
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    DataManager.save(state);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
                }
            }

            dispose();
            auth.logout();
            autoSave.stop();

            new LoginFrame(state);
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                int choice = JOptionPane.showConfirmDialog(MainDashboard.this, "Save before exiting?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);

                if (choice == JOptionPane.CANCEL_OPTION) return;
                if (choice == JOptionPane.YES_OPTION) {
                    try {
                        DataManager.save(state);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MainDashboard.this, "Save failed: " + ex.getMessage());
                    }
                }

                autoSave.stop();
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void switchPanel(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}