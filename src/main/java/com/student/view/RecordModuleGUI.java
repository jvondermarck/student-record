package com.student.view;

import com.student.controller.SettingController;
import com.student.model.Observer;
import com.student.model.Student;
import com.student.model.University;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class RecordModuleGUI implements Observer {

    private SettingController controller;
    private ComboBox<Student> cboStudent;
    private TextField txtModuleName;
    private TextField txtGrade;

    public RecordModuleGUI(BorderPane paneRoot, SettingController controller)
    {
        this.controller = controller;
        this.controller.addObserver(this); // we add the class to our Observer, like that the model will be able to trigger the Update() method in case of adding a contact for example

        // We put in a pane the logo of the application
        HBox boxTitle = new HBox();

        ImageView imageView = new ImageView();
        Image imgLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/banner-mtu-sized.jpg")));
        imageView.setImage(imgLogo);
        imageView.setFitWidth(225);
        imageView.setPreserveRatio(true);
        boxTitle.getChildren().add(imageView);
        boxTitle.setAlignment(Pos.CENTER);
        boxTitle.getStyleClass().add("main-logo-title");

        paneRoot.setTop(boxTitle);

        // We create a title and a logo next to the label
        GridPane gridTitle = new GridPane();
        imageView = new ImageView();
        imgLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/survey.png")));
        imageView.setImage(imgLogo);
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);
        gridTitle.add(imageView, 0,0);

        Label lblTitle = new Label("Student module");
        lblTitle.getStyleClass().add("main-title"); // css File
        gridTitle.setPadding(new Insets(0,0,30,20));
        gridTitle.add(lblTitle, 1,0);

        // Then we draw a pane to put the 4 inputs (textfield) and 4 labels
        GridPane gridInputField = new GridPane();
        Label lblStudent = new Label("Select a student");
        cboStudent = new ComboBox<>();
        setUpCombobox();
        gridInputField.add(lblStudent, 0,0);
        gridInputField.add(cboStudent, 0,1);

        Label lblModule = new Label("Choose module");
        txtModuleName = new TextField();
        gridInputField.add(lblModule, 2,0);
        gridInputField.add(txtModuleName,3,0);

        Label lblGrade = new Label("Choose grade");
        txtGrade = new TextField();
        gridInputField.add(lblGrade, 2,1);
        gridInputField.add(txtGrade,3,1);

        gridInputField.getStyleClass().add("gridInputTab2"); // css File

        // We set some margin to put some space between each textfield and labels
        GridPane.setMargin(lblStudent, new Insets(20,0,0,50));
        GridPane.setMargin(lblModule, new Insets(0,0,0,0));
        GridPane.setMargin(txtGrade, new Insets(20,0,0,0));

        // We create the add, remove, list buttons
        HBox hboxModule = new HBox();
        Button btnAdd = new Button("Add the module");
        hboxModule.getChildren().add(btnAdd);
        hboxModule.setAlignment(Pos.CENTER);

        GridPane gridAllPane = new GridPane();
        gridAllPane.add(gridTitle, 0,0);
        gridAllPane.add(gridInputField, 0,1);
        gridAllPane.add(hboxModule, 0,2);

        GridPane.setMargin(hboxModule, new Insets(30,0,0,0)); // We add a bit of margin to put some space

        paneRoot.setCenter(gridAllPane);
        paneRoot.getStyleClass().add("paneRoot-tab1");

        btnAdd.setOnAction(event -> {
            if(checkTextfieldEmpty()){ // If not empty
                this.controller.addModuleStudent(cboStudent.getSelectionModel().getSelectedItem(),
                        txtModuleName.getText(), Integer.parseInt(txtGrade.getText()));
                cboStudent.getSelectionModel().clearSelection();
                txtGrade.setText("");
                txtModuleName.setText("");
            }
        });

        txtGrade.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*$")) { // if value is not a number in regex, we put empty string  (we use *$ to accept no digit like + or IDK)
                txtGrade.setText(newValue.replaceAll("[^0-9]", "")); // replaces all occurrences of "non digit value" to "empty string"
            }
        });
    }

    public void setUpCombobox()
    {
        // when we click on the student we want in the combobox we convert the object class to a string to show in the combobox
        cboStudent.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                if (student == null) return null;
                return student.getFullname();
            }

            @Override
            public Student fromString(String idNumber) {
                Student student = null;
                for(Student student1 : cboStudent.getItems()){
                    if(student1.getId().equals(Integer.parseInt(idNumber))){
                        student = student1;
                        break;
                    }
                }
                return student;
            }
        });

        // we retrieve the ArrayList of students from our database
        ObservableList<Student> wordsList = FXCollections.observableArrayList();
        wordsList.addAll(this.controller.getAllStudentDatabase());
        cboStudent.setItems(wordsList);

        // we now convert all the Student object into a fullname string
        cboStudent.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                return new ListCell<Student>() {
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

        cboStudent.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println(newValue.getFullname() + " -- " + newValue.getId());
        });
    }

    // we check before adding a module to the student if we all textfield and datepicker are not empty
    public boolean checkTextfieldEmpty()
    {
        return !cboStudent.getSelectionModel().isEmpty() && !txtModuleName.getText().isEmpty() &&
                !txtGrade.getText().isEmpty();
    }

    @Override
    public void updateView(University university) {

    }

    @Override
    public void displayError(University university, String errorMessage) {

    }
}
