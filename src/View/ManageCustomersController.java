package View;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import Model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import Utilities.QueryController;

/**
 * FXML Controller class for Manage Customers Screen
 *
 * @author Ryan Blumenhorst
 */
public class ManageCustomersController implements Initializable {

    //Sets Stage & Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> customerID_col;
    @FXML
    private TableColumn<Customer, String> customerName_col;
    @FXML
    private TableColumn<Customer, String> customerPhone_col;
    @FXML
    private TableColumn<Customer, String> customerAddress_col;
    @FXML
    private TableColumn<Customer, String> customerZip_col;
    @FXML
    private Button addCustomer_btn;
    @FXML
    private Button updateCustomer_btn;
    @FXML
    private Button deleteCustomer_btn;
    @FXML
    private Button mainMenu_btn;

    /**
     * Button method to take user to update customer screen
     * */
    @FXML
    void UpdateCustomer(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if(customer == null)
            return;
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/UpdateCustomer.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        UpdateCustomerController CustomerController = fxmlLoader.getController();
        CustomerController.retrieveCustomer(customer);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Button method to take user to Add Customer Screen
     * */
    @FXML
    void addNewCustomer(ActionEvent event) throws IOException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/AddCustomer.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button method to delete selected customer from Customer Table
     * */
    @FXML
    void deleteCustomer(ActionEvent event) {
        int selectedCustomer_ID = 0;
        if(customerTableView.getSelectionModel().isEmpty()) {
            return;
        }
        else {
            selectedCustomer_ID = customerTableView.getSelectionModel().getSelectedItem().getCustomerId();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("This will delete the customer and all appointments for the customer.");
        alert.setContentText("Would you like to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            QueryController.deleteAppointmentByCustomerID(selectedCustomer_ID);
            QueryController.deleteCustomer(selectedCustomer_ID);
        }
        ObservableList<Customer> customer_Data = QueryController.getAllCustomer();
        customerTableView.setItems(customer_Data);
        customerID_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerId".toString()));
        customerName_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName".toString()));
        customerPhone_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPhone".toString()));
        customerAddress_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address".toString()));
        customerZip_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode".toString()));
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

        //Sets initial TableView Data
        ObservableList<Customer> customer_Data = QueryController.getAllCustomer();
        customerTableView.setItems(customer_Data);
        customerID_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerId".toString()));
        customerName_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName".toString()));
        customerPhone_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerPhone".toString()));
        customerAddress_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("Address".toString()));
        customerZip_col.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode".toString()));

    }

}
