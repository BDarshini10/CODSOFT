import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StudentGradeCalculatorGUI extends JFrame implements ActionListener {

    private JTextField[] subjectFields;
    private JButton calculateButton;
    private JTextArea resultArea;
    private int numSubjects = 5;

    public StudentGradeCalculatorGUI() {
        setTitle("Student Grade Calculator");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Enter marks out of 100 for " + numSubjects + " subjects:"));

        subjectFields = new JTextField[numSubjects];
        for (int i = 0; i < numSubjects; i++) {
            subjectFields[i] = new JTextField(10);
            add(new JLabel("Subject " + (i + 1) + ":"));
            add(subjectFields[i]);
        }

        calculateButton = new JButton("Calculate Grade");
        calculateButton.addActionListener(this);
        add(calculateButton);

        resultArea = new JTextArea(6, 30);
        resultArea.setEditable(false);
        add(resultArea);

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int total = 0;
        boolean validInput = true;

        for (int i = 0; i < numSubjects; i++) {
            try {
                int marks = Integer.parseInt(subjectFields[i].getText());

                if (marks < 0 || marks > 100) {
                    throw new NumberFormatException();
                }

                total += marks;
            } catch (NumberFormatException ex) {
                resultArea.setText("Please enter valid marks (0-100) for all subjects.");
                validInput = false;
                break;
            }
        }

        if (validInput) {
            double average = (double) total / numSubjects;
            String grade;

            if (average >= 90) {
                grade = "A+";
            } else if (average >= 80) {
                grade = "A";
            } else if (average >= 70) {
                grade = "B";
            } else if (average >= 60) {
                grade = "C";
            } else if (average >= 50) {
                grade = "D";
            } else {
                grade = "F (Fail)";
            }

            resultArea.setText("Total Marks: " + total + " / " + (numSubjects * 100)
                    + "\nAverage: " + String.format("%.2f", average) + "%"
                    + "\nGrade: " + grade);
        }
    }

    public static void main(String[] args) {
        new StudentGradeCalculatorGUI();
    }
}
