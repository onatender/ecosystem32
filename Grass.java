import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Grass extends Organism {

    public Grass() {
        this.clr = Color.green;
    }

    public void GiveBirth() {
        Random rnd = new Random();
        this.PregnancyTime++;

        if (this.PregnancyTime > this.GestationPeriod) {
            System.out.println("girdi");
            this.PregnancyTime = 0;

            int s = 10;
            int v = rnd.nextInt(s) - s;
            while (!tryCreate(new Grass((x + (v*(rnd.nextBoolean()?1:-1))), (y + v*(rnd.nextBoolean()?1:-1)), width, height, env), false)) {
                s += 1;
                v = rnd.nextInt(s) - s/2;                
            }
        }
        return;

    }

    public void LifeCycle() {
        Age();
        GiveBirth();
    }

    public Grass(int x, int y, int width, int height, EnvironmentInfo env) {
        super(x, y, width, height, env);
        this.clr = Color.green;
        this.NutritionValue = 25;
        this.Health = 1;
        this.GestationPeriod = 1999;
        this.LifeTimeTicks = 2000;
    }

    public void draw(Graphics g) {
        g.setColor(clr);
        g.fillOval(x, y, width, height);
    }
}
