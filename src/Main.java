import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static BlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>();
    public static void main(String[] args) {
        Controller.initializeActionListeners();
        MainLayout.initializeGUI();
        MainLayout.MainFrame.setVisible(true);
    }
}
