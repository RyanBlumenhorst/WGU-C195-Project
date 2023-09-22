package View;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import Model.TypeSort;
import Utilities.QueryController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * FXML Controller Class for Report1 Screen
 *
 * @author Ryan Blumenhorst
 * */
public class Report1Controller implements Initializable {
    Stage stage;
    Parent scene;

    //FXML Elements
    @FXML
    public TableView<TypeSort> typeTableView;
    @FXML
    public Label jan_lbl;
    @FXML
    public Label feb_lbl;
    @FXML
    public Label mar_lbl;
    @FXML
    public Label apr_lbl;
    @FXML
    public Label may_lbl;
    @FXML
    public Label jun_lbl;
    @FXML
    public Label jul_lbl;
    @FXML
    public Label aug_lbl;
    @FXML
    public Label sept_lbl;
    @FXML
    public Label oct_lbl;
    @FXML
    public Label nov_lbl;
    @FXML
    public Label dec_lbl;
    @FXML
    public TableColumn<TypeSort, String> type_col;
    @FXML
    public TableColumn<TypeSort, String> amount_col;

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
     * Initialize the Controller. Get Month information, Appointment Type Information
     * Populates Table
     * */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> months = QueryController.getMonthTotals();

        int JanCount = 0;
        int FebCount = 0;
        int MarCount = 0;
        int AprCount = 0;
        int MayCount = 0;
        int JunCount = 0;
        int JulCount = 0;
        int AugCount = 0;
        int SepCount = 0;
        int OctCount = 0;
        int NovCount = 0;
        int DecCount = 0;


        for(String m: months){
            if (m.equals("01")){
                JanCount = JanCount + 1;
            } else if(m.equals("02")){
                FebCount = FebCount + 1;
            } else if(m.equals("03")){
                MarCount = MarCount + 1;
            } else if(m.equals("04")){
                AprCount = AprCount + 1;
            } else if(m.equals("05")){
                MayCount = MayCount + 1;
            } else if(m.equals("06")){
                JunCount = JunCount + 1;
            } else if(m.equals("07")){
                JulCount = JulCount + 1;
            } else if(m.equals("08")){
                AugCount = AugCount + 1;
            } else if(m.equals("09")){
                SepCount = SepCount + 1;
            } else if(m.equals("10")){
                OctCount = OctCount + 1;
            } else if(m.equals("11")){
                NovCount = NovCount + 1;
            } else if(m.equals("12")){
                DecCount = DecCount + 1;
            }
        }
        jan_lbl.setText(Integer.toString(JanCount));
        feb_lbl.setText(Integer.toString(FebCount));
        mar_lbl.setText(Integer.toString(MarCount));
        apr_lbl.setText(Integer.toString(AprCount));
        may_lbl.setText(Integer.toString(MayCount));
        jun_lbl.setText(Integer.toString(JunCount));
        jul_lbl.setText(Integer.toString(JulCount));
        aug_lbl.setText(Integer.toString(AugCount));
        sept_lbl.setText(Integer.toString(SepCount));
        oct_lbl.setText(Integer.toString(OctCount));
        nov_lbl.setText(Integer.toString(NovCount));
        dec_lbl.setText(Integer.toString(DecCount));

        ObservableList<TypeSort> types = QueryController.getAppointmentTypes();
        typeTableView.setItems(types);
        type_col.setCellValueFactory(new PropertyValueFactory<>("Type"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("Amount"));

    }

}
