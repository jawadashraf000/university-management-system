package gui.panels;
import controller.StudentController;
import model.academic.Department;
import model.people.Student;
import persistence.AppState;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;

public class StudentPanel extends JPanel {
    private AppState state;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField;
    private JTextField nameField;
    private JTextField emailField;
    private JTextField programField;
    private JTextField deptField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public StudentPanel(AppState state) {
        this.state = state;
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("Student Management");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 10));
        add(title, BorderLayout.NORTH);

        String[] cols = {"ID", "Name", "Email", "Program", "Department"};
        model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };

        table = new JTable(model);

        model.addTableModelListener(e -> {
            if (e.getType() != javax.swing.event.TableModelEvent.UPDATE) return;

            int row = e.getFirstRow();
            if (row < 0 || row >= model.getRowCount()) return;

            try {
                String studentID = model.getValueAt(row, 0).toString();
                Student student = state.findStudent(studentID);

                if (student == null) return;

                student.setName(model.getValueAt(row, 1).toString());
                student.setEmail(model.getValueAt(row, 2).toString());
                student.setProgram(model.getValueAt(row, 3).toString());
                student.setDepartmentID(model.getValueAt(row, 4).toString());

            } catch (Exception ignored) {
                // Ignored Exception.
            }
        });

        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(table);

        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(300, 0));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        idField = new JTextField();
        nameField = new JTextField();
        emailField = new JTextField();
        programField = new JTextField();
        deptField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(PanelsHelper.createField("Student ID", idField));
        formPanel.add(PanelsHelper.createField("Name", nameField));
        formPanel.add(PanelsHelper.createField("Email", emailField));
        formPanel.add(PanelsHelper.createField("Program", programField));
        formPanel.add(PanelsHelper.createField("Department", deptField));
        formPanel.add(PanelsHelper.createField("Username", usernameField));
        formPanel.add(PanelsHelper.createField("Password", passwordField));
        formPanel.add(Box.createVerticalStrut(12));

        JButton addBtn = PanelsHelper.createButton("Add Student");
        JButton removeBtn = PanelsHelper.createButton("Remove Student");

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

        add(scroll, BorderLayout.CENTER);
        add(formPanel, BorderLayout.EAST);

        loadStudents();

        addBtn.addActionListener(e -> {
            try {
                if (idField.getText().isEmpty() || nameField.getText().isEmpty() || emailField.getText().isEmpty()
                        || programField.getText().isEmpty() || deptField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getPassword().length == 0) {

                    JOptionPane.showMessageDialog(this, "Fill all fields");
                    return;
                }

                Student s = new Student(idField.getText(), nameField.getText(), emailField.getText(),programField.getText(), deptField.getText(), usernameField.getText(), new String(passwordField.getPassword()));

                Department d = state.getDepartmentRepository().findById((deptField.getText()));
                d.incrementTotalStudents();

                StudentController controller = new StudentController(state);
                if (controller.addStudent(s)) {
                    model.addRow(new Object[]{s.getStudentID(), s.getName(), s.getEmail(), s.getProgram(), s.getDepartmentID()});
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Student Added Successfully");
                }
                else {
                    JOptionPane.showMessageDialog(this, "Student ID already exists");
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a student first");
                return;
            }

            String deptID = model.getValueAt(row, 4).toString();
            Department d = state.getDepartmentRepository().findById((deptID));
            d.decrementTotalStudents();

            String id = model.getValueAt(row, 0).toString();
            StudentController controller = new StudentController(state);
            if (controller.removeStudent(id)) {
                model.removeRow(row);

                JOptionPane.showMessageDialog(this, "Student Removed");
            }
        });
    }

    private void loadStudents() {

        model.setRowCount(0);
        for (Student s : state.getStudents()) {
            model.addRow(new Object[]{s.getStudentID(), s.getName(), s.getEmail(), s.getProgram(), s.getDepartmentID()});
        }
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        programField.setText("");
        deptField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }
}