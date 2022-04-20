package ie.mtu.application.student.model;

/**
 * Class that will have the information of a person like his firstname and lastname and his fullname
 */
public class Name {
    private String firstname;
    private String lastname;

    public Name()
    {
        this.firstname = "";
        this.lastname = "";
    }

    public Name(String firstname, String lastname)
    {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFullname() {
        String fullname = "";
        if(!this.firstname.equals("") && !this.lastname.equals("")){
            fullname = this.firstname + " " + this.lastname;
        }
        return fullname;
    }
}
