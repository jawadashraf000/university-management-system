package gui.panels;
import persistence.AppState;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CampusMapPanel extends JPanel {
    public CampusMapPanel(AppState state) {

        setLayout(new BorderLayout(15, 15));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel heading = new JLabel("Campus Map");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 28));
        heading.setForeground(new Color(35, 35, 35));

        add(heading, BorderLayout.NORTH);

        JPanel gridPanel = new JPanel();
        gridPanel.setOpaque(false);
        gridPanel.setLayout(new GridLayout(3, 1, 10, 20));

        JPanel row1 = new JPanel(new GridLayout(1, 3, 15, 15));
        row1.setOpaque(false);
        row1.add(createMapButton("Security"));
        row1.add(createMapButton("Hospital"));
        row1.add(createMapButton("Transport"));

        JPanel row2 = new JPanel(new BorderLayout(15, 15));
        row2.setOpaque(false);
        JButton departmentBtn = createMapButton("Department Block");
        JButton libraryBtn = createMapButton("Library");

        row2.add(departmentBtn, BorderLayout.CENTER);
        row2.add(libraryBtn, BorderLayout.EAST);

        libraryBtn.setPreferredSize(new Dimension(194, 70));

        JPanel row3 = new JPanel(new GridLayout(1, 3, 15, 15));
        row3.setOpaque(false);
        row3.add(createMapButton("Cafeteria"));
        row3.add(createMapButton("Hostel"));
        row3.add(createMapButton("Sports Area"));

        gridPanel.add(row1);
        gridPanel.add(row2);
        gridPanel.add(row3);

        add(gridPanel, BorderLayout.CENTER);
    }

    private JButton createMapButton(String text) {

        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(160, 70));

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(52, 152, 219));
        UIManager.put("Button.disabledText", Color.WHITE);
        button.setBorder(new LineBorder(Color.DARK_GRAY, 1, true));

        button.setFocusPainted(false);
        button.setEnabled(false);

        return button;
    }
}