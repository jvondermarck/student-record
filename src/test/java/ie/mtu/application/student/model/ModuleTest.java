package ie.mtu.application.student.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ModuleTest {

    Module module;
    Student student;

    @BeforeEach
    void setUp(){
        Name name = new Name("Firstname", "Lastname");
        student = new Student(name, 5, Date.valueOf(LocalDate.now()));
        module = new Module("ModuleName", 78, student.getId());
    }

    @DisplayName("getModuleName()")
    @Test
    void getModuleName() {
        String expected = "ModuleName";
        assertEquals(expected, module.getModuleName());
    }

    @DisplayName("getGradeModule()")
    @Test
    void getGradeModule() {
        int expected = 78;
        assertEquals(expected, module.getGradeModule());
    }

    @DisplayName("getId()")
    @Test
    void getId() {
        int expected = 5;
        assertEquals(expected, module.getId());
        assertEquals(expected, student.getId());
    }

    @DisplayName("testToString()")
    @Test
    void testToString() {
        String expected = "5,ModuleName,78";
        assertEquals(expected, module.toString());
    }
}