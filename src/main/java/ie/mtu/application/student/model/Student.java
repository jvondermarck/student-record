package ie.mtu.application.student.model;

import java.io.*;
import java.sql.Date;
import java.util.List;
import java.io.Serializable;

import static java.time.Instant.now;

public class Student implements Serializable {

    @Serial
    private static final long serialVersionUID = 7392105208659553468L;

    // Student variables
    private String firstName;
    private String lastName;
    private Date dateBirth;
    private Integer id;

    // DATABASE
    public Student(String firstname, String lastname, int id, java.sql.Date dateBirth) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.dateBirth = dateBirth;
        this.id = id;
    }

    public void setStudent(String firstname, String lastname, int id, java.sql.Date dateBirth){
        this.firstName = firstname;
        this.lastName = lastname;
        this.dateBirth = dateBirth;
        this.id = id;
    }

    public void addModule(String name, int grade)
    {
        //return new Module(name, grade, id);
    }

    public void retrieveModuleDatabase()
    {
        //if(this.listModule == null) {
        //    this.listModule = new ArrayList<>();
        //} else {
        //    this.listModule.clear();
        //}
        //ObjectParser.deserializeModuleStudent(listModule, this.id);
    }

    public List<Module> getModule() { return DBConnection.getModule(this.id); }

    public List<Module> getModuleHonours() { return DBConnection.getHonourModule(this.id); }

    public String getFirstname() { return this.firstName; }

    public String getLastname() { return this.lastName; }

    public Integer getId() { return this.id; }//id;

    public java.sql.Date getDateBirth() { return this.dateBirth; }

    public String getFullname() { return this.firstName + " " + this.lastName; }

    @Override
    public String toString() {
        return this.getFirstname() + "," + this.lastName + "," + this.id + "," + this.dateBirth;
    }
}
