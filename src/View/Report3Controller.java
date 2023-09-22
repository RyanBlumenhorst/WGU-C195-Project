package View;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import Model.Appointment;
import Model.Customer;
import Utilities.QueryController;
import javafx.collections.FXCollections;
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

/**
 * FXML Controller for Report 3
 *
 * @author Ryan Blumenhorst
 * */
public class Report3Controller implements Initializable{
    Parent scene;
    Stage stage;

    //FXML Elements
    @FXML
    public TableView<Appointment> rpt3TableView;
    @FXML
    public TableColumn<Appointment, Integer> aptID_col;
    @FXML
    public TableColumn<Appointment, String> title_col;
    @FXML
    public TableColumn<Appointment, String> type_col;
    @FXML
    public TableColumn<Appointment, String> desc_col;
    @FXML
    public TableColumn<Appointment, String> start_col;
    @FXML
    public TableColumn<Appointment, String> end_col;
    @FXML
    public TableColumn<Appointment, Integer> custID_col;
    @FXML
    public Button back_btn;
    @FXML
    public Button sort_btn;
    @FXML
    public TextField custFilter_txt;

    /**
     * Button method to return to report page
     * @param event Button Press
     * @throws IOException IOException
     * */
    @FXML
    public void back_btn(ActionEvent event) throws IOException{
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
     * Button method to get appointments for specified customer
     * @param event Button Press
     * @throws IOException IOException
     * */
    @FXML
    public void sort_btn(ActionEvent event) throws IOException{
        String Customer_Name = custFilter_txt.getText();
        ObservableList<Customer> customer_Name_List = QueryController.getCustomerNames();
        ObservableList<Appointment> customer_Appointments = FXCollections.observableArrayList();
        if(Customer_Name != null){
            for(Customer c: customer_Name_List) {
                if(c.getCustomerName().toLowerCase().equals(Customer_Name.toLowerCase())) {
                    customer_Appointments = QueryController.getCustomerAppointments(Customer_Name);
                    rpt3TableView.setItems(customer_Appointments);
                    aptID_col.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
                    title_col.setCellValueFactory(new PropertyValueFactory<Appointment, String >("title"));
                    type_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
                    desc_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Description"));
                    start_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("Start"));
                    end_col.setCellValueFactory(new PropertyValueFactory<Appointment, String>("End"));
                    custID_col.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
                }
            }

        } else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("No Appointments Found.");
            alert.showAndWait();
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }
}
