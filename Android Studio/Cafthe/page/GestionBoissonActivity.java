package com.example.cafth.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafth.Adapter.ItemGestionBoissonAdaptater;
import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;
import com.example.cafth.objet.Boisson;

import java.util.ArrayList;

public class GestionBoissonActivity extends AppCompatActivity {

    //Variable lien avec XML
    public static ListView ListGestionBoisson;
    public static ListView ListRecherche;
    public static TextView DebutText1;
    public static TextView DebutText2;
    public static ImageView boutonRecherche;
    public static TextView textRecherche;
    public Spinner spinnerRecherche;
    public EditText saisieRecherche;


    //Variable pour la DB
    private static CaftheDataBase db;
    public static ArrayList<Boisson> valeurListGestionBoisson;

    public static ArrayList<Boisson> valeurListRecherche;

    //en Public pour pouvoir etre utiliser dans d'autre class
    public static ArrayAdapter adapterGestionBoisson;
    public static ArrayAdapter adapterRecherche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_boisson);

        ListGestionBoisson = findViewById(R.id.List_Gestion_Boisson);
        DebutText1 = findViewById(R.id.List_Gestion_Boisson_presentation_1);
        DebutText2 = findViewById(R.id.List_Gestion_Boisson_presentation_2);

        ListRecherche = findViewById(R.id.List_Gestion_Boisson_Recherche);
        boutonRecherche = findViewById(R.id.bouton_Recherche);
        textRecherche = findViewById(R.id.Text_Recherche);
        spinnerRecherche = findViewById(R.id.spinner_Recherche);
        saisieRecherche = findViewById(R.id.saisie_Recherche);

        //Création d'une liste d'élément à mettre dans le Spinner de recherche
        ArrayList<String> ListSpinnerRecherche = new ArrayList<String>();
        ListSpinnerRecherche.add("Tout");
        ListSpinnerRecherche.add("Type");
        ListSpinnerRecherche.add("Catégorie");
        ListSpinnerRecherche.add("Nom");
        ListSpinnerRecherche.add("Quantité");

        spinnerRecherche.setSelection(ListSpinnerRecherche.indexOf("Tout"));

        valeurListRecherche = new ArrayList<Boisson>();

        //Renseigne la variable du nom de la base
        db = new CaftheDataBase(this);

        valeurListGestionBoisson = db.selectTotalBoisson();

        //On appel l'adaptateur fait maison qui va afficher les composant de la liste sur la page.
        // Parametre :
        // - Le context ou se passe l'action (celui ou on se trouve)
        // - Le format que doti prendre la liste
        // - Les valeur a afficher
        adapterGestionBoisson = new ItemGestionBoissonAdaptater(this, R.layout.item_menu_gestion_boisson, valeurListGestionBoisson);
        adapterRecherche = new ItemGestionBoissonAdaptater(this, R.layout.item_menu_gestion_boisson, valeurListRecherche);

        //initialisation de la recherche a null pour pouvoir lancher l'adapter puis remise a zero de la liste
        valeurListRecherche.add(null);
        ListRecherche.setAdapter(adapterRecherche);
        valeurListRecherche.clear();

        // On applique l'adaptater fait juste avant sur le XML qui nous intresse
        if (valeurListGestionBoisson.size() != 0) {
            ListGestionBoisson.setAdapter(adapterGestionBoisson);
        } else {
            DebutText1.setVisibility(View.VISIBLE);
            DebutText2.setVisibility(View.VISIBLE);
            boutonRecherche.setVisibility(View.INVISIBLE);
            textRecherche.setVisibility(View.INVISIBLE);
        }

        //Permet de mettre les diffetente ligne de la liste sur ecoute
        ListGestionBoisson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent VersVisu = new Intent(GestionBoissonActivity.this, VisualisationBoissonActivity.class);
                VersVisu.putExtra("Cle_Boisson", valeurListGestionBoisson.get(position).getClé());
                VersVisu.putExtra("Origine", "ListBoisson");
                startActivity(VersVisu);
            }
        });

        //Permet de mettre les diffetente ligne de la liste sur ecoute
        ListRecherche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent VersVisu = new Intent(GestionBoissonActivity.this, VisualisationBoissonActivity.class);
                VersVisu.putExtra("Cle_Boisson", valeurListRecherche.get(position).getClé());
                VersVisu.putExtra("Origine", "ListBoisson");
                startActivity(VersVisu);
            }
        });

        // Rend visible ou non les infos recherches en cliquant sur le bouton
        boutonRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibiliteRecherche();
            }
        });

        // Rend visible ou non les infos recherches en cliquant sur le text
        textRecherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visibiliteRecherche();
            }
        });

        //Parti pour le Spinner

        //Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
        //        un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        ArrayAdapter adapterSpinner = new ArrayAdapter(
                this,
                R.layout.spinner_list,
                ListSpinnerRecherche
        );

        /* On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item) */
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner
        spinnerRecherche.setAdapter(adapterSpinner);

        //Action quand ecrit dans une zone de text
        saisieRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Vérifi si quelque chose de renseigner par utilisateur
                valeurListRecherche.clear();
                adapterRecherche.notifyDataSetChanged();
                if (!s.toString().isEmpty()) {
                    ListRecherche.setVisibility(View.VISIBLE);
                    ListGestionBoisson.setVisibility(View.INVISIBLE);
                    alimentationRecherche(spinnerRecherche.getSelectedItem().toString(), s.toString());

                } else {
                    ListRecherche.setVisibility(View.INVISIBLE);
                    ListGestionBoisson.setVisibility(View.VISIBLE);
                }
            }
        });

    }//Fin OnCreate


    //Partie :  création du menu en haut a droite
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_ajout_boisson, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.GestionBoisson_Menu_Ajout) {
            Intent VersAjout = new Intent(GestionBoissonActivity.this, AjoutBoissonActivity.class);
            VersAjout.putExtra("Origine", "GestionBoisson");
            startActivity(VersAjout);
            //Le finish c'est pour fermer l'activity, pour qu'on retour de l'ajour elle soit refaite et mise a jour = Bidouille..
            //    finish();
        }
        return super.onOptionsItemSelected(item);
    }


    //Partie : recherche

    public void visibiliteRecherche() {
        if (spinnerRecherche.getVisibility() == View.VISIBLE) {
            spinnerRecherche.setVisibility(View.GONE);
            saisieRecherche.setVisibility(View.GONE);
            ListRecherche.setVisibility(View.INVISIBLE);
            ListGestionBoisson.setVisibility(View.VISIBLE);
            saisieRecherche.setText(null);
        } else if (spinnerRecherche.getVisibility() == View.GONE) {
            spinnerRecherche.setVisibility(View.VISIBLE);
            saisieRecherche.setVisibility(View.VISIBLE);
        }
    }

    public void alimentationRecherche(String item, String rechercher) {
        for (Boisson boisson:valeurListGestionBoisson) {
            switch (item){
                case "Type" :
                    if (boisson.getType().toUpperCase().contains(rechercher.toUpperCase())){
                        valeurListRecherche.add(boisson);
                        adapterRecherche.notifyDataSetChanged();
                    }
                    break;
                case "Catégorie" :
                    if (boisson.getCategorie() != null) {
                        if (boisson.getCategorie().toUpperCase().contains(rechercher.toUpperCase())) {
                            valeurListRecherche.add(boisson);
                            adapterRecherche.notifyDataSetChanged();
                        }
                    }
                    break;
                case "Nom" :
                    if (boisson.getNom().toUpperCase().contains(rechercher.toUpperCase())){
                        valeurListRecherche.add(boisson);
                        adapterRecherche.notifyDataSetChanged();
                    }
                    break;
                case "Quantité" :
                    if (String.valueOf(boisson.getQuantite()).contains(rechercher)){
                        valeurListRecherche.add(boisson);
                        adapterRecherche.notifyDataSetChanged();
                    }
                    break;
                case "Tout" :
                    String categorie;
                    if (boisson.getCategorie() != null) {
                        categorie = boisson.getCategorie();
                    }else {
                        categorie = "";
                    }
                    if (String.valueOf(boisson.getQuantite()).contains(rechercher) ||
                            boisson.getType().toUpperCase().contains(rechercher.toUpperCase()) ||
                            boisson.getNom().toUpperCase().contains(rechercher.toUpperCase()) ||
                            categorie.toUpperCase().contains(rechercher.toUpperCase())){
                        valeurListRecherche.add(boisson);
                        adapterRecherche.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }

}
