package ie.mtu.application.student.model;

/**
 * Class that will have the information of a person like his firstname and lastname and his fullname
 */
public class NameInfo {

    private String firstname;
    private String lastname;

    /**
     * Set up to null the name of the student
     */
    public NameInfo()
    {
        this.firstname = "";
        this.lastname = "";
    }

    /**
     * Set up the full name of a student
     * @param firstname string
     * @param lastname string
     */
    public NameInfo(String firstname, String lastname)
    {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Change the firstname
     * @param firstname his name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Change the lastname
     * @param lastname his lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get the fullname with the firstname + a space + the lastname
     * @return the full name string
     */
    public String getFullname() {
        String fullname = "";
        if(!this.firstname.equals("") && !this.lastname.equals("")){
            fullname = this.firstname + " " + this.lastname;
        }
        return fullname;
    }
}
