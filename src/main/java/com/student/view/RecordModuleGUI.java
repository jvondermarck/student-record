package com.student.view;

import com.student.controller.SettingController;
import com.student.model.Module;
import com.student.model.Observer;
import com.student.model.Student;
import com.student.model.University;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class RecordModuleGUI extends TemplateGUI implements Observer, IGUI {

    private SettingController controller;
    private ComboBox<Student> cboStudent;
    private TextField txtModuleName;
    private TextField txtGrade;
    private BorderPane paneRoot;
    private TableView<Module> tableView;
    private Label lblError;

    @Override
    public void setUpGUI(BorderPane paneRoot, SettingController controller) {
        super.setUpGUI(paneRoot, controller);
        this.paneRoot = paneRoot;
        this.controller = controller;
    }

    @Override
    public void createWindow()
    {
        HBox boxTitle = new HBox();
        GridPane gridTitle = new GridPane();
        lblError = new Label();

        createTitleView(boxTitle, gridTitle, "Student module"); // To avoid duplicate code we use a template
        createErrorLabel(lblError, gridTitle);
        gridTitle.setPadding(new Insets(0,0,30,20));

        // Then we draw a pane to put the 4 inputs (textfield) and 4 labels
        GridPane gridInputField = new GridPane();
        Label lblStudent = new Label("Select a student");
        lblStudent.setStyle(" -fx-font-size: 17px; ");
        cboStudent = new ComboBox<>();
        createComboboxStudent(cboStudent); // we call the template to avoid dupliacted code
        gridInputField.add(lblStudent, 0,0);
        gridInputField.add(cboStudent, 0,1);

        Label lblModule = new Label("Choose module");
        txtModuleName = new TextField();
        gridInputField.add(lblModule, 2,0);
        gridInputField.add(txtModuleName,3,0);
        GridPane.setHalignment(lblModule, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(lblModule, VPos.CENTER); // To align vertically in the cell

        Label lblGrade = new Label("Choose grade");
        txtGrade = new TextField();
        gridInputField.add(lblGrade, 2,1);
        gridInputField.add(txtGrade,3,1);
        GridPane.setHalignment(lblGrade, HPos.CENTER); // To align horizontally in the cell
        GridPane.setValignment(lblGrade, VPos.CENTER); // To align vertically in the cell

        gridInputField.getStyleClass().add("gridInputTab2"); // css File

        // We set some margin to put some space between each textfield and labels
        GridPane.setMargin(lblStudent, new Insets(20,0,0,50));
        GridPane.setMargin(lblGrade, new Insets(18,0,0,0));
        GridPane.setMargin(txtGrade, new Insets(20,0,0,0));

        // We create the add, remove, list buttons
        HBox hboxModule = new HBox();
        Button btnAdd = new Button("Add the module");
        hboxModule.getChildren().add(btnAdd);
        hboxModule.setAlignment(Pos.CENTER);

        tableView = new TableView<>();
        setUpListViewModule(tableView, cboStudent);

        GridPane gridAllPane = new GridPane();
        gridAllPane.add(gridTitle, 0,0);
        gridAllPane.add(gridInputField, 0,1);
        gridAllPane.add(hboxModule, 0,2);
        gridAllPane.add(tableView, 0,3);

        GridPane.setMargin(hboxModule, new Insets(30,0,0,0)); // We add a bit of margin to put some space
        GridPane.setMargin(tableView, new Insets(30,50,30,50)); // We add a bit of margin to put some space

        paneRoot.setCenter(gridAllPane);
        paneRoot.getStyleClass().add("paneRoot-tab1");

        btnAdd.setOnAction(event -> this.controller.addModuleStudent(cboStudent, txtGrade, txtModuleName, tableView));

        txtGrade.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*$")) { // if value is not a number in regex, we put empty string  (we use *$ to accept no digit like + or IDK)
                txtGrade.setText(newValue.replaceAll("[^0-9]", "")); // replaces all occurrences of "non digit value" to "empty string"
                displayMessage("Error : please only put numbers.", ColorMsg.ERROR.getColor());
            }
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
        createComboboxStudent(cboStudent);
    }

    @Override
    public void displayMessage(String errorMessage, String colorHexa) {
        showMessageTemplate(lblError, errorMessage, colorHexa);
    }
}
