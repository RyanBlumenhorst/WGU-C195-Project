package Resources;

import Helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Author: Ryan Blumenhorst
 * Student ID: 002094804
 * C195 Task - Scheduling Program
 * @author Ryan Blumenhorst
 */

/**
 * Main Method
 * */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Show Login Screen
        ResourceBundle resourceBundle = ResourceBundle.getBundle("Properties/Nat", Locale.getDefault());
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login.fxml"), resourceBundle);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
