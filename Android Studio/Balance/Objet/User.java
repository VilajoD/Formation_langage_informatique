package com.pandart.balance.Objet;

import androidx.annotation.Nullable;

public class User {

    private String Pseudo;
    private String Password;
    private String Nom;
    private String Prenom;
    private byte[] Image;
    private int Age;
    private String Sexe;
    private int Taille;
    private int Objectif;

    public User(String pseudo, @Nullable String password, @Nullable String nom, @Nullable String prenom, @Nullable byte[] image, @Nullable int age, @Nullable String sexe, @Nullable int taille, @Nullable int objectif){
        Pseudo = pseudo;
        Password = password;
        Nom = nom;
        Prenom = prenom;
        Image = image;
        Age = age;
        Sexe = sexe;
        Taille = taille;
        Objectif = objectif;
    }


    public String getPseudo() {
        return Pseudo;
    }
    public void setPseudo(String pseudo) {
        Pseudo = pseudo;
    }


    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }


    public String getNom() {
        return Nom;
    }
    public void setNom(String nom) {
        Nom = nom;
    }


    public String getPrenom() {
        return Prenom;
    }
    public void setPrenom(String prenom) {
        Prenom = prenom;
    }


    public byte[] getImage() {
        return Image;
    }
    public void setImage(byte[] image) {
        Image = image;
    }


    public int getAge() {
        return Age;
    }
    public void setAge(int age) {
        Age = age;
    }


    public String getSexe() {
        return Sexe;
    }
    public void setSexe(String sexe) {
        Sexe = sexe;
    }


    public int getTaille() {
        return Taille;
    }
    public void setTaille(int taille) {
        Taille = taille;
    }


    public int getObjectif() {
        return Objectif;
    }
    public void setObjectif(int objectif) {
        Objectif = objectif;
    }

}
