package View;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import Model.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Utilities.QueryController;

/**
 * FXML Controller class for Manage Appointments Screen
 *
 * @author Ryan Blumenhorst
 */
public class ManageAppointmentsController implements Initializable {

    //Sets Stage and Screen
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private RadioButton filterName;

    @FXML
    private RadioButton filterTitle;

    @FXML
    private TableColumn<Appointment, Integer> appointmentId_col;

    @FXML
    private TableColumn<Appointment, String> title_col;

    @FXML
    private TableColumn<Appointment, String> description_col;

    @FXML
    private TableColumn<Appointment, String> customerName_col;

    @FXML
    private TableColumn<Appointment, String> location_col;

    @FXML
    private TableColumn<Appointment, String> createDate_col;

    @FXML
    private TableColumn<Appointment, String> createdBy_col;

    @FXML
    private TableColumn<Appointment, Integer> customerId_col;

    @FXML
    private TableColumn<Appointment, String> appointmentType_col;

    @FXML
    private TableColumn<Appointment, String> appointmentStart_col;

    @FXML
    private TableColumn<Appointment, String> appointmentStop_col;

    @FXML
    private Button addCustomer_btn;

    @FXML
    private Button updateCustomer_btn;

    @FXML
    private Button deleteCustomer_btn;

    @FXML
    private Button mainMenu_btn;

    //Date Formatter Variable
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Radio Button Method to sort Appointments by Name
     * */
    @FXML
    void filterName(ActionEvent event) throws Exception{
        if(filterName.isSelected()){
            filterTitle.setSelected(false);
            if(appointmentTableView.getSortOrder().isEmpty() == false) {
                appointmentTableView.getSortOrder().remove(0);
            }
            customerName_col.setSortType(TableColumn.SortType.DESCENDING);
            appointmentTableView.getSortOrder().add(customerName_col);
            appointmentTableView.sort();
        }
    }

    /**
     * Radio Button Method to sort Appointments by Title
     * */
    @FXML
    void filterTitle(ActionEvent event) throws Exception{
        if(filterTitle.isSelected()){
            filterName.setSelected(false);
            if(appointmentTableView.getSortOrder().isEmpty() == false) {
                appointmentTableView.getSortOrder().remove(0);
            }
            title_col.setSortType(TableColumn.SortType.DESCENDING);
            appointmentTableView.getSortOrder().add(title_col);
            appointmentTableView.sort();
        }
    }

    /**
     * Button Method to take user to add appointment screen
     * */
    @FXML
    void AddNewAppt(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/AddAppointment.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button method to delete selected appointment
     * */
    @FXML
    void DeleteAppt(ActionEvent event) {
        int selectedAppointmentId = appointmentTableView.getSelectionModel().getSelectedItem().getAppointmentId();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("This will delete the selected appointment.");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            QueryController.deleteAppointment(selectedAppointmentId);
        }

        ObservableList<Appointment> apptData = QueryController.getAllAppointments();
        appointmentId_col.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("AppointmentID"));
        title_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        location_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
        customerName_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("CustomerName"));
        customerId_col.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("CustomerID"));
        appointmentType_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("AppointmentType"));
        appointmentStart_col.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentStop_col.setCellValueFactory(new PropertyValueFactory<>("End"));
        createDate_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("CreateDate"));
        createdBy_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("CreatedBy"));
        description_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        appointmentTableView.setItems(apptData);

    }
    /**
     * Button Method to take user to update appointment screen
     * */
    @FXML
    void UpdateAppt(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(appointment == null)
            return;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/UpdateAppointment.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        UpdateAppointmentController AppointmentController = fxmlLoader.getController();
        AppointmentController.retrieveAppointments(appointment);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }
    /**
     * Button method to return user to main menu
     * */
    @FXML
    void returnMainMenu(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/MainMenu.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }


    /**
     * Initializes the controller class. Populates TableView
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Appointment> apptData = QueryController.getAllAppointments();
        appointmentId_col.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        title_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Title"));
        location_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Location"));
        customerName_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("customerName"));
        customerId_col.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        appointmentType_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        appointmentStart_col.setCellValueFactory(new PropertyValueFactory<>("Start"));
        appointmentStop_col.setCellValueFactory(new PropertyValueFactory<>("End"));
        createDate_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("CreateDate"));
        createdBy_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("CreatedBy"));
        description_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
        appointmentTableView.setItems(apptData);
    }
}
