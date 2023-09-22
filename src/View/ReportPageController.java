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
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller Class for Report Page
 *
 * @author  Ryan Blumenhorst
 * */
public class ReportPageController implements Initializable {

    Stage stage;
    Parent scene;

    //FXML Elements
    @FXML
    private Button cancel_btn;
    @FXML
    private Button report1;
    @FXML
    private Button report2;
    @FXML
    private Button report3;

    /**
     * Button method to return to main menu
     * */
    @FXML
    private void cancel_btn(ActionEvent event) throws Exception{
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
     * Button method for report 1
     * */
    @FXML
    private void report1(ActionEvent event) throws IOException{
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/Report1.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button method for report 2
     * */
    @FXML
    private void report2(ActionEvent event) throws IOException{
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/Report2.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Button method report 3
     * */
    @FXML
    private void report3(ActionEvent event) throws IOException{
        ResourceBundle resource = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/View/Report3.fxml"));
        fxmlLoader.setResources(resource);
        fxmlLoader.load();
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = fxmlLoader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }
}
