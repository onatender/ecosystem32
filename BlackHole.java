import java.awt.Color;
import java.awt.Graphics;

public class BlackHole extends Creature {
        public BlackHole(int x, int y, int width, int height,EnvironmentInfo env) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.env = env;

        
   
    }

     public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);

        
    }
}
