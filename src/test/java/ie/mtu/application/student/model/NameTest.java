package ie.mtu.application.student.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NameTest {

    Name name;

    @BeforeEach
    void setUp(){
        name = new Name("Firstname", "Lastname");
    }

    @DisplayName("getModuleName()")
    @Test
    void nullName() {
        Name actual = new Name();
        assertEquals("", actual.getFirstname());
        assertEquals("", actual.getLastname());
        assertEquals("", actual.getFullname());
    }

    @DisplayName("getFirstname()")
    @Test
    void getFirstname() {
        assertEquals("Firstname", name.getFirstname());
    }

    @DisplayName("getLastname()")
    @Test
    void getLastname() {
        assertEquals("Lastname", name.getLastname());
    }

    @DisplayName("getFullname()")
    @Test
    void getFullname() {
        assertEquals("Firstname Lastname", name.getFullname());
    }

    @DisplayName("setFirstname()")
    @Test
    void setFirstname() {
        name.setFirstname("NewFirstname");
        assertEquals("NewFirstname", name.getFirstname());
    }

    @DisplayName("setLastname()")
    @Test
    void setLastname() {
        name.setLastname("NewLastname");
        assertEquals("NewLastname", name.getLastname());
    }

    @DisplayName("getFullnameSetter()")
    @Test
    void getFullnameSetter() {
        name.setFirstname("NewFirstname");
        name.setLastname("NewLastname");
        assertEquals("NewFirstname NewLastname", name.getFullname());
    }
}
