package com.student.view;

import com.student.controller.SettingController;
import com.student.model.Observer;
import com.student.model.University;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class MainGUI extends Application implements Observer {

    private static SettingController controller;
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

        primaryStage.setTitle("Student Record");

        // The main principal pane of the GUI
        BorderPane root = new BorderPane();
        tabPane = new TabPane();
        root.setCenter(tabPane);

        // We create the first tab which is to add a student
        addStudentTab();

        // We create a second tab to record the modules of a student
        recordModuleTab();

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/logo-mtu.jpg")));
        primaryStage.getIcons().add(icon);

        Scene mainScene = new Scene(root, 700, 650);
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/student/stylesheet.css")).toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public void addStudentTab(){
        // Tab where we will add a contact
        Tab addStudent = new Tab("Student");
        addStudent.setClosable(false);
        tabPane.getTabs().add(addStudent);

        // Another class (AddStudentGUI) will take care of displaying the student tab
        BorderPane paneAddStudent = new BorderPane();
        new AddStudentGUI(paneAddStudent, controller);

        // We display the layout in the tab
        addStudent.setContent(paneAddStudent);
    }

    public void recordModuleTab(){
        Tab recordModule = new Tab("Record module");
        recordModule.setClosable(false);
        tabPane.getTabs().add(recordModule);

        BorderPane paneRecordModule = new BorderPane();
        new RecordModuleGUI(paneRecordModule, controller);

        recordModule.setContent(paneRecordModule);
    }

    @Override
    public void updateView(University university) {

    }

    @Override
    public void displayError(University university, String errorMessage) {

    }
}
