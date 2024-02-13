import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleArea extends JPanel {
    CopyOnWriteArrayList<Particle> particleList = new CopyOnWriteArrayList<>();
    CopyOnWriteArrayList<Wall> wallList = new CopyOnWriteArrayList<>();


    ParticleArea() {
        super();
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
    }

    public void addParticle(Particle p) {
        particleList.add(p);
        repaint();
    }

    public void addWall(Wall w) {
        wallList.add(w);
        repaint();
    }

    public void changeParticleLocation(int index, int x, int y) {
        particleList.get(index).setX(x);
        particleList.get(index).setY(y);

    }

    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.WHITE);
        for (Particle p : particleList) {
            System.out.println(p);
            g.drawOval(p.x, p.y, 9, 9);
        }

        for (Wall w : wallList) {
            g.drawLine(w.getX1(), w.getY1(), w.getX2(), w.getY2());
        }
    }
}

