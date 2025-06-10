import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public class Predator extends Animal {

    public Predator() {
        this.clr = Color.red;

    }

    

    public Predator(int x, int y, int width, int height, EnvironmentInfo env) {
        super(x, y, width, height, env);
        this.clr = Color.red;
        this.speed = 9;
        this.minReproduceAge = 1800;
    }

    public void draw(Graphics g) {

          if (this.ageTicks < 1800) {
            this.width = 20;
            this.height = 20;
        }

        if (this.ageTicks >= 1800) {
            this.width = 25;
            this.height = 25;
        }


        drawinfo(g);
        g.setColor(isPregnant ? Color.cyan : Gender == Genders.FEMALE ? Color.yellow : Color.red);
        g.fillOval(x, y, width, height);

        // İki göz çizimi ve 2 kat hızlı kırpma animasyonu
        int eyeWidth = width / 5;
        int eyeHeight = height / 4;
        int leftEyeX, rightEyeX;
        int eyeY = y + height / 3;

        // Facing değişkenine göre gözlerin yönünü değiştir
        if (Facing) { // sağa bakıyor
            leftEyeX = x + width / 3;
            rightEyeX = x + width * 3 / 5;
        } else { // sola bakıyor
            leftEyeX = x + width / 10;
            rightEyeX = x + width * 2 / 5;
        }

        // Kırpma: ageTicks'in belirli aralıklarında göz kapansın (2 kat hızlı)
        boolean blink = (ageTicks / 10) % 20 == 0; // Her 10*20=200 tickte bir kırpma

        for (int eyeX : new int[]{leftEyeX, rightEyeX}) {
            if (blink) {
                // Göz kapalı (ince çizgi)
                g.setColor(Color.BLACK);
                g.fillRect(eyeX, eyeY + eyeHeight / 2, eyeWidth, 2);
            } else {
                // Göz açık (oval)
                g.setColor(Color.WHITE);
                g.fillOval(eyeX, eyeY, eyeWidth, eyeHeight);

                // Göz bebeği ortada
                int pupilWidth = eyeWidth * 2 / 3;
                int pupilHeight = eyeHeight * 2 / 3;
                int pupilX = eyeX + (eyeWidth - pupilWidth) / 2;
                int pupilY = eyeY + (eyeHeight - pupilHeight) / 2;

                g.setColor(Color.BLACK);
                g.fillOval(pupilX, pupilY, pupilWidth, pupilHeight);
            }
        }

       

    }

    public void GiveBirth() {
        Random rnd = new Random();
        if (this.Gender == Genders.FEMALE && this.isPregnant) {
            this.PregnancyTime++;
            if (PregnancyTime > GestationPeriod) {
                this.isPregnant = false;
                this.PregnancyTime = 0;

                int s = 10;
                int v = rnd.nextInt(s) - s;
                while (!tryCreate(new Predator((x + (v * (rnd.nextBoolean() ? 1 : -1))),
                        (y + v * (rnd.nextBoolean() ? 1 : -1)), width, height, env), true)) {
                    s += 1;
                    v = rnd.nextInt(s) - s / 2;
                }
            }
            return;
        }
    }

    public void LifeCycle() {
          this.Age();

        if (this.MatingTimeLeft > 0) {
            this.MatingTimeLeft--;
            return;
        }

        this.GiveBirth();
        // this.RunAway();

        // if (RunFrom != null)
        //     return;

        this.SearchFood();
        if (Following != null) {

            return;

        }
        if (Hungry < 100)
            return;

        this.Reproduce();
    }

    public void Reproduce() {
        System.out.println(this.ageTicks);
        System.out.println(this.minReproduceAge);
        if (this.isPregnant || this.ageTicks < this.minReproduceAge) {
            return;
        }
        Creature closest = null;

        for (Creature canli : env.creatures) {
            if (canli instanceof Predator) {
                if (((Organism) canli).Gender == ((Organism) this).Gender || ((Organism) canli).isPregnant
                        || ((Organism) canli).ageTicks < ((Organism) canli).minReproduceAge)
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
                    if (this.Gender == Genders.MALE && ((Organism) Partner).Gender == Genders.FEMALE)
                        ((Organism) Partner).isPregnant = true;
                    else if (this.Gender == Genders.FEMALE && ((Organism) Partner).Gender == Genders.MALE)
                        this.isPregnant = true;

                    ((Animal)(((Animal)this).Partner)).Hungry -= 40;
                    this.Hungry -= 40;

                    this.MatingTimeLeft = 100;
                    ((Organism) (this.Partner)).MatingTimeLeft = 100;
                    env.particles.add(new HeartParticles(40, x, y));
                    env.particles.add(new HeartParticles(40, Partner.x, Partner.y));
                }
            }

        }

    }

    public void SearchFood() {

        if (this.Hungry > 60) {
            Following = null;
            return;
        }

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
