package com.example.madagascar.model;

import org.w3c.dom.Text;

public class Site {
   private String id;
   private String nom;
   private String description;
   private String region;
   private int idMedia;
   private String urlMedia;
   private String descriptionMedia;
   private String typeMedia;
   private String imagePosteur;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getNom() {
      return nom;
   }

   public void setNom(String nom) {
      this.nom = nom;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
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

   public String getImagePosteur() {
      return imagePosteur;
   }

   public void setImagePosteur(String imagePosteur) {
      this.imagePosteur = imagePosteur;
   }

   public String getTypeMedia() {
      return typeMedia;
   }

   public void setTypeMedia(String typeMedia) {
      this.typeMedia = typeMedia;
   }

   public Site(String id, String nom, String description, String region, int idMedia, String urlMedia, String descriptionMedia, String typeMedia) {
      this.id = id;
      this.nom = nom;
      this.description = description;
      this.region = region;
      this.idMedia = idMedia;
      this.urlMedia = urlMedia;
      this.descriptionMedia = descriptionMedia;
      this.typeMedia = typeMedia;
   }

   public Site(String nom, String description, String region, int idMedia, String urlMedia, String descriptionMedia, String typeMedia) {
      this.nom = nom;
      this.description = description;
      this.region = region;
      this.idMedia = idMedia;
      this.urlMedia = urlMedia;
      this.descriptionMedia = descriptionMedia;
      this.typeMedia = typeMedia;
   }

   public Site(String nom, String description) {
      this.nom = nom;
      this.description = description;
   }

   public Site(String nom, String description, String imagePosteur) {
      this.nom = nom;
      this.description = description;
      this.imagePosteur = imagePosteur;
   }

   public Site() {
   }

   @Override
   public String toString() {
      return "Site{" +
              "id=" + id +
              ", nom='" + nom + '\'' +
              ", description='" + description + '\'' +
              ", region='" + region + '\'' +
              '}';
   }
}
