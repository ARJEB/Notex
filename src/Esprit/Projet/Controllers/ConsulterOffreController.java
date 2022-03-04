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
import java.io.IOException;
import static java.lang.String.valueOf;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ahmed
 */
public class ConsulterOffreController implements Initializable {

    
    @FXML
    private Button btnCRUDOFFRE;
    @FXML
    private VBox chosenOffreCard;
    @FXML
    private Label OffreNameLable;
    @FXML
    private Label IdLabel;
    @FXML
    private ImageView OffreImg;
    
    @FXML
    private GridPane grid;
     @FXML
    private Label datedebut;
     @FXML
    private Label Description;
     @FXML
    private Button btnCRUDreclamation;

      Connection cnxs;
    @FXML
    private Label datefin;
    @FXML
    private ScrollPane scroll;
    public ConsulterOffreController() {
        cnxs = MaConnexion.getInstance().getCnx();
    }
    private List<Offre> myList = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        btnCRUDOFFRE.setOnAction(event->{
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/Esprit/Projet/Views/OffreCRUD.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("err");
            }
        });
        btnCRUDreclamation.setOnAction(event->{
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/Esprit/Projet/Views/tableView.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("err");
            }
        });
        
        
        
        myList.addAll(affichageOffres());
        if (myList.size() > 0) {
            setChosenOffre(myList.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Offre offre) {
                    setChosenOffre(offre);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < myList.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Esprit/Projet/Views/unOffre.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                unOffreController unOffreController = fxmlLoader.getController();
                unOffreController.setData(myList.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new javafx.geometry.Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public List<Offre> affichageOffres() {
        List<Offre> myList = new ArrayList();
        try {
            String requete = "SELECT * FROM OFFRE";
            Statement st = cnxs.createStatement();
            ResultSet res = st.executeQuery(requete);
while (res.next()) {
                Offre p = new Offre();
                p.setId(res.getInt(1));
                p.setNomoffre(res.getString("nomoffre"));
                p.setDatedebut(res.getDate("datedebut"));
                p.setDatefin(res.getDate("datefin"));
                p.setDescription(res.getString("description"));
                p.setImgsrc(res.getString("imgsrc"));
                p.setCouleur(res.getString("couleur"));
               myList.add(p);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
    }
    
    private void setChosenOffre(Offre offre) {
        
    
    
        OffreNameLable.setText(offre.getNomoffre());
        IdLabel.setText(Main.CURRENCY + offre.getId());
        
        datedebut.setText(String.valueOf(offre.getDatedebut()));
        datefin.setText(String.valueOf(offre.getDatefin()));
        Description.setText(offre.getDescription());
        image = new Image(getClass().getResourceAsStream(offre.getImgsrc()));
        
        OffreImg.setImage(image);
        
        chosenOffreCard.setStyle("-fx-background-color: #" + offre.getCouleur()+ ";\n" +
                "    -fx-background-radius: 30;");
    }
    



    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    
    
    
    
    
    
    
    
    
    
    
   
}
