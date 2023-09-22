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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Utilities.QueryController;

/**
 * FXML Controller class for Update Appointment Screen
 *
 * @author Ryan Blumenhorst
 */
public class UpdateAppointmentController implements Initializable {

    //Sets Stage & Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private ComboBox<String> customerName_combo;
    @FXML
    private ComboBox<String> appointmentWith_combo;
    @FXML
    private TextField appointmentType_txt;
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
    private TextField appointmentId_txt;

    /**
     * Checks to see if Appointments will overlap
     * @return boolean
     * */
    private boolean getOverlap() {
        ObservableList<Appointment> appointment_List = QueryController.getAllAppointments();
        //Expression to get appointment information from the appointment list
        for(Appointment a : appointment_List) {
            DateTimeFormatter DTFormat = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
            LocalDateTime appointmentListStartDT = LocalDateTime.parse(a.getStart(), DTFormat);
            LocalDateTime appointmentListEndDT = LocalDateTime.parse(a.getEnd(), DTFormat);
            LocalDate selectedStartDate = startDate_dp.getValue();
            LocalDate selectedEndDate = endDate_dp.getValue();
            int Appointment_ID = Integer.parseInt(appointmentId_txt.getText());
            LocalTime selectedStartTime = LocalTime.parse(start_combo.getSelectionModel().getSelectedItem());
            LocalTime selectedEndTime = LocalTime.parse(end_combo.getSelectionModel().getSelectedItem());
            int Appointment_ID_A = a.getAppointmentId();
            LocalDateTime Local_Start_DT = LocalDateTime.of(selectedStartDate, selectedStartTime);
            LocalDateTime Local_End_DT = LocalDateTime.of(selectedEndDate, selectedEndTime);
            int Start_Compare = Local_Start_DT.compareTo(appointmentListStartDT);
            int Start_End_Compare = Local_Start_DT.compareTo(appointmentListEndDT);
            int End_Compare = Local_End_DT.compareTo(appointmentListEndDT);
            int End_Start_Compare = Local_End_DT.compareTo(appointmentListStartDT);
            if(Appointment_ID != Appointment_ID_A) {
                if (Start_Compare >= 0 && Start_End_Compare <= 0 || End_Compare <= 0 && End_Start_Compare >= 0)
                    return true;
            }
        }
        return false;
    }


    /**
     * Button method to save information as a new appointment
     * */
    @FXML
    private void saveAppt(ActionEvent event) throws IOException {
        DateTimeFormatter UTC_Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"));
        ZoneId Local_Zone_ID = ZoneId.of(TimeZone.getDefault().getID());
        String Selected_Customer_Name = customerName_combo.getSelectionModel().getSelectedItem();
        String Selected_Appointment_With = appointmentWith_combo.getSelectionModel().getSelectedItem();
        String Selected_Start_Date = String.valueOf(startDate_dp.getValue());
        String Selected_Start_Time = start_combo.getSelectionModel().getSelectedItem();
        String Selected_End_Date = String.valueOf(endDate_dp.getValue());
        String Selected_End_Time = end_combo.getSelectionModel().getSelectedItem();
        LocalDate Start_Date = LocalDate.parse(Selected_Start_Date);
        LocalTime Start_Time = LocalTime.parse(Selected_Start_Time);
        LocalDate End_Date = LocalDate.parse(Selected_End_Date);
        LocalTime End_Time = LocalTime.parse(Selected_End_Time);
        ZonedDateTime Local_Start_DT = ZonedDateTime.of(Start_Date, Start_Time, Local_Zone_ID);
        ZonedDateTime Local_End_DT = ZonedDateTime.of(End_Date, End_Time, Local_Zone_ID);
        String UTC_Start_DT_String = UTC_Formatter.format(Local_Start_DT);
        String UTC_End_DT_String = UTC_Formatter.format(Local_End_DT);
        int Start_Value = LocalTime.parse(Selected_Start_Time).compareTo(LocalTime.parse("08:00:00"));
        int End_Value = LocalTime.parse(Selected_End_Time).compareTo(LocalTime.parse("17:00:00"));
        int End_Start_Time_Compare = Selected_End_Time.compareTo(Selected_Start_Time);
        int End_Start_Date_Compare = Selected_End_Date.compareTo(Selected_Start_Date);

        if(Start_Value < 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("Start time outside of business hours. Please select a time between 8AM and 5PM.");
            alert.showAndWait();
            return;
        }
        else if(End_Value > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("End time outside of business hours. Please select a time between 8AM and 5PM.");
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
            alert.setContentText("Your appointment overlaps with another appointment. Please select a new date and/or time.");
            alert.showAndWait();
            return;
        }
        else {
            ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
            QueryController.updateAppointment(Integer.parseInt(appointmentId_txt.getText()), Selected_Customer_Name, Selected_Appointment_With, appointmentType_txt.getText(),
                    UTC_Start_DT_String, UTC_End_DT_String, LoginController.currentUser);
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
     * Button method to return to manage appointments screen
     * */
    @FXML
    private void cancel(ActionEvent event) throws IOException {
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
     * Returns appointments to populate update screen on open.
     * @param appointment Selected Appointment
     * */
    public void retrieveAppointments(Appointment appointment) {

        String appointmentStart = appointment.getStart();
        String appointmentEnd = appointment.getEnd();
        LocalDate appointmentStartDate = LocalDate.parse(appointmentStart.substring(0, 10));
        String appointmentStartTime = appointmentStart.substring(11, 19);
        LocalDate appointmentEndDate = LocalDate.parse(appointmentEnd.substring(0, 10));
        String appointmentEndTime = appointmentEnd.substring(11, 19);
        appointmentId_txt.setText(Integer.toString(appointment.getAppointmentId()));
        appointmentType_txt.setText(appointment.getType());
        startDate_dp.setValue(appointmentStartDate);
        endDate_dp.setValue(appointmentEndDate);
        start_combo.setValue(appointmentStartTime);
        end_combo.setValue(appointmentEndTime);
        customerName_combo.setValue(appointment.getCustomerName());
        appointmentWith_combo.setValue(appointment.getUserName());
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Sets combo box data for Update Appointment Screen
        ObservableList<String> Customer_List = QueryController.getCustomerList();
        ObservableList<String> AppointmentWith_List = QueryController.getUserList();
        ObservableList<String> AppointmentTimes_List = QueryController.getAppointmentTimes();
        customerName_combo.setItems(Customer_List);
        appointmentWith_combo.setItems(AppointmentWith_List);
        start_combo.setItems(AppointmentTimes_List);
        end_combo.setItems(AppointmentTimes_List);
    }

}
