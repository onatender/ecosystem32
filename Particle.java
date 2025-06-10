import java.awt.Graphics;

public class Particle {
    public int ticksPlayed = 0;
    public int lifetime = 100;
    protected int x;
    protected int y;

    public Particle(int lifetime,int x,int y) {
        this.lifetime = lifetime;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        
    }

    public void drawFrame(Graphics g,int tick) {

    }
}
