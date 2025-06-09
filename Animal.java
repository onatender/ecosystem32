import java.awt.Color;
import java.awt.Graphics;
import java.awt.SystemColor;
import java.util.ArrayList;

public class Animal extends Organism {

    public int Hungry = 40;
    public int speed = 3;
    public Creature Following;
    public Creature Partner;
    public int AttackRange = 30;
    public int Damage = 1;
    public Animal RunFrom;

    public void drawinfo(Graphics g) {
        g.setColor(Color.white);
        g.drawString("Health:" + this.Health, this.x - 10, this.y - 5);
        g.drawString("Hunger:" + this.Hungry, this.x - 10, this.y - 17);
        g.drawString("Gender:" + this.Gender, this.x - 10, this.y - 29);
    }

    public void Reproduce() {

    }

    public void Hunt() {

        MoveTo(Following.x, Following.y);

        if (this.CalculateDistance(Following) < this.AttackRange) {
            // vurabilir
            ((Organism) Following).TakeDamage(this.Damage);
            if (((Organism) Following).isDead()) {
                this.Hungry += ((Organism) Following).NutritionValue;
            }
        } else {
            // System.out.println(this.CalculateDistance(Following) + ">" +
            // this.AttackRange);

        }
    }

    public void RunAway() {
        Creature closest = null;
        this.RunFrom = null;

        // for (Creature cr : creatures) {
        //     if (!(cr instanceof Animal))
        //         continue;
        //     if (((Animal) (cr)).Following == this) {

        //         if (closest == null || this.CalculateDistance(cr) < this.CalculateDistance(closest)) {
        //             closest = (Creature) cr;
        //         }
        //     }
        // }

          for (Creature cr : env.creatures) {
            if (!(cr instanceof Animal)) continue;
            if (cr instanceof Herbivore) continue;
            if (cr == this) continue;
            if (this.CalculateDistance(cr) < 200) {
                if (closest == null || this.CalculateDistance(cr) < this.CalculateDistance(closest)) {
                    closest = (Creature) cr;
                }
            }
        }

        this.RunFrom = (Animal) closest;

        if (RunFrom == null)
            return;
        if (this.CalculateDistance((Creature) RunFrom) < 30)
            return; // yakalandı
        if (this.CalculateDistance((Creature) RunFrom) < 200) {

            int x_difference = RunFrom.x - this.x; // 40 - 20 : sağda -> sola gidilecek x -
            int y_difference = RunFrom.y - this.y; // 40 - 20 : alağıda -> yukarı gidilecek y-

            MoveTo(x - x_difference, y - y_difference);
        }
    }

    public void MoveTo(int target_x, int target_y) {
        int x_difference = target_x - this.x;
        int y_difference = target_y - this.y;

        int new_x = this.x;
        int new_y = this.y;

        if (x_difference != 0 && y_difference != 0) {
            speed = Math.abs(speed);
        }

        if (x_difference > 0) // sağa gitmeli
        {
            if (Math.abs(x_difference) >= speed) {
                new_x += speed;
            } else {
                new_x += Math.abs(x_difference);
            }
        }
        if (x_difference < 0) // sola gitmeli
        {
            if (Math.abs(x_difference) >= speed) {
                new_x -= speed;
            } else {
                new_x -= Math.abs(x_difference);
            }
        }

        if (y_difference > 0) // sağa gitmeli
        {
            if (Math.abs(y_difference) >= speed) {
                new_y += speed;
            } else {
                new_y += Math.abs(y_difference);
            }
        }
        if (y_difference < 0) // sola gitmeli
        {
            if (Math.abs(y_difference) >= speed) {
                new_y -= speed;
            } else {
                new_y -= Math.abs(y_difference);
            }
        }
        // kendisini kontrolden çıkar ve yanlış iki şeyi kontrol ediyosun
        if (this.isMovePossible(new_x, this.y)) {
            this.x = new_x;
        }
        if (this.isMovePossible(this.x, new_y)) {
            this.y = new_y;
        }
    }

    public Animal() {

    }

    public Animal(int x, int y, int width, int height,EnvironmentInfo env) {
        super(x, y, width, height,env);
    }

    public void SearchFood() {
    };
}
