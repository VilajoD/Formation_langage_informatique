package com.example.cafth.page;

import static com.example.cafth.page.GestionCategorieActivity.adapterGestionCategorie;
import static com.example.cafth.page.GestionCategorieActivity.valeurListGestionCategorie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.cafth.objet.Boisson;
import com.example.cafth.objet.Categorie;

import java.util.ArrayList;
import java.util.List;

public class ModifCategorieActivity extends AppCompatActivity {

    //Déclaration des variables
    private Spinner spinnerType;
    private EditText saisiCategorie;
    private ImageButton boutonModifCategorie;
    private ImageButton boutonModifCategorieSombre;
    private ImageButton boutonAnnuler;

    //Variable de récupération de donnée d'entrées
    private Intent Retour = new Intent();
    private String RecupOrigine;
    private int RecupCle;
    private int RecupPosition;

    //Variable pour la DB
    private CaftheDataBase db;
    private ArrayList selectCategorie;

    //Création variable de travail
    private boolean boissonLierCategorie;
    private ArrayList listBoisson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_caregorie);

        //Lien entre les varaible et les zone XML
        spinnerType = (Spinner) findViewById(R.id.Modif_Categorie_spinner_categorie);
        saisiCategorie = (EditText) findViewById(R.id.Modif_Categorie_saisie_categorie_boisson);
        boutonModifCategorie = (ImageButton) findViewById(R.id.Modif_Categorie_bouton_ajout_categorie);
        boutonModifCategorieSombre = (ImageButton) findViewById(R.id.Modif_Categorie_bouton_ajout_categorie);
        boutonAnnuler = (ImageButton) findViewById(R.id.Modif_Categorie_bouton_annuler);

        //Renseigne la variable du nom de la base
        db = new CaftheDataBase(this);
        selectCategorie= null;

        //Récupération des donnée recu
        RecupCle = getIntent().getIntExtra("Cle",0);
        RecupOrigine = getIntent().getStringExtra("Origine");
        RecupPosition = getIntent().getIntExtra("Position",0);

        //Initialisation variable de travail
        boissonLierCategorie = false;
        listBoisson = null;

        //Alimentation des info XML apres récupération dans la table boisson a partir de la Clé reçu en entrée de l'activity
        Categorie categorieSelect = db.selectCleCategorie(RecupCle);
        saisiCategorie.setText(categorieSelect.getCategorie());


        //Création d'une liste d'élément à mettre dans le Spinner Type de boisson
        List ListType = new ArrayList();
        ListType.add("Café");
        ListType.add("Thé");
        ListType.add("Chocolat");
        ListType.add("Infusion");

        //Active les effets de clic sur les boutons images
        buttonEffect(boutonModifCategorie);
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
                    boutonModifCategorie.setEnabled(true);
                    boutonModifCategorie.setVisibility(View.VISIBLE);
                }else {
                    boutonModifCategorie.setEnabled(false);
                    boutonModifCategorie.setVisibility(View.INVISIBLE);
                }
            }

        });

        //Action quand clic sur bouton
        boutonModifCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Création des variable.
                //- nouvelleBoisson pour récuperer la nouvelle boisson a inserer dans la table
                //- resultat pour récuperer le code retour de l'insert et voir si tout c'est bien passé
                Categorie modifcategorie = new Categorie(String.valueOf(spinnerType.getSelectedItem().toString()), String.valueOf(saisiCategorie.getText()));
                boolean resultat;

                //Verification que la categorie n'est pas deja existante
                selectCategorie = db.selectCategorieUnitaire(modifcategorie);
                if (selectCategorie.size() == 0) {

                    modifcategorie.setClé(RecupCle);

                    //Verification s'il y a eu modification de quelque chose avant de charger en base
                    if (!modifcategorie.getCategorie().equals(categorieSelect.getCategorie()) || !modifcategorie.getType().equals(categorieSelect.getType())) {

                        listBoisson = db.selectListCategorieBoisson(categorieSelect.getType(), categorieSelect.getCategorie());
                        if (listBoisson.size() == 0) {
                            resultat = db.modifCategorie(modifcategorie);
                            if (resultat) {
                                modifierListeGestionCategorie(modifcategorie);
                            } else {
                                Toast.makeText(ModifCategorieActivity.this, "Oups ! Il y a eu un souci...", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } else {
                            confirmeModification(categorieSelect, modifcategorie, listBoisson);
                        }
                    }
                }else{
                    Toast.makeText(ModifCategorieActivity.this, "La Catégorie " + modifcategorie.getCategorie() + " existe deja.", Toast.LENGTH_LONG).show();

                }
            }
        });

        boutonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Parti pour le Spinner

        //Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
        //        un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        //  Avec la liste des elements (exemple)
        ArrayAdapter adapter = new ArrayAdapter(
                this,
                R.layout.spinner_list,
                ListType
        );

        // On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner et c'est tout
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(ListType.indexOf(categorieSelect.getType()));

    }

    public void modifierListeGestionCategorie(Categorie modifcategorie){
        //Action de modifier dans la liste puis actualisation de l'affichage
        valeurListGestionCategorie.set(RecupPosition,modifcategorie);
        adapterGestionCategorie.notifyDataSetChanged();
    }

    // Si la catégorie modifiée est utiliser dans des boisson. On previens l'utilisateur que ces boisson seront aussi modifier
    public void confirmeModification(Categorie categorieSelect, Categorie modifcategorie, ArrayList<Boisson> boissonLierCategorie){

        //Variable de l'alerte
        AlertDialog alertModif;

        //il faut un builder pour utiliser l'alerte.
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifCategorieActivity.this);

        // Alimente les differentes parties de la boite de dialogue d'alerte
        builder.setTitle("Attention !");
        builder.setMessage("La catégorie " + categorieSelect.getCategorie() + " que vous essayez de modifier est utilisée pour certaines boissons. Si vous continuez, les boissons faisant partie de cette catégorie seront modifiées en consequence pour respecter les nouveaux noms (modification de leur catégorie et/ou de leur type). Souhaitez-vous continuer et sauvegarder ces modifications ?");
        builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Modification de la partie categorie
                boolean resultat = db.modifCategorie(modifcategorie);
                if (resultat) {
                    modifierListeGestionCategorie(modifcategorie);

                    for (Boisson boisson : boissonLierCategorie) {
                        boisson.setType(modifcategorie.getType());
                        boisson.setCategorie(modifcategorie.getCategorie());
                        db.modifBoisson(boisson);
                        Toast.makeText(ModifCategorieActivity.this,"Modification reussi",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ModifCategorieActivity.this,"Oups ! Il y a eu un souci...",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        alertModif = builder.create();
        alertModif.show();
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