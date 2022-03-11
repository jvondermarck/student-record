package com.student.model;

import com.student.utilities.ObjectParser;
import com.student.view.AddStudentGUI;
import com.student.view.ColorMsg;

import java.util.ArrayList;
import java.util.List;

public class University {

    private final List<Observer> listObserver;
    private final List<Student> listStudent;

    public University()
    {
        this.listObserver = new ArrayList<>();
        this.listStudent = new ArrayList<>();
        //CSVParser.setupDatabase();
        //CSVParser.retrieveContactDatabase(listStudent);

        ObjectParser.deserializeStudent(listStudent); // We deserialize all the students in a list
        for(Student student : listStudent){ // For each student we retrieve their module
            student.retrieveModuleDatabase();
        }

    }

    public void addStudent(List<String> list)
    {
        // We check if the student we want to add is not already in the ArrayList, so we check the ID
        if(!checkAlreadyInList(list.get(2)))
        {
            Student student = new Student(list.get(0), list.get(1),
                    list.get(2), list.get(3));
            this.listStudent.add(student);
            // we display a succes message
            for(Observer observer : listObserver){
                if(observer instanceof AddStudentGUI)
                    observer.displayMessage("Success : Student added.", ColorMsg.SUCCESS.getColor());
            }
            notifyObservers();
        } else {
            for(Observer observer : listObserver){
                if(observer instanceof AddStudentGUI)
                    observer.displayMessage("This ID number is already existing.", ColorMsg.ERROR.getColor());
            }
        }
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

    public boolean checkAlreadyInList(String IDContact)
    {
        boolean alreadyInArray = false;

        // We check the list of students first
        for(Student student : listStudent)
            if(student.getId().equals(IDContact))
            {
                alreadyInArray = true;
                break;
            }

        return alreadyInArray;
    }

    public void deleteStudent(Student student)
    {
        this.listStudent.remove(student);
        notifyObservers();
    }

    public void saveStudentDatabase()
    {
        //CSVParser.addDataDatabase(listStudent);
        ObjectParser.addStudent(listStudent);
        notifyObservers();
    }

    public void addObservers(Observer observer){
        this.listObserver.add(observer);
    }

    public void notifyObservers(){
        for(Observer observer : listObserver){
            observer.updateView(this);
        }
    }

    public List<Student> getListStudent() {
        return listStudent;
    }

}
