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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ViewRecordGUI extends TemplateGUI implements Observer, IGUI {

    private SettingController controller;
    private TableView<Module> tableView;
    private ComboBox<Student> cboStudent;
    private Label lblInfoStudent;
    private BorderPane paneRoot;

    public ViewRecordGUI(){  }

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

        createTitleView(boxTitle, gridTitle); // To avoid duplicate code we use a template
        this.paneRoot.setTop(boxTitle);

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
        createComboboxStudent(cboStudent); // we call the template class to avoid dupliacted code

        // when we click on the student we want in the combobox we convert the object class to a string to show in the combobox
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
