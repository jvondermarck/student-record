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
    private final int id;

    // Module variable
    private transient final List<Module> listModule; // transient = which specifies that the attribute it qualifies should not be included in a serialization process

    public Student(String firstname, String lastname, int id, String dateBirth) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.dateBirth = dateBirth;
        this.id = id;

        this.listModule = new ArrayList<>();
        retrieveModuleDatabase();
    }

    public void addStudentDatabase()
    {
        List<List<String>> rows = List.of(Arrays.asList(this.firstName, this.lastName, Integer.toString(id), this.dateBirth));
        CSVParser.addDataDatabase(CSVParser.csvWriterStudent, rows);
    }

    public void addModuleDatabase(String name, int grade)
    {
        this.listModule.add(new Module(name, grade));
        List<List<String>> rows = List.of(Arrays.asList(Long.toString(CSVParser.getLines()), Integer.toString(this.id), name, Integer.toString(grade)));
        CSVParser.addDataDatabase(CSVParser.csvWriterModule, rows);
    }

    public void retrieveModuleDatabase()
    {
        listModule.clear();
        CSVParser.retrieveModuleDatabase(listModule, this.id);
    }

    public List<Module> getModule() { return this.listModule;}

    public String getFirstname() { return this.firstName; }

    public String getLastname() { return this.lastName; }

    public Integer getId() { return id; }

    public String getDateBirth() { return this.dateBirth; }

    public String getFullname() { return this.firstName + " " + this.lastName; }

    @Override
    public String toString() {
        return "Student{" + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", dateBirth='" + dateBirth + '\'' + ", id=" + id + '}';
    }
}
