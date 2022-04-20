package ie.mtu.application.student.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that will take care of the database connection
 * This class will create, delete, update, insert the Student and Module table
 */
public class DBConnection {
    /**
     * Link to the database location from a relative path of the project
     */
	private final String dbURL = "jdbc:derby:\\database\\MTUDATABASE";
    /**
     * Connection variable that will connect to the relative path @dbURL
     */
	private static Connection connection;

    /**
     * This constructor will connect to the database and create the Student and Module table if they don't already exist
     */
	public DBConnection() {
		try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            // connect method #1 - embedded driver
            connection = DriverManager.getConnection(dbURL);
            if (connection != null) {
                System.out.println("Connected to database #1");
                DatabaseMetaData dbm = connection.getMetaData();

                //deleteTable("MODULE");
                //deleteTable("STUDENT");

                ResultSet tables = dbm.getTables(null, null, "STUDENT", null);
                if (!tables.next()) {
                    // Table does not exist
                    createStudentTable();
                }

                tables = dbm.getTables(null, null, "MODULE", null);
                if (!tables.next()) {
                    // Table does not exist
                    createModuleTable();
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * The method will delete a table
     * @param tableName : the name of the table we want to delete
     */
    public void deleteTable(String tableName)
    {
        try {
            Statement stmt = connection.createStatement();
            String sql = "DROP TABLE " + tableName;
            stmt.executeUpdate(sql);
            System.out.println("Deleted table " + tableName + " in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method will create the STUDENT table in the database
     */
	public void createStudentTable() {
		try {
		  Statement stmt = connection.createStatement();
          String sql = "CREATE TABLE STUDENT (" +
                   "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                   "studentID INTEGER NOT NULL," +
                   "firstname VARCHAR(50) NOT NULL, " +
                   "lastname VARCHAR(50) NOT NULL, " +
                   "dateBirth DATE NOT NULL," +
                   "CONSTRAINT pk_studentID PRIMARY KEY (studentID))";

          stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
          ResultSet rs = stmt.getGeneratedKeys();
         System.out.println("Created table Student in given database...");
      } catch (SQLException e) {
         e.printStackTrace();
      } 
	}

    /**
     * The method will create the MODULE table in the database
     */
    public void createModuleTable() {
        //because ON UPDATE CASCADE I won't declare the studentID as a foreign key, otherwise I'll get an error if I change the studentID
        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE MODULE (" +
                    "id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
                    "studentID INTEGER NOT NULL," +
                    "moduleName VARCHAR(50) NOT NULL, " +
                    "grade INTEGER NOT NULL, " +
                    //"CONSTRAINT fk_moduleID FOREIGN KEY (studentID) REFERENCES STUDENT(studentID)," +
                    "CONSTRAINT pk_moduleName PRIMARY KEY (moduleName))";

            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            System.out.println("Created table Module in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method will check if from the given parameter the Student in the table STUDENT is already existing
     * @param studentID : the student ID we want to check
     * @return boolean value : true if the student exists, false if he doesn't exist
     */
    public static boolean isStudentExist(int studentID){
        boolean isExist = false;
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT studentID FROM STUDENT WHERE studentID = " + studentID;
            ResultSet resultSet = stmt.executeQuery(sql);
            isExist = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExist;
    }

    /**
     * It checks if from the given parameters, in the MODULE table if module name from a specific student is already existing
     * @param moduleName : the name of the module
     * @param studentID : the student ID that we want to check the duplicated module name
     * @return boolean value : true if found a duplicated module name, false if no duplicated module name
     */
    public static boolean isDuplicatedModuleName(String moduleName, int studentID){
        boolean isDuplicated = false;
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT studentID FROM MODULE WHERE studentID = " + studentID + " AND moduleName = '" + moduleName + "'";
            ResultSet resultSet = stmt.executeQuery(sql);
            isDuplicated = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isDuplicated;
    }

    /**
     * The method will insert a Student object in the STUDENT table
     * @param student : a Student object
     */
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * It will add a Module object in the MODULE table in the database
     * @param module : the Module object we want to add in the database
     */
    public static void insertModule(Module module) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "INSERT INTO MODULE (studentID, moduleName, grade) VALUES (" +
                    module.getId() + ", '" +
                    module.getModuleName() + "', " +
                    module.getGradeModule() + ")" ;

            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * It will delete a student in the database and all his modules
     * @param student : the student object we want to delete
     */
    public static void deleteStudent(Student student) {
        try {
            Statement stmt = connection.createStatement();
            // we delete first all his modules in the MODULE table
            String sql = "DELETE FROM MODULE WHERE studentID = " + student.getId();
            stmt.executeUpdate(sql);
            // Then we delete the student from the STUDENT table
            stmt = connection.createStatement();
            sql = "DELETE FROM STUDENT WHERE studentID = " + student.getId();
            stmt.executeUpdate(sql);
            System.out.println("Delete student in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method will delete a specific module from the database from a specific student
     * @param module : the module Object that we want to delete
     */
    public static void deleteModule(Module module) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "DELETE FROM MODULE WHERE moduleName = '" + module.getModuleName() +
                         "' AND studentID = " + module.getId();
            stmt.executeUpdate(sql);
            System.out.println("Delete module in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method will update an existing Student in the STUDENT table
     * @param student : the student with his new information
     * @param oldId : his old ID integer (if we changed it, we will need his old id to update the student)
     */
    public static void updateStudent(Student student, int oldId) {
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT id FROM STUDENT WHERE studentID = " + oldId;
            ResultSet resultSet = stmt.executeQuery(sql);
            int id = 0;
            while(resultSet.next()){
                id = resultSet.getInt("id");
            }

            // We update the student information in the STUDENT table
            stmt = connection.createStatement();
            sql = "UPDATE STUDENT SET studentID = " + student.getId() +
                    ", firstname = '" + student.getFirstname() +
                    "', lastname = '" + student.getLastname() +
                    "', dateBirth = '" + student.getDateBirth().toString() + "' " +
                    "WHERE id=" + id ;
            stmt.executeUpdate(sql);

            // If we changed the ID, we will update the modules of the students to change the ID column from the MODULE table
            if(oldId != student.getId()){
                stmt = connection.createStatement();
                sql = "UPDATE MODULE SET studentID = " + student.getId() +
                        " WHERE studentID=" + oldId ;
                stmt.executeUpdate(sql);
            }
            System.out.println("Update student in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * The function will run a SELECT query to get all the students in the database
     * @return a List of type Student from all the students in the database
     */
    public static List<Student> getStudent() {
        List<Student> studentList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM STUDENT";
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Name name = new Name(res.getString("firstname"), res.getString("lastname"));
                Student s1 = new Student(name, res.getInt("studentID"), res.getDate("dateBirth"));
                studentList.add(s1);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    /**
     * The function will run a SELECT query to get all the modules in the database from a specific student
     * @param studentID : the student ID that we want to get all of his modules
     * @return a List of type Module from all the modules in the database from a student
     */
    public static List<Module> getModule(int studentID) {
        List<Module> moduleList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM MODULE WHERE studentID = " + studentID;
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Module m1 = new Module(
                        res.getString("moduleName"), res.getInt("grade"),
                        res.getInt("studentID"));
                moduleList.add(m1);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moduleList;
    }

    /**
     * The function will run a SELECT query to get all the modules in the database from a specific student where he got a grade >= 70%
     * @param studentID : the student ID that we want to get all of his modules >= 70
     * @return a List if Module
     */
    public static List<Module> getHonourModule(int studentID){
        List<Module> honourList = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM MODULE WHERE studentID = " + studentID + " AND grade >= 70 ";
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Module m1 = new Module(
                        res.getString("moduleName"),
                        res.getInt("grade"),
                        res.getInt("studentID")
                );
                honourList.add(m1);
            }
            res.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return honourList;
    }
}
