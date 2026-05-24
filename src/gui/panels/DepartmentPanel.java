package gui.panels;
import controller.CourseController;
import model.academic.Department;
import persistence.AppState;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static gui.panels.PanelsHelper.createButton;
import static gui.panels.PanelsHelper.createField;

public class DepartmentPanel extends JPanel {
    private AppState state;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField;
    private JTextField nameField;
    private JTextField locationField;
    private JTextField codeField;

    public DepartmentPanel(AppState state) {
        this.state = state;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));
        JLabel title = new JLabel("Department Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 10));

        add(title, BorderLayout.NORTH);
        String[] columns = {"Department ID", "Name", "Location", "Code"};
        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);

        model.addTableModelListener(e -> {

            if (e.getType() != javax.swing.event.TableModelEvent.UPDATE) return;

            int row = e.getFirstRow();
            if (row < 0 || row >= model.getRowCount()) return;

            try {
                String deptID = model.getValueAt(row, 0).toString();
                Department dept = state.getDepartmentRepository().findById(deptID);

                if (dept == null) return;

                dept.setName(model.getValueAt(row, 1).toString());
                dept.setLocation(model.getValueAt(row, 2).toString());
                dept.setDepartmentCode(model.getValueAt(row, 3).toString());

            } catch (Exception ignored) {
                // Ignored Exception.
            }
        });

        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(300, 0));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        idField = new JTextField();
        nameField = new JTextField();
        locationField = new JTextField();
        codeField = new JTextField();

        formPanel.add(createField("Department ID", idField));
        formPanel.add(createField("Department Name", nameField));
        formPanel.add(createField("Location", locationField));
        formPanel.add(createField("Department Code", codeField));
        formPanel.add(Box.createVerticalStrut(12));

        JButton addBtn = createButton("Add Department");
        JButton removeBtn = createButton("Remove Department");

        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        addBtn.setMaximumSize(new Dimension(180, 40));
        removeBtn.setMaximumSize(new Dimension(180, 40));
        addBtn.setBackground(new Color(46, 125, 50));
        removeBtn.setBackground(new Color(198, 40, 40));

        formPanel.add(addBtn);
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(removeBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.EAST);

        loadDepartments();
        addBtn.addActionListener(e -> {

            try {
                if (idField.getText().isEmpty() || nameField.getText().isEmpty() || locationField.getText().isEmpty() || codeField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Fill all fields");
                    return;
                }

                Department dept = new Department(idField.getText(), nameField.getText(), locationField.getText(), codeField.getText());
                state.getDepartmentRepository().add(dept);
                model.addRow(new Object[]{dept.getEntityID(), dept.getName(), dept.getLocation(), dept.getDepartmentCode()});

                clearFields();
                JOptionPane.showMessageDialog(this, "Department Added Successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a department first");
                return;
            }
            String deptID = model.getValueAt(row, 0).toString();
            boolean removed = state.getDepartmentRepository().remove(deptID);
            if (removed) {
                model.removeRow(row);
                JOptionPane.showMessageDialog(this, "Department Removed");
            }
        });
    }

    private void loadDepartments() {
        model.setRowCount(0);
        for (Department d : state.getDepartmentRepository().getAll()) {
            model.addRow(new Object[]{d.getEntityID(), d.getName(), d.getLocation(), d.getDepartmentCode()});
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        locationField.setText("");
        codeField.setText("");
    }
}