package com.student.model;

import com.student.view.AddStudentGUI;
import com.student.view.ColorMsg;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class University {

    public static List<Observer> listObserver;

    public University()
    {
        listObserver = new ArrayList<>();
    }

    public void saveStudentDatabase(Student student)
    {
        DBConnection.insertStudent(student);
        notifyObservers();
    }

    public void deleteStudent(Student student)
    {
        DBConnection.deleteStudent(student);
        notifyObservers();
    }

    public void updateStudent(Student student)
    {
        DBConnection.updateStudent(student);
        notifyObservers();
    }

    public void addModuleStudent(Student student, String name, int grade)
    {
        student.addModule(name, grade);
        notifyObservers();
    }

    public void deleteModuleStudent(Student student, Module module)
    {
        student.deleteModule(module);
        notifyObservers();
    }

    public void addObservers(Observer observer){
        listObserver.add(observer);
    }

    public void notifyObservers(){
        for(Observer observer : listObserver){
            observer.updateView(this);
        }
    }
}
