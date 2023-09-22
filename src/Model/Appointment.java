package Model;

import java.sql.Date;

/**
 * Class method to keep appointment information
 *
 * @author Ryan Blumenhorst
 */
public class Appointment {
    //Appointment Variables
    int appointmentId;
    int customerId;
    int userId;
    String type;
    String start;
    String end;
    String userName;
    String customerName;
    String description;
    String title;
    String location;
    Date createDate;
    String createdBy;


    /**
     * Appointment Constructor Method
     *
     * @param appointmentId Appointment Identification Key
     * @param customerId Customer Identification Key
     * @param userId User Identification Key
     * @param type Appointment Type
     * @param start Appointment Start Date and Time
     * @param end Appointment End Date and Time
     * @param userName Current User
     * @param customerName Customer Name
     * @param description Appointment Description
     * @param title Appointment Title
     * @param location Appointment Location
     * @param createDate Date the Appointment was Created
     * @param createdBy Who Created the Appointment
     * */
    public Appointment(int appointmentId, int customerId, int userId,
                       String type, String start, String end, String userName, String customerName,
                       String description, String title, String location, Date createDate, String createdBy) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.type = type;
        this.start = start;
        this.end = end;
        this.userName = userName;
        this.customerName = customerName;
        this.description = description;
        this.title = title;
        this.location = location;
        this.createDate = createDate;
        this.createdBy = createdBy;

    }
    //Accessors
    public int getAppointmentId() {return appointmentId;}
    public int getCustomerId() {return customerId;}
    public int getUserId() {return userId;}
    public String getType() {return type;}
    public String getStart() {return start;}
    public String getEnd() {return end;}
    public String getUserName() {return userName;}
    public String getCustomerName() {return customerName;}
    public String getDescription(){return description;}
    public String getTitle(){return title;}
    public String getLocation(){return location;}
    public Date getCreateDate(){return createDate;}
    public String getCreatedBy(){return createdBy;}


    //Mutators
    public void setAppointmentId(int appointmentId) {this.appointmentId = appointmentId;}
    public void setCustomerId(int customerId) {this.customerId = customerId;}
    public void setUserId(int userId) {this.userId = userId;}
    public void setType(String type) {this.type = type;}
    public void setStart(String start) {this.start = start;}
    public void setEnd(String end) {this.end = end;}
    public void setUserName(String userName) {this.userName = userName;}
    public void setCustomerName(String customerName) {this.customerName = customerName; }
    public void setDescription(String description) {this.description = description;}
    public void setTitle(String title){this.title = title;}
    public void setLocation(String location){this.location = location;}
    public void setCreateDate(Date createDate){this.createDate = createDate;}
    public void setCreatedBy(String createdBy){this.createdBy = createdBy;}

}
