package com.example.cafth.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafth.Adapter.ItemAdaptater;
import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;
import com.example.cafth.objet.Boisson;

import java.util.ArrayList;

public class Menu_Liste_Activity extends AppCompatActivity {

    //Variable pour le fichier SharedPreferences
    private SharedPreferences sharedPreferences;
    private Boolean presenceBoissonZeroQuantite;

    //Déclaration des variables
    private ListView listAffich;
    private TextView TitreView;
    public static TextView DebutText1;
    public static TextView DebutText2;
    public static ImageView titreCafe;
    public static ImageView titreThe;
    public static ImageView titreChocolat;
    public static ImageView titreInfusion;


    private String TitreRecup;
    public static ArrayList<Boisson> valeurList;

    //en Public pour pouvoir etre utiliser dans d'autre class
    public static ArrayAdapter adapter;

    //Variable pour la DB
    private static CaftheDataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_liste);

        // Initialise le fichier preferenceZeroQuantite
        sharedPreferences = this.getSharedPreferences("Préférences", Context.MODE_PRIVATE);

        presenceBoissonZeroQuantite = sharedPreferences.getBoolean("Presence_Zero_Quantite",true);

        //Lien entre les varaible et les zone XML
        listAffich = (ListView) findViewById(R.id.ListAffich);
        TitreView = findViewById(R.id.Titre_Menu_Liste);
        DebutText1 = findViewById(R.id.List_boisson_presentation_1);
        DebutText2 = findViewById(R.id.List_boisson_presentation_2);
        titreCafe = findViewById(R.id.Titre_Cafe);
        titreThe = findViewById(R.id.Titre_The);
        titreChocolat = findViewById(R.id.Titre_Chocolat);
        titreInfusion = findViewById(R.id.Titre_Infusion);

        //Récuperation de text de l'activity precedente et Modification du titre de la page de cette activity
        TitreRecup = getIntent().getStringExtra("Type_Boisson");

        switch (TitreRecup){
            case "Café" :
                titreCafe.setVisibility(View.VISIBLE);
                break;

            case "Thé" :
                titreThe.setVisibility(View.VISIBLE);
                break;

            case "Chocolat" :
                titreChocolat.setVisibility(View.VISIBLE);
                break;

            case "Infusion" :
                titreInfusion.setVisibility(View.VISIBLE);
                break;
        }
        TitreView.setText(TitreRecup);

        //Renseigne la variable du nom de la base
        db = new CaftheDataBase(this);


        //Valorise la liste des info a afficher (Avec ou sans les Boisson a zero en fonction du fichier Preference)
        if (presenceBoissonZeroQuantite){
            valeurList = db.selectListTypeBoisson(TitreRecup);
        }else{
            valeurList = db.selectListTypeBoissonSansZero(TitreRecup);
        }

        //La liste est vide ?
        if (valeurList.size() == 0){
            DebutText1.setVisibility(View.VISIBLE);
            DebutText2.setVisibility(View.VISIBLE);
        }

        //On appel l'adaptateur fait maison qui va afficher les composant de la liste sur la page.
        // Parametre :
        // - Le context ou se passe l'action (celui ou on se trouve)
        // - Le format que doti prendre la liste
        // - Les valeur a afficher
        adapter = new ItemAdaptater(this,R.layout.item_menu_liste,valeurList);

        // On applique l'adaptater fait juste avant sur le XML qui nous intresse
        listAffich.setAdapter(adapter);


        //Permet de mettre les diffetente ligne de la liste sur ecoute
        listAffich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent VersVisu = new Intent(Menu_Liste_Activity.this, VisualisationBoissonActivity.class);
                VersVisu.putExtra("Cle_Boisson",valeurList.get(position).getClé());
                VersVisu.putExtra("Origine", "ListBoisson");
                startActivity(VersVisu);
            }
        });


        //Permet de créer un menu contextuel sur la liste qui est affichée
        registerForContextMenu(listAffich);



    } //Fin OnCreate

    //Partie :  création du menu en haut a droite
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_ajout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Ajouter_Boisson) {
            Intent VersAjout = new Intent(Menu_Liste_Activity.this, AjoutBoissonActivity.class);
            VersAjout.putExtra("Type_Boisson",TitreRecup);
            VersAjout.putExtra("Origine", "ListBoisson");
            startActivity(VersAjout);
        }
        if (item.getItemId() == R.id.Ajout_Categorie) {
            Intent VersAjoutCategorie = new Intent(Menu_Liste_Activity.this, AjoutCategorieActivity.class);
            VersAjoutCategorie.putExtra("Type_Boisson", TitreRecup);
            VersAjoutCategorie.putExtra("Origine", "ListBoisson");
            startActivity(VersAjoutCategorie);
        }
        if (item.getItemId() == R.id.Aide) {
            Intent VersAideListe = new Intent(Menu_Liste_Activity.this, AideMenuListeActivity.class);
            startActivity(VersAideListe);
        }
        return super.onOptionsItemSelected(item);
    }

}