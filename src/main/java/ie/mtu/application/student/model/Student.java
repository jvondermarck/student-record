package ie.mtu.application.student.model;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;

/**
 * The class Student will contain some information such as his name, date of birth and an ID
 */
@Entity
@Table(name = "STUDENT")
public class Student {

    /**
     * His name Object information that contains his firstname, lastname and fullname
     */
    @Transient
    private NameInfo name;

    /**
     * His firstname (use for JPA)
     */
    @Column(name = "firstname")
    private String firstname;

    /**
     * His lastname (use for JPA)
     */
    @Column(name = "lastname")
    private String lastname;

    /**
     * The date of birth of the student
     */
    @Column(name = "dateBirth")
    private Date dateBirth;

    /**
     * ID of the student that must be unique to each Student
     */
    @Column(name = "studentID")
    private int studentID;

    /**
     * ID generated by JPA
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Set new information to the student
     * @param nameInfo : His name Object information that contains his firstname, lastname and fullname
     * @param studentID : ID of the student that must be unique to each Student
     * @param dateBirth : The date of birth of the student
     */
    public Student(NameInfo nameInfo, int studentID, Date dateBirth) {
        setStudent(nameInfo, studentID, dateBirth);
    }

    /**
     * Use for JPA
     */
    public Student() {
        setStudent(new NameInfo("", ""), 0, null);
    }

    /**
     * Set new information to the student
     * @param nameInfo : His name Object information that contains his firstname, lastname and fullname
     * @param studentID : ID of the student that must be unique to each Student
     * @param dateBirth : The date of birth of the student
     */
    public void setStudent(NameInfo nameInfo, int studentID, Date dateBirth){
        this.name = nameInfo;
        this.firstname = this.name.getFirstname();
        this.lastname = this.name.getLastname();
        this.dateBirth = dateBirth;
        this.studentID = studentID;
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
    public List<Module> getModule() { return DBConnection.getModule(this.studentID); }

    /**
     * @return a list of all the modules of the student where he got grades >= 70%
     */
    public List<Module> getModuleHonours() { return DBConnection.getHonourModule(this.studentID); }


    /**
     * @return his firstname
     */
    public String getFirstname() { return this.firstname; }

    /**
     * @return his lastname
     */
    public String getLastname() { return this.lastname; }

    /**
     * @return his studentID
     */
    public Long getId() { return this.id; }

    /**
     * @param id from JPA
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the student ID
     */
    public Integer getStudentID() { return this.studentID; }

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
        return this.name.getFirstname() + "," + this.name.getLastname() + "," + this.studentID + "," + this.dateBirth;
    }
}
