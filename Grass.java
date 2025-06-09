import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Color;

public class Grass extends Organism {


    public Grass() {
        this.clr = Color.green;
    }

    public Grass(int x, int y, int width, int height,EnvironmentInfo env) {
        super(x, y, width, height,env);
        this.clr = Color.green;
        this.NutritionValue = 25;
        this.Health = 1;
    }

    public void draw(Graphics g) {
        g.setColor(clr);
        g.fillOval(x, y, width, height);
    }
}
