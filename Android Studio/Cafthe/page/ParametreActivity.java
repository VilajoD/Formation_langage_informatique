package com.example.cafth.page;

import static com.example.cafth.page.GestionBoissonActivity.adapterGestionBoisson;
import static com.example.cafth.page.GestionBoissonActivity.valeurListGestionBoisson;
import static com.example.cafth.page.Menu_Liste_Activity.adapter;
import static com.example.cafth.page.Menu_Liste_Activity.valeurList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cafth.R;
import com.example.cafth.objet.Boisson;

import java.util.Set;

public class ParametreActivity extends AppCompatActivity {

    private Switch switchZeroQuantite;
    private Switch switchCouleurListe;
    private Switch switchCouleurGestion;
    private ImageButton boutonValider;
    private ImageButton boutonAnnuler;
    private EditText saisiDose;

    //Variable pour le fichier SharedPreferences
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametre);

        switchZeroQuantite = findViewById(R.id.Param_Switch_Libel_Zero);
        switchCouleurListe = findViewById(R.id.Param_Switch_Couleur_Liste);
        switchCouleurGestion = findViewById(R.id.Param_Switch_Couleur_Gestion);
        boutonValider = findViewById(R.id.Param_bouton_Valider);
        boutonAnnuler = findViewById(R.id.Parem_Bouton_Annuler);
        saisiDose = findViewById(R.id.saisie_Dose);

        // Initialise le fichier
        sharedPreferences = this.getSharedPreferences("Préférences", Context.MODE_PRIVATE);

        //Lire le fichier preference pour les differente donnée sauvagerder et les afficher dans le XML
        switchZeroQuantite.setChecked(sharedPreferences.getBoolean("Presence_Zero_Quantite",true));
        switchCouleurListe.setChecked(sharedPreferences.getBoolean("Couleur_Menu_Liste",false));
        switchCouleurGestion.setChecked(sharedPreferences.getBoolean("Couleur_Menu_Gestion",true));
        saisiDose.setText(String.valueOf(sharedPreferences.getInt("Dose_Gramme",2)));

        //Active les effets de clic sur les boutons images
        buttonEffect(boutonValider);
        buttonEffect(boutonAnnuler);

        //Quand clique sur valider, récuperer ce qui est choisi et l'enregistrer dans le preference
        //Action quand clic sur bouton
        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Verification que la modification soit utile pour le parametre ZeroQuantité
                if (switchZeroQuantite.isChecked() != sharedPreferences.getBoolean("Presence_Zero_Quantite", true)) {
                    //Création de l'editor qui permet de toucher au fichier préférences
                    SharedPreferences.Editor editeurZeroQuantite = sharedPreferences.edit();

                    // Ecriture dans le fichier
                    editeurZeroQuantite.putBoolean("Presence_Zero_Quantite", switchZeroQuantite.isChecked());
                    editeurZeroQuantite.apply();
                }

                //Verification que la modification soit utile pour le parametre couleur boisson dans menu liste
                if (switchCouleurListe.isChecked() != sharedPreferences.getBoolean("Couleur_Menu_Liste", false)) {
                    //Création de l'editor qui permet de toucher au fichier préférences
                    SharedPreferences.Editor editeurCouleurListe = sharedPreferences.edit();

                    // Ecriture dans le fichier
                    editeurCouleurListe.putBoolean("Couleur_Menu_Liste", switchCouleurListe.isChecked());
                    editeurCouleurListe.apply();
                }

                //Verification que la modification soit utile pour le parametre couleur boisson dans menu Gestion
                if (switchCouleurGestion.isChecked() != sharedPreferences.getBoolean("Couleur_Menu_Gestion", true)) {
                    //Création de l'editor qui permet de toucher au fichier préférences
                    SharedPreferences.Editor editeurCouleurGestion = sharedPreferences.edit();

                    // Ecriture dans le fichier
                    editeurCouleurGestion.putBoolean("Couleur_Menu_Gestion", switchCouleurGestion.isChecked());
                    editeurCouleurGestion.apply();
                }

                //Verification que la modification soit utile pour le parametre dose en gramme des boissons
                if (!saisiDose.getText().equals(String.valueOf(sharedPreferences.getInt("Dose_Gramme", 2))))  {
                    //Création de l'editor qui permet de toucher au fichier préférences
                    SharedPreferences.Editor editeurDoseGramme = sharedPreferences.edit();

                    // Ecriture dans le fichier
                    editeurDoseGramme.putInt("Dose_Gramme", Integer.parseInt(String.valueOf(saisiDose.getText())));
                    editeurDoseGramme.apply();
                }
                finish();
            }
        });


        boutonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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