package com.example.cafth.objet;

public class Categorie {
    private int Clé;
    private String Type;


    public Categorie(int clé, String type, String categorie) {
        Clé = clé;
        Type = type;
        Categorie = categorie;

    }

    public Categorie(String type, String categorie) {
        Type = type;
        Categorie = categorie;
    }

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

    private String Categorie;

}
