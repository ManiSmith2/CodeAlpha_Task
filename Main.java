public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            StudentManager manager = new StudentManager();
            new StudentGradesGUI(manager);
        });
    }
}
