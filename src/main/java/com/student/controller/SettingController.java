package com.student.controller;

import com.student.model.Observer;
import com.student.model.Student;
import com.student.model.University;

import java.util.List;

public class SettingController {

    private final University university;

    public SettingController(University university)
    {
        this.university = university;
    }

    public void addStudent(List<String> student)
    {
        this.university.addStudent(student);
    }

    public void deleteStudent(Student student)
    {
        this.university.deleteStudent(student);
    }

    public void saveStudentDatabase()
    {
        this.university.saveStudentDatabase();
    }

    public List<Student> getStudentList()
    {
        return this.university.getListStudent();
    }

    public void addModuleStudent(Student student, String name, int grade) { this.university.addModuleStudent(student, name, grade);};

    public List<Student> getAllStudentDatabase()
    {
        return this.university.getListContactDatabase();
    }

    public void addObserver(Observer observer) { this.university.addObservers(observer);}
}
