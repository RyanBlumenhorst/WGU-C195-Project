package View;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import Model.Customer;
import javafx.collections.FXCollections;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Utilities.QueryController;

/**
 * FXML Controller class for Add Customer Screen
 *
 * @author Ryan Blumenhorst
 */
public class AddCustomerController implements Initializable {

    //Set Stage and Scene
    Stage stage;
    Parent scene;

    //FXML ID Data
    @FXML
    private TextField customerName_txt;
    @FXML
    private TextField customerPhone_txt;
    @FXML
    private TextField customerAddress_txt;
    @FXML
    private ComboBox<String> state_combo;
    @FXML
    private ComboBox<String> country_combo;
    @FXML
    private TextField customerZip_txt;
    @FXML
    private Button save_btn;
    @FXML
    private Button cancel_btn;

    /**
     * Button Method to return user to Manage Customers Screen
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
     * Button method to save and add customer to Customers Table
     * */
    @FXML
    void saveCustomer(ActionEvent event) throws IOException {
        ObservableList<Customer> customer_Data = QueryController.getAllCustomer();
        int Customer_Total = customer_Data.size();
        if(customerName_txt.getText().isEmpty() || customerPhone_txt.getText().isEmpty()|| customerZip_txt.getText().isEmpty() ||
                customerAddress_txt.getText().isEmpty() || state_combo.getSelectionModel().getSelectedItem().isEmpty()
                || country_combo.getSelectionModel().getSelectedItem().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("All Fields are Required.");
            alert.showAndWait();
        }
        else {
            String Selected_State = state_combo.getSelectionModel().getSelectedItem();
            String Selected_Country = country_combo.getSelectionModel().getSelectedItem();
            int countryID = 0;
            if(Selected_Country == "U.S"){
                countryID = 1;
            } else if(Selected_Country == "UK"){
                countryID = 2;
            }else if(Selected_Country == "Canada"){
                countryID = 3;
            }
            QueryController.insertNewCustomer(Customer_Total+1, customerName_txt.getText(),customerPhone_txt.getText(),
                    customerZip_txt.getText(), customerAddress_txt.getText(), LoginController.currentUser, LoginController.currentUser,
                    countryID, Selected_State);
            Customer_Total += 1;
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
     * Method to set the State/Province Combo list
     * */
    public void setStateProvinceCombo(){

        //Canada Provinces
        String[] province = new String[10];
        province[0] = "Yukon";
        province[1] = "Northwest Territories";
        province[2] = "Nunavut";
        province[3] = "British Columbia";
        province[4] = "Alberta";
        province[5] = "Manitoba";
        province[6] = "Ontario";
        province[7] = "Quebec";
        province[8] = "Saskatchewan";
        province[9] = "Newfoundland and Labrador";
        ObservableList<String> provinceList = FXCollections.observableArrayList(province);

        //United Kingdom Regions
        String[] regions = new String[21];
        regions[0] = "England";
        regions[1] = "Northern Ireland";
        regions[2] = "Scotland";
        regions[3] = "Wales";
        regions[4] = "Guernsey";
        regions[5] = "Jersey";
        regions[6] = "Isle of Man";
        regions[7] = "Anguilla";
        regions[8] = "Bermuda";
        regions[9] = "British Virgin Islands";
        regions[10] = "Cayman Islands";
        regions[11] = "Falkland Islands";
        regions[12] = "Gibraltar";
        regions[13] = "Montserrat";
        regions[14] = "Pitcairn Islands";
        regions[15] = "Saint Helena";
        regions[16] = "Ascension";
        regions[17] = "Tristan da Cunha";
        regions[18] = "South Georgia";
        regions[19] = "South Sandwich Islands";
        regions[20] = "Turks and Caicos Islands";
        ObservableList<String> regionsList = FXCollections.observableArrayList(regions);

        //United States State List
        String[] states = new String[50];
        states[0] = "Alabama";
        states[1] = "Alaska";
        states[2] = "Arizona";
        states[3] = "Arkansas";
        states[4] = "California";
        states[5] = "Colorado";
        states[6] = "Connecticut";
        states[7] = "Delaware";
        states[8] = "Florida";
        states[9] = "Georgia";
        states[10] = "Hawaii";
        states[11] = "Idaho";
        states[12] = "Illinois";
        states[13] = "Indiana";
        states[14] = "Iowa";
        states[15] = "Kansas";
        states[16] = "Kentucky";
        states[17] = "Louisiana";
        states[18] = "Maine";
        states[19] = "Maryland";
        states[20] = "Massachusetts";
        states[21] = "Michigan";
        states[22] = "Minnesota";
        states[23] = "Mississippi";
        states[24] = "Missouri";
        states[25] = "Montana";
        states[26] = "Nebraska";
        states[27] = "Nevada";
        states[28] = "New Hampshire";
        states[29] = "New Jersey";
        states[30] = "New Mexico";
        states[31] = "New York";
        states[32] = "North Carolina";
        states[33] = "North Dakota";
        states[34] = "Ohio";
        states[35] = "Oklahoma";
        states[36] = "Oregon";
        states[37] = "Pennsylvania";
        states[38] = "Rhode Island";
        states[39] = "South Carolina";
        states[40] = "South Dakota";
        states[41] = "Tennessee";
        states[42] = "Texas";
        states[43] = "Utah";
        states[44] = "Vermont";
        states[45] = "Virginia";
        states[46] = "Washington";
        states[47] = "West Virginia";
        states[48] = "Wisconsin";
        states[49] = "Wyoming";
        ObservableList<String> statesList = FXCollections.observableArrayList(states);

        if(country_combo.getSelectionModel().getSelectedItem() == "Canada"){
            state_combo.setItems(provinceList);
        } else if(country_combo.getSelectionModel().getSelectedItem() == "UK"){
            state_combo.setItems(regionsList);
        } else if(country_combo.getSelectionModel().getSelectedItem() == "U.S"){
            state_combo.setItems(statesList);
        }
    }

    /**
     * Passes selected Country to the State/Province Method
     * @param event Country Combo Picked
     * @throws Exception exception
     * */
    @FXML
    public void countryCombo(ActionEvent event) throws Exception{
        if(country_combo.getSelectionModel().getSelectedItem() != null){
            setStateProvinceCombo();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Sets Country and City/Province List
        String[] country = new String[3];
        country[0] = "Canada";
        country[1] = "UK";
        country[2] = "U.S";

        ObservableList<String> countryList = FXCollections.observableArrayList(country);
        country_combo.setItems(countryList);
    }
}
