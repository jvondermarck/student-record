package com.student.view;

import com.student.controller.SettingController;
import javafx.scene.layout.BorderPane;

// Interface GUI
public interface IGUI {
    void setUpGUI(BorderPane paneRoot, SettingController controller);
    void createWindow();
}
