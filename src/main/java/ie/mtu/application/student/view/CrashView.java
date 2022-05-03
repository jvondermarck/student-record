package ie.mtu.application.student.view;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.Observer;
import ie.mtu.application.student.model.University;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Crash the application
 */
public class CrashView extends TemplateGUI implements Observer, IGUI {

    private SettingController controller;
    private BorderPane paneRoot;

    @Override
    public void setUpGUI(BorderPane paneRoot, SettingController controller) {
        super.setUpGUI(paneRoot, controller);
        this.paneRoot = paneRoot;
        this.controller = controller;
    }

    @Override
    public void createWindow() {
        HBox box = new HBox();
        Button btnLoop = new Button("Loop create Students");
        box.getChildren().add(btnLoop);
        this.paneRoot.setCenter(box);

        AtomicLong heapSize = new AtomicLong(Runtime.getRuntime().totalMemory());
        // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
        AtomicLong heapMaxSize = new AtomicLong(Runtime.getRuntime().maxMemory());
        // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
        AtomicLong heapFreeSize = new AtomicLong(Runtime.getRuntime().freeMemory());

        //System.out.println("heap max size: " + heapMaxSize.get());
        btnLoop.setOnAction(event -> {
            System.out.println("------------------------------------------");
            System.out.println("heap size: " + formatSize(heapSize.get()));
            System.out.println("heap max size: " + formatSize(heapMaxSize.get()));
            System.out.println("heap free size: " + formatSize(heapFreeSize.get()));
            System.out.println("------------------------------------------");
            try {
                List<byte[]> list = new LinkedList<>();
                int index = 1;

                while (true) {
                    byte[] b = new byte[123456789]; 
                    list.add(b);
                    Runtime rt = Runtime.getRuntime();
                    heapSize.set(Runtime.getRuntime().totalMemory());
                    // Get maximum size of heap in bytes. The heap cannot grow beyond this size.// Any attempt will result in an OutOfMemoryException.
                    heapMaxSize.set(Runtime.getRuntime().maxMemory());
                    // Get amount of free memory within the heap in bytes. This size will increase // after garbage collection and decrease as new objects are created.
                    heapFreeSize.set(Runtime.getRuntime().freeMemory());
                    System.out.println("------------------------------------------");
                    System.out.println("heap size: " + formatSize(heapSize.get()));
                    System.out.println("heap max size: " + formatSize(heapMaxSize.get()));
                    System.out.println(index++ + ":  heap free size: " + formatSize(heapFreeSize.get()));
                    System.out.println("------------------------------------------");
                }
            } catch (OutOfMemoryError e) {
                //Log the info
                System.err.println("Array size too large");
                System.err.println("Max JVM memory: " + Runtime.getRuntime().maxMemory());
                System.err.println("heap free size: " + formatSize(heapFreeSize.get()));
                e.printStackTrace();
            }

        });
    }

    /**
     * Code from <a href="https://stackoverflow.com/questions/2015463/how-to-view-the-current-heap-size-that-an-application-is-using">https://stackoverflow.com/questions/2015463/how-to-view-the-current-heap-size-that-an-application-is-using</a>
     * @param v : the value to format
     * @return the string format for better vision
     */
    public static String formatSize(long v) {
        if (v < 1024) return v + " B";
        int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
        return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
    }

    @Override
    public void updateView(University university) {

    }

    @Override
    public void displayMessage(String errorMessage, String colorHexa) {

    }
}
