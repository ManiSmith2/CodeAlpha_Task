import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class StudentGradesGUI {
    private JFrame frame;
    private JTextField nameField, gradeField;
    private JTextArea outputArea;
    private StudentManager manager;
    private Student currentStudent;

    private final Color BG_COLOR = new Color(34, 45, 65);
    private final Color PANEL_COLOR = new Color(44, 62, 80);
    private final Color BTN_COLOR = new Color(52, 152, 219);
    private final Color TEXT_COLOR = Color.WHITE;

    public StudentGradesGUI(StudentManager manager) {
        this.manager = manager;
        frame = new JFrame("Student Grades Management System");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(BG_COLOR);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        inputPanel.setBackground(PANEL_COLOR);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Student Name:");
        nameLabel.setForeground(TEXT_COLOR);
        inputPanel.add(nameLabel);

        nameField = new JTextField();
        inputPanel.add(nameField);

        JLabel gradeLabel = new JLabel("Grade (Enter one at a time):");
        gradeLabel.setForeground(TEXT_COLOR);
        inputPanel.add(gradeLabel);

        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addGradeBtn = new JButton("Add Grade");
        JButton addStudentBtn = new JButton("Add Student");
        styleButton(addGradeBtn);
        styleButton(addStudentBtn);
        inputPanel.add(addGradeBtn);
        inputPanel.add(addStudentBtn);

        JButton showReportBtn = new JButton("Show Summary Report");
        JButton saveBtn = new JButton("Save to File");
        JButton loadBtn = new JButton("Load from File");
        styleButton(showReportBtn);
        styleButton(saveBtn);
        styleButton(loadBtn);
        inputPanel.add(showReportBtn);
        inputPanel.add(saveBtn);
        inputPanel.add(loadBtn);

        outputArea = new JTextArea();
        outputArea.setBackground(new Color(30, 40, 55));
        outputArea.setForeground(Color.GREEN);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        addGradeBtn.addActionListener(e -> addGrade());
        addStudentBtn.addActionListener(e -> addStudent());
        showReportBtn.addActionListener(e -> showSummaryReport());
        saveBtn.addActionListener(e -> saveToFile());
        loadBtn.addActionListener(e -> loadFromFile());

        frame.setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(BTN_COLOR);
        button.setForeground(TEXT_COLOR);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 13));
    }

    private void addGrade() {
        String gradeText = gradeField.getText().trim();
        if (currentStudent == null) {
            JOptionPane.showMessageDialog(frame, "Please enter student name first.");
            return;
        }
        try {
            int grade = Integer.parseInt(gradeText);
            if (grade < 0 || grade > 100) throw new NumberFormatException();
            currentStudent.grades.add(grade);
            outputArea.append("Added grade: " + grade + " for " + currentStudent.name + "\n");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid grade (0-100).");
        }
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter the student's name.");
            return;
        }
        currentStudent = new Student(name);
        manager.addStudent(currentStudent);
        outputArea.append("Student added: " + name + "\n");
        nameField.setText("");
        gradeField.setText("");
    }

    private void showSummaryReport() {
        outputArea.append(manager.generateSummaryReport());
    }

    private void saveToFile() {
        try {
            manager.saveToFile("students.csv");
            JOptionPane.showMessageDialog(frame, "Data saved to students.csv");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error saving file: " + ex.getMessage());
        }
    }

    private void loadFromFile() {
        try {
            manager.loadFromFile("students.csv");
            outputArea.append("Loaded data from students.csv\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading file: " + ex.getMessage());
        }
    }
}
