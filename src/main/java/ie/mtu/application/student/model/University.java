package ie.mtu.application.student.model;

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

    public void addModuleStudent(Module module)
    {
        DBConnection.insertModule(module);
    }

    public void deleteModuleStudent(Module module)
    {
        DBConnection.deleteModule(module);
    }

    public void updateModuleStudent(Module module){
        DBConnection.updateModule(module);
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
