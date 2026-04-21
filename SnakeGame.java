import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakeGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.add(new GamePanel());
        frame.setVisible(true);
    }
}

class GamePanel extends JPanel implements ActionListener, KeyListener {
    private final int UNIT_SIZE = 30;
    private final int GAME_UNITS = 600 / UNIT_SIZE;
    private final int SCREEN_WIDTH = 600;
    private final int SCREEN_HEIGHT = 600;
    private int bodyParts = 3;
    private int[] x = new int[GAME_UNITS * GAME_UNITS];
    private int[] y = new int[GAME_UNITS * GAME_UNITS];
    private char direction = 'R';
    private boolean running = true;
    private Timer timer;
    private Random random;
    private int appleX;
    private int appleY;

    public GamePanel() {
        random = new Random();
        setBackground(Color.DARK_GRAY);
        setFocusable(true);
        addKeyListener(this);
        x[0] = 10 * UNIT_SIZE;
        y[0] = 10 * UNIT_SIZE;
        x[1] = 9 * UNIT_SIZE;
        y[1] = 10 * UNIT_SIZE;
        x[2] = 8 * UNIT_SIZE;
        y[2] = 10 * UNIT_SIZE;
        newApple();
        timer = new Timer(150, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawApple(g);
        drawSnake(g);
        drawScore(g);
        if (!running) {
            drawGameOver(g);
        }
    }

    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i <= SCREEN_WIDTH / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
        }
    }

    private void drawApple(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    }

    private void drawSnake(Graphics g) {
        for (int i = 0; i < bodyParts; i++) {
            if (i == 0) {
                g.setColor(new Color(210, 180, 140)); // tan
            } else {
                g.setColor(Color.GREEN);
            }
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + (bodyParts - 3), 10, g.getFont().getSize());
    }

    private void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + (bodyParts - 3), (SCREEN_WIDTH - metrics.stringWidth("Score: " + (bodyParts - 3))) / 2, SCREEN_HEIGHT / 2 + 40);
        g.drawString("Press R to restart", (SCREEN_WIDTH - metrics.stringWidth("Press R to restart")) / 2, SCREEN_HEIGHT / 2 + 80);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
        }
        repaint();
    }

    private void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }
        // Check apple
        if (x[0] == appleX && y[0] == appleY) {
            bodyParts++;
            newApple();
        }
        // Check collisions
        if (x[0] < 0 || x[0] >= SCREEN_WIDTH || y[0] < 0 || y[0] >= SCREEN_HEIGHT) {
            gameOver();
        }
        for (int i = bodyParts; i > 0; i--) {
            if (x[0] == x[i] && y[0] == y[i]) {
                gameOver();
            }
        }
    }

    private void gameOver() {
        running = false;
        timer.stop();
    }

    private void newApple() {
        boolean valid = false;
        while (!valid) {
            appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
            valid = true;
            for (int i = 0; i < bodyParts; i++) {
                if (x[i] == appleX && y[i] == appleY) {
                    valid = false;
                    break;
                }
            }
        }
    }

    private void reset() {
        bodyParts = 3;
        direction = 'R';
        running = true;
        x[0] = 10 * UNIT_SIZE;
        y[0] = 10 * UNIT_SIZE;
        x[1] = 9 * UNIT_SIZE;
        y[1] = 10 * UNIT_SIZE;
        x[2] = 8 * UNIT_SIZE;
        y[2] = 10 * UNIT_SIZE;
        newApple();
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (direction != 'R') direction = 'L';
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != 'L') direction = 'R';
                break;
            case KeyEvent.VK_UP:
                if (direction != 'D') direction = 'U';
                break;
            case KeyEvent.VK_DOWN:
                if (direction != 'U') direction = 'D';
                break;
            case KeyEvent.VK_R:
                if (!running) reset();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}