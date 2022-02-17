package com.student.model;

import com.student.utilities.CSVParser;
import com.student.view.AddStudentGUI;
import com.student.view.ColorMsg;

import java.util.ArrayList;
import java.util.List;

public class University {

    private final List<Observer> listObserver;
    private final List<Student> listStudent;
    private final List<Student> listStudentDatabase;

    public University()
    {
        this.listObserver = new ArrayList<>();
        this.listStudent = new ArrayList<>();
        this.listStudentDatabase = new ArrayList<>();
        CSVParser.setupDatabase();
    }

    public void addStudent(List<String> list)
    {
        // We check if the student we want to add is not already in the ArrayList, so we check the ID
        if(!checkAlreadyInList(Integer.parseInt(list.get(2))))
        {
            try{
                Student student = new Student(list.get(0), list.get(1),
                        Integer.parseInt(list.get(2)), list.get(3));
                this.listStudent.add(student);
                notifyObservers();
            } catch(NumberFormatException ex){ // handle bad parsing of the string value to integer
                System.out.println("NumberFormatException : Bad number value, might be too long...");
            }
        } else {
            for(Observer observer : listObserver){
                if(observer instanceof AddStudentGUI)
                    observer.displayMessage("This ID number is already existing.", ColorMsg.ERROR.getColor());
            }
        }
    }

    public void addModuleStudent(Student student, String name, int grade)
    {
        student.addModuleDatabase(name, grade);
        notifyObservers();
    }

    public boolean checkAlreadyInList(int IDContact)
    {
        boolean alreadyInArray = false;

        // We check the list of students first
        for(Student student : listStudent)
            if(student.getId() == IDContact)
            {
                alreadyInArray = true;
                break;
            }

        // Then we check the database
        retrieveContactDatabase();
        for(Student student : listStudentDatabase)
            if(student.getId() == IDContact)
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
        for(Student student : listStudent)
            student.addStudentDatabase();

        this.listStudent.clear(); // we delete the list of Contact because we just saved it to the database
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

    public void retrieveContactDatabase()
    {
        listStudentDatabase.clear();
        CSVParser.retrieveContactDatabase(listStudentDatabase);
    }

    public List<Student> getListStudent() {
        return listStudent;
    }

    public List<Student> getListContactDatabase() {
        retrieveContactDatabase();
        return listStudentDatabase;
    }
}
