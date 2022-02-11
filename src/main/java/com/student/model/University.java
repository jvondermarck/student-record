package com.student.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
            notifyObserversError("This ID number is already existing.");
        }
    }

    public void addModuleStudent(Student student, String name, int grade)
    {
        student.addModuleDatabase(name, grade);
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

    public void notifyObserversError(String errorMessage){
        for(Observer observer : listObserver){
            observer.displayError(this, errorMessage);
        }
    }

    public void retrieveContactDatabase()
    {
        listStudentDatabase.clear();
        //InputStream inputStreamm = ClassLoader.getSystemClassLoader().getResourceAsStream("src/main/java/covid/version1/databse_student.csv");
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/student/databse_student.csv"))) {
                String line;
                br.readLine(); // we skip the header line
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    Student contact = new Student(values[0], values[1], Integer.parseInt(values[2]), values[3]);
                    listStudentDatabase.add(contact);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getListStudent() {
        return listStudent;
    }

    public List<Student> getListContactDatabase() {
        retrieveContactDatabase();
        return listStudentDatabase;
    }
}
