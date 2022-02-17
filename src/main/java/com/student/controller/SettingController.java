package com.student.controller;

import com.student.model.Module;
import com.student.model.Observer;
import com.student.model.Student;
import com.student.model.University;
import com.student.view.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SettingController {

    private final University university;

    private MainGUI mainView;
    private AddStudentGUI addStudentGUI;
    private RecordModuleGUI recordModuleGUI;
    private ViewRecordGUI viewRecordGUI;

    public SettingController(University university)
    {
        this.university = university;
    }

    public void setUpView(MainGUI view)
    {
        this.mainView = view;
        this.addStudentGUI = mainView.getAddStudent();
        this.recordModuleGUI = mainView.getRecordModule();
        this.viewRecordGUI = mainView.getViewRecord();
    }

    public void addStudent(TextField txtFirstname, TextField txtLastname, TextField txtID, DatePicker txtDateBirth, DateTimeFormatter formatterDate)
    {
        if(this.addStudentGUI.checkTextfieldEmpty()){ // If not empty
            // we add the student into the ArrayList of <Student> and display it in the TableView
            List<String> student = new ArrayList<>();
            student.add(txtFirstname.getText());
            student.add(txtLastname.getText());
            student.add(txtID.getText());
            student.add(formatterDate.format(txtDateBirth.getValue()));

            // We clear the inputs
            txtFirstname.setText("");
            txtLastname.setText("");
            txtID.setText("");
            txtDateBirth.setValue(LocalDate.now());

            // we send the contact value to the model
            this.university.addStudent(student);
            // we display a succes message
            this.addStudentGUI.displayMessage("Success : student added.", ColorMsg.SUCCESS.getColor());
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
            this.addStudentGUI.displayMessage("Warning : student deleted.", ColorMsg.SUCCESS.getColor());
        } else {
            this.addStudentGUI.displayMessage("Error : please select a student.", ColorMsg.ERROR.getColor());
        }

    }

    public void saveStudentDatabase()
    {
        this.university.saveStudentDatabase();
    }

    public List<Student> getStudentList()
    {
        return this.university.getListStudent();
    }

    public void addModuleStudent(ComboBox<Student> cboStudent, TextField txtGrade, TextField txtModuleName, TableView<Module> tableView) {
        if(this.recordModuleGUI.checkTextfieldEmpty()){ // If not empty
            this.recordModuleGUI.displayMessage("Success : " + cboStudent.getSelectionModel().getSelectedItem().getFirstname() + "'s module added.", ColorMsg.SUCCESS.getColor());
            Student student = cboStudent.getSelectionModel().getSelectedItem();
            this.university.addModuleStudent(student, txtModuleName.getText(), Integer.parseInt(txtGrade.getText()));

            txtGrade.setText("");
            txtModuleName.setText("");
            this.recordModuleGUI.updateListView(tableView, cboStudent);
        } else {
            this.recordModuleGUI.displayMessage("Error : please fill all input.", ColorMsg.ERROR.getColor());
        }
    }

    public List<Student> getAllStudentDatabase()
    {
        return this.university.getListContactDatabase();
    }

    public void addObserver(Observer observer) { this.university.addObservers(observer);}
}
