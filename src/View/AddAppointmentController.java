package View;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
import Model.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Utilities.QueryController;


/**
 * FXML Controller class for Add Appointment Screen
 *
 * @author Ryan Blumenhorst
 */
public class AddAppointmentController implements Initializable {

    //Setting Stage and Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private ComboBox<String> contactName_combo;
    @FXML
    private ComboBox<String> customerName_combo;
    @FXML
    private ComboBox<String> appointmentWith_combo;
    @FXML
    private TextField appointmentType_txt;
    @FXML
    private TextField appointmentLocation_txt;
    @FXML
    private TextField appointmentTitle_txt;
    @FXML
    private TextArea appointmentDescription_txtb;
    @FXML
    private DatePicker startDate_dp;
    @FXML
    private ComboBox<String> start_combo;
    @FXML
    private DatePicker endDate_dp;
    @FXML
    private ComboBox<String> end_combo;
    @FXML
    private Button save_btn;
    @FXML
    private Button cancel_btn;
    @FXML
    private TextField appointmentID_txt;

    /**
     * Button method Returns User to Manage Appointments Screen
     * */
    @FXML
    void cancel(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/ManageAppointments.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**
     * Determines if appointments overlap
     * @return boolean
     * */
    private boolean getOverlap() {
        ObservableList<Appointment> appointment_List = QueryController.getAllAppointments();
        //Expression to get appointment information from appointment list
        for(Appointment a : appointment_List) {
            DateTimeFormatter DT_Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime Appointment_List_Start_DT = LocalDateTime.parse(a.getStart(), DT_Formatter);
            LocalDateTime Appointment_List_End_DT = LocalDateTime.parse(a.getEnd(), DT_Formatter);
            LocalDate Selected_Start_Date = startDate_dp.getValue();
            LocalDate Selected_End_Date = endDate_dp.getValue();
            LocalTime Selected_Start_Time = LocalTime.parse(start_combo.getSelectionModel().getSelectedItem());
            LocalTime Selected_End_Time = LocalTime.parse(end_combo.getSelectionModel().getSelectedItem());
            LocalDateTime Local_Start_DT = LocalDateTime.of(Selected_Start_Date, Selected_Start_Time);
            LocalDateTime Local_End_DT = LocalDateTime.of(Selected_End_Date, Selected_End_Time);
            int Start_Compare = Local_Start_DT.compareTo(Appointment_List_Start_DT);
            int Start_End_Compare = Local_Start_DT.compareTo(Appointment_List_End_DT);
            int End_Compare = Local_End_DT.compareTo(Appointment_List_End_DT);
            int End_Start_Compare = Local_End_DT.compareTo(Appointment_List_Start_DT);
            if(Start_Compare >= 0 && Start_End_Compare <= 0 || End_Compare <= 0 && End_Start_Compare >= 0)
                return true;
        }
        return false;
    }

    /**
     * Button method to Save information to a new Appointment
     * */
    @FXML
    void Save_Appointment(ActionEvent event) throws IOException {
        ObservableList<Appointment> appointment_Data = QueryController.getAllAppointments();
        int Appointment_Total = appointment_Data.size();
        DateTimeFormatter UTC_Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String Selected_Customer_Name = customerName_combo.getSelectionModel().getSelectedItem();
        String Selected_Appointment_With = appointmentWith_combo.getSelectionModel().getSelectedItem();
        ZoneId Local_Zone_ID = ZoneId.of(TimeZone.getDefault().getID());
        String Selected_Appointment_Type = appointmentType_txt.getText();
        LocalDate Selected_Start_Date = startDate_dp.getValue();
        LocalDate Selected_End_Date = endDate_dp.getValue();
        LocalTime Selected_Start_Time = LocalTime.parse(start_combo.getSelectionModel().getSelectedItem().toString());
        LocalTime Selected_End_Time = LocalTime.parse(end_combo.getSelectionModel().getSelectedItem().toString());
        String Appointment_Title = appointmentTitle_txt.getText();
        String Appointment_Description = appointmentDescription_txtb.getText();
        String Appointment_Location = appointmentLocation_txt.getText();
        ZonedDateTime Local_Start_DT = ZonedDateTime.of(Selected_Start_Date, Selected_Start_Time, Local_Zone_ID);
        ZonedDateTime Local_End_DT = ZonedDateTime.of(Selected_End_Date, Selected_End_Time, Local_Zone_ID);
        String UTC_Start_String = UTC_Formatter.format(Local_Start_DT);
        String UTC_End_String = UTC_Formatter.format(Local_End_DT);
        int Start_Value = Selected_Start_Time.compareTo(LocalTime.parse("08:00:00"));
        int End_Value = Selected_End_Time.compareTo(LocalTime.parse("17:00:00"));
        int End_Start_Time_Compare = Selected_End_Time.compareTo(Selected_Start_Time);
        int End_Start_Date_Compare = Selected_End_Date.compareTo(Selected_Start_Date);
        String Contact_Name = contactName_combo.getSelectionModel().getSelectedItem();
        if(Start_Value < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Start time is outside of business hours. Please select a time between 8AM and 5PM.");
            alert.showAndWait();
            return;
        }
        else if(End_Value > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("End time is outside of business hours. Please select a time between 8AM and 5PM.");
            alert.showAndWait();
            return;
        }
        else if(End_Start_Time_Compare <= 0 || End_Start_Date_Compare < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("End Date and Time cannot be before Start Date and Time.");
            alert.showAndWait();
            return;
        }
        else if(getOverlap() == true) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Your appointment overlaps with another appointment. Please select a different date or time.");
            alert.showAndWait();
            return;
        }
        else {
            ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
            QueryController.insertNewAppointment(Appointment_Total+1, Selected_Customer_Name,Appointment_Title,Appointment_Description,
                    Selected_Appointment_With, Appointment_Location, Selected_Appointment_Type, UTC_Start_String, UTC_End_String, LoginController.currentUser, Contact_Name);
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/View/ManageAppointments.fxml"));
            fxmlLoader.setResources(resource);
            fxmlLoader.load();
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = fxmlLoader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }

    }



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Populate Combo Boxes for Add Appointment Screen
        ObservableList<String> Customer_List = QueryController.getCustomerList();
        ObservableList<String> Appointment_With_List = QueryController.getUserList();
        ObservableList<String> Appointment_Times_List = QueryController.getAppointmentTimes();

        //Contact List
        String[] Contacts_List = new String[3];
        Contacts_List[0] = "Anika Costa";
        Contacts_List[1] = "Daniel Garcia";
        Contacts_List[2] = "Li Lee";
        ObservableList<String> Contact_Data = FXCollections.observableArrayList(Contacts_List);

        customerName_combo.setItems(Customer_List);
        appointmentWith_combo.setItems(Appointment_With_List);
        start_combo.setItems(Appointment_Times_List);
        end_combo.setItems(Appointment_Times_List);
        contactName_combo.setItems(Contact_Data);


    }


}
