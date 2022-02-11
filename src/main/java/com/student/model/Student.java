package com.student.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Student {

    // Student variables
    private final String firstName;
    private final String lastName;
    private final String dateBirth;
    private final int id;

    // Module variable
    private Set<Module> moduleStudent;

    // Database variables
    private File csvFileStudent;
    private FileWriter csvWriterStudent;
    private File csvFileModule;
    private FileWriter csvWriterModule;

    public Student(String firstname, String lastname, int id, String dateBirth) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.dateBirth = dateBirth;
        this.id = id;
        this.moduleStudent = new HashSet<>();
    }

    // We set up the csv file, we instantiate the File and FileWriter variable to be able to append the database
    public void setupDatabase()
    {
        csvFileStudent = null;
        csvFileModule = null;
        try {
            //csvFileStudent = new File(Objects.requireNonNull(getClass().getResource("/databse_student.csv")).toURI());
            csvFileStudent = new File("src/main/java/com/student/databse_student.csv");
            csvWriterStudent = new FileWriter(csvFileStudent, true);

            csvFileModule = new File("src/main/java/com/student/database_module.csv");
            csvWriterModule = new FileWriter(csvFileModule, true);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    // If CSV file empty, we create the header
    public void checkDatabaseEmpty() {
        try {
            if(csvFileStudent.length() == 0)
            {
                csvWriterStudent.append("Firstname");
                csvWriterStudent.append(",");
                csvWriterStudent.append("Lastname");
                csvWriterStudent.append(",");
                csvWriterStudent.append("ID");
                csvWriterStudent.append(",");
                csvWriterStudent.append("DateBirth");
                csvWriterStudent.append("\n");
            }
            if(csvFileModule.length() == 0)
            {
                csvWriterModule.append("Module");
                csvWriterModule.append(",");
                csvWriterModule.append("Grade");
                csvWriterModule.append(",");
                csvWriterModule.append("ID");
                csvWriterModule.append("\n");
            }
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addDataDatabase(FileWriter fileWriter, List<List<String>> rowsData){
        try {
            checkDatabaseEmpty();  // check if it is the first time we use the database (check if empty or not to put the header or not)

            for (List<String> row : rowsData) {
                fileWriter.append(String.join(",", row));
                fileWriter.append("\n");
            }

            fileWriter.flush();
            fileWriter.close();
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addStudentDatabase()
    {
        List<List<String>> rows = List.of(Arrays.asList(this.firstName, this.lastName, Integer.toString(id), this.dateBirth));
        setupDatabase(); // We instantiate the variable File and FileWrite to be able to write on the CSV file
        addDataDatabase(csvWriterStudent, rows);
    }

    public void addModuleDatabase(String name, int grade)
    {
        this.moduleStudent.add(new Module(name, grade));
        List<List<String>> rows = List.of(Arrays.asList(name, Integer.toString(grade),Integer.toString(this.id)));
        setupDatabase(); // We instantiate the variable File and FileWrite to be able to write on the CSV file
        addDataDatabase(csvWriterModule, rows);
    }

    public Set<Module> getModule()
    {
        return this.moduleStudent;
    }

    public String getFirstname() {
        return this.firstName;
    }

    public String getLastname() { return this.lastName; }

    public Integer getId() { return id; }

    public String getDateBirth() { return this.dateBirth; }

    public String getFullname() { return this.firstName + " " + this.lastName; }
}
