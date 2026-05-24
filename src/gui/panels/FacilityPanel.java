package gui.panels;

import model.facility.Cafeteria;
import model.facility.Hostel;
import model.facility.Library;
import persistence.AppState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FacilityPanel extends JPanel {

    private AppState state;

    private JTable table;
    private DefaultTableModel model;

    private JTextField idField;
    private JTextField nameField;
    private JTextField locationField;

    private JTextField extraField1;
    private JTextField extraField2;

    private JLabel extraLabel1;
    private JLabel extraLabel2;

    private JRadioButton libraryBtn;
    private JRadioButton hostelBtn;
    private JRadioButton cafeteriaBtn;

    public FacilityPanel(AppState state) {

        this.state = state;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Facility Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 10));

        add(title, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Location", "Type", "Details"};

        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(300, 0));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        radioPanel.setBackground(Color.WHITE);

        libraryBtn = new JRadioButton("Library");
        hostelBtn = new JRadioButton("Hostel");
        cafeteriaBtn = new JRadioButton("Cafeteria");

        libraryBtn.setBackground(Color.WHITE);
        hostelBtn.setBackground(Color.WHITE);
        cafeteriaBtn.setBackground(Color.WHITE);

        libraryBtn.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(libraryBtn);
        group.add(hostelBtn);
        group.add(cafeteriaBtn);

        radioPanel.add(libraryBtn);
        radioPanel.add(hostelBtn);
        radioPanel.add(cafeteriaBtn);

        formPanel.add(radioPanel);

        idField = new JTextField();
        nameField = new JTextField();
        locationField = new JTextField();

        extraField1 = new JTextField();
        extraField2 = new JTextField();

        extraLabel1 = new JLabel("Seats");
        extraLabel2 = new JLabel("Maintenance");

        formPanel.add(createField("Facility ID", idField));
        formPanel.add(createField("Name", nameField));
        formPanel.add(createField("Location", locationField));
        formPanel.add(createCustomField(extraLabel1, extraField1));
        formPanel.add(createCustomField(extraLabel2, extraField2));

        libraryBtn.addActionListener(e -> updateFields());
        hostelBtn.addActionListener(e -> updateFields());
        cafeteriaBtn.addActionListener(e -> updateFields());

        JButton addBtn = createButton("Add Facility");
        JButton removeBtn = createButton("Remove Facility");

        JPanel buttonPanel = new JPanel();

        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        addBtn.setMaximumSize(new Dimension(180, 40));
        removeBtn.setMaximumSize(new Dimension(180, 40));

        addBtn.setBackground(new Color(46, 125, 50));
        removeBtn.setBackground(new Color(198, 40, 40));

        buttonPanel.add(addBtn);
        buttonPanel.add(Box.createVerticalStrut(12));
        buttonPanel.add(removeBtn);
        formPanel.add(buttonPanel);

        add(formPanel, BorderLayout.EAST);

        updateFields();
        loadData();

        addBtn.addActionListener(e -> {
            try {
                String id = idField.getText();
                String name = nameField.getText();
                String location = locationField.getText();

                if (libraryBtn.isSelected()) {

                    int seats = Integer.parseInt(extraField1.getText());
                    double maintenance = Double.parseDouble(extraField2.getText());

                    Library lib = new Library(id, name, location, seats, maintenance);
                    state.getLibraryRepository().add(lib);

                } else if (hostelBtn.isSelected()) {

                    int rooms = Integer.parseInt(extraField1.getText());

                    double fee = Double.parseDouble(extraField2.getText());
                    Hostel hostel = new Hostel(id, name, location, rooms, fee, "BOYS");
                    state.getHostelRepository().add(hostel);
                } else {

                    int seats = Integer.parseInt(extraField1.getText());
                    double maintenance = Double.parseDouble(extraField2.getText());

                    Cafeteria caf = new Cafeteria(id, name, location, seats, maintenance);
                    state.getCafeteriaRepository().add(caf);
                }

                loadData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Facility Added Successfully");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        removeBtn.addActionListener(e -> {

            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a facility first");
                return;
            }

            String type = model.getValueAt(row, 3).toString();
            String id = model.getValueAt(row, 0).toString();

            if (type.equals("Library")) {
                state.getLibraryRepository().remove(id);
            } else if (type.equals("Hostel")) {
                state.getHostelRepository().remove(id);
            } else {
                state.getCafeteriaRepository().remove(id);
            }

            loadData();
            JOptionPane.showMessageDialog(this, "Facility Removed");
        });
    }

    private void loadData() {

        model.setRowCount(0);

        for (Library l : state.getLibraryRepository().getAll()) {
            model.addRow(new Object[]{l.getEntityID(), l.getName(), l.getLocation(), "Library", "Books: " + l.getBooks().size()});
        }

        for (Hostel h : state.getHostelRepository().getAll()) {
            model.addRow(new Object[]{h.getEntityID(), h.getName(), h.getLocation(), "Hostel", "Rooms: " + h.getOccupiedRooms() + "/" + h.getTotalRooms()});
        }

        for (Cafeteria c : state.getCafeteriaRepository().getAll()) {
            model.addRow(new Object[]{c.getEntityID(), c.getName(), c.getLocation(), "Cafeteria", "Seats: " + c.getOccupiedSeats() + "/" + c.getTotalSeats()});
        }
    }

    private void updateFields() {

        if (libraryBtn.isSelected()) {
            extraLabel1.setText("Total Seats");
            extraLabel2.setText("Maintenance Cost");
        } else if (hostelBtn.isSelected()) {
            extraLabel1.setText("Total Rooms");
            extraLabel2.setText("Monthly Fee");
        } else {
            extraLabel1.setText("Total Seats");
            extraLabel2.setText("Maintenance Cost");
        }
    }

    private JPanel createField(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,70));
        panel.setPreferredSize(new Dimension(280, 65));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));

        field.setPreferredSize(new Dimension(200, 35));

        panel.add(lbl, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCustomField(JLabel label, JComponent field) {

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE,70));
        panel.setPreferredSize(new Dimension(280, 65));

        label.setFont(new Font("SansSerif", Font.BOLD, 13));

        field.setPreferredSize(new Dimension(200, 35));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JButton createButton(String text) {

        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(180, 40));

        return btn;
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        extraField1.setText("");
        extraField2.setText("");
    }
}