package ie.mtu.application.student.model;
import java.sql.Date;
import java.util.List;

/**
 * The class Student will contain some information such as his name, date of birth and an ID
 */
public class Student {

    private String firstName;
    private String lastName;
    private Date dateBirth;
    private int id;

    public Student(String firstname, String lastname, int id, Date dateBirth) {
        setStudent(firstname, lastname, id, dateBirth);
    }

    public void setStudent(String firstname, String lastname, int id, Date dateBirth){
        this.firstName = firstname;
        this.lastName = lastname;
        this.dateBirth = dateBirth;
        this.id = id;
    }

    public void insertModule(Module module)
    {
        DBConnection.insertModule(module);
    }

    public void deleteModule(Module module)
    {
        DBConnection.deleteModule(module);
    }

    public List<Module> getModule() { return DBConnection.getModule(this.id); }

    public List<Module> getModuleHonours() { return DBConnection.getHonourModule(this.id); }

    public String getFirstname() { return this.firstName; }

    public String getLastname() { return this.lastName; }

    public Integer getId() { return this.id; }

    public Date getDateBirth() { return this.dateBirth; }

    public String getFullname() { return this.firstName + " " + this.lastName; }

    @Override
    public String toString() {
        return this.getFirstname() + "," + this.lastName + "," + this.id + "," + this.dateBirth;
    }
}
