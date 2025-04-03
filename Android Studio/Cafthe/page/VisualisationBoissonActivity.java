package com.example.cafth.page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;
import com.example.cafth.objet.Boisson;

public class VisualisationBoissonActivity extends AppCompatActivity {

    //Variable pour la DB
    private CaftheDataBase db;

    //Variable XML
    private TextView nomBoisson;
    private TextView quantiteBoisson;
    private TextView typeBoisson;
    private TextView categorieBoisson;
    private TextView spinnerType;
    private TextView spinnerCategorie;
    private TextView saisiNomBoisson;
    private TextView saisiQuantiteBoisson;
    private ImageView image;
    private ImageView retour;
    private TextView description;
    private TextView saisieDescription;

    //Info récupérer de l'activity parent
    private int cleBoisson;
    private String RecupOrigine;

    //Variable de travail
    private Boisson boisson;

    //Variable pour l'image
    private Bitmap imageSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisation_boisson);

        //Lien entre les varaible et les zone XML
        nomBoisson =  findViewById(R.id.Visu_nom_boisson);
        quantiteBoisson =  findViewById(R.id.Visu_quantite_boisson);
        typeBoisson =  findViewById(R.id.Visu_type_de_boisson);
        categorieBoisson =  findViewById(R.id.Visu_categorie_de_boisson);
        spinnerType =  findViewById(R.id.Visu_spinner_type_boisson);
        spinnerCategorie =  findViewById(R.id.Visu_spinner_categorie_boisson);
        saisiNomBoisson =  findViewById(R.id.Visu_saisie_nom_boisson);
        saisiQuantiteBoisson =  findViewById(R.id.Visu_saisie_quantite_boisson);
        image =  findViewById(R.id.Visu_Image);
        retour = findViewById(R.id.Visu_bouton_retour);
        description =  findViewById(R.id.Visu_description);
        saisieDescription = findViewById(R.id.Visu_Saisi_Description);

        imageSelect= null;

        //Récupération de la donnée Type_Boisson --> On la garde pour savoir dans quelle type de boisson on est. (Pour le moment je ne sais pas
        // faire autre pour garder le titre sur l'activity precedente...
        cleBoisson = getIntent().getIntExtra("Cle_Boisson",0);
        RecupOrigine = getIntent().getStringExtra("Origine");

        //Renseigne la variable du nom de la base
        db = new CaftheDataBase(this);

        //Select de la boisson
        boisson = db.selectCleBoisson(cleBoisson);

        spinnerType.setText(boisson.getType());
        saisiNomBoisson.setText(boisson.getNom());
        saisiQuantiteBoisson.setText(String.valueOf(boisson.getQuantite()));


        if (boisson.getDescription().isEmpty()){
            saisieDescription.setVisibility(View.GONE);
            description.setVisibility(View.GONE);
        }else{
            saisieDescription.setText(boisson.getDescription());
        }

        if (boisson.getCategorie() == null){
            spinnerCategorie.setVisibility(View.GONE);
            categorieBoisson.setVisibility(View.GONE);
        }else{
            spinnerCategorie.setText(boisson.getCategorie());
        }

        if (boisson.getImage() != null) {
            imageSelect = getBitmap(boisson.getImage());
            image.setImageBitmap(imageSelect);
        }

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //Permet de mettre en forme le fichier Image pour le visualiser
    public Bitmap getBitmap(byte[] imageByte){
        Bitmap imageBitmap  = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
        return imageBitmap;
    }
}