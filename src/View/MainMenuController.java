package View;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import Model.Appointment;
import Model.Customer;
import Utilities.QueryController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class for Main Menu
 *
 * @author Ryan Blumenhorst
 */
public class MainMenuController implements Initializable {

    //Sets Stage & Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private TableView<Customer> customer_tbl;
    @FXML
    private TableColumn<Customer,Integer> customerID_col;
    @FXML
    private TableColumn<Customer, String> customerName_col;
    @FXML
    private TableView<Appointment> appointment_tbl;
    @FXML
    private TableColumn<Appointment, String> appointmentTitle_col;
    @FXML
    private TableColumn<Appointment, String> appointmentCustomer_col;
    @FXML
    private TableColumn<Appointment, String> appointmentStartDateTime_col;
    @FXML
    private TableColumn<Appointment, String> appointmentEndDateTime_col;
    @FXML
    private Button manageUsers_btn;
    @FXML
    private Button manageCustomers_btn;
    @FXML
    private Button manageAppointments_btn;
    @FXML
    private Button reporting_btn;
    @FXML
    private Button exit_btn;

    /**
     * Button method to go to log in screen
     * */
    @FXML
    void exitSystem(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/Login.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        return;
    }

    /**
     * Button method to go to the Manage Appointments Screen
     * */
    @FXML
    void loadAppointments(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/ManageAppointments.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        return;

    }

    /**
     * Button method to go to Manage Customers Screen
     * */
    @FXML
    void loadCustomers(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/ManageCustomers.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Button Method to go to Report Page Screen
     * */
    @FXML
    void loadReports(ActionEvent event) throws IOException{
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/ReportPage.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**
     * Initializes the controller class. Populates TableViews
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Appointment> appointment_Data = QueryController.getAllAppointments();
        ObservableList<Customer> customer_Data = QueryController.getAllCustomer();

        customer_tbl.setItems(customer_Data);
        customerID_col.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId".toString()));
        customerName_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));

        appointment_tbl.setItems(appointment_Data);
        appointmentTitle_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        appointmentCustomer_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customerName"));
        appointmentStartDateTime_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Start"));
        appointmentEndDateTime_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("End"));

    }

}
