package com.student.view;

import com.student.controller.SettingController;
import javafx.scene.layout.BorderPane;

// Interface GUI - this interface is used to be able to create more easily the different class which implement this interfac
public interface IGUI {
    void setUpGUI(BorderPane paneRoot, SettingController controller);
    void createWindow();
}
