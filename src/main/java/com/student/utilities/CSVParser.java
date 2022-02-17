package com.student.utilities;

import com.student.model.Module;
import com.student.model.Student;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class CSVParser {

    // Database variables
    private static File csvFileStudent;
    public static FileWriter csvWriterStudent;
    private static File csvFileModule;
    public static FileWriter csvWriterModule;

    private static final String studentDBPath = "src/main/java/com/student/database_student.csv";
    private static final String moduleDBPath = "src/main/java/com/student/database_module.csv";
    //private static final String studentDBPath = "src/main/resources/com/student/database_student.csv";
    //private static final String moduleDBPath = "src/main/resources/com/student/database_module.csv";

    // We set up the csv file, we instantiate the File and FileWriter variable to be able to append the database
    public static void setupDatabase()
    {
        try {
            //csvFileStudent = new File(Objects.requireNonNull(loader.getResource("/ressources/database_student")).getFile());
           csvFileStudent = new File(studentDBPath);
           csvWriterStudent = new FileWriter(csvFileStudent, true);

            csvFileModule = new File(moduleDBPath);
            csvWriterModule = new FileWriter(csvFileModule, true);
        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    // If CSV file empty, we create the header
    private static void checkDatabaseEmpty() {
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

    public static void addDataDatabase(FileWriter fileWriter, List<List<String>> rowsData) {
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

    // get the number of lines of the database module to increment the ID which is unique for each row
    public static long getLines() {
        long number = 0;
        try {
            number = Files.lines(Paths.get(moduleDBPath)).count();
            if(number == 0) {
                number = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return number;
    }

    public static void retrieveModuleDatabase(List<Module> listModule, Integer id)
    {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(moduleDBPath))) {
                String line;
                br.readLine(); // we skip the header line
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if(Integer.parseInt(values[1]) == id) // If in the database the id of the Student is equal to the student we want
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

    public static void retrieveContactDatabase(List<Student> listStudentDatabase)
    {
        //InputStream inputStreamm = ClassLoader.getSystemClassLoader().getResourceAsStream("src/main/java/covid/version1/database_student.csv");
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(studentDBPath))) {
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
}

        /*
        public void addDataDatabase(FileWriter fileWriter, List<List<String>> rowsData) {
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