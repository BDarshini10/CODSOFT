import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Represents a class Student
class Student implements Serializable {
    String name;
    String rollNo;
    String grade;

    public Student(String name, String rollNo, String grade) {
        this.name = name;
        this.rollNo = rollNo;
        this.grade = grade;
    }

    public String[] toArray() {
        return new String[]{name, rollNo, grade};
    }
}

// Main class GUI-based 
public class StudentManagementSystem extends JFrame {

    // List to store all students
    private ArrayList<Student> students = new ArrayList<>();

    // UI Components
    private DefaultTableModel model;
    private JTable table;
    private JTextField nameField, rollNoField, gradeField;

    public StudentManagementSystem() {
        // Setup the frame
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        nameField = new JTextField();
        rollNoField = new JTextField();
        gradeField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Roll No:"));
        formPanel.add(rollNoField);
        formPanel.add(new JLabel("Grade:"));
        formPanel.add(gradeField);

        // Add and Edit buttons
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit Selected");
        formPanel.add(addButton);
        formPanel.add(editButton);

        add(formPanel, BorderLayout.NORTH);

        // Center: Table to display students
        model = new DefaultTableModel(new String[]{"Name", "Roll No", "Grade"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        //Bottom Panel: Action buttons
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete Selected");
        JButton searchButton = new JButton("Search");
        JButton displayButton = new JButton("Display All");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Load existing students from file
        loadStudents();
        refreshTable();

        // Button Actions

        // Add Student
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String roll = rollNoField.getText().trim();
            String grade = gradeField.getText().trim();

            // Validate input
            if (name.isEmpty() || roll.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            students.add(new Student(name, roll, grade));
            saveStudents();
            refreshTable();
            clearFields();
        });

        // Edit Student
        editButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to edit.");
                return;
            }

            String name = nameField.getText().trim();
            String roll = rollNoField.getText().trim();
            String grade = gradeField.getText().trim();

            if (name.isEmpty() || roll.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            students.set(selectedRow, new Student(name, roll, grade));
            saveStudents();
            refreshTable();
            clearFields();
        });

        // Delete Student
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a student to delete.");
                return;
            }

            students.remove(selectedRow);
            saveStudents();
            refreshTable();
        });

        // Search Student by Roll Number
        searchButton.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(this, "Enter roll number to search:");
            if (keyword == null || keyword.trim().isEmpty()) return;

            model.setRowCount(0);
            for (Student s : students) {
                if (s.rollNo.equalsIgnoreCase(keyword.trim())) {
                    model.addRow(s.toArray());
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Student not found.");
        });

        // Display All Students
        displayButton.addActionListener(e -> refreshTable());

        // Exit Application
        exitButton.addActionListener(e -> System.exit(0));

        // Fill form 
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                nameField.setText(model.getValueAt(row, 0).toString());
                rollNoField.setText(model.getValueAt(row, 1).toString());
                gradeField.setText(model.getValueAt(row, 2).toString());
            }
        });
    }

    // Clear form fields
    private void clearFields() {
        nameField.setText("");
        rollNoField.setText("");
        gradeField.setText("");
    }

    // Refresh table to show current students
    private void refreshTable() {
        model.setRowCount(0);
        for (Student s : students) {
            model.addRow(s.toArray());
        }
    }

    // Save students to file
    private void saveStudents() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            out.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load students from file
    private void loadStudents() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.dat"))) {
            students = (ArrayList<Student>) in.readObject();
        } catch (Exception e) {
            // If file doesn't exist or is corrupted, start fresh
            students = new ArrayList<>();
        }
    }

    // Main method to start
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem().setVisible(true));
    }
}
