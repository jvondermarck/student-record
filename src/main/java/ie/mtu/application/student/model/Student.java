package ie.mtu.application.student.model;
import java.sql.Date;
import java.util.List;

/**
 * The class Student will contain some information such as his name, date of birth and an ID
 */
public class Student {

    /**
     * His name Object information that contains his firstname, lastname and fullname
     */
    private Name name;
    /**
     * The date of birth of the student
     */
    private Date dateBirth;
    /**
     * ID of the student that must be unique to each Student
     */
    private int id;

    /**
     * Set new information to the student
     * @param name : His name Object information that contains his firstname, lastname and fullname
     * @param id : ID of the student that must be unique to each Student
     * @param dateBirth : The date of birth of the student
     */
    public Student(Name name, int id, Date dateBirth) {
        setStudent(name, id, dateBirth);
    }

    /**
     * Set new information to the student
     * @param name : His name Object information that contains his firstname, lastname and fullname
     * @param id : ID of the student that must be unique to each Student
     * @param dateBirth : The date of birth of the student
     */
    public void setStudent(Name name, int id, Date dateBirth){
        this.name = name;
        this.dateBirth = dateBirth;
        this.id = id;
    }

    /**
     * Insert a module of the student in the database
     * @param module : the module of the student to insert
     */
    public void insertModule(Module module)
    {
        DBConnection.insertModule(module);
    }

    /**
     * Delete a module of the student in the database
     * @param module : the module of the student to delete
     */
    public void deleteModule(Module module)
    {
        DBConnection.deleteModule(module);
    }

    /**
     * @return a list of all the modules of the student
     */
    public List<Module> getModule() { return DBConnection.getModule(this.id); }

    /**
     * @return a list of all the modules of the student where he got grades >= 70%
     */
    public List<Module> getModuleHonours() { return DBConnection.getHonourModule(this.id); }


    /**
     * @return his firstname
     */
    public String getFirstname() { return this.name.getFirstname(); }

    /**
     * @return his lastname
     */
    public String getLastname() { return this.name.getLastname(); }

    /**
     * @return his id
     */
    public Integer getId() { return this.id; }

    /**
     * @return his date of birth
     */
    public Date getDateBirth() { return this.dateBirth; }

    /**
     * @return his fullname that contains his firstname and lastname
     */
    public String getFullname() { return this.name.getFullname(); }

    @Override
    public String toString() {
        return this.name.getFirstname() + "," + this.name.getLastname() + "," + this.id + "," + this.dateBirth;
    }
}
