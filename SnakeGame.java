import javax.swing.JFrame;
import javax.swing.JPanel;

public class SnakeGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new GamePanel());
        frame.setVisible(true);
    }
}

class GamePanel extends JPanel {
    // Game logic will be added here later
}