package ie.mtu.application.student.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NameInfoTest {

    NameInfo nameInfo;

    @BeforeEach
    void setUp(){
        nameInfo = new NameInfo("Firstname", "Lastname");
    }

    @DisplayName("getModuleName()")
    @Test
    void nullName() {
        NameInfo actual = new NameInfo();
        assertEquals("", actual.getFirstname());
        assertEquals("", actual.getLastname());
        assertEquals("", actual.getFullname());
    }

    @DisplayName("getFirstname()")
    @Test
    void getFirstname() {
        assertEquals("Firstname", nameInfo.getFirstname());
    }

    @DisplayName("getLastname()")
    @Test
    void getLastname() {
        assertEquals("Lastname", nameInfo.getLastname());
    }

    @DisplayName("getFullname()")
    @Test
    void getFullname() {
        assertEquals("Firstname Lastname", nameInfo.getFullname());
    }

    @DisplayName("setFirstname()")
    @Test
    void setFirstname() {
        nameInfo.setFirstname("NewFirstname");
        assertEquals("NewFirstname", nameInfo.getFirstname());
    }

    @DisplayName("setLastname()")
    @Test
    void setLastname() {
        nameInfo.setLastname("NewLastname");
        assertEquals("NewLastname", nameInfo.getLastname());
    }

    @DisplayName("getFullnameSetter()")
    @Test
    void getFullnameSetter() {
        nameInfo.setFirstname("NewFirstname");
        nameInfo.setLastname("NewLastname");
        assertEquals("NewFirstname NewLastname", nameInfo.getFullname());
    }
}
