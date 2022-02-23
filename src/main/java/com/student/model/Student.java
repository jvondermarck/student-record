package com.student.model;

import com.student.utilities.CSVParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

public class Student implements Serializable {

    @Serial
    private static final long serialVersionUID = 7392105208659553468L;

    // Student variables
    private final String firstName;
    private final String lastName;
    private final String dateBirth;
    private final String id;

    // Module variable
    private final transient List<Module> listModule; // transient = which specifies that the attribute it qualifies should not be included in a serialization process

    public Student(String id) {
        this.firstName = null;
        this.lastName = null;
        this.dateBirth = null;
        this.id = id;

        this.listModule = new ArrayList<>();
        retrieveModuleDatabase();
    }

    public Student(String firstname, String lastname, String id, String dateBirth) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.dateBirth = dateBirth;
        this.id = id;

        this.listModule = new ArrayList<>();
        retrieveModuleDatabase();
    }

    public void addStudentDatabase()
    {
        List<List<String>> rows = List.of(Arrays.asList(this.firstName, this.lastName, this.id, this.dateBirth));
        CSVParser.addDataDatabase(CSVParser.csvWriterStudent, rows);
        //WriteReader.addStudent(this);
        // TODO : db
    }

    public void addModuleDatabase(String name, int grade)
    {
        Module module = new Module(name, grade, id);
        this.listModule.add(module);
        List<List<String>> rows = List.of(Arrays.asList(Long.toString(CSVParser.getLines()), this.id, name, Integer.toString(grade)));
        CSVParser.addDataDatabase(CSVParser.csvWriterModule, rows);
        //WriteReader.addModule(module);
        // TODO : db
    }

    public void retrieveModuleDatabase()
    {
        this.listModule.clear();
        CSVParser.retrieveModuleDatabase(listModule, this.id);
        //WriteReader.deserializeModuleStudent(listModule, this.id);
    }

    public List<Module> getModule() { return this.listModule;}

    public String getFirstname() { return this.firstName; }

    public String getLastname() { return this.lastName; }

    public String getId() { return id; }

    public String getDateBirth() { return this.dateBirth; }

    public String getFullname() { return this.firstName + " " + this.lastName; }

    @Override
    public String toString() {
        return "Student{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", dateBirth='" + dateBirth + '\'' + ", id=" + id + '}';
    }
}
