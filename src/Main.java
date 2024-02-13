import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    static final int THREAD_COUNT = 8;
    public static LinkedBlockingQueue<Command> commandQueue = new LinkedBlockingQueue<>();
    public static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

    public static boolean ready;
    public static void main(String[] args) throws InterruptedException {
        RenderRunnable renderRunnable = new RenderRunnable();
        Thread renderThread = new Thread(renderRunnable);
        renderThread.start(); // is it ok for this to get interrupted by closing the window?

        Controller.initializeActionListeners();
        MainLayout.initializeGUI();
        MainLayout.MainFrame.setVisible(true);

        executorService.execute(new WorkerRunnable(commandQueue.take()));
        System.out.println("oki na");

        // idk if this is ok
        while(true) {
            executorService.execute(new WorkerRunnable(commandQueue.take()));
        }
    }
}
