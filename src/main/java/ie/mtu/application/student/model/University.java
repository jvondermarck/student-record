package ie.mtu.application.student.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The University class is the main class of the Model package.
 * This class is taking care of the students
 */
public class University {

    /**
     * List that contains all the classes that implimented the Observer interface
     */
    public static List<Observer> listObserver;

    /**
     * University set up the observers
     */
    public University()
    {
        listObserver = new ArrayList<>();
    }

    /**
     * Insert a student in the database
     * @param student : the student object to add
     */
    public void insertStudent(Student student)
    {
        DBConnection.insertStudent(student);
        notifyObservers();
    }

    /**
     * Delete a student in the database
     * @param student : the student object to delete
     */
    public void deleteStudent(Student student)
    {
        DBConnection.deleteStudent(student);
        notifyObservers();
    }

    /**
     * Update a student information in the database
     * @param student : the student object to update
     * @param oldId : the initial ID of the student
     */
    public void updateStudent(Student student, int oldId)
    {
        DBConnection.updateStudent(student, oldId);
        notifyObservers();
    }

    /**
     * It will add in a list the given parameter
     * @param observer the class that has implemented the Observer interface
     */
    public void addObservers(Observer observer){
        listObserver.add(observer);
    }

    /**
     * It will call all the classes that implemented the Observer interface from a list and call the method updateView
     */
    public void notifyObservers(){
        for(Observer observer : listObserver){
            observer.updateView(this);
        }
    }
}
