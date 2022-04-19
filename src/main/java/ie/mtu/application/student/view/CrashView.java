package ie.mtu.application.student.view;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.Observer;
import ie.mtu.application.student.model.Student;
import ie.mtu.application.student.model.University;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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

        btnLoop.setOnAction(event -> {
            try {
                List<byte[]> list = new LinkedList<>();
                int index = 1;
                System.out.printf("[0] Available heap memory: %s%n", Runtime.getRuntime().freeMemory());
                while (true) {
                    byte[] b = new byte[10 * 1024 * 1024]; // 10MB byte object
                    list.add(b);
                    Runtime rt = Runtime.getRuntime();
                    System.out.printf("[%3s] Available heap memory: %s%n", index++, rt.freeMemory());
                }
            } catch (OutOfMemoryError e) {
                //Log the info
                System.err.println("Array size too large");
                System.err.println("Max JVM memory: " + Runtime.getRuntime().maxMemory());
                e.printStackTrace();
            }

        });
    }

    @Override
    public void updateView(University university) {

    }

    @Override
    public void displayMessage(String errorMessage, String colorHexa) {

    }
}
