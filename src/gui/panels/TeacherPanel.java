package gui.panels;
import model.people.Teacher;
import persistence.AppState;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static gui.panels.PanelsHelper.createButton;
import static gui.panels.PanelsHelper.createField;

public class TeacherPanel extends JPanel {
    private AppState state;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField deptField;
    private JTextField designationField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public TeacherPanel(AppState state) {
        this.state = state;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Teacher Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 10));

        add(title, BorderLayout.NORTH);
        String[] columns = {"Teacher ID", "Name", "Email", "Department", "Designation"};

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(table);

        model.addTableModelListener(e -> {
            if (e.getType() != javax.swing.event.TableModelEvent.UPDATE) return;

            int row = e.getFirstRow();
            if (row < 0 || row >= model.getRowCount()) return;

            try {
                String teacherID = model.getValueAt(row, 0).toString();

                Teacher teacher = null;

                for (Teacher t : state.getTeachers()) {
                    if (t.getTeacherID().equals(teacherID)) {
                        teacher = t;
                        break;
                    }
                }

                if (teacher == null) return;

                teacher.setName(model.getValueAt(row, 1).toString());
                teacher.setEmail(model.getValueAt(row, 2).toString());
                teacher.setDepartmentID(model.getValueAt(row, 3).toString());
                teacher.setDesignation(model.getValueAt(row, 4).toString());

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
        emailField = new JTextField();
        deptField = new JTextField();
        designationField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(createField("Teacher ID", idField));
        formPanel.add(createField("Name", nameField));
        formPanel.add(createField("Email", emailField));
        formPanel.add(createField("Department", deptField));
        formPanel.add(createField("Designation", designationField));
        formPanel.add(createField("Username", usernameField));
        formPanel.add(createField("Password", passwordField));

        formPanel.add(Box.createVerticalStrut(12));

        JButton addBtn = createButton("Add Teacher");
        JButton removeBtn = createButton("Remove Teacher");

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

        loadTeachers();
        addBtn.addActionListener(e -> {
            try {
                if (idField.getText().isEmpty() || nameField.getText().isEmpty() || emailField.getText().isEmpty()
                        || deptField.getText().isEmpty() || designationField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getPassword().length == 0) {

                    JOptionPane.showMessageDialog(this, "Fill all fields");
                    return;
                }
                Teacher teacher = new Teacher(idField.getText(), nameField.getText(),emailField.getText(), deptField.getText(), designationField.getText(), usernameField.getText(), new String(passwordField.getPassword()));

                for (Teacher t : state.getTeachers()) {
                    if (t.getTeacherID().equals(teacher.getTeacherID())) {
                        JOptionPane.showMessageDialog(this, "Teacher ID already exists");
                        return;
                    }
                }
                state.getTeachers().add(teacher);
                model.addRow(new Object[]{teacher.getTeacherID(), teacher.getName(), teacher.getEmail(), teacher.getDepartmentID(), teacher.getDesignation()});
                clearFields();

                JOptionPane.showMessageDialog(this, "Teacher Added Successfully");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a teacher first");
                return;
            }
            String teacherID = model.getValueAt(row, 0).toString();

            Teacher removeTeacher = null;
            for (Teacher t : state.getTeachers()) {
                if (t.getTeacherID().equals(teacherID)) {
                    removeTeacher = t;
                    break;
                }
            }
            if (removeTeacher != null) {
                state.getTeachers().remove(removeTeacher);
                model.removeRow(row);
                JOptionPane.showMessageDialog(this, "Teacher Removed");
            }
        });
    }

    private void loadTeachers() {

        model.setRowCount(0);
        for (Teacher t : state.getTeachers()) {
            model.addRow(new Object[]{t.getTeacherID(), t.getName(), t.getEmail(), t.getDepartmentID(), t.getDesignation()});
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        deptField.setText("");
        designationField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }

}