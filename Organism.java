import java.util.ArrayList;
import java.util.Random;

public class Organism extends Creature {
    public int Health = 100;
    public Genders Gender;
    public enum Genders {MALE,FEMALE};
    public int NutritionValue;
    public int MatingTimeLeft;

    public int GestationPeriod = 500;
    public int PregnancyTime;
    public boolean isPregnant;
    public int ageTicks = 0;
    public int minReproduceAge;

    public int LifeTimeTicks = 10000;

    public void Age() {
        ageTicks += 1;
        if (ageTicks > LifeTimeTicks) this.kill();
    }


    public void TakeDamage(int damage) {
        this.Health -= damage;
    }

    public boolean isDead() {
        return this.Health <= 0 ? true : false;
    }

    public Organism(int x, int y, int width, int height,EnvironmentInfo env) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.env = env;

        Random rnd = new Random();

        if (rnd.nextBoolean()) this.Gender = Genders.MALE;
        else this.Gender = Genders.FEMALE;
    }
    public void kill() {
        this.TakeDamage(Integer.MAX_VALUE);
    }

    public Organism() {

    }


    public void LifeCycle() {};
   

}
