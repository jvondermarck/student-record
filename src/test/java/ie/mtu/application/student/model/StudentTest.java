package ie.mtu.application.student.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    Student testStudent;
    Date today;

    @BeforeEach
    void setUp(){
        today = Date.valueOf(LocalDate.now());
        testStudent = new Student("Firstname", "Lastname", 5, today);
    }

    @DisplayName("createStudent()")
    @Test
    void createStudent() {
        String date = LocalDate.now().toString();
        Student student = new Student("Julien", "Test", 79854, Date.valueOf(LocalDate.now()));
        String expected = "Julien,Test,79854," + date;
        assertEquals(expected, student.toString());
    }

    @DisplayName("getFirstname()")
    @Test
    void getFirstname() {
        String expected = "Firstname";
        assertEquals(expected, testStudent.getFirstname());
    }

    @DisplayName("getLastname()")
    @Test
    void getLastname() {
        String expected = "Lastname";
        assertEquals(expected, testStudent.getLastname());
    }

    @DisplayName("getID()")
    @Test
    void getID() {
        int expected = 5;
        assertEquals(expected, testStudent.getId());
    }

    @DisplayName("getDate()")
    @Test
    void getDate() {
        Date expected = Date.valueOf(LocalDate.now());
        assertEquals(expected, testStudent.getDateBirth());
    }

    @DisplayName("getFullname()")
    @Test
    void getFullname() {
        String expected = "Firstname Lastname";
        assertEquals(expected, testStudent.getFullname());
    }



}