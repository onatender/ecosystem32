import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Predator extends Animal {

    public Predator() {
        this.clr = Color.red;

    }

    public Predator(int x, int y, int width, int height,EnvironmentInfo env) {
        super(x, y, width, height,env);
        this.clr = Color.red;
        this.speed = 7;
    }

    public void draw(Graphics g) {
        drawinfo(g);
        g.setColor(clr);
        g.fillOval(x, y, width, height);
    }

    public void LifeCycle() {
        this.SearchFood();
    }

    public void SearchFood() {

        if (this.Hungry > 60)
            return;

        Creature closest = null;

        for (Creature canli : env.creatures) {
            if (canli instanceof Herbivore) {
                if (closest == null || this.CalculateDistance(canli) < this.CalculateDistance(closest)) {
                    closest = (Creature) canli;
                }
            }
        }

        this.Following = closest;

        if (this.Following != null) { // zaten takip ettiği birisi varsa ona gitmeye devam etsin

            if (((Organism) (this.Following)).Health <= 0) {
                this.Following = null;
            } // hedef ölmüşse artık hedef değil

            if (this.Following != null) { // hala takip edilen birisi varsa ona doğru git ve öldür ve ye
                Hunt();
                return;
            }

        }

 

    }
}
