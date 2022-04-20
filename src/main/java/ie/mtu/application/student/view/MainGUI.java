package ie.mtu.application.student.view;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.Observer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class is the main GUI controller that will create multiple GUI pane
 */
public class MainGUI extends Application {

    /**
     * Variable to communicate with the model, this need to be static otherwise when the JavaFX window will launch it won't acces to this variable
     */
    private static SettingController controller;

    /**
     * The principal TabPane which will contains different tabs
     */
    private TabPane tabPane;

    private AddStudentGUI addStudent;
    private RecordModuleGUI recordModule;
    private ViewRecordGUI viewRecord;
    private CrashView crashView;

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
        Font.loadFont(getClass().getResourceAsStream("/ie/mtu/application/student/font/Marianne.ttf"), 10);

        primaryStage.setTitle("Student Record"); // Title of the application

        // The main principal pane of the GUI
        BorderPane root = new BorderPane();
        tabPane = new TabPane();
        root.getStyleClass().add("tab-pane");
        root.setCenter(tabPane);

        addStudent = new AddStudentGUI();
        recordModule = new RecordModuleGUI();
        viewRecord = new ViewRecordGUI();
        crashView = new CrashView();
        controller.setUpView(this);

        // We create the first tab which is to add a student
        addTab("Student", addStudent);

        // We create a second tab to record the modules of a student
        addTab("Record module", recordModule);

        // The third tab is to view the information of a specific student
        addTab("View module", viewRecord);

        // The last tab is to make the application crash
        addTab("Crash app", crashView);

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ie/mtu/application/student/img/logo-mtu.jpg")));
        primaryStage.getIcons().add(icon);

        Scene mainScene = new Scene(root, 700, 650);
        mainScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/ie/mtu/application/student/stylesheet.css")).toExternalForm());

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Function which will create in a tab the specific window
     * @param tabName : the title name of the Tab
     * @param window : the class that immplemented the IGUI interface which will be in the GUI app
     */
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

    /**
     * @return the class AddStudentGUI
     */
    public AddStudentGUI getAddStudent() {
        return this.addStudent;
    }

    /**
     * @return the class RecordModuleGUI
     */
    public RecordModuleGUI getRecordModule() {
        return this.recordModule;
    }

    /**
     * @return the class ViewRecordGUI
     */
    public ViewRecordGUI getViewRecord() {
        return this.viewRecord;
    }
}
