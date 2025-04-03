package com.example.cafth.page;

import static com.example.cafth.page.GestionBoissonActivity.adapterGestionBoisson;
import static com.example.cafth.page.GestionBoissonActivity.valeurListGestionBoisson;
import static com.example.cafth.page.GestionCategorieActivity.adapterGestionCategorie;
import static com.example.cafth.page.GestionCategorieActivity.valeurListGestionCategorie;
import static com.example.cafth.page.Menu_Liste_Activity.adapter;
import static com.example.cafth.page.Menu_Liste_Activity.valeurList;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;
import com.example.cafth.objet.Categorie;

import java.util.ArrayList;
import java.util.List;

public class AjoutCategorieActivity extends AppCompatActivity {

    //Déclaration des variables
    private Spinner spinnerType;
    private EditText saisiCategorie;
    private ImageButton boutonAjoutCategorie;
    private ImageButton boutonAjoutCategorieSombre;
    private ImageButton boutonAnnuler;

    //Intente pour aller a l'activiyé Menu
    private String catégorie_Boisson;
    private String RecupOrigine;

    //Variable pour la DB
    private CaftheDataBase db;
    private ArrayList selectCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_categorie);

        //Lien entre les varaible et les zone XML
        spinnerType = (Spinner) findViewById(R.id.spinner_categorie);
        saisiCategorie = (EditText) findViewById(R.id.saisie_categorie_boisson);
        boutonAjoutCategorie = (ImageButton) findViewById(R.id.bouton_ajout_categorie);
        boutonAjoutCategorieSombre = (ImageButton) findViewById(R.id.bouton_ajout_categorie);
        boutonAnnuler = (ImageButton) findViewById(R.id.bouton_annuler);

        //Renseigne la variable du nom de la base
        db = new CaftheDataBase(this);
        selectCategorie = null;

        //rendre le bouton non cliquable
        boutonAjoutCategorie.setEnabled(false);

        //Valorisation de l'intent pour aller a l'activiyé Menu
   //     Retour = new Intent(AjoutCategorieActivity.this, Menu_Liste_Activity.class);

        //Récupération de la donnée Type_Boisson --> On la garde pour savoir dans quelle type de boisson on est. (Pour le moment je ne sais pas
        // faire autre pour garder le titre sur l'activity precedente...
        catégorie_Boisson = getIntent().getStringExtra("Type_Boisson");
        RecupOrigine = getIntent().getStringExtra("Origine");

        //Active les effets de clic sur les boutons images
        buttonEffect(boutonAjoutCategorie);
        buttonEffect(boutonAnnuler);

        //Action quand ecrit dans une zone de text
        saisiCategorie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //Vérifi si quelque chose de renseigner par utilisateur
                if (!s.toString().isEmpty()) {
                    boutonAjoutCategorie.setEnabled(true);
                    boutonAjoutCategorie.setVisibility(View.VISIBLE);
                }else {
                    boutonAjoutCategorie.setEnabled(false);
                    boutonAjoutCategorie.setVisibility(View.INVISIBLE);
                }
            }

        });

        //Action quand clic sur bouton
        boutonAjoutCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Création des variable.
                //- nouvelleBoisson pour récuperer la nouvelle boisson a inserer dans la table
                //- resultat pour récuperer le code retour de l'insert et voir si tout c'est bien passé
                Categorie nouvellecategorie = new Categorie(String.valueOf(spinnerType.getSelectedItem().toString()), String.valueOf(saisiCategorie.getText()));
                boolean resultat;

                //Verification que la categorie n'est pas deja existante
                selectCategorie = db.selectCategorieUnitaire(nouvellecategorie);
                if (selectCategorie.size() == 0) {

                    //création nouvelle catégorie (la clé est généré automatiquement). Pour le moment la clé de la "nouvellecategorie" est a null
                    resultat = db.addCategorie(nouvellecategorie);

                    //Récupértion de la boisson qui vient juste d'etre inserer mais avec ca nouvelle Clé
                    nouvellecategorie = (Categorie) db.selectCategorieUnitaire(nouvellecategorie).get(0);

                    if (resultat) {
                        if (RecupOrigine.contains("GestionCategorie")) {
                            //Action de modifier dans la liste puis actualisation de l'affichage
                            valeurListGestionCategorie.add(nouvellecategorie);
                            if (valeurListGestionCategorie.size() == 1){
                                GestionCategorieActivity.ListGestionCategorie.setAdapter(adapterGestionCategorie);
                            }
                            GestionCategorieActivity.DebutText1.setVisibility(View.GONE);
                            GestionCategorieActivity.DebutText2.setVisibility(View.GONE);
                            adapterGestionCategorie.notifyDataSetChanged();
                        }

                        Toast.makeText(AjoutCategorieActivity.this, "Catégorie : " + nouvellecategorie.getCategorie() + " ajoutée", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AjoutCategorieActivity.this, "Oups ! Il y a eu un souci...", Toast.LENGTH_SHORT).show();

                    }

                }else{
                    Toast.makeText(AjoutCategorieActivity.this, "La Catégorie " + nouvellecategorie.getCategorie() + " existe deja.", Toast.LENGTH_LONG).show();

                }
            }
        });

        boutonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //       Retour.putExtra("Type_Boisson",catégorie_Boisson);
         //       startActivity(Retour);
                finish();
            }
        });

        //Parti pour le Spinner
        //Création d'une liste d'élément à mettre dans le Spinner
        List ListType = new ArrayList();
        ListType.add("Café");
        ListType.add("Thé");
        ListType.add("Chocolat");
        ListType.add("Infusion");

        /*Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
                un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        Avec la liste des elements (exemple) */
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.spinner_list,
                ListType
        );

        /* On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item) */
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(ListType.indexOf(catégorie_Boisson));
    }

    //Pour créer un effect sur le bouton quand on clic dessus
    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ResourceAsColor")
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(R.color.clic, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }
}