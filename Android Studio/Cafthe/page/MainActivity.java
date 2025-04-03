package com.example.cafth.page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafth.R;

public class MainActivity extends AppCompatActivity {

    //Variable pour le fichier SharedPreferences
    private SharedPreferences sharedPreferences;

    //Déclaration des variables
    private TextView mTitre;
    private ImageButton mCafé;
    private ImageButton mThé;
    private ImageButton mChocolat;
    private ImageButton mInfusion;

    Intent VersMenuListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Lien entre les variable et les zone XML
        mTitre = findViewById(R.id.Titre);
        mCafé = findViewById(R.id.Café);
        mThé = findViewById(R.id.Thé);
        mChocolat = findViewById(R.id.Chocolat);
        mInfusion = findViewById(R.id.Infusion);

        // Initialise le fichier
        sharedPreferences = this.getSharedPreferences("Préférences", Context.MODE_PRIVATE);

        //Vérification si 1ere conexion
        if (!sharedPreferences.getBoolean("Uilisateur_Connu", false)){
            SharedPreferences.Editor editeur1erConnexion = sharedPreferences.edit();
            editeur1erConnexion.putBoolean("Uilisateur_Connu", true);
            editeur1erConnexion.putBoolean("Presence_Zero_Quantite", true);
            editeur1erConnexion.putBoolean("Couleur_Menu_Liste", false);
            editeur1erConnexion.putBoolean("Couleur_Menu_Gestion", true);
            editeur1erConnexion.putInt("Dose_Gramme", 2);
            editeur1erConnexion.apply();
        }

        //Valorisation de l'intente pour aller a la page suivante
        VersMenuListe = new Intent(MainActivity.this, Menu_Liste_Activity.class);

        //Active les effets de clic sur les boutons images
        buttonEffect(mCafé);
        buttonEffect(mThé);
        buttonEffect(mChocolat);
        buttonEffect(mInfusion);

        //Quand clic sur le bouton Café:
        //    - A partir de l'activité  MainActivity on va vers l'activité Menu_Liste_Activity. (L'info de ou a ou on va est contenu dans un Intent)
        mCafé.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersMenuListe.putExtra("Type_Boisson","Café");
                startActivity(VersMenuListe);
            }
        });

        mThé.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersMenuListe.putExtra("Type_Boisson","Thé");
                startActivity(VersMenuListe);
            }
        });

        mChocolat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersMenuListe.putExtra("Type_Boisson","Chocolat");
                startActivity(VersMenuListe);
            }
        });

        mInfusion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersMenuListe.putExtra("Type_Boisson","Infusion");
                startActivity(VersMenuListe);
            }
        });

    }//Fin OnCreate

    //Partie :  création du menu en haut a droite
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_parametre_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Main_Gestion_Boisson) {
            Intent VersGestionBoisson = new Intent(MainActivity.this, GestionBoissonActivity.class);
            startActivity(VersGestionBoisson);
            //Le finish c'est pour fermer l'activity, pour qu'on retour de l'ajour elle soit refaite et mise a jour = Bidouille..
        }
        if (item.getItemId() == R.id.Main_Gestion_Categorie) {
            Intent VersGestionCategorie = new Intent(MainActivity.this, GestionCategorieActivity.class);
            startActivity(VersGestionCategorie);
            //Le finish c'est pour fermer l'activity, pour qu'on retour de l'ajour elle soit refaite et mise a jour = Bidouille..
        }
        if (item.getItemId() == R.id.Main_Param) {
            Intent VersParam = new Intent(MainActivity.this, ParametreActivity.class);
            startActivity(VersParam);
            //Le finish c'est pour fermer l'activity, pour qu'on retour de l'ajour elle soit refaite et mise a jour = Bidouille..
        }
        return super.onOptionsItemSelected(item);
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