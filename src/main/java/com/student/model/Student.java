package com.student.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Student {

    // Student variables
    private final String firstName;
    private final String lastName;
    private final String dateBirth;
    private final int id;

    // Module variable
    private final List<Module> listModule;

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
                csvWriterModule.append("ID");
                csvWriterModule.append(",");
                csvWriterModule.append("Module");
                csvWriterModule.append(",");
                csvWriterModule.append("Grade");
                csvWriterModule.append("\n");
                // TODO : create an unique ID for each module to identify them better
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
        this.listModule.add(new Module(name, grade));
        List<List<String>> rows = List.of(Arrays.asList(Integer.toString(this.id), name, Integer.toString(grade)));
        setupDatabase(); // We instantiate the variable File and FileWrite to be able to write on the CSV file
        addDataDatabase(csvWriterModule, rows);
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
                    if(Integer.parseInt(values[0]) == this.id) // If in the database the id of the Student is equal to the student we want
                    {
                        Module module = new Module(values[1], Integer.parseInt(values[2]));
                        listModule.add(module);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteModule(Module module){
        this.listModule.remove(module);
        // TODO : remove from csv module
    }

    public List<Module> getModule()
    {
        return this.listModule;
    }

    public String getFirstname() {
        return this.firstName;
    }

    public String getLastname() { return this.lastName; }

    public Integer getId() { return id; }

    public String getDateBirth() { return this.dateBirth; }

    public String getFullname() { return this.firstName + " " + this.lastName; }
}
