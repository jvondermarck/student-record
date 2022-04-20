package ie.mtu.application.student.view;

import ie.mtu.application.student.controller.SettingController;
import javafx.scene.layout.BorderPane;

/**
 * Interface GUI -
 * this interface is used to be able to create more easily the different class which implement this interfac
 */
public interface IGUI {
    /**
     * This method will instantiate the given parameters
     * @param paneRoot : the main panel where everything will be stored
     * @param controller : the Controller class that the View classes will use to deal with the model
     */
    void setUpGUI(BorderPane paneRoot, SettingController controller);

    /**
     * This method will be used to create the GUI
     */
    void createWindow();
}
