package ie.mtu.application.student.model;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * Class that will have the information of a person like his firstname and lastname and his fullname
 */
public class NameInfo {

    private String firstname;
    private String lastname;

    public NameInfo()
    {
        this.firstname = "";
        this.lastname = "";
    }

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
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Change the lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Get the fullname with the firstname + a space + the lastname
     */
    public String getFullname() {
        String fullname = "";
        if(!this.firstname.equals("") && !this.lastname.equals("")){
            fullname = this.firstname + " " + this.lastname;
        }
        return fullname;
    }


}
