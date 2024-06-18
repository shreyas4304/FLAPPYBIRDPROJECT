import java.awt.*;

public class Bird {

    private static final int SIZE = 20; // Size of the bird
    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -18;

    private int x;
    private int y;
    private int velocity;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        velocity = 0;
    }

    public void update() {
        velocity += GRAVITY;
        y += velocity;
    }

    public void draw(Graphics g) {
        // Draw bird shape (crow-like)
        g.setColor(Color.BLACK);

        // Body
        g.fillOval(x, y, SIZE, SIZE);

        // Head
        g.fillOval(x + 10, y - 5, SIZE - 10, SIZE - 10);

        // Beak
        int[] beakX = {x + 20, x + 22, x + 25};
        int[] beakY = {y - 1, y + 5, y + 1};
        g.setColor(Color.ORANGE);
        g.fillPolygon(beakX, beakY, 3);

        // Eyes
        g.setColor(Color.WHITE);
        g.fillOval(x + 15, y - 2, 4, 4);

        // Wings
        g.setColor(Color.BLACK);
        g.fillPolygon(new int[]{x + SIZE - 5, x + SIZE + 5, x + SIZE - 5}, new int[]{y + 5, y + SIZE / 2, y + SIZE - 5}, 3);
        g.fillPolygon(new int[]{x - 5, x + 5, x - 5}, new int[]{y + 5, y + SIZE / 2, y + SIZE - 5}, 3);
    }

    public void jump() {
        velocity = JUMP_STRENGTH;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, SIZE, SIZE);
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
