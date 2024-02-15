import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleArea extends JPanel {
    static CopyOnWriteArrayList<Particle> particleList = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Wall> wallList = new CopyOnWriteArrayList<>();


    ParticleArea() {
        super();
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.WHITE);
        //start timer
        for (Particle p : particleList) {
            g.drawOval(p.x.intValue(), p.y.intValue(), 9, 9);
        }

        for (Wall w : wallList) {
            System.out.println(w);
            g.drawLine(w.getX1(), w.getY1(), w.getX2(), w.getY2());
        }

        //end timer
    }
}

