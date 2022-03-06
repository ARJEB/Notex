/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Controllers;

import Esprit.Projet.Connexion.DbConnect;
import Esprit.Projet.Connexion.MaConnexion;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import Esprit.Projet.Entities.Reclamation;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hocin
 */
public class TableViewController implements Initializable {

    @FXML
    private TableView<Reclamation> reclamationTABLE;
    @FXML
    private TableColumn<Reclamation, String> idCol;
    @FXML
    private TableColumn<Reclamation, String> typereclamationCol;
    @FXML
    private TableColumn<Reclamation, String> datereclamationCol;
    @FXML
    private TableColumn<Reclamation, String> descriptionCol;
    @FXML
    private TableColumn<Reclamation, String> etatCol;
    @FXML
    private TableColumn<Reclamation, String> editCol;
    
    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Reclamation reclamation = null ;
    
    ObservableList<Reclamation>  Reclamationlist = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
    }    
    
    
    Connection cnxs;
    public TableViewController() {
        cnxs = MaConnexion.getInstance().getCnx();
        
    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Esprit/Projet/Views/addReclamation.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void refreshTable() {
        try {
            Reclamationlist.clear();
            
            query = "SELECT * FROM `reclamation`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()){
                Reclamationlist.add(new  Reclamation(
                        resultSet.getInt("id"),
                        resultSet.getString("typereclamation"),
                        resultSet.getDate("datereclamation"),
                        resultSet.getString("description"),
                        resultSet.getString("etat")));
                reclamationTABLE.setItems(Reclamationlist);
                
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }

    

    private void loadDate() {
        
        connection = DbConnect.getConnect();
        refreshTable();
        
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        typereclamationCol.setCellValueFactory(new PropertyValueFactory<>("typereclamation"));
        datereclamationCol.setCellValueFactory(new PropertyValueFactory<>("datereclamation"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        etatCol.setCellValueFactory(new PropertyValueFactory<>("etat"));
        
        //add cell of button edit 
         Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFoctory = (TableColumn<Reclamation, String> param) -> {
            // make cell containing buttons
            final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {
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
                                reclamation = reclamationTABLE.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `reclamation` WHERE id  ="+reclamation.getId();
                                connection = DbConnect.getConnect();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();
                                
                            } catch (SQLException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                           

                          

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            
                            reclamation = reclamationTABLE.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("/Esprit/Projet/Views/addReclamation.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                            AddReclamationController AddReclamationController = loader.getController();
                            AddReclamationController.setUpdate(true);
                            AddReclamationController.setTextField(reclamation.getId(), reclamation.getTypereclamation(), 
                                    reclamation.getDatereclamation().toLocalDate(),reclamation.getDescription(), reclamation.getEtat());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                            

                           

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
        reclamationTABLE.setItems(Reclamationlist);
         
         
    }
       @FXML
    public void retour(MouseEvent event) {
        try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/Esprit/Projet/Views/Home.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.out.println("err");
            }
    
    
   }

    







@FXML
    public void print(MouseEvent event) throws SQLException, FileNotFoundException, IOException {    

        try {
       com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
       PdfWriter.getInstance(doc,new FileOutputStream("C:\\Users\\ahmed\\OneDrive\\Desktop\\Reclamation.pdf"));  
       doc.open();
       
    doc.add(new Paragraph(" "));
       
       Paragraph p = new Paragraph("liste des Reclamation  ");
       p.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       doc.add(p);
       doc.add(new Paragraph(" "));
       doc.add(new Paragraph(" "));

       PdfPTable tabpdf = new PdfPTable(5);
       tabpdf.setWidthPercentage(100);
       
       PdfPCell cell;
       cell = new PdfPCell(new Phrase("Id"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("Type_Reclamation"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("Date_Reclamation"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       cell = new PdfPCell(new Phrase("Description"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       cell = new PdfPCell(new Phrase("Etat_Reclamation"));
       cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
       cell.setBackgroundColor(BaseColor.WHITE);
       tabpdf.addCell(cell);
       
       
       
       String requete = "SELECT * FROM reclamation";
       Statement st = cnxs.createStatement();
       ResultSet rs = st.executeQuery(requete);
          
      while (rs.next()) {
           cell = new PdfPCell(new Phrase(rs.getString("id")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
           cell = new PdfPCell(new Phrase(rs.getString("typereclamation")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           cell = new PdfPCell(new Phrase(rs.getString("datereclamation")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           cell = new PdfPCell(new Phrase(rs.getString("description")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           cell = new PdfPCell(new Phrase(rs.getString("etat")));
           cell.setHorizontalAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
           cell.setBackgroundColor(BaseColor.WHITE);
           tabpdf.addCell(cell);
           
       }
     
   
          doc.add(tabpdf);
          JOptionPane.showMessageDialog(null, "Votre fichier a ete exporter avec succes");
          doc.close();
          Desktop.getDesktop().open(new File("C:\\Users\\ahmed\\OneDrive\\Desktop\\Reclamation.pdf"));
       }
 
        catch (DocumentException | HeadlessException e) {
            System.out.println("ERROR PDF");
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
          }


}

}
    
   
    
    
    
    
    
    
    
  



    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    




























    