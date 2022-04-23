package ie.mtu.application.student.view;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.Observer;
import ie.mtu.application.student.model.Student;
import ie.mtu.application.student.model.University;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * The class AddStudentGUI from the View package will display all students from the application
 * It will be also possible to create a student and update/delete an existing student
 */
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
        formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        txtDateBirth = new DatePicker();
        txtDateBirth.setValue(LocalDate.parse(LocalDate.now().format(formatterDate)));
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
        Button btnUpdate = new Button("Update");
        hboxStudentDetail.getChildren().addAll(btnAdd, btnRemove, btnUpdate);
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
        tableId.setCellValueFactory(new PropertyValueFactory<>("studentID"));

        TableColumn<Student, Date> tableDateBirth = new TableColumn<>("Date Birth");
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

        /*
        // The two last buttons : load, save and exit
        HBox boxFooter = new HBox();
        //Button btnLoad = new Button("Load");
        //Button btnSave = new Button("Save");
        //Button btnExit = new Button("Exit");
        //HBox.setMargin(btnLoad, new Insets(0,0,0,250));
        //HBox.setMargin(btnSave, new Insets(0,0,0,0));
        HBox.setMargin(btnExit, new Insets(0,0,0,550));
        boxFooter.getChildren().addAll(btnExit);
        boxFooter.setAlignment(Pos.CENTER);
        boxFooter.setSpacing(15);
        this.paneRoot.setBottom(boxFooter);
         */

        loadStudent(); // we load automatically

        // we call the controller to add a student if everything is okay
        btnAdd.setOnAction(event -> this.controller.insertStudent(txtFirstname, txtLastname, txtID, txtDateBirth));

        // We remove the Student which has been clicked-on, on the TableView
        btnRemove.setOnAction(event -> controller.deleteStudent(tableView));

        btnUpdate.setOnAction(event -> controller.updateStudent(tableView, txtFirstname, txtLastname, txtID, txtDateBirth));

        // When exiting the app, we ask the user to save, or quit without saving
        //btnExit.setOnAction(event -> exitButton());

        // when we click on a row we update the list to show the data to the textfield
        tableView.setOnMouseClicked((MouseEvent event) -> {
            if(!tableView.getItems().isEmpty() && !tableView.getSelectionModel().getSelectedItems().isEmpty())
            {
                Student selectedItems = tableView.getSelectionModel().getSelectedItems().get(0); // We retrieve the Student that we clicked-on and cast it as a <Student>
                // And we display in the four textfield the Student information
                txtFirstname.setText(selectedItems.getFirstname());
                txtLastname.setText(selectedItems.getLastname());
                txtID.setText(Integer.toString(selectedItems.getStudentID()));
                //LocalDate lol = new java.sql.Date(selectedItems.getDateBirth().getTime()).toLocalDate();
                txtDateBirth.setValue(LocalDate.parse(selectedItems.getDateBirth().toString(), formatterDate));

            }
        });

        // when we change manually the date, we convert the string to a date format to avoid discarding what we wrote
        txtDateBirth.getEditor().focusedProperty().addListener((obj, wasFocused, isFocused)->{
            if (!isFocused) {
                try {
                    txtDateBirth.setValue(txtDateBirth.getConverter().fromString(txtDateBirth.getEditor().getText()));
                } catch (DateTimeParseException e) {
                    txtDateBirth.getEditor().setText(txtDateBirth.getConverter().toString(txtDateBirth.getValue()));
                    displayMessage("Error : Enter valid date (dd/mm/yyyy).", ColorMsg.ERROR.getColor());
                }
            }
        });

        txtID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*$")) { // if value is not a number in regex, we put empty string  (we use *$ to accept no digit like + or IDK)
                txtID.setText(newValue.replaceAll("[^0-9]", "")); // replaces all occurrences of "non digit value" to "empty string"
                displayMessage("Error : please only put numbers.", ColorMsg.ERROR.getColor());
            }
        });

        this.paneRoot.setCenter(gridAllStudentPanes);
        this.paneRoot.getStyleClass().add("paneRoot-tab1");
    }

    /**
     * This function will reset the three main Textfields and DateTimePicker
     */
    public void resetTable()
    {
        txtFirstname.setText("");
        txtLastname.setText("");
        txtID.setText("");
        txtDateBirth.setValue(LocalDate.now());
    }

    /**
     * The method will ask the controller to get a list of all students and display them in the TableView panel
     */
    public void loadStudent()
    {
        tableView.getItems().clear();
        for (Student contact : controller.getStudentList()){
            tableView.getItems().add(contact);
        }
        displayMessage("Success : Database loaded.", ColorMsg.SUCCESS.getColor());
    }

    /**
     * We check before adding a student in the arraylist if we all textfield and datepicker are not empty
     */
    public boolean checkTextfieldEmpty()
    {
        return !txtFirstname.getText().isEmpty() && !txtLastname.getText().isEmpty() &&
                !txtID.getText().isEmpty() && (!(txtDateBirth.getValue() == null));
    }

    /**
     * when a student will be added in the List of students in our model, this method will be automatically called
     */
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
    }

    @Override
    public void updateView(University university) {
        displayListStudent();
    }

    @Override
    public void displayMessage(String errorMessage, String colorHexa) {
        showMessageTemplate(lblError, errorMessage, colorHexa);
    }
}
