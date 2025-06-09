import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

public class OyunEkrani extends JFrame {
    public OyunEkrani(String title) {
        super(title);
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
        OyunEkrani ekran = new OyunEkrani("Benim Oyunum");

        ekran.setResizable(false);
        ekran.setFocusable(false);

        int width = 1600;
        int height = 1000;

        ekran.setSize(width,height);
        ekran.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Oyun oyun = new Oyun(ekran);
        oyun.requestFocus();
        oyun.addKeyListener(oyun);
        oyun.addMouseListener(oyun);
        oyun.setFocusable(true);
        oyun.setFocusTraversalKeysEnabled(false);

        ekran.add(oyun);
        ekran.setVisible(true);
    }
}