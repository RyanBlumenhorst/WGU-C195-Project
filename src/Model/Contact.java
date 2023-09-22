package Model;


/**
 * The Contact Class is used to collect Contact Information
 * @author Ryan Blumenhorst
 * */
public class Contact {
    int Contact_ID;
    String Contact_Name;
    String Contact_Email;

    /**
     * Contact Constructor Method
     * @param Contact_ID Contact Identification Key
     * @param Contact_Name Contact First and Last Name
     * @param Contact_Email Contact Email Address
     * */
    public Contact(int Contact_ID, String Contact_Name, String Contact_Email){
        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Contact_Email = Contact_Email;
    }

    //Accessors
    public int getContactID(){return Contact_ID;}
    public String getContact_Name(){return Contact_Name;}
    public String getContact_Email(){return Contact_Email;}

    //Mutators
    public void setContactID(int Contact_ID){this.Contact_ID = Contact_ID;}
    public void setContact_Name(String Contact_Name){this.Contact_Name = Contact_Name;}
    public void setContact_Email(String Contact_Email){this.Contact_Email = Contact_Email;}
}
