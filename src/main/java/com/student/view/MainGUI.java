package com.student.view;

import com.student.controller.SettingController;
import com.student.model.Observer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class MainGUI extends Application {

    private static SettingController controller; // Variable to communicate with the model
    private TabPane tabPane;

    public void setController(SettingController control)
    {
        controller = control;
    }

    public void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // We import the font Marianne to be able to see that font on every computer
        Font.loadFont(getClass().getResourceAsStream("/com/student/font/Marianne.ttf"), 10);

        primaryStage.setTitle("Student Record"); // Title of the application

        // The main principal pane of the GUI
        BorderPane root = new BorderPane();
        tabPane = new TabPane();
        root.setCenter(tabPane);

        // We create the first tab which is to add a student
        //addStudentTab();
        addTab("Student", new AddStudentGUI());

        // We create a second tab to record the modules of a student
        //recordModuleTab();
        addTab("Record module", new RecordModuleGUI());

        // The third tab is to view the information of a specific student
        addTab("View module", new ViewRecordGUI());

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/logo-mtu.jpg")));
        primaryStage.getIcons().add(icon);

        Scene mainScene = new Scene(root, 700, 650);
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/student/stylesheet.css")).toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void addTab(String tabName, IGUI window)
    {
        // Tab where we will add the window
        Tab tab = new Tab(tabName);
        tab.setClosable(false);
        tabPane.getTabs().add(tab);

        BorderPane paneTab = new BorderPane();
        window.setUpGUI(paneTab, controller);
        controller.addObserver((Observer) window); // // we add the class to our Observer, like that the model will be able to trigger the Update() method in case of adding a contact for example
        window.createWindow();

        // We display the layout in the tab
        tab.setContent(paneTab);
    }
}
