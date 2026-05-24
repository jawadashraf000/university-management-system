package gui.panels;
import gui.components.TablePanel;
import model.academic.Course;
import model.academic.Schedule;
import persistence.AppState;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TimetablePanel extends JPanel {
    public TimetablePanel(AppState state) {

        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Timetable");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));

        TablePanel table = new TablePanel(new String[]{"Day", "Time", "Room"});

        for (Course c : state.getAllCourses()) {
            for (Schedule s : c.getAllSchedules()) {
                table.getModel().addRow(new Object[]{s.getDay(), s.getStartTime() + " - " + s.getEndTime(), s.getRoomOrStop()});
            }
        }

        add(heading, BorderLayout.NORTH);
        add(table, BorderLayout.CENTER);
    }
}