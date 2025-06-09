import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Herbivore extends Animal {

    public Herbivore() {
        this.clr = Color.blue;

    }

    public Herbivore(int x, int y, int width, int height, EnvironmentInfo env) {
        super(x, y, width, height, env);
        this.clr = Color.blue;
        this.NutritionValue = 30;
        this.Hungry = 60;
        this.speed = 6;
    }

    public void draw(Graphics g) {
        drawinfo(g);
        g.setColor(clr);
        g.fillOval(x, y, width, height);
    }

    public void LifeCycle() {

        this.RunAway();

        if (RunFrom != null)
            return;

        this.SearchFood();

        if (Following != null) {

            return;

        }

        this.Reproduce();
    }

    public void Reproduce() {
        Random rnd = new Random();
        if (this.Gender == Genders.FEMALE && this.isPregnant) {
            this.PregnancyTime++;
            if (PregnancyTime > GestationPeriod) {
                System.out.println("giving birth");
                this.GiveBirth();
                System.out.println("gave");
               
                int s = 10;
                int v = rnd.nextInt(s)-s;
                while (!tryCreate(new Herbivore((x + v), (y + v), width, height, env))) {
                    s +=1;
                    v = rnd.nextInt(s)-s;
                }
            }
            return;
        }

        Creature closest = null;

        for (Creature canli : env.creatures) {
            if (canli instanceof Herbivore) {
                if (((Organism) canli).Gender == ((Organism) this).Gender)
                    continue;
                if (((Organism) canli).isPregnant)
                    continue;
                if (closest == null || this.CalculateDistance(canli) < this.CalculateDistance(closest)) {
                    closest = (Creature) canli;
                }
            }
        }

        this.Partner = closest;

        if (this.Partner != null) { // zaten takip ettiği birisi varsa ona gitmeye devam etsin

            if (((Organism) (this.Partner)).Health <= 0) {
                this.Partner = null;
            } // hedef ölmüşse artık hedef değil

            if (this.Partner != null) { // hala takip edilen birisi varsa ona doğru git ve öldür ve ye
                MoveTo(Partner.x, Partner.y);
                if (this.CalculateDistance(Partner) < this.AttackRange) {
                    // yakında ciftlesebilir
                    System.out.println("having sex");
                    if (this.Gender == Genders.MALE && ((Organism) Partner).Gender == Genders.FEMALE)
                        ((Organism) Partner).isPregnant = true;
                    else if (this.Gender == Genders.FEMALE && ((Organism) Partner).Gender == Genders.MALE)
                        this.isPregnant = true;
                }
            }

        }

    }

    public void SearchFood() {
        // System.out.println(Hungry);
        if (Hungry > 60) // aç değilse çık
            return;

        if (this.Following != null) { // zaten takip ettiği birisi varsa ona gitmeye devam etsin

            if (((Organism) (this.Following)).Health <= 0) {
                this.Following = null;
            } // hedef ölmüşse artık hedef değil

            if (this.Following != null) { // hala takip edilen birisi varsa ona doğru git ve öldür ve ye
                Hunt();
                return;
            }

        }

        Creature closest = null;

        for (Creature canli : env.creatures) {
            if (canli instanceof Grass) {
                if (closest == null || this.CalculateDistance(canli) < this.CalculateDistance(closest)) {
                    closest = (Creature) canli;
                }
            }
        }

        this.Following = closest;
    }

}
