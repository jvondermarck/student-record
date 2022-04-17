package com.student.view;

import com.student.controller.SettingController;
import com.student.model.Module;
import com.student.model.Student;
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

import java.util.Objects;
import java.util.Optional;

// This method is used by all the GUI Window classes to share in one class partially redundant code, this will avoid duplicated code and too many lines of code
// The class which will want to use that class will need to extend it
// This class implement the Interface IGUI because it some methods of this class use the Controller and the BorderPane layout, so it will be easier that each class use the super() method to implement the two variables
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
    public void createWindow() {

    }

    // This method will create the image banner of the university and show the title of the section named labelTitle
    public void createTitleView(HBox boxImage, GridPane gridTitle, String labelTitle)
    {
        // We put in a pane the logo of the application
        ImageView imageView = new ImageView();
        Image imgLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/banner-mtu-sized.jpg")));
        imageView.setImage(imgLogo);
        imageView.setFitWidth(225);
        imageView.setPreserveRatio(true);
        boxImage.getChildren().add(imageView);
        boxImage.setAlignment(Pos.CENTER);
        boxImage.getStyleClass().add("main-logo-title");
        this.paneRoot.setTop(boxImage);

        // We create a title and a logo next to the label
        imageView = new ImageView();
        imgLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/survey.png")));
        imageView.setImage(imgLogo);
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);
        gridTitle.add(imageView, 0,0);

        Label lblTitle = new Label(labelTitle);
        lblTitle.getStyleClass().add("main-title"); // css File
        gridTitle.add(lblTitle, 1,0);
        gridTitle.setPadding(new Insets(0,0,20,0));
    }

    public void createErrorLabel(Label lblError, GridPane gridPane)
    {
        lblError.setVisible(false);
        lblError.getStyleClass().add("tooltip");
        gridPane.add(lblError, 2,0);
        GridPane.setMargin(lblError, new Insets(0,0,0,100));
        GridPane.setHalignment(lblError, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(lblError, VPos.CENTER); // To align vertically in the cell
    }

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

        updateListView(tableView, cboStudent);
    }

    public void updateListView(TableView<Module> tableView, ComboBox<Student> cboStudent)
    {
        // when we click on the student we want in the combobox we convert the object class to a string to show in the combobox
        cboStudent.getSelectionModel().selectedItemProperty().addListener((options, oldValue, student) -> {
            tableView.getItems().clear();
            if(student != null) // to avoid any problem we make sure that a student is well selected in the combobox
            {
                if(student.getModule() != null)
                {
                    for(int i=0; i<student.getModule().size(); i++)
                    {
                        tableView.getItems().add(student.getModule().get(i));
                    }
                }
            }
        });
    }

    public void showMessageTemplate(Label lblError, String errorMessage, String colorHexa)
    {
        // We check if a message is already being displayed
        if(messageDisplayed){
            // we wait that the message end, to display the next message
            visiblePause.setOnFinished( event -> setPauseDuration(lblError, errorMessage, colorHexa));
        } else {
            messageDisplayed = true;
            lblError.setVisible(true);
            setPauseDuration(lblError, errorMessage, colorHexa);
        }
    }

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
