package com.student.view;

import com.student.controller.SettingController;
import com.student.model.Student;
import com.student.view.IGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.Objects;

public class TemplateGUI implements IGUI {

    private SettingController controller;
    private BorderPane paneRoot;

    @Override
    public void setUpGUI(BorderPane paneRoot, SettingController controller) {
        this.controller = controller;
        this.paneRoot = paneRoot;
    }

    @Override
    public void createWindow() {

    }

    public void createTitleView(HBox boxImage, GridPane gridTitle)
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

        // We create a title and a logo next to the label
        imageView = new ImageView();
        imgLogo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/student/img/survey.png")));
        imageView.setImage(imgLogo);
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);
        gridTitle.add(imageView, 0,0);
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
    }
}
