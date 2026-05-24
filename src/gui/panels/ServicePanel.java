package gui.panels;

import model.service.HealthCenter;
import model.service.SecurityService;
import model.service.TransportService;
import persistence.AppState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ServicePanel extends JPanel {

    private AppState state;

    private JTable table;
    private DefaultTableModel model;

    private JTextField idField;
    private JTextField nameField;
    private JTextField locationField;
    private JTextField staffField;

    private JTextField extraField1;
    private JTextField extraField2;

    private JLabel extraLabel1;
    private JLabel extraLabel2;

    private JRadioButton transportBtn;
    private JRadioButton healthBtn;
    private JRadioButton securityBtn;

    public ServicePanel(AppState state) {

        this.state = state;

        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));


        JLabel title = new JLabel("Service Management");
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

        transportBtn = new JRadioButton("Transport");
        healthBtn = new JRadioButton("Health");
        securityBtn = new JRadioButton("Security");

        transportBtn.setBackground(Color.WHITE);
        healthBtn.setBackground(Color.WHITE);
        securityBtn.setBackground(Color.WHITE);

        transportBtn.setSelected(true);

        ButtonGroup group = new ButtonGroup();
        group.add(transportBtn);
        group.add(healthBtn);
        group.add(securityBtn);

        radioPanel.add(transportBtn);
        radioPanel.add(healthBtn);
        radioPanel.add(securityBtn);

        formPanel.add(radioPanel);

        idField = new JTextField();
        nameField = new JTextField();
        locationField = new JTextField();
        staffField = new JTextField();

        extraField1 = new JTextField();
        extraField2 = new JTextField();

        extraLabel1 = new JLabel("Vehicles");
        extraLabel2 = new JLabel("Routes");

        formPanel.add(createField("Service ID", idField));
        formPanel.add(createField("Name", nameField));
        formPanel.add(createField("Location", locationField));
        formPanel.add(createField("Staff Count", staffField));
        formPanel.add(createCustomField(extraLabel1, extraField1));
        formPanel.add(createCustomField(extraLabel2, extraField2));

        transportBtn.addActionListener(e -> updateFields());
        healthBtn.addActionListener(e -> updateFields());
        securityBtn.addActionListener(e -> updateFields());

        formPanel.add(Box.createVerticalStrut(12));

        JButton addBtn = createButton("Add Service");
        JButton removeBtn = createButton("Remove Service");

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

                int staff = Integer.parseInt(staffField.getText());

                if (transportBtn.isSelected()) {

                    int vehicles = Integer.parseInt(extraField1.getText());
                    TransportService ts = new TransportService(id, name, location, staff, vehicles);
                    ts.addRoute(extraField2.getText());

                    state.getTransportServices().add(ts);
                } else if (healthBtn.isSelected()) {

                    int beds = Integer.parseInt(extraField1.getText());
                    int doctors = Integer.parseInt(extraField2.getText());
                    HealthCenter hc = new HealthCenter(id, name, location, staff, beds, doctors);

                    state.getHealthCenters().add(hc);
                } else {

                    int cameras = Integer.parseInt(extraField1.getText());
                    SecurityService ss = new SecurityService(id, name, location, staff, cameras);

                    state.getSecurityServices().add(ss);
                }

                loadData();
                clearFields();

                JOptionPane.showMessageDialog(this, "Service Added Successfully");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        removeBtn.addActionListener(e -> {

            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a service first");
                return;
            }

            String type = model.getValueAt(row, 3).toString();
            String id = model.getValueAt(row, 0).toString();

            if (type.equals("Transport")) {
                for (TransportService s : state.getTransportServices()) {
                    if (s.getEntityID().equals(id)) {
                        state.getTransportServices().remove(s);
                        break;
                    }
                }

            } else if (type.equals("Health")) {
                for (HealthCenter s : state.getHealthCenters()) {
                    if (s.getEntityID().equals(id)) {
                        state.getHealthCenters().remove(s);
                        break;
                    }
                }
            } else {
                for (SecurityService s : state.getSecurityServices()) {
                    if (s.getEntityID().equals(id)) {
                        state.getSecurityServices().remove(s);
                        break;
                    }
                }
            }

            loadData();

            JOptionPane.showMessageDialog(this, "Service Removed");
        });
    }

    private void loadData() {

        model.setRowCount(0);

        for (TransportService t : state.getTransportServices()) {
            model.addRow(new Object[]{t.getEntityID(), t.getName(), t.getLocation(), "Transport", "Vehicles: " + t.getTotalVehicles()});
        }

        for (HealthCenter h : state.getHealthCenters()) {
            model.addRow(new Object[]{h.getEntityID(), h.getName(), h.getLocation(), "Health", "Beds: " + h.getTotalBeds()});
        }

        for (SecurityService s : state.getSecurityServices()) {
            model.addRow(new Object[]{s.getEntityID(), s.getName(), s.getLocation(), "Security", "Cameras: " + s.getTotalCameras()});
        }
    }


    private void updateFields() {

        if (transportBtn.isSelected()) {
            extraLabel1.setText("Vehicles");
            extraLabel2.setText("Route");
        } else if (healthBtn.isSelected()) {
            extraLabel1.setText("Beds");
            extraLabel2.setText("Doctors");
        } else {
            extraLabel1.setText("Cameras");
            extraLabel2.setText("Extra");
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
        btn.setMaximumSize(new Dimension(180, 40));

        return btn;
    }

    private void clearFields() {

        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        staffField.setText("");
        extraField1.setText("");
        extraField2.setText("");

    }
}