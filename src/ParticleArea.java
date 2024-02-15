import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParticleArea extends JPanel {
    static CopyOnWriteArrayList<Particle> particleList = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Wall> wallList = new CopyOnWriteArrayList<>();

    int THREAD_COUNT = 8;
    ArrayList<Thread> drawThreadList = new ArrayList<>();


    ParticleArea() {
        super();
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.setBackground(Color.WHITE);
        for (Particle p : particleList) {
            g.drawOval(p.x.intValue(), p.y.intValue(), 9, 9);
        }

        for (Wall w : wallList) {
            g.drawLine(w.getX1(), w.getY1(), w.getX2(), w.getY2());
        }

        int size = particleList.size();
        if(size > 0) {
            int numPerThread = size / THREAD_COUNT;
            int tempStart = 0;
            int tempEnd = tempStart + numPerThread - 1;

            if(numPerThread < 1) {
                numPerThread = 1;
            }

            for (int i = 0; i < THREAD_COUNT; i++) {
                Thread t = new Thread(new DrawRunnable(tempStart, tempEnd, g));
                drawThreadList.add(t);
                drawThreadList.get(drawThreadList.size() - 1).start();

                tempStart = tempEnd + 1;
                //Any excess goes to the final thread
                if (i == THREAD_COUNT - 2) {
                    tempEnd = size - 1;
                }
                else
                    tempEnd += numPerThread;
            }

            for(Thread t : drawThreadList) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            drawThreadList.clear();
        }
    }
}

class DrawRunnable implements Runnable {
    int start;
    int end;
    Graphics g;

    public DrawRunnable(int start, int end, Graphics g) {
        this.start = start;
        this.end = end;
        this.g = g;
    }

    @Override
    public void run() {
        for(int i = start; i <= end; i++) {
            Particle p = ParticleArea.particleList.get(i);
            synchronized (g) {
                g.drawOval(p.x.intValue(), p.y.intValue(), 9, 9);
            }

        }
    }
}

class RenderRunnable implements Runnable {
    int frames = 0;
    double fps = 0;
    @Override
    public void run() {
        runTimers();
        Timer timer = new Timer((0), e -> {
            SwingUtilities.invokeLater(() -> {
                MainLayout.particlePanel.repaint();
                frames++;
                System.out.println(frames);
            });
        });

        timer.start();
    }

    // i have no idea what im doing
    private void runTimers() {
        Timer timer = new Timer(1000, e ->{
            fps = frames / 1000.0;
            frames = 0;
        });

        timer.start();

        Timer displayTimer = new Timer(500, e -> {
            MainLayout.fpsValue.setText(String.valueOf(fps));
        });
        displayTimer.start();

    }
}

