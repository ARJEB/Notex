/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Controllers;

import Esprit.Projet.Connexion.MaConnexion;
import Esprit.Projet.Entities.Offre;
import Esprit.Projet.Testes.Main;
import Esprit.Projet.Testes.MyListener;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class unOffreController implements Initializable {

    @FXML
    private Label offreNameLable;
    @FXML
    private Label IdLabel;
    @FXML
    private ImageView img;
    
    private Offre offre;
    private MyListener myListener;
    @FXML
    private Label nomuser;
    @FXML
    private ImageView imguser;
    
    @FXML
    private Label datedebut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
     private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(offre);
    }
    
Connection cnxs;
    
    public unOffreController() {
        cnxs = MaConnexion.getInstance().getCnx();
        
    }

public void setData(Offre offre, MyListener myListener) {
        this.offre = offre;
        this.myListener = myListener;
        offreNameLable.setText(offre.getNomoffre());
        datedebut.setText(String.valueOf(offre.getDatedebut()));
        IdLabel.setText(Main.CURRENCY + offre.getId());
        Image image = new Image(getClass().getResourceAsStream(offre.getImgsrc()));
        img.setImage(image);
        
    }
}

















