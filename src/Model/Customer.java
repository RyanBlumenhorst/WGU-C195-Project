package Model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *This Customer class is used to collect customer information
 * @author Ryan Blumenhorst
 */
public class Customer {
    //Customer Variables
    public int Customer_ID;
    public String Customer_Name;
    public String Address;
    public String Postal_Code;
    public String Phone;
    public Date Create_Date;
    public String Created_By;
    public Timestamp Last_Update;
    public String Last_Updated_By;
    public int Division_ID;


    /**
     * Customer Constructor
     * @param Customer_ID Customer Identification Key
     * @param Customer_Name Customer First and Last Name
     * @param Address Customer Address
     * @param Postal_Code Customer Postal Code
     * @param Phone Customer Phone Number
     * @param Create_Date Date Customer was added to database
     * @param Created_By Who added the customer to the database
     * @param Last_Update when customer information was last updated
     * @param Last_Updated_By who updated the customer's information
     * @param Division_ID customer division identification
     * */
    public Customer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, Date Create_Date, String Created_By,
                    Timestamp Last_Update, String Last_Updated_By, int Division_ID) {
        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Division_ID = Division_ID;

    }

    //Accessors
    public int getCustomerId() {return Customer_ID; }
    public String getCustomerName() {return Customer_Name;}
    public int getDivision_ID() {return Division_ID;}
    public String getAddress() {return Address;}
    public String getCustomerPhone() {return Phone;}
    public Date getCreate_Date(){return Create_Date;}
    public String getCreated_By(){return Created_By;}
    public Timestamp getLast_Update(){return Last_Update;}
    public String getLast_Updated_By(){return Last_Updated_By;}
    public String getPostalCode() {return Postal_Code;}

    //Mutators
    public void setCustomerId(int Customer_ID) {this.Customer_ID = Customer_ID; }
    public void setCustomerName(String Customer_Name) {this.Customer_Name = Customer_Name;}
    public void setDivision_ID(int Division_ID) {this.Division_ID = Division_ID;}
    public void setAddress(String Address) {this.Address = Address;}
    public void setCustomerPhone(String Customer_Phone) {this.Phone = Customer_Phone;}
    public void setCreate_Date(Date Create_Date){this.Create_Date = Create_Date;}
    public void setCreated_By(String Created_By){this.Created_By =Created_By;}
    public void setLast_Update(Timestamp Last_Update){this.Last_Update = Last_Update;}
    public void setLast_Updated_By(String Last_Updated_By){this.Last_Updated_By = Last_Updated_By;}
    public void setPostalCode(String Postal_Code) {this.Postal_Code = Postal_Code;}
}
