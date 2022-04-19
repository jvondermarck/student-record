package ie.mtu.application.student.view;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.Module;
import ie.mtu.application.student.model.Student;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *  This method is used by all the GUI Window classes to share in one class partially redundant code, this will avoid duplicated code and too many lines of code
 *  The class which will want to use that class will need to extend it
 *  This class implement the Interface IGUI because it some methods of this class use the Controller and the BorderPane layout,
 *  so it will be easier that each class use the super() method to implement the two variables
 */
public class TemplateGUI implements IGUI {

    private SettingController controller;
    private BorderPane paneRoot;
    private PauseTransition visiblePause;
    private boolean messageDisplayed;

    @Override
    public void setUpGUI(BorderPane paneRoot, SettingController controller) {
        this.controller = controller;
        this.paneRoot = paneRoot;
        this.visiblePause = new PauseTransition();
    }

    @Override
    public void createWindow() {}

    /**
     * This method will create the image banner of the university and show the title of the section named labelTitle
     * @param boxImage : the pane HBox where we want to put the image
     * @param gridTitle : the pane GridPane where we want to put the title
     * @param labelTitle : the title name we want to display
     */
    public void createTitleView(HBox boxImage, GridPane gridTitle, String labelTitle)
    {
        // We put in a pane the logo of the application
        ImageView imageView = new ImageView();
        Image imgLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ie/mtu/application/student/img/banner-mtu-sized.jpg")));
        imageView.setImage(imgLogo);
        imageView.setFitWidth(225);
        imageView.setPreserveRatio(true);
        boxImage.getChildren().add(imageView);
        boxImage.setAlignment(Pos.CENTER);
        boxImage.getStyleClass().add("main-logo-title");
        this.paneRoot.setTop(boxImage);

        // We create a title and a logo next to the label
        imageView = new ImageView();
        imgLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/ie/mtu/application/student/img/survey.png")));
        imageView.setImage(imgLogo);
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);
        gridTitle.add(imageView, 0,0);

        Label lblTitle = new Label(labelTitle);
        lblTitle.getStyleClass().add("main-title"); // css File
        gridTitle.add(lblTitle, 1,0);
        gridTitle.setPadding(new Insets(0,0,20,0));
    }

    /**
     * The method will create at a specific space in the GUI a place to ouput future error message and create a specific design
     * @param lblError : the Label where will be outputed the error messages
     * @param gridPane : the GridPane where we add the label
     */
    public void createErrorLabel(Label lblError, GridPane gridPane)
    {
        lblError.setVisible(false);
        lblError.getStyleClass().add("tooltip");
        gridPane.add(lblError, 2,0);
        GridPane.setMargin(lblError, new Insets(0,0,0,100));
        GridPane.setHalignment(lblError, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(lblError, VPos.CENTER); // To align vertically in the cell
    }

    /**
     * The fubction will configure from the combobox given in parameter the combobox to be able to well display a student object
     * @param cboStudent the combobox of type Student that we want to create
     */
    public void createComboboxStudent(ComboBox<Student> cboStudent)
    {
        // when we click on the student we want in the combobox we convert the object class to a string to show in the combobox
        cboStudent.setConverter(new StringConverter<>() {
            @Override
            public String toString(Student student) {
                if (student == null) return null;
                return student.getFullname();
            }

            @Override
            public Student fromString(String idNumber) {
                Student student = null;
                for (Student student1 : cboStudent.getItems()) {
                    if (student1.getId().equals(Integer.parseInt(idNumber))) {
                        student = student1;
                        break;
                    }
                }
                return student;
            }
        });

        // we retrieve the ArrayList of students from our database
        ObservableList<Student> wordsList = FXCollections.observableArrayList();
        wordsList.addAll(this.controller.getStudentList());
        cboStudent.setItems(wordsList);

        // we now convert all the Student object into a fullname string
        cboStudent.setCellFactory(new Callback<>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Student student, boolean empty) {
                        super.updateItem(student, empty);
                        if (empty || student == null) {
                            setText(null);
                        } else {
                            setText(student.getFullname());
                        }
                        getStyleClass().add("ListView-row");
                    }
                };
            }
        });
    }

    /**
     * The method will set up the TableView to display all the module from a specific Student
     * @param tableView : the Table View that we want to set up
     * @param cboStudent : the combobox of type Student
     */
    public void setUpListViewModule(TableView<Module> tableView, ComboBox<Student> cboStudent)
    {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // We center the text of the tableView
        tableView.setPlaceholder(new Label("No module."));
        tableView.getStyleClass().add("tableView-column"); // css File

        TableColumn<Module, String> tableModule = new TableColumn<>("Module");
        tableModule.setCellValueFactory(new PropertyValueFactory<>("moduleName")); // it will take the getter of getFirstname() in the Student class
        tableModule.getStyleClass().add("tableView-column");

        TableColumn<Module, String> tableGrade = new TableColumn<>("Grade");
        tableGrade.setCellValueFactory(new PropertyValueFactory<>("gradeModule"));

        // We add the 4 columns into the tableView
        tableView.getColumns().add(tableModule);
        tableView.getColumns().add(tableGrade);

        cboStudent.getSelectionModel().selectedItemProperty().addListener((options, oldValue, student) ->
                updateListView(tableView, cboStudent));

        updateListView(tableView, cboStudent);
    }

    /**
     * It will display all the modules from a specific student
     * @param tableView : the TableView of type Module where will be displayed the modules
     * @param cboStudent : the combobox of the student selected to display his modules
     */
    public void updateListView(TableView<Module> tableView, ComboBox<Student> cboStudent)
    {
        // when we click on the student we want in the combobox we convert the object class to a string to show in the combobox
        Student s = cboStudent.getValue();
        tableView.getItems().clear();
        if(s != null) // to avoid any problem we make sure that a student is well selected in the combobox
        {
            List<Module> listModule = s.getModule();
            if(listModule != null)
            {
                for (Module module : listModule) {
                    tableView.getItems().add(module);
                }
            }
        }
    }

    /**
     * It will display all the modules from a specific student where he got a grade >= 70
     * @param tableView : the TableView of type Module where will be displayed the modules
     * @param cboStudent : the combobox of the student selected to display his modules
     */
    public void moduleTableViewHonours(TableView<Module> tableView, ComboBox<Student> cboStudent){
        Student s = cboStudent.getValue();
        tableView.getItems().clear();
        if(s != null) // to avoid any problem we make sure that a student is well selected in the combobox
        {
            List<Module> listModule = s.getModuleHonours();
            if(listModule != null)
            {
                for (Module module : listModule) {
                    tableView.getItems().add(module);
                }
            }
        }
    }

    /**
     * We will display a quick message in the GUI
     * @param lblError : the label where we want the message to be displayed
     * @param errorMessage : the message we want to display
     * @param colorHexa : the hexa color of the message
     */
    public void showMessageTemplate(Label lblError, String errorMessage, String colorHexa)
    {
        // We check if a message is already being displayed
        if(messageDisplayed){
            // The method will wait that the message end, to display the next message
            visiblePause.setOnFinished( event -> setPauseDuration(lblError, errorMessage, colorHexa));
        } else {
            messageDisplayed = true;
            lblError.setVisible(true);
            setPauseDuration(lblError, errorMessage, colorHexa);
        }
    }

    /**
     * The method will wait that the message end, to display the next message
     * @param lblError : the label where we want the message to be displayed
     * @param errorMessage : the message we want to display
     * @param colorHexa : the hexa color of the message
     */
    private void setPauseDuration(Label lblError, String errorMessage, String colorHexa)
    {
        visiblePause = new PauseTransition(
                Duration.seconds(4)
        );
        lblError.setText(errorMessage);
        lblError.setStyle(" -fx-background-color: " + colorHexa + "; ");
        visiblePause.setOnFinished( e -> {
            lblError.setVisible(false);
            messageDisplayed = false;
        });
        visiblePause.play();
    }

    /**
     * It will create a pop-up when with two different choice before exit the application
     */
    public void exitButton(){
        // we create two buttons to save and exit OR to exit without saving
        ButtonType btnSave1 = new ButtonType("Save and exit", ButtonBar.ButtonData.YES);
        ButtonType btnExit1 = new ButtonType("Exit without saving", ButtonBar.ButtonData.NO);

        // we create the alert which will appear on the screen
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "You should maybe save your data before exit the application.\n", btnSave1, btnExit1);

        alert.setTitle("Exit the application");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(null) == btnExit1) // if clicked on "Exit without saving"
        {
            Platform.exit(); // we stop the application
        } else { // if clicked on "Save and exit"
            //controller.saveStudentDatabase(); // we save everything in the database
            Platform.exit(); // and we exit
        }
    }

}
