/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Controllers;

import Esprit.Projet.Connexion.DbConnect;
import Esprit.Projet.Connexion.MaConnexion;
import Esprit.Projet.Entities.Offre;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author ahmed
 */
public class OffreCRUDController implements Initializable {
    
    private Label label;
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
    
    Offre offretable =null;
    int idof ;
    @FXML
    private TableColumn<Offre, String> editCol;
    @FXML
    private ImageView excel;
    
    private FileInputStream fis;
    
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
    
//    ================================ bouton modifier et supprimer
    //add cell of button edit 
         Callback<TableColumn<Offre, String>, TableCell<Offre, String>> cellFoctory = (TableColumn<Offre, String> param) -> {
            // make cell containing buttons
            final TableCell<Offre, String> cell = new TableCell<Offre, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#ff1744;"
                        );
                        editIcon.setStyle(
                                " -fx-cursor: hand ;"
                                + "-glyph-size:28px;"
                                + "-fx-fill:#00E676;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            try {
                                offretable = tvoffre.getSelectionModel().getSelectedItem();
                                String requete = "DELETE FROM OFFRE WHERE id = "+offretable.getId()+"";
                                PreparedStatement pst = cnxs.prepareStatement(requete);
                                pst.executeUpdate();
                                afficherOffre();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                           

                          

                        });
                            
                         
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            offretable = tvoffre.getSelectionModel().getSelectedItem();
                            setTextField(offretable.getId(),offretable.getNomoffre()
                                    ,offretable.getDatedebut().toLocalDate(),offretable.getDatefin().toLocalDate(),offretable.getDescription(), offretable.getImgsrc(), offretable.getCouleur());
                            });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
         editCol.setCellFactory(cellFoctory);
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
                    +tfcouleur.getText()+ "'WHERE id =" + offretable.getId()+"";
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




void setTextField(int id, String nomoffre, LocalDate datedebut,LocalDate datefin, String description, String image,String couleur) {

        idof = id;
        tfnom.setText(nomoffre);
        tfdated.setValue(datedebut);
        tfdatef.setValue(datefin);
        tfdescription.setText(description);
        tfimg.setText(image);
        
        tfcouleur.setText(couleur);

    }






    @FXML
    private void PDF(MouseEvent event) throws SQLException, FileNotFoundException, IOException {    

        try {
       com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
       PdfWriter.getInstance(doc,new FileOutputStream("C:\\Users\\ahmed\\OneDrive\\Desktop\\Offre.pdf"));  
       doc.open();
       
    doc.add(new Paragraph(" "));
       
       Paragraph p = new Paragraph("liste des Offres  ");
       p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       doc.add(p);
       doc.add(new Paragraph(" "));
       doc.add(new Paragraph(" "));

       PdfPTable tabpdf = new PdfPTable(4);
       tabpdf.setWidthPercentage(100);
       
       PdfPCell cell;
       cell = new PdfPCell(new Phrase("Non_Offre"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("Date_debut"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("Date_Fin"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("Description"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       
       
       String requete = "SELECT * FROM offre";
       Statement st = cnxs.createStatement();
       ResultSet rs = st.executeQuery(requete);
          
      while (rs.next()) {
           cell = new PdfPCell(new Phrase(rs.getString("nomoffre")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
           cell = new PdfPCell(new Phrase(rs.getString("datedebut")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           cell = new PdfPCell(new Phrase(rs.getString("datefin")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           cell = new PdfPCell(new Phrase(rs.getString("description")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
       }
     
   
          doc.add(tabpdf);
          JOptionPane.showMessageDialog(null, "Votre fichier a ete exporter avec succes");
          doc.close();
          Desktop.getDesktop().open(new File("C:\\Users\\ahmed\\OneDrive\\Desktop\\Offre.pdf"));
       }
 
        catch (DocumentException | HeadlessException e) {
            System.out.println("ERROR PDF");
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
          }



}
}