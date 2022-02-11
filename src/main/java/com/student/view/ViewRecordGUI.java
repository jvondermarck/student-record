package com.student.view;

import com.student.controller.SettingController;
import com.student.model.Module;
import com.student.model.Observer;
import com.student.model.Student;
import com.student.model.University;
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
import javafx.util.StringConverter;

import java.util.Objects;

public class ViewRecordGUI implements Observer {

    private final SettingController controller;
    private final TableView<Module> tableView;
    private final ComboBox<Student> cboStudent;
    private final Label lblInfoStudent;

    public ViewRecordGUI(BorderPane paneRoot, SettingController controller)
    {
        this.controller = controller;
        this.controller.addObserver(this);

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

        // We create a title and a logo next to the label
        GridPane gridTitle = new GridPane();
        imageView = new ImageView();
        imgLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/survey.png")));
        imageView.setImage(imgLogo);
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);
        gridTitle.add(imageView, 0,0);

        Label lblTitle = new Label("Record Student");
        lblTitle.getStyleClass().add("main-title"); // css File
        gridTitle.add(lblTitle, 1,0);
        gridTitle.setPadding(new Insets(0,0,20,0));

        // Then we draw a pane to put the 4 inputs (textfield) and 4 labels
        GridPane gridInputCombobox = new GridPane();
        Label lblStudent = new Label("Select a student");
        cboStudent = new ComboBox<>();
        setUpCombobox();
        gridInputCombobox.add(lblStudent, 0,0);
        gridInputCombobox.add(cboStudent, 0,1);

        lblInfoStudent = new Label("");
        gridInputCombobox.add(lblInfoStudent, 0,2);
        GridPane.setMargin(lblInfoStudent, new Insets(15, 0,0,0));
        GridPane.setHalignment(lblInfoStudent, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(lblInfoStudent, VPos.CENTER); // To align vertically in the cell
        GridPane.setHalignment(lblStudent, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(lblStudent, VPos.CENTER); // To align vertically in the cell
        GridPane.setMargin(lblStudent, new Insets(0,0,10,0));

        // tableView view to show the arrayList of Contacts that we add when we click on the Add button
        tableView = new TableView<>();
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

        GridPane gridPaneAll = new GridPane();
        gridPaneAll.add(gridTitle, 0, 0);
        gridPaneAll.add(gridInputCombobox, 0, 1);
        gridPaneAll.add(tableView, 0, 2);
        gridPaneAll.setAlignment(Pos.CENTER);
        GridPane.setMargin(tableView, new Insets(30,0,30,0));

        paneRoot.setTop(boxTitle);
        paneRoot.setCenter(gridPaneAll);
        paneRoot.getStyleClass().add("paneRoot-tab1");
    }

    public void setUpCombobox()
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
        wordsList.addAll(this.controller.getAllStudentDatabase());
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

        cboStudent.getSelectionModel().selectedItemProperty().addListener((options, oldValue, student) -> {
            tableView.getItems().clear();
            for(int i=0; i<student.getModule().size(); i++)
            {
                tableView.getItems().add(student.getModule().get(i));
            }
            lblInfoStudent.setText("ID : " + student.getId() + " - Birth : " + student.getDateBirth());
        });
    }

    @Override
    public void updateView(University university) {
        setUpCombobox();
    }

    @Override
    public void displayError(University university, String errorMessage) {

    }
}
