/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ahmed
 */
public class MaConnexion {
    public String url="jdbc:mysql://localhost:3306/piprojet";
    public String login="root";
    public String pwd= "";
    Connection cnx;
    public static MaConnexion instance;
    
    private  MaConnexion() {
        try {
           cnx = DriverManager.getConnection(url, login, pwd);
           System.out.println("connexion etablie !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


public Connection getCnx(){
    return cnx;
}


public static MaConnexion getInstance(){
    if(instance == null){
        instance = new MaConnexion();
    }
    return instance;
}






}
