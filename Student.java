import java.util.ArrayList;

public class Student {
    String name;
    ArrayList<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public double calculateAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades) sum += grade;
        return (double) sum / grades.size();
    }

    public int getHighestGrade() {
        if (grades.isEmpty()) return 0;
        int highest = grades.get(0);
        for (int grade : grades) if (grade > highest) highest = grade;
        return highest;
    }

    public int getLowestGrade() {
        if (grades.isEmpty()) return 0;
        int lowest = grades.get(0);
        for (int grade : grades) if (grade < lowest) lowest = grade;
        return lowest;
    }

    @Override
    public String toString() {
        return name + "," + grades.toString().replaceAll("[\\[\\] ]", "");
    }
}
