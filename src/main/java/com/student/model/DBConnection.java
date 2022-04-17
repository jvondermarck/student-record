package com.student.model;

import com.student.view.AddStudentGUI;
import com.student.view.ColorMsg;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
	private String dbURL = "jdbc:derby:\\database\\MTUDATABASE";
	public static Connection connection;
	
	public DBConnection() {

		try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            // connect method #1 - embedded driver
            connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                System.out.println("Connected to database #1");
                DatabaseMetaData dbm = connection.getMetaData();

                //Statement stmt = connection.createStatement();
                //String sql = "DROP TABLE STUDENT";
                //stmt.executeUpdate(sql);

                ResultSet tables = dbm.getTables(null, null, "STUDENT", null);
                if (!tables.next()) {
                    // Table does not exist
                    createStudentTable();
                }
            }
         
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
	// "(id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY," +
	public void createStudentTable() {
		try {
		  Statement stmt = connection.createStatement();
          String sql = "CREATE TABLE STUDENT (" +
                   "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                   "studentID INTEGER NOT NULL," +
                   "firstname VARCHAR(50) NOT NULL, " +
                   "lastname VARCHAR(50) NOT NULL, " +
                   "dateBirth DATE NOT NULL," +
                  "PRIMARY KEY (id, studentID))";

          stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
          ResultSet rs = stmt.getGeneratedKeys();
         System.out.println("Created table in given database...");   	  
      } catch (SQLException e) {
         e.printStackTrace();
      } 
	}

    public static void insertStudent(Student student) {
        try {
            Date date = new Date(student.getDateBirth().getTime());
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO STUDENT (studentID, firstname, lastname, dateBirth) VALUES ("
                    + student.getId() + ", '" +
                    student.getFirstname() + "', '" +
                    student.getLastname() + "', '" +
                    student.getDateBirth().toString() + "')" ;

            stmt.execute(sql);
            for(Observer observer : University.listObserver){
                if(observer instanceof AddStudentGUI)
                    observer.displayMessage("Success : Student added.", ColorMsg.SUCCESS.getColor());
            }
        } catch (SQLException e) {
            for(Observer observer : University.listObserver){
                if(observer instanceof AddStudentGUI)
                    observer.displayMessage("This ID number is already existing.", ColorMsg.ERROR.getColor());
            }
            e.printStackTrace();
        }
    }

    public static void deleteStudent(Student student) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "DELETE FROM STUDENT WHERE studentID = " + student.getId();
            stmt.executeUpdate(sql);
            System.out.println("Delete student in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(Student student) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT id FROM STUDENT WHERE studentID = " + student.getId();
            ResultSet resultSet = stmt.executeQuery(sql);
            int id = 0;
            while(resultSet.next()){
                id = resultSet.getInt("id"); //IDTable
            }

            System.out.println("id = " + id);

            stmt = connection.createStatement();
            sql = "UPDATE STUDENT SET studentID = " + student.getId() +
                    ", firstname = '" + student.getFirstname() +
                    "', lastname = '" + student.getLastname() +
                    "', dateBirth = '" + student.getDateBirth().toString() + "' " +
                    "WHERE id=" + id ;
            stmt.executeUpdate(sql);
            System.out.println("Update student in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Student> getStudent() {
        List<Student> studentList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM STUDENT";
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Student s1 = new Student(
                        res.getString("firstname"),
                        res.getString("lastname"),
                        res.getInt("studentID"),
                        res.getDate("dateBirth")
                );
                studentList.add(s1);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }
}
