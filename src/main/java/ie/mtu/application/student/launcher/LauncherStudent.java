package ie.mtu.application.student.launcher;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.DBConnection;
import ie.mtu.application.student.model.University;
import ie.mtu.application.student.view.MainGUI;

public class LauncherStudent {
    public static void main(String[] args) {
        // Model
        DBConnection database = new DBConnection();
        University universityMTU = new University();

        // Controller
        SettingController controller = new SettingController(universityMTU, database);

        // View
        MainGUI mainView = new MainGUI();
        mainView.setController(controller);
        mainView.main(args);
    }
}
