import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bird {

    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -18;

    private int x;
    private int y;
    private int velocity;
    private BufferedImage birdImage;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        velocity = 0;
        try {
            birdImage = ImageIO.read(getClass().getResource("/bird.png")); // Load the bird image
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        velocity += GRAVITY;
        y += velocity;
    }

    public void draw(Graphics g) {
        if (birdImage != null) {
            g.drawImage(birdImage, x, y, null); // Draw the bird image
        } else {
            // If the image fails to load, draw a placeholder shape
            g.setColor(Color.BLACK);
            g.fillOval(x, y, 20, 20);
        }
    }

    public void jump() {
        velocity = JUMP_STRENGTH;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, birdImage.getWidth(), birdImage.getHeight());
    }

    public int getY() {
        return y;
    }

    public void reset() {
        x = 100;
        y = 300;
        velocity = 0;
    }
}
