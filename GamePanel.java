import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int DELAY = 20;

    private Bird bird;
    private Timer timer;
    private List<Rectangle> pipes;
    private Random random;
    private int ticks;
    private boolean gameStarted;
    private boolean gameOver;

    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.CYAN);
        setFocusable(true);
        addKeyListener(this);

        bird = new Bird(WIDTH / 4, HEIGHT / 2); // Initial position of the bird
        pipes = new ArrayList<>();
        random = new Random();
        ticks = 0;
        gameStarted = false;
        gameOver = false;

        timer = new Timer(DELAY, this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        bird.draw(g); // Draw the bird

        for (Rectangle pipe : pipes) {
            g.setColor(Color.GREEN);
            g.fillRect(pipe.x, pipe.y, pipe.width, pipe.height);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        if (!gameStarted) {
            g.drawString("Press SPACE to Start", 200, HEIGHT / 2 - 20);
        } else if (gameOver) {
            g.drawString("Game Over!", 300, HEIGHT / 2 - 20);
            g.drawString("Score: " + (ticks / 2), 320, HEIGHT / 2 + 40);
        } else {
            g.drawString("Score: " + (ticks / 2), 20, 40);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;

        if (gameStarted && !gameOver) {
            bird.update();

            if (ticks % 80 == 0) {
                int gap = 200;
                int height = random.nextInt(HEIGHT - gap - 200) + 100;
                pipes.add(new Rectangle(WIDTH + 100, 0, 80, height));
                pipes.add(new Rectangle(WIDTH + 100, height + gap, 80, HEIGHT - height - gap));
            }

            List<Rectangle> pipesToRemove = new ArrayList<>();
            for (Rectangle pipe : pipes) {
                pipe.x -= 5;
                if (pipe.x + pipe.width <= 0) {
                    pipesToRemove.add(pipe);
                }
            }
            pipes.removeAll(pipesToRemove);

            checkCollision();
        }

        repaint();
    }

    private void checkCollision() {
        Rectangle birdBounds = bird.getBounds();

        for (Rectangle pipe : pipes) {
            if (pipe.intersects(birdBounds)) {
                gameOver();
                return;
            }
        }

        if (bird.getY() >= HEIGHT || bird.getY() <= 0) {
            gameOver();
        }
    }

    private void gameOver() {
        gameOver = true;
        timer.stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            if (!gameStarted) {
                gameStarted = true;
                timer.start();
            } else if (!gameOver) {
                bird.jump();
            } else {
                restartGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Not used
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    private void restartGame() {
        bird.reset();
        pipes.clear();
        ticks = 0;
        gameOver = false;
        gameStarted = false;
        repaint();
    }
}
