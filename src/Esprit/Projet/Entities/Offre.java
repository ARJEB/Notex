/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Esprit.Projet.Entities;

import java.sql.Date;



/**
 *
 * @author ahmed
 */
public class Offre {
    private int id;
    private String nomoffre;
    private Date datedebut;
    private Date datefin;
    private String description;
    private String imgsrc;
    private String couleur;
    

    public Offre() {
    }

    public Offre(int id, String nomoffre, Date datedebut, Date datefin, String description, String imgsrc, String couleur) {
        this.id = id;
        this.nomoffre = nomoffre;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.description = description;
        this.imgsrc = imgsrc;
        this.couleur = couleur;
    }

    public Offre(String nomoffre, Date datedebut, Date datefin, String description, String imgsrc, String couleur) {
        this.nomoffre = nomoffre;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.description = description;
        this.imgsrc = imgsrc;
        this.couleur = couleur;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public String getCouleur() {
        return couleur;
    }

    
    

    
    

    public int getId() {
        return id;
    }

    public String getNomoffre() {
        return nomoffre;
    }

    public Date getDatedebut() {
        return datedebut;
    }

    public Date getDatefin() {
        return datefin;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomoffre(String nomoffre) {
        this.nomoffre = nomoffre;
    }

    public void setDatedebut(Date datedebut) {
        this.datedebut = datedebut;
    }

    public void setDatefin(Date datefin) {
        this.datefin = datefin;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Offre{" + "id=" + id + ", nomoffre=" + nomoffre + ", datedebut=" + datedebut + ", datefin=" + datefin + ", description=" + description + '}';
    }
    














}

