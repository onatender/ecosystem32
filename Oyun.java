import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.management.relation.RoleNotFoundException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// class Ates {
//     private int x;
//     private int y;

//     public Ates(int x, int y) {
//         this.x = x;
//         this.y = y;
//     }

//     public int getX() {
//         return x;
//     }

//     public int getY() {
//         return y;
//     }

//     public void setX(int x) {
//         this.x = x;
//     }

//     public void setY(int y) {
//         this.y = y;
//     }
// }

public class Oyun extends JPanel implements KeyListener, ActionListener, MouseListener {
    public OyunEkrani ekran;
    Random rnd = new Random();
    int fps = 60;

    public int interval() {
        return 1000 / fps;
    }

    public int realtimeToTick(int milliseconds) {
        return milliseconds / interval();
    }

    Timer timer = new Timer(interval(), this);// 60fps
    int ticks = 0;
    public ArrayList<Creature> varliklar = new ArrayList<Creature>();
    public EnvironmentInfo env = new EnvironmentInfo();
    // private int gecen_sure = 0;
    // private int harcanan_ates = 0;
    // private BufferedImage image;
    // private ArrayList<Ates> atesler = new ArrayList<Ates>();
    // private int atesdirY = 1;

    // private int topX = 0;
    // private int topdirX = 2;

    // private int uzayGemisiX = 0;
    // private int dirUzayX = 20;

    // public boolean kontrolEt() {
    // for (Ates ates:atesler) {
    // if (new Rectangle(ates.getX(),ates.getY(),10,20).intersects(new
    // Rectangle(topX,0,20,20)))
    // return true;
    // }
    // return false;
    // }

    public Oyun(OyunEkrani ekran) throws FileNotFoundException, IOException {

        this.ekran = ekran;

        // image = ImageIO.read(new FileImageInputStream(new File("spaceship.png")));
        setBackground(Color.black);

        // varliklar.add(new Predator(rnd.nextInt(800), rnd.nextInt(600), 25, 25));
        // varliklar.add(new Predator(rnd.nextInt(800), rnd.nextInt(600), 25, 25));
        // varliklar.add(new Predator(44, 144, 25, 25, varliklar));
        // varliklar.add(new Predator(444, 444, 25, 25, varliklar));
        // varliklar.add(new Predator(644, 444, 25, 25, varliklar));
        // varliklar.add(new Predator(744, 444, 25, 25, varliklar));
        // varliklar.add(new Herbivore(rnd.nextInt(ekran.getWidth()),
        // rnd.nextInt(ekran.getHeight()), 25, 25, varliklar));

        // varliklar.add(new Predator(700, 500, 25, 25,varliklar));

        env.ekran = ekran;
        env.creatures = varliklar;

        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        int bitkisayisi = 0;
        int predatorsayisi = 0;
        int otculsayisi = 0;
        for (Creature cr : varliklar) {
            if (cr instanceof Predator)
                predatorsayisi++;
            if (cr instanceof Grass)
                bitkisayisi++;
            if (cr instanceof Herbivore)
                otculsayisi++;
        }

        g.setColor(Color.white);
        g.drawString("Hayattaki Bitki:" + bitkisayisi, 20, 20);
        g.drawString("Hayattaki Otcul:" + otculsayisi, 20, 35);
        g.drawString("Hayattaki Etcil:" + predatorsayisi, 20, 50);

        for (Creature varlik : varliklar) {
            varlik.draw(g);
        }
        // super.paint(g);
        // g.setColor(Color.red);
        // g.fillOval(topX, 0, 20, 20);
        // g.drawImage(image, uzayGemisiX, 490, image.getWidth() / 10, image.getHeight()
        // / 10, this);

        // for (Ates ates : atesler) {
        // if (ates.getY() < 0) {
        // atesler.remove(ates);
        // }
        // }

        // g.setColor(Color.blue);
        // for (Ates ates: atesler) {
        // g.fillRect(ates.getX(), ates.getY(), 10, 20);
        // }

        // if (kontrolEt()) {
        // timer.stop();
        // String message = "u win\n"+"harcanan ates:"+harcanan_ates+"\ngecen
        // sure:"+gecen_sure/1000.0;
        // JOptionPane.showMessageDialog(this, message);
        // System.exit(0);
        // }

    }

    @Override
    public void repaint() {
        super.repaint();
    }

     public boolean tryCreate(Creature crt) {
        if (crt.isMovePossible(crt.x, crt.y)) {
            env.creatures.add(crt);
            return true;
        }

        System.out.println("yaratma girişimi başarısız.");
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ticks += 1;

        if (ticks % realtimeToTick(1000) == 0)
        while (!tryCreate(
        new Grass(rnd.nextInt(ekran.getWidth()), rnd.nextInt(ekran.getHeight()), 10,
        10, env)))
        ;

        if (ticks % realtimeToTick(1000) == 0)
        while (!tryCreate(
        new Herbivore(rnd.nextInt(ekran.getWidth()), rnd.nextInt(ekran.getHeight()),
        25, 25, env)))
        ;

        if (ticks % realtimeToTick(6000) == 0)
        while (!tryCreate(new Predator(rnd.nextInt(ekran.getWidth()),
        rnd.nextInt(ekran.getHeight()), 25, 25, env)))
        ;

        if (ticks % realtimeToTick(1000) == 0) {
            for (Creature varlik : varliklar) {
                if (varlik instanceof Animal) {
                    if (((Animal) varlik).Hungry > 0)
                        ((Animal) varlik).Hungry -= 1;
                    else
                        ((Animal) varlik).Health -= 1;
                }
            }
        }

        for (int i = 0; i < env.creatures.size(); i++) {
            if (env.creatures.get(i) instanceof Organism)
                ((Organism) env.creatures.get(i)).LifeCycle();
        }

        for (int i = 0; i < varliklar.size(); i++) {
            if (varliklar.get(i) instanceof Organism)
                if (((Organism) (varliklar.get(i))).Health <= 0) {
                    varliklar.remove(i); // kill
                }
        }
        // gecen_sure+=5;
        // for (Ates ates: atesler) {
        // ates.setY(ates.getY() - atesdirY);
        // }

        // topX += topdirX;

        // if (topX >= 750) {
        // topdirX = -topdirX;
        // }
        // if (topX <= 0) {
        // topdirX = -topdirX;
        // }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        // int c = e.getKeyCode();
        // if (c == KeyEvent.VK_LEFT) {
        // if (uzayGemisiX <= 0) {
        // uzayGemisiX = 0;
        // } else {
        // uzayGemisiX -= dirUzayX;
        // }
        // }

        // else if (c == KeyEvent.VK_RIGHT) {
        // if (uzayGemisiX >= 720) {
        // uzayGemisiX = 720;
        // } else {
        // uzayGemisiX += dirUzayX;
        // }
        // }

        // else if (c == KeyEvent.VK_CONTROL) {
        // atesler.add(new Ates(uzayGemisiX+33, 470));

        // harcanan_ates++;
        // }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        Creature varlik;

        if (e.getButton() == MouseEvent.BUTTON1)
            varlik = new Herbivore(e.getX(), e.getY(), 25, 25, env);
        else if (e.getButton() == MouseEvent.BUTTON3)
            varlik = new Predator(e.getX(), e.getY(), 25, 25, env);

        else {
            for (Creature v : varliklar) {
                if (new Rectangle(e.getX(), e.getY(), 5, 5)
                        .intersects(new Rectangle(v.x, v.y, v.width, v.height)))
                    ((Organism) (v)).kill();
            }
            return;
        }

        varlik.tryCreate(varlik);

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
