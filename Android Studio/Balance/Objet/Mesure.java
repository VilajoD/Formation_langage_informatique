package com.pandart.balance.Objet;

import androidx.annotation.Nullable;

import java.util.Date;

public class Mesure {

    private String Pseudo;
    private int Jour;
    private int Mois;
    private int Annee;
    private String Poids;
    private String Mass_Grais;
    private String Calorie;
    private String Mass_Musc;
    private String Mass_Oss;
    private String IMC;
    private String Mass_hydr;

    public Mesure(String pseudo, int jour, int mois, int annee, @Nullable String poids, @Nullable String mass_Grais, @Nullable String calorie, @Nullable String mass_Musc, @Nullable String mass_Oss, @Nullable String imc, @Nullable String mass_hydr){
        Pseudo = pseudo;
        Jour = jour;
        Mois = mois;
        Annee = annee;
        Poids = poids;
        Mass_Grais = mass_Grais;
        Calorie = calorie;
        Mass_Musc = mass_Musc;
        Mass_Oss = mass_Oss;
        IMC = imc;
        Mass_hydr= mass_hydr;
    }

    public String getPseudo() {
        return Pseudo;
    }
    public void setPseudo(String pseudo) {
        Pseudo = pseudo;
    }

    public int getJour() {
        return Jour;
    }
    public void setJour(int jour) {
        Jour = jour;
    }


    public int getMois() {
        return Mois;
    }
    public void setMois(int mois) {
        Mois = mois;
    }


    public int getAnnee() {
        return Annee;
    }
    public void setAnnee(int annee) {
        Annee = annee;
    }


    public String getPoids() {
        return Poids;
    }
    public void setPoids(String poids) { Poids = poids; }

    public String getMass_Grais() {
        return Mass_Grais;
    }
    public void setMass_Grais(String mass_Grais) {
        Mass_Grais = mass_Grais;
    }


    public String getCalorie() {
        return Calorie;
    }
    public void setCalorie(String calorie) {
        Calorie = calorie;
    }


    public String getMass_Musc() {
        return Mass_Musc;
    }
    public void setMass_Musc(String mass_Musc) {
        Mass_Musc = mass_Musc;
    }


    public String getMass_Oss() {
        return Mass_Oss;
    }
    public void setMass_Oss(String mass_Oss) {
        Mass_Oss = mass_Oss;
    }


    public String getIMC() {
        return IMC;
    }
    public void setIMC(String IMC) {
        this.IMC = IMC;
    }


    public String getMass_hydr() {
        return Mass_hydr;
    }
    public void setMass_hydr(String mass_hydr) {
        Mass_hydr = mass_hydr;
    }
}
