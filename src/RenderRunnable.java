import javax.swing.*;

public class RenderRunnable implements Runnable {
    @Override
    public void run() {
        Timer timer = new Timer((1000 / 60), e -> {
            MainLayout.particlePanel.repaint();
        });

        timer.start();
    }
}
