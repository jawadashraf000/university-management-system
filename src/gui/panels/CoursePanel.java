package gui.panels;
import model.academic.Course;
import model.academic.Department;
import persistence.AppState;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static gui.panels.PanelsHelper.createButton;
import static gui.panels.PanelsHelper.createField;

public class CoursePanel extends JPanel {
    private AppState state;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField;
    private JTextField nameField;
    private JTextField deptField;
    private JTextField creditField;
    private JTextField maxField;

    public CoursePanel(AppState state){
        this.state = state;

        setLayout(new BorderLayout());
        setBackground(new Color(245,247,250));
        JLabel title = new JLabel("Course Management");
        title.setFont(new Font("SansSerif",Font.BOLD,26));
        title.setBorder(BorderFactory.createEmptyBorder(15,20,15,10));

        add(title,BorderLayout.NORTH);

        String[] cols = {"Course ID", "Name", "Department", "Credits", "Max Enroll"};
        model = new DefaultTableModel(cols,0){

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

            String id = model.getValueAt(row, 0).toString();
            Course c = state.findCourse(id);
            if (c == null) return;

            try {
                c.setCourseName(model.getValueAt(row,1).toString());
                c.setDepartmentID(model.getValueAt(row,2).toString());
                c.setCreditHours(Integer.parseInt(model.getValueAt(row,3).toString()));
                c.setMaxEnrollment(Integer.parseInt(model.getValueAt(row,4).toString()));
            } catch (Exception ex) {
                // Ignored Exception.
            }
        });

        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(table);
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(300,0));
        formPanel.setLayout(new BoxLayout(formPanel,BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        idField = new JTextField();
        nameField = new JTextField();
        deptField = new JTextField();
        creditField = new JTextField();
        maxField = new JTextField();

        formPanel.add(createField("Course ID", idField));
        formPanel.add(createField("Course Name", nameField));
        formPanel.add(createField("Department", deptField));
        formPanel.add(createField("Credit Hours", creditField));
        formPanel.add(createField("Max Enrollment", maxField));

        formPanel.add(Box.createVerticalStrut(12));
        JButton addBtn = createButton("Add Course");
        JButton removeBtn = createButton("Remove Course");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        addBtn.setBackground(new Color(46, 125, 50));
        removeBtn.setBackground(new Color(198, 40, 40));

        addBtn.setMaximumSize(new Dimension(180, 40));
        removeBtn.setMaximumSize(new Dimension(180, 40));

        buttonPanel.add(addBtn);
        buttonPanel.add(Box.createVerticalStrut(12));
        buttonPanel.add(removeBtn);
        formPanel.add(buttonPanel);

        add(scroll,BorderLayout.CENTER);
        add(formPanel,BorderLayout.EAST);
        loadCourses();

        addBtn.addActionListener(e -> {
            try {
                if (idField.getText().isEmpty() || nameField.getText().isEmpty() || deptField.getText().isEmpty()
                        || creditField.getText().isEmpty() || maxField.getText().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Fill all fields");
                    return;
                }

                Course c = new Course(idField.getText(), nameField.getText(), deptField.getText(), Integer.parseInt(creditField.getText()), Integer.parseInt(maxField.getText()));

                state.getAllCourses().add(c);
                JOptionPane.showMessageDialog(this, "Course Added Successfully");
                Department d = state.getDepartmentRepository().findById(deptField.getText());
                d.addCourse(c);

                model.addRow(new Object[]{c.getCourseID(), c.getCourseName(), c.getDepartmentID(), c.getCreditHours(), c.getMaxEnrollment()});
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        removeBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row == -1) return;
            String id = model.getValueAt(row,0).toString();

            String deptID = model.getValueAt(row, 2).toString();
            Department d = state.getDepartmentRepository().findById(deptID);
            d.removeCourse(id);

            for (int i = 0; i < state.getAllCourses().size(); i++) {
                Course c = state.getAllCourses().get(i);

                if (c.getCourseID().equals(id)) {
                    state.getAllCourses().remove(i);
                    break;
                }
            }

            model.removeRow(row);

            JOptionPane.showMessageDialog(this, "Course Removed");
        });
    }

    private void loadCourses(){
        model.setRowCount(0);
        for(Course c : state.getAllCourses()){
            model.addRow(new Object[]{c.getCourseID(), c.getCourseName(), c.getDepartmentID(), c.getCreditHours(), c.getMaxEnrollment()});
        }
    }

    private void clearFields(){
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
        creditField.setText("");
        maxField.setText("");

    }
}