package Utilities;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.util.TimeZone;

import Helper.JDBC;
import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import View.LoginController;

/**
 * This class is used to make all Queries to the database
 * @author Ryan Blumenhorst
 */
public class QueryController {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;
    /**
     * Used to make a Query
     * @param queryString String that gets put through the Query
     * */
    public static void makeQuery(String queryString) {
        query = queryString;
        try {
            stmt = JDBC.connection.createStatement();
            if (query.toLowerCase().startsWith("select"))
                result = stmt.executeQuery(query);

            if (query.toLowerCase().startsWith("delete") ||
                    query.toLowerCase().startsWith("insert") ||
                    query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(query);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Returns results of a Query
     * @return ResultSet
     * */
    public static ResultSet getResult() {
        return result;
    }

    /**
     * Returns all customers from the Customers Table
     * @return ObservableList
     * */
    public static ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT * FROM Customers";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Customer_Phone = rs.getString("Phone");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                Date Create_Date = rs.getDate("Create_Date");
                String Created_By = rs.getString("Created_By");
                Timestamp Last_Update = rs.getTimestamp("Last_Update");
                String Last_Updated_By = rs.getString("Last_Updated_By");
                int Division_ID = rs.getInt("Division_ID");

                Customer c = new Customer(Customer_ID, Customer_Name, Address, Postal_Code, Customer_Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID);
                data.add(c);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }


    //Customer Queries

    /**
     * Return a list of all customers in the Customers Table
     * @return ObservableList
     * */
    public static ObservableList<String> getCustomerList() {
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT * FROM Customers";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                String Customer_Name = rs.getString("Customer_Name");
                String Customer_List = new String(Customer_Name);
                data.add(Customer_List);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }

    /**
     * Inserts a new customer into the Customers Table
     * @param Customer_ID Customer Identification Key
     * @param Customer_Name Customer First and Last Name
     * @param Customer_Phone Customer Phone
     * @param Postal_Code Customer Postal Code
     * @param Address Customer Address
     * @param Created_By User that created the Customer
     * @param Last_Update_By Who last updated the Customer
     * @param Country_ID Country Identification Key
     * @param State State Key
     * @return String
     * */
    public static String insertNewCustomer(int Customer_ID, String Customer_Name, String Customer_Phone,
                                           String Postal_Code, String Address, String Created_By, String Last_Update_By,
                                           int Country_ID, String State) {
        try {
            PreparedStatement myStatement;
            String pass_String = "INSERT INTO Customers(Customer_Name, Address, Phone, Postal_Code, Create_Date, "
                    + "Created_By, Last_Update, Last_Updated_By, Customer_ID, Division_ID) VALUES (?, "
                    + "?, ?, ?, now(), ?, now(), ?, ?, (SELECT Division_ID FROM First_Level_Divisions" +
                    " WHERE Country_ID = ? AND Division = ?))";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setString(1, Customer_Name);
            myStatement.setString(2, Address);
            myStatement.setString(3, Customer_Phone);
            myStatement.setString(4, Postal_Code);
            myStatement.setString(5, Created_By);
            myStatement.setString(6, Last_Update_By);
            myStatement.setString(7, Integer.toString(Customer_ID));
            myStatement.setString(8, Integer.toString(Country_ID));
            myStatement.setString(9, State);
            myStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }


    /**
     * Updates a current Customer
     * @param Customer_ID Customer Identification Key
     * @param Customer_Name Customer First and Last name
     * @param Customer_Phone Customer Phone Number
     * @param Postal_Code Customer Postal Code
     * @param Address Customer Address
     * @param Last_Update_By Who last Updated Customer
     * @return String
     * */
    public static String updateCustomer(int Customer_ID, String Customer_Name, String Customer_Phone,
                                        String Postal_Code, String Address, String Last_Update_By) {
        try {
            PreparedStatement myStatement;
            String pass_String = "UPDATE Customers SET Customer_Name = ?, Last_Update = now(), "
                    + "Last_Updated_By = ?, Address = ?, Postal_Code = ?, "
                    + "Phone = ? WHERE Customer_Id = ?";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setString(1, Customer_Name);
            myStatement.setString(2, Last_Update_By);
            myStatement.setString(3, Address);
            myStatement.setString(4, Postal_Code);
            myStatement.setString(5, Customer_Phone);
            myStatement.setInt(6, Customer_ID);
            myStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Deletes a customer from the Customers Table
     * @param Customer_ID Customer Identification Key
     * @return String
     * */
    public static String deleteCustomer(int Customer_ID) {
        try {
            PreparedStatement myStatement;
            String pass_String = "DELETE FROM Customers WHERE Customer_ID = ?";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setInt(1, Customer_ID);
            myStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Returns a list of Customer Names
     * @return ObservableList
     * */
    public static ObservableList<Customer> getCustomerNames() {
        ObservableList<Customer> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT Customer_ID, Customer_Name FROM Customers";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                Customer c = new Customer(Customer_ID, Customer_Name, null, null, null, null, null, null, null,
                        0);
                data.add(c);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }

    /**
     * Returns customer appointments for selected customer
     * @param Customer_Name Name of customer
     * @return ObservableList
     * */
    public static ObservableList<Appointment> getCustomerAppointments(String Customer_Name) {
        ObservableList<Appointment> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT a.Appointment_ID, a.Title, a.Type, a.Description, a.Start, a.End, a.Customer_ID FROM Appointments a" +
                    " WHERE a.Customer_ID = (SELECT Customer_ID FROM Customers WHERE Customer_Name = ?)";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setString(1, Customer_Name);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Appointment_Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Appointment_Type = rs.getString("Type");
                String Start = rs.getString("Start".toString());
                String End = rs.getString("End".toString());
                int Customer_ID = rs.getInt("Customer_ID");
                Appointment a = new Appointment(Appointment_ID, Customer_ID, 0, Appointment_Type, Start, End, null, null,
                        Description, Appointment_Title, null, null, null);
                data.add(a);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }

    //Appointment Queries

    /**
     * Returns all appointments from Appointments table
     * @return ObservableList
     * */
    public static ObservableList<Appointment> getAllAppointments() {
        ObservableList<Appointment> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT a.Appointment_ID, a.Customer_ID, a.User_ID, a.Type, "
                    + "a.Start, a.End, c.Customer_Name, u.User_Name, a.Description, a.Title, a.Create_Date, a.Created_By, a.Location "
                    + "FROM Appointments a, Customers c, Users u "
                    + "WHERE a.Customer_ID = c.Customer_ID and a.User_ID = u.User_ID";

            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                ZoneId Local_Zone_ID = ZoneId.of(TimeZone.getDefault().getID());
                DateTimeFormatter Format_Local = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(Local_Zone_ID);
                LocalDate Start_Date = LocalDate.parse(rs.getString("Start").substring(0, 10));
                LocalTime Start_Time = LocalTime.parse(rs.getString("Start").substring(11, 19));
                LocalDate End_Date = LocalDate.parse(rs.getString("End").substring(0, 10));
                LocalTime End_Time = LocalTime.parse(rs.getString("End").substring(11, 19));
                ZonedDateTime UTC_Start = ZonedDateTime.of(Start_Date, Start_Time, ZoneId.of("UTC"));
                ZonedDateTime UTC_End = ZonedDateTime.of(End_Date, End_Time, ZoneId.of("UTC"));
                String Local_Start = Format_Local.format(UTC_Start);
                String Local_End = Format_Local.format(UTC_End);
                int Appointment_ID = rs.getInt("Appointment_ID");
                int Customer_ID = rs.getInt("Customer_ID");
                int User_ID = rs.getInt("User_ID");
                String Appointment_Type = rs.getString("Type");
                String Start = Local_Start;
                String End = Local_End;
                String User_Name = rs.getString("User_Name");
                String Customer_Name = rs.getString("Customer_Name");
                String Description = rs.getString("Description");
                String Title = rs.getString("Title");
                String Location = rs.getString("Location");
                Date Create_Date = rs.getDate("Create_Date");
                String Created_By = rs.getString("Created_By");
                Appointment a = new Appointment(Appointment_ID, Customer_ID, User_ID, Appointment_Type, Start, End, User_Name, Customer_Name, Description, Title, Location, Create_Date, Created_By);
                data.add(a);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }

    /**
     * Inserts a new appointment in the Appointments table
     * @param Appointment_ID Appointment Identification Key
     * @param Customer_Name Customer First and last name
     * @param Appointment_Title Appointment Title
     * @param Description Appointment Description
     * @param Appointment_With Who the Appointment is with
     * @param Location Location of the appointment
     * @param Appointment_Type Type of appointment
     * @param Start Appointment start date and time
     * @param End Appointment end date and time
     * @param Current_User Current logged in user
     * @param Contact_Name Contact first and last name
     * @return String
     * */
    public static String insertNewAppointment(int Appointment_ID, String Customer_Name, String Appointment_Title, String Description,
                                              String Appointment_With, String Location, String Appointment_Type, String Start, String End, String Current_User, String Contact_Name) {
        try {
            PreparedStatement myStatement;
            String pass_String = "INSERT INTO Appointments(Customer_ID, User_ID, Title, Description, "
                    + "Location, Type, Start, End, Create_Date, Created_By, "
                    + "Last_Update, Last_Updated_By, Contact_ID) VALUES ((select Customer_ID from Customers "
                    + "WHERE Customer_Name = ?), (SELECT User_Id FROM Users WHERE User_Name = ?), "
                    + "?, ?, ?, ?, ?, ?, now(), ?, now(), ?, (SELECT Contact_ID FROM Contacts WHERE Contact_Name = ?))";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setString(1, Customer_Name);
            myStatement.setString(2, Appointment_With);
            myStatement.setString(3, Appointment_Title);
            myStatement.setString(4, Description);
            myStatement.setString(5, Location);
            myStatement.setString(6, Appointment_Type);
            myStatement.setString(7, Start);
            myStatement.setString(8, End);
            myStatement.setString(9, Current_User);
            myStatement.setString(10, Current_User);
            myStatement.setString(11, Contact_Name);
            myStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Method to update an appointment in the Appointments table
     * @param Appointment_ID Appointment Identification Key
     * @param Customer_Name Customer First and Last Name
     * @param Appointment_With Who the Appointment is with
     * @param Appointment_Type Type of appointment
     * @param Start Appointment Start Date and Time
     * @param End Appointment End Date and Time
     * @param Current_User Current Logged in User
     * @return String
     * */
    public static String updateAppointment(int Appointment_ID, String Customer_Name,
                                           String Appointment_With, String Appointment_Type, String Start, String End, String Current_User) {
        try {
            PreparedStatement myStatement;
            String pass_String = "UPDATE Appointments SET Customer_ID = (select Customer_ID from Customers "
                    + "WHERE Customer_Name = ?), User_ID = (SELECT User_ID FROM Users WHERE User_Name = ?), "
                    + "Type = ?, Start = ?, End = ?, Last_Update = now(), Last_Updated_By = ? "
                    + "WHERE Appointment_ID = ?";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setString(1, Customer_Name);
            myStatement.setString(2, Appointment_With);
            myStatement.setString(3, Appointment_Type);
            myStatement.setString(4, Start);
            myStatement.setString(5, End);
            myStatement.setString(6, Current_User);
            myStatement.setInt(7, Appointment_ID);
            myStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Delete an appointment from the Appointments Table
     * @param Appointment_ID Appointment Identification Key
     * @return String
     * */
    public static String deleteAppointment(int Appointment_ID) {
        try {
            PreparedStatement myStatement;
            String pass_String = "DELETE FROM Appointments WHERE Appointment_ID = ?";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setString(1, Integer.toString(Appointment_ID));
            myStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Delete an appointment from Appointments Table by customer id
     * @param Customer_ID Customer Identification Key
     * @return String
     * */
    public static String deleteAppointmentByCustomerID(int Customer_ID) {
        try {
            PreparedStatement myStatement;
            String pass_String = "DELETE FROM Appointments WHERE Customer_ID = ?";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setString(1, Integer.toString(Customer_ID));
            myStatement.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Check if user has an appointment soon.
     * */
    public static void checkAppointmentStart() {
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT a.Appointment_ID, a.Customer_ID, a.User_ID, a.Type, "
                    + "a.Start, a.End, c.Customer_Name, u.User_Name "
                    + "FROM Appointments a, Customers c, Users u "
                    + "WHERE a.Customer_ID = c.Customer_ID and a.User_ID = u.User_ID";
            String Current_User = LoginController.currentUser;
            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                ZoneId Local_Zone_ID = ZoneId.of(TimeZone.getDefault().getID());
                DateTimeFormatter Format_Local_Date = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(Local_Zone_ID);
                DateTimeFormatter Format_Local_Time = DateTimeFormatter.ofPattern("HH:mm:ss").withZone(Local_Zone_ID);
                LocalDate Start_Date = LocalDate.parse(rs.getString("Start").substring(0, 10));
                LocalTime Start_Time = LocalTime.parse(rs.getString("Start").substring(11, 19));
                ZonedDateTime UTC_Start = ZonedDateTime.of(Start_Date, Start_Time, ZoneId.of("UTC"));
                String Local_Start_Date_String = Format_Local_Date.format(UTC_Start);
                LocalDate Local_Start_Date = LocalDate.parse(Local_Start_Date_String);
                String Local_Start_Time_String = Format_Local_Time.format(UTC_Start);
                LocalTime Local_Start_Time = LocalTime.parse(Local_Start_Time_String);
                LocalDate Current_Date = LocalDate.now();
                LocalTime Current_Time = LocalTime.now();
                Long Time_Difference = ChronoUnit.MINUTES.between(Current_Time, Local_Start_Time);
                long Elapsed_Time = Time_Difference + 1;
                if (Current_User == null ? rs.getString("u.User_Name") == null : Current_User.equals(rs.getString("u.User_Name"))) {
                    if (Current_Date.equals(Local_Start_Date)) {
                        if (Elapsed_Time >= 0 && Elapsed_Time <= 15) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Appointment Reminder");
                            alert.setContentText("Hello " + rs.getString("u.User_Name") + "! You have an appointment in approximately " + Elapsed_Time + " minute(s) with " + rs.getString("c.Customer_Name"));
                            alert.showAndWait();
                            return;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Reads a list of appointment times to choose from.
     * @return ObservableList
     * */
    public static ObservableList<String> getAppointmentTimes() {
        String[] Appointment_Times = new String[19];
        Appointment_Times[0] = "08:00:00";
        Appointment_Times[1] = "08:30:00";
        Appointment_Times[2] = "09:00:00";
        Appointment_Times[3] = "09:30:00";
        Appointment_Times[4] = "10:00:00";
        Appointment_Times[5] = "10:30:00";
        Appointment_Times[6] = "11:00:00";
        Appointment_Times[7] = "11:30:00";
        Appointment_Times[8] = "12:00:00";
        Appointment_Times[9] = "12:30:00";
        Appointment_Times[10] = "13:00:00";
        Appointment_Times[11] = "13:30:00";
        Appointment_Times[12] = "14:00:00";
        Appointment_Times[13] = "14:30:00";
        Appointment_Times[14] = "15:00:00";
        Appointment_Times[15] = "15:30:00";
        Appointment_Times[16] = "16:00:00";
        Appointment_Times[17] = "16:30:00";
        Appointment_Times[18] = "17:00:00";
        ObservableList<String> data = FXCollections.observableArrayList(Appointment_Times);
        return data;
    }

    /**
     * Returns appointment totals for each month
     * @return ObservableList
     * */
    public static ObservableList<String> getMonthTotals() {
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT * FROM Appointments";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                String fullDate = rs.getString("Start");
                String Month = fullDate.substring(5, 7);
                data.add(Month);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }

    /**
     * Returns a list of contact names
     * @return ObservableList
     * */
    public static ObservableList<Contact> getContactNames() {
        ObservableList<Contact> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT Contact_ID, Contact_Name FROM Contacts";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                String Contact_Name = rs.getString("Contact_Name");
                int Contact_ID = rs.getInt("Contact_ID");
                Contact c = new Contact(Contact_ID, Contact_Name, null);
                data.add(c);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }

    /**
     * Returns appointments for a selected contact
     * @param Contact_Name Name of contact
     * @return ObservableList
     * */
    public static ObservableList<Appointment> getContactSchedule(String Contact_Name) {
        ObservableList<Appointment> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT a.Appointment_ID, a.Title, a.Type, a.Description, a.Start, a.End, a.Customer_ID FROM Appointments a" +
                    " WHERE a.Contact_ID = (SELECT Contact_ID FROM Contacts WHERE Contact_Name = ?)";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            myStatement.setString(1, Contact_Name);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                int Appointment_ID = rs.getInt("Appointment_ID");
                String Appointment_Title = rs.getString("Title");
                String Description = rs.getString("Description");
                String Appointment_Type = rs.getString("Type");
                String Start = rs.getString("Start".toString());
                String End = rs.getString("End".toString());
                int Customer_ID = rs.getInt("Customer_ID");
                Appointment a = new Appointment(Appointment_ID, Customer_ID, 0, Appointment_Type, Start, End, null, null,
                        Description, Appointment_Title, null, null, null);
                data.add(a);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }

    /**
     * Returns Appointment Types and Count of those Types
     * @return ObservableList
     * */
    public static ObservableList<TypeSort> getAppointmentTypes() {
        ObservableList<TypeSort> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT DISTINCT Type FROM Appointments";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()){
                String Type = rs.getString("Type");
                pass_String = "SELECT COUNT(Type) As typeTotal FROM Appointments WHERE Type = ?";
                myStatement = JDBC.connection.prepareStatement(pass_String);
                myStatement.setString(1, Type);
                ResultSet rs2 = myStatement.executeQuery();
                while (rs2.next()){
                    int typeTotal = rs2.getInt("typeTotal");
                    TypeSort t = new TypeSort(Type, typeTotal);
                    data.add(t);
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }

    //User Queries

    /**
     * Return a list of all Users
     * @return ObservableList
     * */
    public static ObservableList<String> getUserList() {
        ObservableList<String> data = FXCollections.observableArrayList();
        try {
            PreparedStatement myStatement;
            String pass_String = "SELECT * FROM Users";
            myStatement = JDBC.connection.prepareStatement(pass_String);
            ResultSet rs = myStatement.executeQuery();
            while (rs.next()) {
                String User_Name = rs.getString("User_Name");
                String User_List = new String(User_Name);
                data.add(User_List);
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return data;
    }
}
