package com.example.cafth.objet;

import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class Boisson {
    private int Clé;
    private String Type;
    private String Categorie;
    private String Nom;
    private int Quantite;
    private byte[] Image;
    private String Description;


    public Boisson(int clé, String type, @Nullable String categorie, String nom, int quantite,byte[] image, @Nullable String description) {
        Clé = clé;
        Type = type;
        Categorie = categorie;
        Nom = nom;
        Quantite = quantite;
        Image = image;
        Description = description;
    }

  /*  public Boisson(int clé, String type, @Nullable String categorie, String nom, int quantite) {
        Clé = clé;
        Type = type;
        Categorie = categorie;
        Nom = nom;
        Quantite = quantite;
        Image = null;
    }*/

    public Boisson(String type, @Nullable String categorie, String nom, int quantite,byte[] image, @Nullable String description) {
        Type = type;
        Categorie = categorie;
        Nom = nom;
        Quantite = quantite;
        Image = image;
        Description = description;
    }


   /* public Boisson(String type, @Nullable String categorie, String nom, int quantite) {
        Type = type;
        Categorie = categorie;
        Nom = nom;
        Quantite = quantite;
        Image = null;
    }*/

    public int getClé() {
        return Clé;
    }

    public void setClé(int clé) {
        Clé = clé;
    }



    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }



    public String getCategorie() {
        return Categorie;
    }
    public void setCategorie(String categorie) {
        Categorie = categorie;
    }



    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }



    public int getQuantite() {
        return Quantite;
    }

    public void setQuantite(int quantite) {
        Quantite = quantite;
    }



    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


}
