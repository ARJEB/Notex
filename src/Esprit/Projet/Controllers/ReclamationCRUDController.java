/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class ReclamationCRUDController implements Initializable {

    @FXML
    private TextField tfid;
    @FXML
    private TextField tftypereclamation;
    @FXML
    private DatePicker tfdatereclamation;
    @FXML
    private TextArea tfdescriptionreclamation;
    @FXML
    private SplitMenuButton tfetatreclamation;
    @FXML
    private TableView<?> tvoffre;
    @FXML
    private TableColumn<?, ?> coltypereclamation;
    @FXML
    private TableColumn<?, ?> coldatedreclamation;
    @FXML
    private TableColumn<?, ?> coldescriptionreclamation;
    @FXML
    private TableColumn<?, ?> coletatreclamation;
    @FXML
    private TableColumn<?, ?> colajsu;
    @FXML
    private Button btninserrer;
    @FXML
    private Button btnmodifier;
    @FXML
    private Button btnsupprimer;
    @FXML
    private Label Message;
    @FXML
    private Button tfconsuleroffre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    
}
