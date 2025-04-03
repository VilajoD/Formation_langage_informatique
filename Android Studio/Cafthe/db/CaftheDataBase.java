package com.example.cafth.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.cafth.objet.Boisson;
import com.example.cafth.objet.Categorie;
import com.example.cafth.page.GestionBoissonActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CaftheDataBase extends SQLiteOpenHelper {

    private Context context;
    private String nom;
    private int version;

    public CaftheDataBase(@Nullable Context context) {
        super(context, "Cafthe", null, 8);
    //public CaftheDataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    //    super(context, name, factory, version);
        this.context = context;
        this.nom = "Cafthe";
        this.version =8;

    }


    //Création des tables de la bases si non existant
    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(" CREATE TABLE boisson( " +
                 "Clé integer PRIMARY KEY AUTOINCREMENT,"+
                 "Type varchar(30),"+
                 "Categorie varchar(30),"+
                 "Nom varchar(50),"+
                 "Quantite integer," +
                 "Image blob,"+
                 "Description varchar(500)," +
                 "Stockage varchar(70))");

    db.execSQL(" CREATE TABLE categorie( " +
                 "Clé integer PRIMARY KEY AUTOINCREMENT,"+
                 "Type varchar(30),"+
                 "Categorie varchar(30))");
    }


    //Si nouvelle version de la base, on recrer la base correctement.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS boisson");
    db.execSQL("DROP TABLE IF EXISTS categorie");
    onCreate(db);
    }


    //------------------------------------------ Table Boisson ------------------------------------------ //

    // Pour ajouter une Boisson dans la table
    public boolean addBoisson(Boisson boisson){ //

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getWritableDatabase();

        //Création d'une variable contenant ce qu'on veux inserer
        ContentValues valeur = new ContentValues();
        valeur.put("Type", boisson.getType());
        valeur.put("Categorie", boisson.getCategorie());
        valeur.put("Nom", boisson.getNom());
        valeur.put("Quantite", boisson.getQuantite());
        valeur.put("Image", boisson.getImage());
        valeur.put("Description", boisson.getDescription());

        //Lance l'insert dans la table Boisson
        long resultat = db.insert("boisson",null,valeur);
                
        db.close();
        return resultat != -1;
    }

    //Retourne la liste de toutes les boissons
    public ArrayList selectTotalBoisson(){

        ArrayList listBoisson = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM boisson ORDER BY Type, Categorie, Nom";
        curseur = db.rawQuery(requete, null);

        //On se palce au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme de la boisson récupéré
            @SuppressLint("Range") Boisson boissonSelect = new Boisson(curseur.getInt(curseur.getColumnIndex("Clé")),
                    curseur.getString(curseur.getColumnIndex("Type")),
                    curseur.getString(curseur.getColumnIndex("Categorie")),
                    curseur.getString(curseur.getColumnIndex("Nom")),
                    curseur.getInt(curseur.getColumnIndex("Quantite")),
                    curseur.getBlob(curseur.getColumnIndex("Image")),
                    curseur.getString(curseur.getColumnIndex("Description"))
            );
            //Ajout de la boisson a la liste
            listBoisson.add(boissonSelect);

            curseur.moveToNext();
        }
        db.close();
        return listBoisson;
    }

    //Permet de retourner une boisson
    public ArrayList<Boisson> selectBoissonUnitaire(Boisson boisson){

        ArrayList listBoisson = new ArrayList<>();
        Cursor curseur;
        String requete;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        if (boisson.getCategorie() != null){
            //On créer la requete avec catégorie
             requete = "SELECT * FROM boisson where Type = '"+ boisson.getType().replace("'","''") +"' AND Categorie = '"+ boisson.getCategorie().replace("'","''") +
                    "' AND Nom = '"+ boisson.getNom().replace("'","''") + "'" ;
        }else{
            //On créer la requete sans catégorie
             requete = "SELECT * FROM boisson where Type = '"+ boisson.getType().replace("'","''") + "' AND Nom = '"+ boisson.getNom().replace("'","''") + "'" ;
        }

        // On lance la requete
        curseur = db.rawQuery(requete, null);

        //On se palce au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme de la boisson récupéré
            @SuppressLint("Range") Boisson boissonSelect = new Boisson(curseur.getInt(curseur.getColumnIndex("Clé")),
                    curseur.getString(curseur.getColumnIndex("Type")),
                    curseur.getString(curseur.getColumnIndex("Categorie")),
                    curseur.getString(curseur.getColumnIndex("Nom")),
                    curseur.getInt(curseur.getColumnIndex("Quantite")),
                    curseur.getBlob(curseur.getColumnIndex("Image")),
                    curseur.getString(curseur.getColumnIndex("Description"))
            );
            //Ajout de la boisson a la liste
            listBoisson.add(boissonSelect);

            curseur.moveToNext();
        }
        db.close();
        return listBoisson;
    }

    //Retourne la liste des boisson qui sont du Type passé en parametre
    public ArrayList selectListTypeBoisson(String type){

        ArrayList listBoisson = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM boisson WHERE Type = '" + type.replace("'","''") +"'" + " ORDER BY Categorie, Nom";
        curseur = db.rawQuery(requete, null);

        //On se palce au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
            curseur.moveToFirst();
            while (curseur.isAfterLast() == false) {
                //Mise en forme de la boisson récupéré
                @SuppressLint("Range") Boisson boissonSelect = new Boisson(curseur.getInt(curseur.getColumnIndex("Clé")),
                        curseur.getString(curseur.getColumnIndex("Type")),
                        curseur.getString(curseur.getColumnIndex("Categorie")),
                        curseur.getString(curseur.getColumnIndex("Nom")),
                        curseur.getInt(curseur.getColumnIndex("Quantite")),
                        curseur.getBlob(curseur.getColumnIndex("Image")),
                        curseur.getString(curseur.getColumnIndex("Description"))
                );
                //Ajout de la boisson a la liste
                listBoisson.add(boissonSelect);

                curseur.moveToNext();
            }

        db.close();
        return listBoisson;
    }

    //Retourne la liste des boisson qui sont du Type passé en parametre mais sans prendre en compte les boisson avec 0 de quantité
    public ArrayList selectListTypeBoissonSansZero(String type){

        ArrayList listBoisson = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM boisson WHERE Type = '" + type.replace("'","''") +"'" + " AND QUANTITE <> 0 ORDER BY Categorie, Nom";
        curseur = db.rawQuery(requete, null);

        //On se palce au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme de la boisson récupéré
            @SuppressLint("Range") Boisson boissonSelect = new Boisson(curseur.getInt(curseur.getColumnIndex("Clé")),
                    curseur.getString(curseur.getColumnIndex("Type")),
                    curseur.getString(curseur.getColumnIndex("Categorie")),
                    curseur.getString(curseur.getColumnIndex("Nom")),
                    curseur.getInt(curseur.getColumnIndex("Quantite")),
                    curseur.getBlob(curseur.getColumnIndex("Image")),
                    curseur.getString(curseur.getColumnIndex("Description"))
            );
            //Ajout de la boisson a la liste
            listBoisson.add(boissonSelect);

            curseur.moveToNext();
        }
        db.close();
        return listBoisson;
    }

    //Retourne la liste des boisson qui sont du Type passé en parametre
    public ArrayList selectListCategorieBoisson(String type, String categorie){

        ArrayList listBoisson = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM boisson WHERE Categorie = '" + categorie.replace("'","''") +"' AND Type = '" + type.replace("'","''") + "'";
        curseur = db.rawQuery(requete, null);

        //On se palce au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme de la boisson récupéré
            @SuppressLint("Range") Boisson boissonSelect = new Boisson(curseur.getInt(curseur.getColumnIndex("Clé")),
                    curseur.getString(curseur.getColumnIndex("Type")),
                    curseur.getString(curseur.getColumnIndex("Categorie")),
                    curseur.getString(curseur.getColumnIndex("Nom")),
                    curseur.getInt(curseur.getColumnIndex("Quantite")),
                    curseur.getBlob(curseur.getColumnIndex("Image")),
                    curseur.getString(curseur.getColumnIndex("Description"))
            );
            //Ajout de la boisson a la liste
            listBoisson.add(boissonSelect);

            curseur.moveToNext();
        }

        db.close();
        return listBoisson;
    }

    //Retourne la boisson qui a al Clé passée en entrée
    public Boisson selectCleBoisson(int Cle){

        // Création du curseur
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM boisson WHERE Clé = '" + Cle +"'";
        curseur = db.rawQuery(requete, null);

        //On se palce au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();

        //Ajout de la boisson a la liste
        @SuppressLint("Range") Boisson boisson = new Boisson(curseur.getInt(curseur.getColumnIndex("Clé")),
                curseur.getString(curseur.getColumnIndex("Type")),
                curseur.getString(curseur.getColumnIndex("Categorie")),
                curseur.getString(curseur.getColumnIndex("Nom")),
                curseur.getInt(curseur.getColumnIndex("Quantite")),
                curseur.getBlob(curseur.getColumnIndex("Image")),
                curseur.getString(curseur.getColumnIndex("Description"))
        );

        db.close();
        return boisson;
    }


    public boolean modifBoisson(Boisson boisson){

        //Variable de travail
        long resultat;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Clé", boisson.getClé());
        values.put("Type", boisson.getType());
        values.put("Categorie", boisson.getCategorie());
        values.put("Nom", boisson.getNom());
        values.put("Quantite", boisson.getQuantite());
        values.put("Image", boisson.getImage());
        values.put("Description", boisson.getDescription());

        resultat= db.update("boisson", values, "Clé=?", new String[]{String.valueOf(boisson.getClé())});

        db.close();
        return resultat != -1;
    }

    public void DeleteBoisson(int Cle){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("boisson", "Clé=?", new String[]{String.valueOf(Cle)});
        db.close();
    }



    //------------------------------------------ Table categorie ------------------------------------------ //

    //Retourne la liste de toutes les Catégories
    public ArrayList selectTotalCategorie(){

        ArrayList listCategorie = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM categorie ORDER BY Type, Categorie";
        curseur = db.rawQuery(requete, null);

        //On se place au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme de la Categorie récupéré
            @SuppressLint("Range") Categorie CategorieSelect = new Categorie(curseur.getInt(curseur.getColumnIndex("Clé")),
                    curseur.getString(curseur.getColumnIndex("Type")),
                    curseur.getString(curseur.getColumnIndex("Categorie"))
            );
            //Ajout de la boisson a la liste
            listCategorie.add(CategorieSelect);

            curseur.moveToNext();
        }
        db.close();
        return listCategorie;
    }

    public ArrayList selectCategorieUnitaire(Categorie categorie){

        ArrayList listCategorie = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM categorie WHERE Type = '" + categorie.getType().replace("'","''") + "' AND Categorie = '" + categorie.getCategorie().replace("'","''") + "'";
        curseur = db.rawQuery(requete, null);

        //On se place au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme de la Categorie récupéré
            @SuppressLint("Range") Categorie CategorieSelect = new Categorie(curseur.getInt(curseur.getColumnIndex("Clé")),
                    curseur.getString(curseur.getColumnIndex("Type")),
                    curseur.getString(curseur.getColumnIndex("Categorie"))
            );
            //Ajout de la boisson a la liste
            listCategorie.add(CategorieSelect);

            curseur.moveToNext();
        }
        db.close();
        return listCategorie;
    }


    //Retourne la boisson qui a al Clé passée en entrée
    public Categorie selectCleCategorie(int Cle){

        // Création du curseur
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM categorie WHERE Clé = '" + Cle +"'";
        curseur = db.rawQuery(requete, null);

        //On se palce au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();

        //Ajout de la boisson a la liste
        @SuppressLint("Range") Categorie categorie = new Categorie(curseur.getInt(curseur.getColumnIndex("Clé")),
                curseur.getString(curseur.getColumnIndex("Type")),
                curseur.getString(curseur.getColumnIndex("Categorie"))
        );

        db.close();
        return categorie;
    }


    // Pour ajouter une Boisson dans la table
    public boolean addCategorie(Categorie categorie){

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getWritableDatabase();

        //Création d'une variable contenant ce qu'on veux inserer
        ContentValues valeur = new ContentValues();
        valeur.put("Type", categorie.getType());
        valeur.put("Categorie", categorie.getCategorie());

        //Lance l'insert dans la table categorie
        long resultat = db.insert("categorie",null,valeur);

        db.close();
        return resultat != -1;
    }

    //Retourne la liste des carégorie qui sont du Type passé en parametre
    public ArrayList selectListCategorie(String type){

        ArrayList listCategorie = new ArrayList<>();
        Cursor curseur;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getReadableDatabase();

        //On créer la requete puis on la lance
        String requete = "SELECT * FROM categorie WHERE Type = '" + type.replace("'","''") +"'";
        curseur = db.rawQuery(requete, null);

        //On se place au 1er enreg puis jusqu'a la fin on boucle sur chaque et en ecrit dans la liste
        curseur.moveToFirst();
        while (curseur.isAfterLast() == false) {
            //Mise en forme de la categorie récupéré
            @SuppressLint("Range") Categorie categorieSelect = new Categorie(curseur.getInt(curseur.getColumnIndex("Clé")),
                    curseur.getString(curseur.getColumnIndex("Type")),
                    curseur.getString(curseur.getColumnIndex("Categorie"))
            );
            //Ajout de la categorie a la liste
            listCategorie.add(categorieSelect);

            curseur.moveToNext();
        }

        db.close();
        return listCategorie;
    }

    public boolean modifCategorie(Categorie categorie){

        //Variable de travail
        long resultat;

        // Création de la variable indiquant sur quelle database on travail
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Clé", categorie.getClé());
        values.put("Type", categorie.getType());
        values.put("Categorie", categorie.getCategorie());

        resultat= db.update("categorie", values, "Clé=?", new String[]{String.valueOf(categorie.getClé())});

        db.close();
        return resultat != -1;
    }

    public void DeleteCategorie(int Cle){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("categorie", "Clé=?", new String[]{String.valueOf(Cle)});
        db.close();
    }
}
