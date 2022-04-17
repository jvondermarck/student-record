package com.student.launcher;

import com.student.controller.SettingController;
import com.student.model.DBConnection;
import com.student.model.University;
import com.student.view.MainGUI;

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
