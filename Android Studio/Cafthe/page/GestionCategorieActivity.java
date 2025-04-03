package com.example.cafth.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafth.Adapter.GestionCategorieAdaptater;
import com.example.cafth.Adapter.ItemGestionBoissonAdaptater;
import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;

import java.util.ArrayList;

public class GestionCategorieActivity extends AppCompatActivity {

    //Variable lien avec XML
    public static ListView ListGestionCategorie;
    public static TextView DebutText1;
    public static TextView DebutText2;

    //Variable pour la DB
    private static CaftheDataBase db;
    public static ArrayList valeurListGestionCategorie;

    //en Public pour pouvoir etre utiliser dans d'autre class
    public static ArrayAdapter adapterGestionCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_categorie);
        ListGestionCategorie = findViewById(R.id.List_Gestion_Categorie);

        //Renseigne la variable du nom de la base
        db = new CaftheDataBase(this);

        valeurListGestionCategorie = db.selectTotalCategorie();
        DebutText1 = findViewById(R.id.List_Gestion_Categorie_presentation_1);
        DebutText2 = findViewById(R.id.List_Gestion_Categorie_presentation_2);

        //On appel l'adaptateur fait maison qui va afficher les composant de la liste sur la page.
        // Parametre :
        // - Le context ou se passe l'action (celui ou on se trouve)
        // - Le format que doti prendre la liste
        // - Les valeur a afficher
        adapterGestionCategorie = new GestionCategorieAdaptater(this,R.layout.item_menu_gestion_categorie,valeurListGestionCategorie);

        // On applique l'adaptater fait juste avant sur le XML qui nous intresse
        if (valeurListGestionCategorie.size() != 0){
            ListGestionCategorie.setAdapter(adapterGestionCategorie);
        }else{
            DebutText1.setVisibility(View.VISIBLE);
            DebutText2.setVisibility(View.VISIBLE);
        }
    }//Fin OnCreate


    //Partie :  création du menu en haut a droite
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_ajout_categorie,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.GestionCategorie_Menu_Ajout) {
            Intent VersAjout = new Intent(GestionCategorieActivity.this, AjoutCategorieActivity.class);
            VersAjout.putExtra("Origine", "GestionCategorie");
            VersAjout.putExtra("Type_Boisson", "Café");
            startActivity(VersAjout);
        }
        return super.onOptionsItemSelected(item);
    }

}