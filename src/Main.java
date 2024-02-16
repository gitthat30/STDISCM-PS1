import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    static final int THREAD_COUNT = 8;
    public static DelayQueue<Command> commandQueue = new DelayQueue<Command>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        RenderRunnable renderRunnable = new RenderRunnable();
        Thread renderThread = new Thread(renderRunnable);

        Controller.initializeActionListeners();
        MainLayout.initializeGUI();
        MainLayout.MainFrame.setVisible(true);

        // Start rendering thread when the first point or wall is added
        executorService.execute(new WorkerRunnable(commandQueue.take()));
        renderThread.start();

        while(true) {
            executorService.execute(new WorkerRunnable(commandQueue.take()));
        }
    }
}
