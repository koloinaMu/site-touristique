package com.example.asus.madagascar.modele;

import org.w3c.dom.Text;

/**
 * Created by ASUS on 23/07/2023.
 */

public class Site {
   private int id;
   private String nom;
   private Text description;
   private String region;
   private int idMedia;
   private String urlMedia;
   private String descriptionMedia;
   private String typeMedia;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Text getDescription() {
        return description;
    }

    public void setDescription(Text description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }

    public String getUrlMedia() {
        return urlMedia;
    }

    public void setUrlMedia(String urlMedia) {
        this.urlMedia = urlMedia;
    }

    public String getDescriptionMedia() {
        return descriptionMedia;
    }

    public void setDescriptionMedia(String descriptionMedia) {
        this.descriptionMedia = descriptionMedia;
    }

    public String getTypeMedia() {
        return typeMedia;
    }

    public void setTypeMedia(String typeMedia) {
        this.typeMedia = typeMedia;
    }

    public Site(int id, String nom, Text description, String region, int idMedia, String urlMedia, String descriptionMedia, String typeMedia) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.region = region;
        this.idMedia = idMedia;
        this.urlMedia = urlMedia;
        this.descriptionMedia = descriptionMedia;
        this.typeMedia = typeMedia;
    }
}
