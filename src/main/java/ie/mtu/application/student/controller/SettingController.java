package ie.mtu.application.student.controller;

import ie.mtu.application.student.model.DBConnection;
import ie.mtu.application.student.model.Module;
import ie.mtu.application.student.model.Observer;
import ie.mtu.application.student.model.Student;
import ie.mtu.application.student.model.University;
import ie.mtu.application.student.view.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.Date;
import java.util.List;

/**
 * This class from is the main controller in the MVC application.
 * The methods will be called by the View package and this class will just control the parameters from the view and give them to the Model
 */
public class SettingController {

    /**
     * The main class of the Model package
     */
    private final University university;

    /**
     * The main class of the View package
     */
    private MainGUI mainView;
    private AddStudentGUI addStudentGUI;
    private RecordModuleGUI recordModuleGUI;
    private ViewRecordGUI viewRecordGUI;

    public SettingController(University university)
    {
        this.university = university;
    }

    /**
     * This function will instantiate the instance variables of the different View classes from the View package
     * @param view the main class of the View package
     */
    public void setUpView(MainGUI view)
    {
        this.mainView = view;
        this.addStudentGUI = mainView.getAddStudent();
        this.recordModuleGUI = mainView.getRecordModule();
        this.viewRecordGUI = mainView.getViewRecord();
    }

    /**
     * The method will create a Student object from the given parameters
     * Then it will call the model to insert the Student object in the database
     * And it will display a message
     * It will also check if the given parameters are correct
     * @param txtFirstname : the firstame of the student from the textfield (will be cast into a string)
     * @param txtLastname : the lastname of the student from the textfield (will be cast into a string)
     * @param txtID : the id of the student from the textfield (will be cast into an integer)
     * @param txtDateBirth : the date of birth of the student from the textfield (will be cast into a Date object)
     */
    public void insertStudent(TextField txtFirstname, TextField txtLastname, TextField txtID, DatePicker txtDateBirth)
    {
        if(this.addStudentGUI.checkTextfieldEmpty()){ // If not empty
            if(txtFirstname.getText().length() < 2 || txtLastname.getText().length() < 2) {
                this.addStudentGUI.displayMessage("Error : Please name too short.", ColorMsg.ERROR.getColor());
            } else {
                Student student = new Student(
                        txtFirstname.getText(), txtLastname.getText(),
                        Integer.parseInt(txtID.getText()), Date.valueOf(txtDateBirth.getValue()));

                // we send the contact value to the model
                if(!DBConnection.isStudentExist(student.getId())){ // check if the student with the same ID doesn't exist already
                    this.university.insertStudent(student);
                    this.addStudentGUI.displayMessage("Success : Student added.", ColorMsg.SUCCESS.getColor());
                    this.addStudentGUI.resetTable();
                } else {
                    this.addStudentGUI.displayMessage("This ID number is already existing.", ColorMsg.ERROR.getColor());
                }
            }
        } else {
            this.addStudentGUI.displayMessage("Error : Please fill in all fields.", ColorMsg.ERROR.getColor());
        }
    }

    /**
     * It will delete a student from the database
     * @param tableView : the TableView from the GUI
     */
    public void deleteStudent(TableView<Student> tableView)
    {
        if(this.addStudentGUI.checkTextfieldEmpty()) // If not empty
        {
            Student student = tableView.getSelectionModel().getSelectedItems().get(0);
            this.university.deleteStudent(student);
            this.addStudentGUI.resetTable();
            this.addStudentGUI.displayMessage("Warning : Student deleted.", ColorMsg.SUCCESS.getColor());
        } else {
            this.addStudentGUI.displayMessage("Error : Please select a student.", ColorMsg.ERROR.getColor());
        }
    }

    /**
     * Function that will update an existing Student
     * @param tableView : the TableView that contains all student from the database
     * @param txtFirstname : the Textfield that contains the fistname of the student
     * @param txtLastname : the Textfield that contains the lastname of the student
     * @param txtID : the Textfield that contains the ID of the student
     * @param txtDateBirth : the DatePicker that contains the date of birth of the student
     */
    public void updateStudent(TableView<Student> tableView, TextField txtFirstname, TextField txtLastname, TextField txtID, DatePicker txtDateBirth) {
        if(this.addStudentGUI.checkTextfieldEmpty()) // If not empty
        {
            Student student = tableView.getSelectionModel().getSelectedItems().get(0);
            int oldId = student.getId();

            // Because we want to update the student, we change its instance variables
            student.setStudent(txtFirstname.getText(), txtLastname.getText(),
                    Integer.parseInt(txtID.getText()), Date.valueOf(txtDateBirth.getValue()));

            this.university.updateStudent(student, oldId);

            this.addStudentGUI.resetTable();
            this.addStudentGUI.displayMessage("Success : Student updated.", ColorMsg.SUCCESS.getColor());
        } else {
            this.addStudentGUI.displayMessage("Error : Please select a student.", ColorMsg.ERROR.getColor());
        }
    }

    /**
     * @return return from the model a list of all students
     */
    public List<Student> getStudentList()
    {
        return DBConnection.getStudent();
    }

    /**
     * The method will insert a new module from a specific student
     * @param cboStudent : the Combobox of type Student that the user selected
     * @param txtGrade : the Textfield that contains the grade
     * @param txtModuleName : the Textfield that contains the module name
     * @param tableView : the TableView that contains all the modules from the student selected
     */
    public void insertModule(ComboBox<Student> cboStudent, TextField txtGrade, TextField txtModuleName, TableView<Module> tableView) {
        if(this.recordModuleGUI.checkTextfieldEmpty()){ // If not empty
            Student student = cboStudent.getSelectionModel().getSelectedItem();
            Module module = new Module(txtModuleName.getText(), Integer.parseInt(txtGrade.getText()), student.getId());

            if(!DBConnection.isDuplicatedModuleName(module.getModuleName(), module.getId())){
                student.insertModule(module);
                this.recordModuleGUI.displayMessage("Success : Module added.", ColorMsg.SUCCESS.getColor());
                this.recordModuleGUI.resetTable();
                this.recordModuleGUI.updateListView(tableView, cboStudent);
            } else {
                this.recordModuleGUI.displayMessage("Error : duplicated module.", ColorMsg.ERROR.getColor());
            }
        } else {
            this.recordModuleGUI.displayMessage("Error : Please fill all input.", ColorMsg.ERROR.getColor());
        }
    }

    /**
     * It will delete a module from a specific student
     * @param cboStudent : the ComboBox of type Student that the user selected
     * @param moduleTableView : the TableView that the user clicked on one of the modules of the specific student
     */
    public void deleteModule(ComboBox<Student> cboStudent, TableView<Module> moduleTableView)
    {
        if(this.recordModuleGUI.checkTextfieldEmpty()) // If not empty
        {
            Student student = cboStudent.getSelectionModel().getSelectedItem();
            Module module = moduleTableView.getSelectionModel().getSelectedItems().get(0);

            student.deleteModule(module);

            this.recordModuleGUI.resetTable();
            this.recordModuleGUI.updateListView(moduleTableView, cboStudent);
            this.recordModuleGUI.displayMessage("Warning : Module deleted.", ColorMsg.SUCCESS.getColor());
        } else {
            this.recordModuleGUI.displayMessage("Error : Please select a module.", ColorMsg.ERROR.getColor());
        }
    }

    /**
     * It will call the model to get the list of all modules from a student
     * @param studentID : the student ID that we want to get his modules
     * @return a List of Module
     */
    public List<Module> getModuleList(int studentID)
    {
        return DBConnection.getModule(studentID);
    }

    /**
     * The method will call the model to make him add a class that implemented the Observer interface
     * @param observer : the class that implemented the Observer interface
     */
    public void addObserver(Observer observer) { this.university.addObservers(observer);}

}
