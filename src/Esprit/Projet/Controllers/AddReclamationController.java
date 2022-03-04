/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Controllers;

import Esprit.Projet.Connexion.DbConnect;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import Esprit.Projet.Entities.Reclamation;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class AddReclamationController implements Initializable {

    @FXML
    private JFXTextField typeFld;
    @FXML
    private JFXDatePicker dateFld;
    @FXML
    private JFXTextField descriptionFld;
    @FXML
    private JFXTextField etatFld;

    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Reclamation reclamation = null;
    private boolean update;
    int reclamationId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void save(MouseEvent event) {

        connection = DbConnect.getConnect();
        String typereclamation = typeFld.getText();
        String datedatereclamation = String.valueOf(dateFld.getValue());
        String description = descriptionFld.getText();
        String etat = etatFld.getText();

        if (typereclamation.isEmpty() || datedatereclamation.isEmpty() || description.isEmpty() || etat.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        } else {
            getQuery();
            insert();
            clean();

        }

    }

    @FXML
    private void clean() {
        typeFld.setText(null);
        dateFld.setValue(null);
        descriptionFld.setText(null);
        etatFld.setText(null);
        
    }

    private void getQuery() {

        if (update == false) {
            
            query = "INSERT INTO `reclamation`( `typereclamation`, `datereclamation`, `description`, `etat`) VALUES (?,?,?,?)";

        }else{
            query = "UPDATE `reclamation` SET "
                    + "`typereclamation`=?,"
                    + "`datereclamation`=?,"
                    + "`description`=?,"
                    + "`etat`= ? WHERE id = '"+reclamationId+"'";
        }

    }

    private void insert() {

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, typeFld.getText());
            preparedStatement.setString(2, String.valueOf(dateFld.getValue()));
            preparedStatement.setString(3, descriptionFld.getText());
            preparedStatement.setString(4, etatFld.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void setTextField(int id, String typereclamation, LocalDate datereclamation, String description, String etat) {

        reclamationId = id;
        typeFld.setText(typereclamation);
        dateFld.setValue(datereclamation);
        descriptionFld.setText(description);
        etatFld.setText(etat);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

}
