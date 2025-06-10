import java.awt.Graphics;

public class HeartParticles extends Particle {

    public HeartParticles(int lifetime, int x, int y) {
        super(lifetime, x, y);
        this.x = x;
        this.y = y;

    }

    public void drawFrame(Graphics g, int tick) {
        int speed = 2;
        g.drawImage(
                new javax.swing.ImageIcon("heart.png").getImage(),
                x - 25, y-10-tick*speed, 10, 10, null);

        if ((float)tick/lifetime > 0.3)

        g.drawImage(
                new javax.swing.ImageIcon("heart.png").getImage(),
                x + 0, y-20-tick*speed, 10, 10, null);

                if ((float)tick/lifetime > 0.6)
        g.drawImage(
                new javax.swing.ImageIcon("heart.png").getImage(),
                x + 25, y-10-tick*speed, 10, 10, null);

    }

    public void draw(Graphics g) {
        this.drawFrame(g, ticksPlayed);
        ticksPlayed++;
    }
}
