package View;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Utilities.QueryController;

/**
 * FXML Controller for Login Screen
 *
 * @author Ryan Blumenhorst
 */
public class LoginController implements Initializable {

    //Sets Stage and Scene
    Stage stage;
    Parent scene;

    //Variable to hold Current LoggedIn User Data
    public static String currentUser = null;

    //FXML ID Data
    @FXML
    private Label title_lbl;
    @FXML
    private Label username_lbl;
    @FXML
    private TextField username_txt;
    @FXML
    private Label password_lbl;
    @FXML
    private TextField password_txt;
    @FXML
    private Button login_btn;
    @FXML
    private Button cancel_btn;
    @FXML
    private Label error_lbl;
    @FXML
    private Label error_userpw_lbl;
    @FXML
    private Label location_lbl;
    @FXML
    private Label error_title_lbl;

    /**
     * Button method to close the program
     * */
    @FXML
    void cancel(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Checks user name and password combo to list of users, if it is a user, then logs in and creates a log on txt file
     * */
    @FXML
    void login(ActionEvent event) throws IOException, SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        if(username_txt.getText().length() == 0 && password_txt.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(resource.getString(error_title_lbl.getId()));
            alert.setContentText(resource.getString(error_lbl.getId()));
            alert.showAndWait();
            FileWriter fw = new FileWriter("login_activity.txt", true);
            fw.write(System.getProperty("line.separator"));
            fw.write(username_txt.getText() + " Failed to login.");
            return;
        }
        String sqlStatement = "SELECT * FROM Users";
        QueryController.makeQuery(sqlStatement);
        ResultSet result = QueryController.getResult();
        Date Current_Date= new Date();
        long Current_Time = Current_Date.getTime();
        Timestamp TimeStamp = new Timestamp(Current_Time);
        try {
            while(result.next()) {
                if(username_txt.getText().equals(result.getString("User_Name"))) {
                    if(password_txt.getText().equals(result.getString("Password"))) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/View/MainMenu.fxml"));
                        loader.setResources(resource);
                        loader.load();
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        Parent scene = loader.getRoot();
                        stage.setScene(new Scene(scene));
                        stage.show();
                        currentUser = username_txt.getText();
                        QueryController.checkAppointmentStart();
                        FileWriter fw = new FileWriter("login_activity.txt", true);
                        fw.write(System.getProperty( "line.separator"));
                        fw.write(LoginController.currentUser + ", " + TimeStamp);
                        fw.write(" Login Successful");
                        fw.close();
                        return;

                    }
                }
            }
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(error_title_lbl.getId());
            alert.setContentText(resource.getString(error_userpw_lbl.getId()));
            alert.showAndWait();
        }
        catch(Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    /**
     * Sets Application Language (English or French Only)
     * */
    public void setLanguage() {
        ResourceBundle resource;
        Locale locale = Locale.getDefault();
        String language = locale.getDisplayLanguage();
        String country = locale.getDisplayCountry();

        if(country == "United States" && language == "English"){
            resource = ResourceBundle.getBundle("Properties/Nat_en_US");
            location_lbl.setText("United States - English");

        } else if(country == "Canada" && language == "French"){
            resource = ResourceBundle.getBundle("Properties/Nat_fr_ca");
            location_lbl.setText("Canada - French");


        } else if(country == "United Kingdom" && language == "English"){
            resource = ResourceBundle.getBundle("Properties/Nat_en_us");
            location_lbl.setText("United Kingdom - English");
        }
    }


    /**
     * Initializes the scene
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage();
    }

}
