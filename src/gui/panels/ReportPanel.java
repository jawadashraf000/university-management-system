package gui.panels;

import model.academic.Course;
import model.academic.Department;
import model.facility.Library;
import persistence.AppState;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ReportPanel extends JPanel {

    private JTextArea reportArea;

    public ReportPanel(AppState state) {

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("System Reports");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.setBackground(new Color(245, 247, 250));

        JRadioButton deptBtn = new JRadioButton("Department Reports");
        JRadioButton libraryBtn = new JRadioButton("Library Reports");
        JRadioButton courseBtn = new JRadioButton("Course Reports");

        deptBtn.setBackground(new Color(245, 247, 250));
        libraryBtn.setBackground(new Color(245, 247, 250));
        courseBtn.setBackground(new Color(245, 247, 250));

        deptBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        libraryBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        courseBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

        ButtonGroup group = new ButtonGroup();
        group.add(deptBtn);
        group.add(libraryBtn);
        group.add(courseBtn);

        topPanel.add(deptBtn);
        topPanel.add(libraryBtn);
        topPanel.add(courseBtn);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        reportArea.setBackground(Color.WHITE);
        reportArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(reportArea);


        deptBtn.addActionListener(e -> {
            reportArea.setText("");

            for (Department d : state.getDepartmentRepository().getAll()) {
                reportArea.append(d.generateReport());
                reportArea.append("\n\n");
            }
        });

        libraryBtn.addActionListener(e -> {
            reportArea.setText("");

            for (Library l : state.getLibraryRepository().getAll()) {
                reportArea.append(l.generateReport());
                reportArea.append("\n\n");
            }
        });

        courseBtn.addActionListener(e -> {
            reportArea.setText("");

            for (Course c : state.getAllCourses()) {
                reportArea.append(c.generateReport());
                reportArea.append("\n\n");
            }
        });

        deptBtn.setSelected(true);

        for (Department d : state.getDepartmentRepository().getAll()) {
            reportArea.append(d.generateReport());
            reportArea.append("\n\n");
        }

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setBackground(new Color(245, 247, 250));

        northPanel.add(heading, BorderLayout.NORTH);
        northPanel.add(topPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}