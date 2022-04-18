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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SettingController {

    private final University university;

    private MainGUI mainView;
    private AddStudentGUI addStudentGUI;
    private RecordModuleGUI recordModuleGUI;
    private ViewRecordGUI viewRecordGUI;
    private DBConnection database;

    public SettingController(University university, DBConnection db)
    {
        this.university = university;
        this.database = db;
    }

    public void setUpView(MainGUI view)
    {
        this.mainView = view;
        this.addStudentGUI = mainView.getAddStudent();
        this.recordModuleGUI = mainView.getRecordModule();
        this.viewRecordGUI = mainView.getViewRecord();
    }

    public void saveStudentDatabase(TextField txtFirstname, TextField txtLastname, TextField txtID, DatePicker txtDateBirth, DateTimeFormatter formatterDate)
    {
        if(this.addStudentGUI.checkTextfieldEmpty()){ // If not empty
            if(txtFirstname.getText().length() < 2 || txtLastname.getText().length() < 2)
            {
                this.addStudentGUI.displayMessage("Error : Please name too short.", ColorMsg.ERROR.getColor());
            } else {
                // we add the student into the ArrayList of <Student> and display it in the TableView
                Student student = new Student(
                        txtFirstname.getText(), txtLastname.getText(),
                        Integer.parseInt(txtID.getText()), Date.valueOf(txtDateBirth.getValue()));

                // We clear the inputs
                txtFirstname.setText("");
                txtLastname.setText("");
                txtID.setText("");
                txtDateBirth.setValue(LocalDate.now());

                // we send the contact value to the model
                this.university.saveStudentDatabase(student);
            }
        } else {
            this.addStudentGUI.displayMessage("Error : Please fill in all fields.", ColorMsg.ERROR.getColor());
        }
    }

    public void deleteStudent(TableView<Student> tableView)
    {
        if(this.addStudentGUI.checkTextfieldEmpty()) // If not empty
        {
            Student selectedItems = tableView.getSelectionModel().getSelectedItems().get(0);
            this.addStudentGUI.resetTable();
            this.university.deleteStudent(selectedItems);
            this.addStudentGUI.displayMessage("Warning : Student deleted.", ColorMsg.SUCCESS.getColor());
        } else {
            this.addStudentGUI.displayMessage("Error : Please select a student.", ColorMsg.ERROR.getColor());
        }
    }

    public void updateStudent(TableView<Student> tableView, TextField txtFirstname, TextField txtLastname, TextField txtID, DatePicker txtDateBirth, DateTimeFormatter formatterDate) {
        if(this.addStudentGUI.checkTextfieldEmpty()) // If not empty
        {
            Student selectedItems = tableView.getSelectionModel().getSelectedItems().get(0);
            selectedItems.setStudent(txtFirstname.getText(), txtLastname.getText(),
                    Integer.parseInt(txtID.getText()), Date.valueOf(txtDateBirth.getValue()));
            this.addStudentGUI.resetTable();
            this.university.updateStudent(selectedItems);
            this.addStudentGUI.displayMessage("Success : Student updated.", ColorMsg.SUCCESS.getColor());
        } else {
            this.addStudentGUI.displayMessage("Error : Please select a student.", ColorMsg.ERROR.getColor());
        }
    }

    public void updateModuleStudent(ComboBox<Student> cboStudent, TextField txtGrade, TextField txtModuleName, TableView<Module> tableView){
        if(this.recordModuleGUI.checkTextfieldEmpty()){ // If not empty
            Module module= tableView.getSelectionModel().getSelectedItems().get(0);
            this.university.updateModuleStudent(module);

            txtGrade.setText("");
            txtModuleName.setText("");
            this.recordModuleGUI.onAddChangeTableViewModule(tableView, cboStudent);
        } else {
            this.recordModuleGUI.displayMessage("Error : Please fill all input.", ColorMsg.ERROR.getColor());
        }
    }

    public void deleteStudentModule(ComboBox<Student> cboStudent, TableView<Module> moduleTableView)
    {
        if(this.recordModuleGUI.checkTextfieldEmpty()) // If not empty
        {
            Module module = moduleTableView.getSelectionModel().getSelectedItems().get(0);
            this.recordModuleGUI.resetTable();
            this.university.deleteModuleStudent(module);
            this.recordModuleGUI.onAddChangeTableViewModule(moduleTableView, cboStudent);
            this.recordModuleGUI.displayMessage("Warning : Module deleted.", ColorMsg.SUCCESS.getColor());
        } else {
            this.recordModuleGUI.displayMessage("Error : Please select a module.", ColorMsg.ERROR.getColor());
        }
    }

    public List<Student> getStudentList()
    {
        return DBConnection.getStudent();
    }

    public void addModuleStudent(ComboBox<Student> cboStudent, TextField txtGrade, TextField txtModuleName, TableView<Module> tableView) {
        if(this.recordModuleGUI.checkTextfieldEmpty()){ // If not empty
            Student student = cboStudent.getSelectionModel().getSelectedItem();
            Module module = new Module(txtModuleName.getText(), Integer.parseInt(txtGrade.getText()), student.getId());
            this.university.addModuleStudent(module);

            txtGrade.setText("");
            txtModuleName.setText("");
            this.recordModuleGUI.onAddChangeTableViewModule(tableView, cboStudent);
        } else {
            this.recordModuleGUI.displayMessage("Error : Please fill all input.", ColorMsg.ERROR.getColor());
        }
    }

    public List<Module> getModuleList(int studentID)
    {
        return DBConnection.getModule(studentID);
    }

    public void addObserver(Observer observer) { this.university.addObservers(observer);}

}
