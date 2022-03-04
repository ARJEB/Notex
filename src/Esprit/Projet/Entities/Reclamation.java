/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Entities;

import java.sql.Date;

/**
 *
 * @author hocin
 */
public class Reclamation {
    
    
   private int id  ;
   private String typereclamation ;
   private Date datereclamation	 ;
   private String description ,etat ;

    public Reclamation() {
    }

    public Reclamation(int id, String typereclamation, Date datereclamation, String description, String etat) {
        this.id = id;
        this.typereclamation = typereclamation;
        this.datereclamation = datereclamation;
        this.description = description;
        this.etat = etat;
    }

    public Reclamation(String typereclamation, Date datereclamation, String description, String etat) {
        this.typereclamation = typereclamation;
        this.datereclamation = datereclamation;
        this.description = description;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public String getTypereclamation() {
        return typereclamation;
    }

    public Date getDatereclamation() {
        return datereclamation;
    }

    public String getDescription() {
        return description;
    }

    public String getEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTypereclamation(String typereclamation) {
        this.typereclamation = typereclamation;
    }

    public void setDatereclamation(Date datereclamation) {
        this.datereclamation = datereclamation;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

  
    
}
