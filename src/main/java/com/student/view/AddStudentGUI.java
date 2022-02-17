package com.student.view;

import com.student.controller.SettingController;
import com.student.model.Observer;
import com.student.model.Student;
import com.student.model.University;
import javafx.application.Platform;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddStudentGUI extends TemplateGUI implements Observer, IGUI {

    private SettingController controller;
    private TableView<Student> tableView;
    private TextField txtFirstname;
    private TextField txtLastname;
    private TextField txtID;
    private DatePicker txtDateBirth;
    private DateTimeFormatter formatterDate;
    private BorderPane paneRoot;
    private GridPane gridInputField;
    private Label lblError;

    public AddStudentGUI(){  }

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
        GridPane gridCheckin = new GridPane();
        lblError = new Label();

        createTitleView(boxTitle, gridCheckin, "Registration Student"); // To avoid duplicate code we use a template
        createErrorLabel(lblError, gridCheckin);

        // Then we draw a pane to put the 4 inputs (textfield) and 4 labels
        gridInputField = new GridPane();
        Label lblFirstname = new Label("First Name");
        txtFirstname = new TextField();
        gridInputField.add(lblFirstname, 0,0);
        gridInputField.add(txtFirstname,1,0);

        Label lblLastname = new Label("Last Name");
        txtLastname = new TextField();
        gridInputField.add(lblLastname, 2,0);
        gridInputField.add(txtLastname,3,0);

        Label lblID = new Label("Unique ID");
        txtID = new TextField();
        gridInputField.add(lblID, 0,1);
        gridInputField.add(txtID,1,1);

        Label lblPhone = new Label("Date birth");
        formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtDateBirth = new DatePicker();
        txtDateBirth.setValue(LocalDate.now());
        gridInputField.add(lblPhone, 2,1);
        gridInputField.add(txtDateBirth,3,1);

        gridInputField.getStyleClass().add("gridInput"); // css File

        // We set some margin to put some space between each textfield and labels
        GridPane.setMargin(lblID, new Insets(20,0,0,0));
        GridPane.setMargin(txtID, new Insets(20,0,0,0));
        GridPane.setMargin(lblLastname, new Insets(0,0,0,20));
        GridPane.setMargin(lblPhone, new Insets(20,0,0,20));
        GridPane.setMargin(txtDateBirth, new Insets(20,0,0,0));

        // We create the add, remove, list buttons
        HBox hboxStudentDetail = new HBox();
        Button btnAdd = new Button("Add");
        Button btnRemove = new Button("Remove");
        Button btnList = new Button("List");
        hboxStudentDetail.getChildren().addAll(btnAdd, btnRemove, btnList);
        hboxStudentDetail.setAlignment(Pos.CENTER);
        hboxStudentDetail.setSpacing(15);

        // tableView view to show the arrayList of Contacts that we add when we click on the Add button
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // We center the text of the tableView
        tableView.setPlaceholder(new Label("No student added. \nAdd a new student !"));
        tableView.getStyleClass().add("tableView-column"); // css File

        TableColumn<Student, String> tableFirstname = new TableColumn<>("First Name");
        tableFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname")); // it will take the getter of getFirstname() in the Student class
        tableFirstname.getStyleClass().add("tableView-column");

        TableColumn<Student, String> tableLastname = new TableColumn<>("Last Name");
        tableLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        TableColumn<Student, Integer> tableId = new TableColumn<>("ID");
        tableId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, Integer> tableDateBirth = new TableColumn<>("Date Birth");
        tableDateBirth.setCellValueFactory(new PropertyValueFactory<>("dateBirth"));

        // We add the 4 columns into the tableView
        tableView.getColumns().add(tableFirstname);
        tableView.getColumns().add(tableLastname);
        tableView.getColumns().add(tableId);
        tableView.getColumns().add(tableDateBirth);

        // We add in a layout the 4 inputs grid, the (add, remove, list) buttons, and the tableView View
        GridPane gridAllStudentPanes = new GridPane();
        gridAllStudentPanes.add(gridCheckin, 0,0); // The title Check-in and the logo
        gridAllStudentPanes.add(gridInputField, 0,1); // The textfield and labels to enter data to create contact
        gridAllStudentPanes.add(hboxStudentDetail,0,2); // The three buttons to Add, Delete and List the Contacts
        gridAllStudentPanes.add(tableView,0,3); // The add the tableView which will display the ArrayList of Student
        GridPane.setMargin(hboxStudentDetail, new Insets(30,0,0,0)); // We add a bit of margin to put some space
        GridPane.setMargin(tableView, new Insets(30,0,30,0));
        gridAllStudentPanes.setAlignment(Pos.CENTER);

        // The two last buttons : load, save and exit
        HBox boxFooter = new HBox();
        Button btnLoad = new Button("Load");
        Button btnSave = new Button("Save");
        Button btnExit = new Button("Exit");
        HBox.setMargin(btnLoad, new Insets(0,0,0,250));
        HBox.setMargin(btnSave, new Insets(0,0,0,0));
        HBox.setMargin(btnExit, new Insets(0,0,0,200));
        boxFooter.getChildren().addAll(btnLoad, btnSave, btnExit);
        boxFooter.setAlignment(Pos.CENTER);
        boxFooter.setSpacing(15);

        // we call the controller to add a student if everything is okay
        btnAdd.setOnAction(event -> this.controller.addStudent(txtFirstname, txtLastname, txtID, txtDateBirth, formatterDate));

        // We remove the Student which has been clicked-on, on the TableView
        btnRemove.setOnAction(event -> controller.deleteStudent(tableView));

        btnList.setOnAction(event -> {
            displayListStudent();
            btnRemove.setDisable(false);
            btnAdd.setDisable(false);
        });

        btnLoad.setOnAction(event -> {
            tableView.getItems().clear();
            for (Student contact : controller.getAllStudentDatabase()){
                tableView.getItems().add(contact);
            }
            displayMessage("Success : database loaded.", ColorMsg.SUCCESS.getColor());
            btnRemove.setDisable(true);
            btnAdd.setDisable(true);
        });

        btnSave.setOnAction(event -> controller.saveStudentDatabase());

        // When exiting the app, we ask the user to save, or quit without saving
        btnExit.setOnAction(event -> {
            // we create two buttons to save and exit OR to exit without saving
            ButtonType btnSave1 = new ButtonType("Save and exit", ButtonBar.ButtonData.APPLY);
            ButtonType btnExit1 = new ButtonType("Exit without saving", ButtonBar.ButtonData.CANCEL_CLOSE);

            // we create the alert which will appear on the screen
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "You should maybe save before exit the application\n", btnSave1, btnExit1);

            alert.setTitle("Exit the application");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(null) == btnExit1) // if clicked on "Exit without saving"
            {
                Platform.exit(); // we stop the application
            } else { // if clicked on "Save and exit"
                controller.saveStudentDatabase(); // we save everything in the database
                Platform.exit(); // and we exit
            }
        });

        // when we click on a row we update the list to show the data to the textfield
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if(!tableView.getItems().isEmpty() && !tableView.getSelectionModel().getSelectedItems().isEmpty())
            {
                Student selectedItems = tableView.getSelectionModel().getSelectedItems().get(0); // We retrieve the Student that we clicked-on and cast it as a <Student>
                // And we display in the four textfield the Student information
                txtFirstname.setText(selectedItems.getFirstname());
                txtLastname.setText(selectedItems.getLastname());
                txtID.setText(Integer.toString(selectedItems.getId()));
                txtDateBirth.setValue(LocalDate.parse(selectedItems.getDateBirth(), formatterDate));
            }
        });

        txtID.textProperty().addListener((observable, oldValue, newValue) -> {
            /* if(!newValue.matches("^[R][0-9]{8}"))
               txtID.setText(newValue.replaceAll("^[^R][^0-9]", "")); // replaces all occurrences of "non digit value" to "empty string"
            */

            if (!newValue.matches("[0-9]*$")) { // if value is not a number in regex, we put empty string  (we use *$ to accept no digit like + or IDK)
                txtID.setText(newValue.replaceAll("[^0-9]", "")); // replaces all occurrences of "non digit value" to "empty string"
            }
        });

        // when we change manually the date, we convert the string to a date format to avoid discarding what we wrote
        txtDateBirth.getEditor().focusedProperty().addListener((obj, wasFocused, isFocused)->{
            if (!isFocused) {
                try {
                    txtDateBirth.setValue(txtDateBirth.getConverter().fromString(txtDateBirth.getEditor().getText()));
                } catch (DateTimeParseException e) {
                    txtDateBirth.getEditor().setText(txtDateBirth.getConverter().toString(txtDateBirth.getValue()));
                    displayMessage("Error : enter valid date (dd/mm/yyyy).", ColorMsg.ERROR.getColor());
                }
            }
        });

        this.paneRoot.setCenter(gridAllStudentPanes);
        this.paneRoot.setBottom(boxFooter);
        this.paneRoot.getStyleClass().add("paneRoot-tab1");
    }

    public void resetTable()
    {
        if(tableView.getItems().isEmpty())
        {
            txtFirstname.setText("");
            txtLastname.setText("");
            txtID.setText("");
            txtDateBirth.setValue(LocalDate.now());
        } else {
            Student selectedItems = tableView.getItems().get(0);
            txtFirstname.setText(selectedItems.getFirstname());
            txtLastname.setText(selectedItems.getLastname());
            txtID.setText(Integer.toString(selectedItems.getId()));
            txtDateBirth.setValue(LocalDate.parse(selectedItems.getDateBirth(), formatterDate));
        }
    }

    // we check before adding a student in the arraylist if we all textfield and datepicker are not empty
    public boolean checkTextfieldEmpty()
    {
        return !txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() &&
                !txtID.getText().isEmpty() && (!(txtDateBirth.getValue() == null));
    }

    // when a student will be added in the List of students in our model, this method will be automatically called
    public void displayListStudent()
    {
        List<Student> listStudent = this.controller.getStudentList();
        tableView.getItems().clear();
        for (Student student : listStudent){
            tableView.getItems().add(student);
        }

        if(tableView.getItems().isEmpty())
        {
            resetTable();
        }

        displayMessage("Success : ArrayList loaded.", ColorMsg.SUCCESS.getColor());
    }

    @Override
    public void updateView(University university) {
        displayListStudent();
    }

    @Override
    public void displayMessage(String errorMessage, String colorHexa) {
        //ColorMsg.ERROR.getColor();
        showMessageTemplate(lblError, errorMessage, colorHexa);
    }
}
