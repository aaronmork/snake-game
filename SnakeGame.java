import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

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
    private final int UNIT_SIZE = 30;
    private final int GAME_UNITS = 600 / UNIT_SIZE;
    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private int bodyParts = 3;
    private int[] x = new int[GAME_UNITS * GAME_UNITS];
    private int[] y = new int[GAME_UNITS * GAME_UNITS];

    public GamePanel() {
        setBackground(Color.DARK_GRAY);
        x[0] = 10 * UNIT_SIZE;
        y[0] = 10 * UNIT_SIZE;
        x[1] = 9 * UNIT_SIZE;
        y[1] = 10 * UNIT_SIZE;
        x[2] = 8 * UNIT_SIZE;
        y[2] = 10 * UNIT_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawSnake(g);
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i <= SCREEN_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
    }

    private void drawSnake(Graphics g) {
        g.setColor(Color.GREEN);
        for (int i = 0; i < bodyParts; i++) {
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }
}