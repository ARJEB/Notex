/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Controllers;

import Esprit.Projet.Connexion.MaConnexion;
import Esprit.Projet.Entities.Offre;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author ahmed
 */
public class OffreCRUDController implements Initializable {
    
    private Label label;
    @FXML
    private TextField tfid;
    @FXML
    private TextField tfnom;
    @FXML
    private DatePicker tfdated;
    @FXML
    private DatePicker tfdatef;
    @FXML
    private TextArea tfdescription;
    @FXML
    private TableView<Offre> tvoffre;
    @FXML
    private TableColumn<Offre, Integer> colid;
    @FXML
    private TableColumn<Offre, String> colnom;
    @FXML
    private TableColumn<Offre, Date> coldated;
    @FXML
    private TableColumn<Offre, Date> coldatef;
    @FXML
    private TableColumn<Offre, String> coldescription;
    @FXML
    private Button btninserrer;
    @FXML
    private Button btnmodifier;
    @FXML
    private Button btnsupprimer;
    @FXML
    private TextField tfimg;
    @FXML
    private TextField tfcouleur;
    @FXML
    private Label Message;
    @FXML
    private TableColumn<Offre, String> colimg;
    @FXML
    private TableColumn<Offre, String> colcouleur;
    @FXML
    private Button tfconsuleroffre;
    @FXML
    private ImageView imageSer;
    @FXML
    private Button upload;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        
        if(event.getSource() == btninserrer ){
            ajouterOffre();
        }else if(event.getSource()== btnmodifier){
        modifierOffre();
    }else if(event.getSource()== btnsupprimer){
        effacerOffre();
    }
    }
    @FXML
    void ajouterimg(ActionEvent envent) throws FileNotFoundException{
        FileChooser filechooser = new FileChooser();
        
        FileChooser.ExtensionFilter extFilterJPG
                = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg
                = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG
                = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng
                = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        
        filechooser.setInitialDirectory(new File("C:\\Users\\ahmed\\OneDrive\\Desktop\\ProjectPIDEV\\src\\Esprit\\Projet\\Image"));
        filechooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        
        File file = filechooser.showOpenDialog(new Stage());
        Image image = new Image(new FileInputStream(file));
        
        
        tfimg.setText("/Esprit/Projet/Image/"+file.getName());
        imageSer.setImage(image);
    }
    @Override
    
    
    public void initialize(URL url, ResourceBundle rb) {
       
        
        
        tfconsuleroffre.setOnAction(event->{
            try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/Esprit/Projet/Views/ConsulterOffre.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("err");
            }
        });
        
// TODO
        afficherOffre();
    }    
    

Connection cnxs;
    public OffreCRUDController() {
        cnxs = MaConnexion.getInstance().getCnx();
        
    }
    
    
    public ObservableList<Offre> getOffresList(){
        ObservableList <Offre> myList = FXCollections.observableArrayList();
        try {
            
            String requete = "SELECT * FROM offre";
                    Statement st = cnxs.createStatement();
                 ResultSet rs = st.executeQuery(requete);
                    while(rs.next()){
                        Offre o = new Offre(); 
                        o.setId(rs.getInt("id"));
                        o.setNomoffre(rs.getString("nomoffre"));
                        o.setDatedebut(rs.getDate("datedebut"));
                        o.setDatefin(rs.getDate("datefin"));
                        o.setDescription(rs.getString("description"));
                        o.setImgsrc(rs.getString("imgsrc"));
                        o.setCouleur(rs.getString("couleur"));
                        
                        
                        
                        myList.add(o);
                    }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return myList;
    }
        
    

public void afficherOffre(){
    ObservableList<Offre> list = getOffresList();
    
    colid.setCellValueFactory(new PropertyValueFactory<Offre,Integer>("id"));
    colnom.setCellValueFactory(new PropertyValueFactory<Offre,String>("nomoffre"));
    coldated.setCellValueFactory(new PropertyValueFactory<Offre,Date>("datedebut"));
    coldatef.setCellValueFactory(new PropertyValueFactory<Offre,Date>("datefin"));
    coldescription.setCellValueFactory(new PropertyValueFactory<Offre,String>("description"));
    colimg.setCellValueFactory(new PropertyValueFactory<Offre,String>("imgsrc"));
    colcouleur.setCellValueFactory(new PropertyValueFactory<Offre,String>("couleur"));
    
    
    
    tvoffre.setItems(list);

}


       
public void ajouterOffre(){
        try {
            String requete = "INSERT INTO OFFRE VALUES ( NULL ,'"+tfnom.getText()+ "','" + String.valueOf(tfdated.getValue()) + "','" +String.valueOf(tfdatef.getValue())+ "','" +tfdescription.getText()+ "','" +tfimg.getText()+ "','" +tfcouleur.getText()+ "')";
        PreparedStatement pst = cnxs.prepareStatement(requete);
          pst.executeUpdate();
            System.out.println("votre offre est ajoutée avec succees ");
                } catch (SQLException ex) {
                      System.err.println(ex.getMessage());
        }
        afficherOffre();
    }

    public void modifierOffre(){
        try {
            String requete = "UPDATE OFFRE SET nomoffre = '"+tfnom.getText()+ "',datedebut ='" 
                    + String.valueOf(tfdated.getValue()) + "',datefin ='" +String.valueOf(tfdatef.getValue())+  "',description ='"+tfdescription.getText()+"',imgsrc ='"+tfimg.getText()+"',couleur ='" 
                    +tfcouleur.getText()+ "'WHERE id =" + tfid.getText()+"";
        PreparedStatement pst = cnxs.prepareStatement(requete);
          pst.executeUpdate();
            System.out.println("votre offre est modifier avec succees ");
                } catch (SQLException ex) {
                      System.err.println(ex.getMessage());
        }
        afficherOffre();
    }
    
    public void effacerOffre(){
        try {
            String requete = "DELETE FROM OFFRE WHERE id = "+tfid.getText()+"";
        PreparedStatement pst = cnxs.prepareStatement(requete);
          pst.executeUpdate();
            System.out.println("votre offre est modifier avec succees ");
                } catch (SQLException ex) {
                      System.err.println(ex.getMessage());
        }
        afficherOffre();
    }






}
