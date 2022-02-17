package com.student.model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    // transient = which specifies that the attribute it qualifies should not be included in a serialization process

    // Module variable
    private transient final List<Module> listModule;

    // Database variables
    private transient File csvFileStudent;
    private transient FileWriter csvWriterStudent;
    private transient File csvFileModule;
    private transient FileWriter csvWriterModule;

    public Student(String firstname, String lastname, int id, String dateBirth) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.dateBirth = dateBirth;
        this.id = id;

        this.listModule = new ArrayList<>();
        retrieveModuleDatabase();
    }

    // We set up the csv file, we instantiate the File and FileWriter variable to be able to append the database
    public void setupDatabase()
    {
        csvFileStudent = null;
        csvFileModule = null;
        try {
            //csvFileStudent = new File(Objects.requireNonNull(getClass().getResource("/database_student.csv")).toURI());
            csvFileStudent = new File("src/main/java/com/student/database_student.csv");
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
                csvWriterStudent.append("Firstname,Lastname,ID,DateBirth\n");
            if(csvFileModule.length() == 0)
                csvWriterModule.append("ID,IDStudent,Module,Grade\n");
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addDataDatabase(FileWriter fileWriter, List<List<String>> rowsData) {
        /*
        checkDatabaseEmpty();  // check if it is the first time we use the database (check if empty or not to put the header or not)
        WriteReader reader = null;
        try {
            reader = new WriteReader();
        } catch(IOException ignored)
        {

        }
        reader.addStudent(this);
        reader.deserialize();

         */

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
        this.listModule.add(new Module(name, grade));
        List<List<String>> rows = List.of(Arrays.asList(Long.toString(getLines()), Integer.toString(this.id), name, Integer.toString(grade)));
        setupDatabase(); // We instantiate the variable File and FileWrite to be able to write on the CSV file
        addDataDatabase(csvWriterModule, rows);
    }

    // get the number of lines of the database module to increment the ID which is unique for each row
    public long getLines() {
        long number = 0;
        try {
            number = Files.lines(Paths.get("src/main/java/com/student/database_module.csv")).count();
            if(number == 0) {
                number = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public void retrieveModuleDatabase()
    {
        listModule.clear();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/student/database_module.csv"))) {
                String line;
                br.readLine(); // we skip the header line
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if(Integer.parseInt(values[1]) == this.id) // If in the database the id of the Student is equal to the student we want
                    {
                        Module module = new Module(values[2], Integer.parseInt(values[3]));
                        listModule.add(module);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Module> getModule() { return this.listModule;}

    public String getFirstname() { return this.firstName; }

    public String getLastname() { return this.lastName; }

    public Integer getId() { return id; }

    public String getDateBirth() { return this.dateBirth; }

    public String getFullname() { return this.firstName + " " + this.lastName; }

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateBirth='" + dateBirth + '\'' +
                ", id=" + id +
                '}';
    }
}
