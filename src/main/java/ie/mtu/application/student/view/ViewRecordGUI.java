package ie.mtu.application.student.view;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.Module;
import ie.mtu.application.student.model.Observer;
import ie.mtu.application.student.model.Student;
import ie.mtu.application.student.model.University;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * Class that will display student information and their grades, user will have to select in a combobox a student to show his information
 */
public class ViewRecordGUI extends TemplateGUI implements Observer, IGUI {

    private SettingController controller;
    private TableView<Module> tableView;
    private ComboBox<Student> cboStudent;
    private Label lblInfoStudent;
    private BorderPane paneRoot;

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

        createTitleView(boxTitle, gridTitle, "Record Student"); // To avoid duplicate code we use a template

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
        setUpListViewModule(tableView, cboStudent);

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

    /**
     * Set up the combobox with the Student class in it
     */
    public void setUpCombobox()
    {
        createComboboxStudent(cboStudent); // we call the template class to avoid dupliacted code
        cboStudent.getSelectionModel().clearSelection();

        // when we click on the student we want in the combobox we convert the object class to a string to show in the combobox
        cboStudent.getSelectionModel().selectedItemProperty().addListener((options, oldValue, student) -> {
            if(student != null) // to avoid any problem we make sure that a student is well selected in the combobox
            {
                lblInfoStudent.setText("ID : " + student.getStudentID() + " - Birth : " + student.getDateBirth());
            }
        });
    }

    @Override
    public void updateView(University university) {
        setUpCombobox();
        updateListView(tableView, cboStudent);
    }

    @Override
    public void displayMessage(String errorMessage, String colorHexa) {

    }
}
