package View;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Model.Customer;
import Utilities.QueryController;

/**
 * FXML Controller class for Update Customer Screen
 *
 * @author Ryan Blumenhorst
 */
public class UpdateCustomerController implements Initializable {

    //Sets Stage & Scene
    Stage stage;
    Parent scene;

    //FXML Elements
    @FXML
    private TextField custCreateDate_txt;
    @FXML
    private TextField customerId_txt;
    @FXML
    private TextField customerName_txt;
    @FXML
    private TextField customerPhone_txt;
    @FXML
    private TextField customerAddress_txt;
    @FXML
    private TextField customerCreateDate_txt;
    @FXML
    private TextField customerCreatedBy_txt;
    @FXML
    private TextField customerZip_txt;
    @FXML
    private TextField customerDivisionID_txt;
    @FXML
    private TextField customerAddressId_txt;
    @FXML
    private Button save_btn;
    @FXML
    private Button cancel_btn;

    /**
     * Button method to return to manage customers screen
     * */
    @FXML
    void cancel(ActionEvent event) throws IOException {
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
     * Button method to save changes to Customer
     * */
    @FXML
    void saveCustomer(ActionEvent event) throws IOException {

        if(customerName_txt.getText().isEmpty() || customerPhone_txt.getText().isEmpty()|| customerZip_txt.getText().isEmpty() ||
                customerAddress_txt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("All Fields are Required.");
            alert.showAndWait();
        }
        else {
            QueryController.updateCustomer(Integer.parseInt(customerId_txt.getText()),customerName_txt.getText(), customerPhone_txt.getText(),
                    customerZip_txt.getText(), customerAddress_txt.getText(), LoginController.currentUser);
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
    }

    /**
     * Returns data to populate the form with customer information
     * @param customer Selected Customer
     * */
    public void retrieveCustomer(Customer customer) {

        customerCreateDate_txt.setText(customer.getCreate_Date().toString());
        customerId_txt.setText(Integer.toString(customer.getCustomerId()));
        customerName_txt.setText(customer.getCustomerName());
        customerAddress_txt.setText(customer.getAddress());
        customerPhone_txt.setText(customer.getCustomerPhone());
        customerCreateDate_txt.setText(customer.getCreate_Date().toString());
        customerZip_txt.setText(customer.getPostalCode());
        customerCreatedBy_txt.setText(customer.getCreated_By());
        customerDivisionID_txt.setText(Integer.toString(customer.getDivision_ID()));

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
