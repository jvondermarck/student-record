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

    private static SettingController controller; // Variable to communicate with the model, this need to be static otherwise when the JavaFX window will launch it won't acces to this variable
    private TabPane tabPane; // The principal TabPane which will contains different tabs
    private AddStudentGUI addStudent;
    private RecordModuleGUI recordModule;
    private ViewRecordGUI viewRecord;

    public void setController(SettingController control)
    {
        controller = control;
    }

    public void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // We import the font Marianne to be able to see that font on every computer
        Font.loadFont(getClass().getResourceAsStream("/com/student/font/Marianne.ttf"), 10);

        primaryStage.setTitle("Student Record"); // Title of the application

        // The main principal pane of the GUI
        BorderPane root = new BorderPane();
        tabPane = new TabPane();
        root.getStyleClass().add("tab-pane");
        root.setCenter(tabPane);

        addStudent = new AddStudentGUI();
        recordModule = new RecordModuleGUI();
        viewRecord = new ViewRecordGUI();
        controller.setUpView(this);

        // We create the first tab which is to add a student
        addTab("Student", addStudent);

        // We create a second tab to record the modules of a student
        addTab("Record module", recordModule);

        // The third tab is to view the information of a specific student
        addTab("View module", viewRecord);

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/logo-mtu.jpg")));
        primaryStage.getIcons().add(icon);

        Scene mainScene = new Scene(root, 700, 650);
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/student/stylesheet.css")).toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Function which will create in a tab the specific window
    public void addTab(String tabName, IGUI window)
    {
        Tab tab = new Tab(tabName); // Tab where we will add the window
        tab.setClosable(false); // Make impossible to close the tab
        tabPane.getTabs().add(tab);

        BorderPane paneTab = new BorderPane(); // we create a border pane which will contain the specific window
        window.setUpGUI(paneTab, controller); // this method will the variables
        controller.addObserver((Observer) window); // // we add the class to our Observer, like that the model will be able to trigger the Update() method in case of adding a contact for example
        window.createWindow(); // this method will create the entire window

        // We display the layout in the tab
        tab.setContent(paneTab);
    }

    public AddStudentGUI getAddStudent() {
        return this.addStudent;
    }

    public RecordModuleGUI getRecordModule() {
        return this.recordModule;
    }

    public ViewRecordGUI getViewRecord() {
        return this.viewRecord;
    }
}
