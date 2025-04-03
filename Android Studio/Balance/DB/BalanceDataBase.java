package com.pandart.balance.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pandart.balance.Objet.Mesure;
import com.pandart.balance.Objet.User;
import com.pandart.balance.Page.BoardActivity;
import com.pandart.balance.Page.SaisiMesureActivity;

import java.util.ArrayList;

public class BalanceDataBase extends SQLiteOpenHelper {


    private Context context;
    private String nom;
    private int version;

    private ArrayList<User> ListeUser = new ArrayList<>();
    private ArrayList<Mesure> ListeMesure = new ArrayList<>();

    public BalanceDataBase(@Nullable Context context) {
        super(context, "Balance", null, 3);
        this.context = context;
        this.nom = "Balance";
        this.version = 3;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User( " +
                    "Pseudo varchar(15) PRIMARY KEY,"+
                    "Password varchar(15),"+
                    "Nom varchar(30),"+
                    "Prenom varchar(30),"+
                    "Image blob,"+
                    "Age varchar(3),"+
                    "Sexe varchar(1)," +
                    "Taille varchar(3)," +
                    "Objectif varchar(4))" );

        db.execSQL("CREATE TABLE Mesure( " +
                    "Pseudo varchar(15), "+
                    "Jour int(2),"+
                    "Mois int (2),"+
                    "Annee int (4),"+
                    "Poids varchar(4),"+
                    "Mass_Grais varchar(4),"+
                    "Calorie varchar(4),"+
                    "Mass_Musc varchar(4),"+
                    "Mass_Oss varchar(4)," +
                    "IMC varchar(4)," +
                    "Mass_hydr varchar(4)," +
                    "PRIMARY KEY (Pseudo,Jour,Mois,Annee))" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        ListeUser = selectTotalUser();
        ListeMesure = selectTotalMesure();
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Mesure");
        onCreate(db);
        for (User user:ListeUser){
            addUser(user);
        }
        for (Mesure mesure:ListeMesure){
            addMesure(mesure);
        }
    }

    //------------------------------------------ Table User ------------------------------------------ //

    // Pour ajouter une Boisson dans la table
    public boolean addUser(User user){

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getWritableDatabase();

        //Création d'une variable contenant ce qu'on veux inserer
        ContentValues valeur = new ContentValues();
        valeur.put("Pseudo", user.getPseudo());
        valeur.put("Password", user.getPassword());
        valeur.put("Nom", user.getNom());
        valeur.put("Prenom", user.getPrenom());
        valeur.put("Image", user.getImage());
        valeur.put("Age", user.getAge());
        valeur.put("Sexe", user.getSexe());
        valeur.put("Taille", user.getTaille());
        valeur.put("Objectif", user.getObjectif());

        //Lance l'insert dans la table Boisson
        long resultat = db.insert("User",null,valeur);

        db.close();
        return resultat != -1;
    }

    //Retourne la liste de tous les user
    public ArrayList selectTotalUser(){

        ArrayList listUser = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM User";
        curseur = db.rawQuery(requete, null);

        //On se place au 1er enreg puis jusqu'à la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme des user récupérés
            @SuppressLint("Range") User userSelect = new User(
                    curseur.getString(curseur.getColumnIndex("Pseudo")),
                    curseur.getString(curseur.getColumnIndex("Password")),
                    curseur.getString(curseur.getColumnIndex("Nom")),
                    curseur.getString(curseur.getColumnIndex("Prenom")),
                    curseur.getBlob(curseur.getColumnIndex("Image")),
                    curseur.getInt(curseur.getColumnIndex("Age")),
                    curseur.getString(curseur.getColumnIndex("Sexe")),
                    curseur.getInt(curseur.getColumnIndex("Taille")),
                    curseur.getInt(curseur.getColumnIndex("Objectif"))
            );
            //Ajout de la boisson a la liste
            listUser.add(userSelect);

            curseur.moveToNext();
        }
        db.close();
        return listUser;
    }

    //Retourne la liste de tous les user
    @SuppressLint("Range")
    public User selectUser(String pseudo){

        User user = null;
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM User " +
                        "WHERE Pseudo = '" + pseudo +"' " ;

        curseur = db.rawQuery(requete, null);

        //On se place au 1er enreg puis jusqu'à la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            user = new User(
                    curseur.getString(curseur.getColumnIndex("Pseudo")),
                    curseur.getString(curseur.getColumnIndex("Password")),
                    curseur.getString(curseur.getColumnIndex("Nom")),
                    curseur.getString(curseur.getColumnIndex("Prenom")),
                    curseur.getBlob(curseur.getColumnIndex("Image")),
                    curseur.getInt(curseur.getColumnIndex("Age")),
                    curseur.getString(curseur.getColumnIndex("Sexe")),
                    curseur.getInt(curseur.getColumnIndex("Taille")),
                    curseur.getInt(curseur.getColumnIndex("Objectif"))
            );
            curseur.moveToNext();
        }
        db.close();
        return user;
    }

    public boolean modifUser(User User){

        //Variable de travail
        long resultat;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues valeur = new ContentValues();
        valeur.put("Pseudo", User.getPseudo());
        valeur.put("Password", User.getPassword());
        valeur.put("Nom", User.getNom());
        valeur.put("Prenom", User.getPrenom());
        valeur.put("Image", User.getImage());
        valeur.put("Age", User.getAge());
        valeur.put("Sexe", User.getSexe());
        valeur.put("Taille", User.getTaille());
        valeur.put("Objectif", User.getObjectif());

        resultat= db.update("User", valeur, "Pseudo=?",
                new String[]{String.valueOf(User.getPseudo())});

        db.close();
        return resultat != -1;

    }

    public void DeleteUser(String Pseudo){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE From User Where Pseudo = " + "'" + Pseudo + "'");
        db.close();
    }

    public void DeleteTotalUser(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE From User");
        db.close();
    }


    //------------------------------------------ Table Mesure ------------------------------------------ //

    // Pour ajouter une Mesure dans la table
    public boolean addMesure(Mesure Mesure){

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getWritableDatabase();

        //Valorisation par defaut à 000
        if(Mesure.getPoids().isEmpty()){
            Mesure.setPoids("000");
        }
        if(Mesure.getMass_Grais().isEmpty()){
            Mesure.setMass_Grais("000");
        }
        if(Mesure.getCalorie().isEmpty()){
            Mesure.setCalorie("000");
        }
        if(Mesure.getMass_Musc().isEmpty()){
            Mesure.setMass_Musc("000");
        }
        if(Mesure.getMass_Oss().isEmpty()){
            Mesure.setMass_Oss("000");
        }
        if(Mesure.getMass_hydr().isEmpty()){
            Mesure.setMass_hydr("000");
        }
        if(Mesure.getIMC().isEmpty()){
            Mesure.setIMC("000");
        }

        //Création d'une variable contenant ce qu'on veux inserer
        ContentValues valeur = new ContentValues();
        valeur.put("Pseudo", Mesure.getPseudo());
        valeur.put("Jour", Mesure.getJour());
        valeur.put("Mois", Mesure.getMois());
        valeur.put("Annee", Mesure.getAnnee());
        valeur.put("Poids", Mesure.getPoids());
        valeur.put("Mass_Grais", Mesure.getMass_Grais());
        valeur.put("Calorie", Mesure.getCalorie());
        valeur.put("Mass_Musc", Mesure.getMass_Musc());
        valeur.put("Mass_Oss", Mesure.getMass_Oss());
        valeur.put("IMC", Mesure.getIMC());
        valeur.put("Mass_hydr", Mesure.getMass_hydr());

        //Lance l'insert dans la table Boisson
        long resultat = db.insert("Mesure",null,valeur);

        db.close();
        return resultat != -1;
    }


    //Retourne la liste de toutes les mesures
    public ArrayList selectTotalMesure(){

        ArrayList listMesure = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM Mesure";
        curseur = db.rawQuery(requete, null);

        //On se place au 1er enreg puis jusqu'à la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme des Mesure récupérés
            @SuppressLint("Range") Mesure MesureSelect = new Mesure(
                    curseur.getString(curseur.getColumnIndex("Pseudo")),
                    curseur.getColumnIndex("Jour"),
                    curseur.getColumnIndex("Mois"),
                    curseur.getColumnIndex("Annee"),
                    curseur.getString(curseur.getColumnIndex("Poids")),
                    curseur.getString(curseur.getColumnIndex("Mass_Grais")),
                    curseur.getString(curseur.getColumnIndex("Calorie")),
                    curseur.getString(curseur.getColumnIndex("Mass_Musc")),
                    curseur.getString(curseur.getColumnIndex("Mass_Oss")),
                    curseur.getString(curseur.getColumnIndex("IMC")),
                    curseur.getString(curseur.getColumnIndex("Mass_hydr"))
            );
            //Ajout de la boisson a la liste
            listMesure.add(MesureSelect);

            curseur.moveToNext();
        }
        db.close();
        return listMesure;
    }


    // Lecture d'une Mesure sur un jour
    @SuppressLint("Range")
    public Mesure MeusureDuJour(String pseudo, int jour, int mois, int annee){

        //Variable de travail
        Mesure mesure = null;
        
        // Création du curseur
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM Mesure " +
                            "WHERE Pseudo = '" + pseudo +"' " +
                            "AND Jour = "+ jour + " " +
                            "AND Mois = "+ mois + " " +
                            "AND Annee = "+ annee ;

        curseur = db.rawQuery(requete, null);

        //On se place au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Ajout de la boisson a la liste
            mesure = new Mesure(curseur.getString(curseur.getColumnIndex("Pseudo")),
                    curseur.getInt(curseur.getColumnIndex("Jour")),
                    curseur.getInt(curseur.getColumnIndex("Mois")),
                    curseur.getInt(curseur.getColumnIndex("Annee")),
                    curseur.getString(curseur.getColumnIndex("Poids")),
                    curseur.getString(curseur.getColumnIndex("Mass_Grais")),
                    curseur.getString(curseur.getColumnIndex("Calorie")),
                    curseur.getString(curseur.getColumnIndex("Mass_Musc")),
                    curseur.getString(curseur.getColumnIndex("Mass_Oss")),
                    curseur.getString(curseur.getColumnIndex("IMC")),
                    curseur.getString(curseur.getColumnIndex("Mass_hydr"))
            );
            curseur.moveToNext();
        }
        db.close();
        return mesure;
    }


    // Lecture de Mesure sur un mois
    public ArrayList MeusureDuMois(String pseudo, int mois, int annee){

        // Liste renvoyée en sortie
        ArrayList listMesure = new ArrayList<>();

        // Création du curseur
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM Mesure " +
                "WHERE Pseudo = '" + pseudo +"'" +
                "AND Mois =  "+ mois +" "+
                "AND Annee = "+ annee + " " +
                "ORDER by Jour asc" ;

        curseur = db.rawQuery(requete, null);

        //On se palce au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Ajout de la boisson a la liste
            @SuppressLint("Range") Mesure mesure = new Mesure(curseur.getString(curseur.getColumnIndex("Pseudo")),
                    curseur.getInt(curseur.getColumnIndex("Jour")),
                    curseur.getInt(curseur.getColumnIndex("Mois")),
                    curseur.getInt(curseur.getColumnIndex("Annee")),
                    curseur.getString(curseur.getColumnIndex("Poids")),
                    curseur.getString(curseur.getColumnIndex("Mass_Grais")),
                    curseur.getString(curseur.getColumnIndex("Calorie")),
                    curseur.getString(curseur.getColumnIndex("Mass_Musc")),
                    curseur.getString(curseur.getColumnIndex("Mass_Oss")),
                    curseur.getString(curseur.getColumnIndex("IMC")),
                    curseur.getString(curseur.getColumnIndex("Mass_hydr"))
            );

            //Ajout de la boisson a la liste
            listMesure.add(mesure);

            curseur.moveToNext();
        }
        db.close();
        return listMesure;
    }

    public boolean modifMesure(Mesure Mesure){

        //Variable de travail
        long resultat;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getWritableDatabase();

        //Valorisation par defaut à 000
        if(Mesure.getPoids().isEmpty()){
            Mesure.setPoids("000");
        }
        if(Mesure.getMass_Grais().isEmpty()){
            Mesure.setMass_Grais("000");
        }
        if(Mesure.getCalorie().isEmpty()){
            Mesure.setCalorie("000");
        }
        if(Mesure.getMass_Musc().isEmpty()){
            Mesure.setMass_Musc("000");
        }
        if(Mesure.getMass_Oss().isEmpty()){
            Mesure.setMass_Oss("000");
        }
        if(Mesure.getMass_hydr().isEmpty()){
            Mesure.setMass_hydr("000");
        }
        if(Mesure.getIMC().isEmpty()){
            Mesure.setIMC("000");
        }

        ContentValues valeur = new ContentValues();
        valeur.put("Pseudo", Mesure.getPseudo());
        valeur.put("Jour", Mesure.getJour());
        valeur.put("Mois", Mesure.getMois());
        valeur.put("Annee", Mesure.getAnnee());
        valeur.put("Poids", Mesure.getPoids());
        valeur.put("Mass_Grais", Mesure.getMass_Grais());
        valeur.put("Calorie", Mesure.getCalorie());
        valeur.put("Mass_Musc", Mesure.getMass_Musc());
        valeur.put("Mass_Oss", Mesure.getMass_Oss());
        valeur.put("IMC", Mesure.getIMC());
        valeur.put("Mass_hydr", Mesure.getMass_hydr());

        resultat= db.update("Mesure", valeur, "Pseudo=? AND Jour=? AND Mois=? AND Annee=? ",
                new String[]{String.valueOf(Mesure.getPseudo()),String.valueOf(Mesure.getJour()),String.valueOf(Mesure.getMois()),String.valueOf(Mesure.getAnnee())});

        db.close();
        return resultat != -1;

    }

    public void DeleteMesureUser(String Pseudo){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE From Mesure Where Pseudo = " + "'" + Pseudo + "'");
        db.close();
    }

    public void DeleteTotalMesure(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE From Mesure");
        db.close();
    }


}
