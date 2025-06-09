import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.awt.Color;

public class Creature {
    public int x;
    public int y;
    public int width;
    public int height;
    public Color clr;
    public EnvironmentInfo env;

    public Creature(int x, int y, int width, int height,EnvironmentInfo environment) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.env = environment;
    }

    public boolean tryCreate(Creature crt) {
        if (crt.isMovePossible(crt.x, crt.y)) {
            env.creatures.add(crt);
            return true;
        }

        System.out.println("yaratma girişimi başarısız.");
        return false;
    }

    public boolean isMovePossible(int new_x, int new_y) {
        // if (new_x < 0 || new_x > env.ekran.getWidth()) return false;
        // if (new_y < 0 || new_y > env.ekran.getHeight() ) return false;
        for (Creature creature : env.creatures) {
            if (creature instanceof Grass) continue;
            if (creature == this)
                continue;
            if (new Rectangle(new_x, new_y, width, height)
                    .intersects(new Rectangle(creature.x, creature.y, creature.width, creature.height)))
                return false;
        }
        return true;

    }

    public Creature() {

    }

    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, width, height);
    }


    public float CalculateDistance(Creature ctr2) {
        // orta noktalar alınarak geliştirilebilir
        int x_difference = Math.abs(this.x - ctr2.x);
        int y_difference = Math.abs(this.y - ctr2.y);

        float distance = (float)Math.sqrt(Math.abs((x_difference * x_difference) + (y_difference * y_difference)));
        return distance;
    }
}
