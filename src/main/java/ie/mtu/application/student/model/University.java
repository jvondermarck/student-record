package ie.mtu.application.student.model;

import java.util.ArrayList;
import java.util.List;

public class University {

    public static List<Observer> listObserver;

    public University()
    {
        listObserver = new ArrayList<>();
    }

    public void insertStudent(Student student)
    {
        DBConnection.insertStudent(student);
        notifyObservers();
    }

    public void deleteStudent(Student student)
    {
        DBConnection.deleteStudent(student);
        notifyObservers();
    }

    public void updateStudent(Student student, int oldId)
    {
        DBConnection.updateStudent(student, oldId);
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
