import javax.swing.*;

public class RenderRunnable implements Runnable {
    @Override
    public void run() {
        Timer timer = new Timer((0), e -> {
            MainLayout.particlePanel.repaint();
        });

        timer.start();
    }
}
