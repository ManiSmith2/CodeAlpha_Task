import java.io.*;
import java.util.ArrayList;

public class StudentManager {
    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.println(student.toString());
            }
        }
    }

    public void loadFromFile(String filename) throws IOException {
        students.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    Student student = new Student(parts[0]);
                    String[] gradeParts = parts[1].split(",");
                    for (String g : gradeParts) {
                        if (!g.isEmpty()) student.grades.add(Integer.parseInt(g));
                    }
                    students.add(student);
                }
            }
        }
    }

    public String generateSummaryReport() {
        if (students.isEmpty()) return "No students available.\n";

        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Summary Report ===\n");
        for (Student student : students) {
            sb.append("Name: ").append(student.name).append("\n");
            sb.append("Grades: ").append(student.grades).append("\n");
            sb.append(String.format("Average: %.2f\n", student.calculateAverage()));
            sb.append("Highest: ").append(student.getHighestGrade()).append("\n");
            sb.append("Lowest: ").append(student.getLowestGrade()).append("\n");
            sb.append("----------------------\n");
        }
        return sb.toString();
    }
}
