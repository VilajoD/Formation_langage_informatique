package com.pandart.balance.Page;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pandart.balance.DB.BalanceDataBase;
import com.pandart.balance.Objet.Mesure;
import com.pandart.balance.Objet.User;
import com.pandart.balance.R;

public class SaisiMesureActivity extends AppCompatActivity {

    //Variable lien avec XML
    private TextView mTitreEtDate;
    private TextView mTextpoids;
    private TextView mChiffrePoids;
    private TextView mTextMassGrai;
    private TextView mChiffreMassGrais;
    private TextView mTextCalorie;
    private TextView mChiffreCalorie;
    private TextView mTextMassMuscu;
    private TextView mChiffreMassMuscu;
    private TextView mTextMassOs;
    private TextView mChiffreMassOs;
    private TextView mTextIMC;
    private TextView mChiffreIMC;
    private TextView mTextMassHydr;
    private TextView mChiffreMassHydr;
    private ImageButton mValider;
    private ImageButton mAnnuler;

    //Donnée recu en entrée
    private String PseudoRecu;
    private int JourRecu;
    private int MoisRecu;
    private int AnneeRecu;

    // Variable base de donnée
    private BalanceDataBase db;

    //Variable de travail
    private Mesure MesureSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saisi_mesure);

        //Lien entre les varaible et les zone XML
        mTitreEtDate = findViewById(R.id.TitreEtDate);
        mTextpoids = findViewById(R.id.Textpoids);
        mChiffrePoids = findViewById(R.id.ChiffrePoids);
        mTextMassGrai = findViewById(R.id.TextMassGrai);
        mChiffreMassGrais = findViewById(R.id.ChiffreMassGrais);
        mTextCalorie = findViewById(R.id.TextCalorie);
        mChiffreCalorie = findViewById(R.id.ChiffreCalorie);
        mTextMassMuscu = findViewById(R.id.TextMassMuscu);
        mChiffreMassMuscu = findViewById(R.id.ChiffreMassMuscu);
        mTextMassOs = findViewById(R.id.TextMassOs);
        mChiffreMassOs = findViewById(R.id.ChiffreMassOs);
        mTextIMC = findViewById(R.id.TextIMC);
        mChiffreIMC = findViewById(R.id.ChiffreIMC);
        mTextMassHydr = findViewById(R.id.TextMassHydr);
        mChiffreMassHydr = findViewById(R.id.ChiffreMassHydr);
        mValider = findViewById(R.id.Valider);
        mAnnuler = findViewById(R.id.Annuler);

        //Récupération reçu en entrée
        PseudoRecu = getIntent().getStringExtra("Pseudo");
        JourRecu = getIntent().getIntExtra("Jour",0);
        MoisRecu = getIntent().getIntExtra("Mois",0);
        AnneeRecu = getIntent().getIntExtra("Annee",0);

        //Renseigne la variable du nom de la base
        db = new BalanceDataBase(this);

        //Active les effets de clic sur les boutons images
        buttonEffect(mValider);
        buttonEffect(mAnnuler);

        //Si mesure deja existante on l'affiche
        MesureSelect = db.MeusureDuJour(PseudoRecu,JourRecu,MoisRecu,AnneeRecu);
        if (MesureSelect != null){
            mChiffrePoids.setText(MesureSelect.getPoids());
            mChiffreMassGrais.setText(MesureSelect.getMass_Grais());
            mChiffreCalorie.setText(MesureSelect.getCalorie());
            mChiffreMassMuscu.setText(MesureSelect.getMass_Musc());
            mChiffreMassOs .setText(MesureSelect.getMass_Oss());
            mChiffreIMC.setText(MesureSelect.getIMC());
            mChiffreMassHydr.setText(MesureSelect.getMass_hydr());
        }

        // Action lors du clic sur le bouton Valider
        mValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Vérification si mesure deja existante. Si oui on passe en modification sinon en création
                if (MesureSelect == null){
                    db.addMesure(new Mesure(String.valueOf(PseudoRecu),
                            JourRecu,
                            MoisRecu,
                            AnneeRecu,
                            String.valueOf(mChiffrePoids.getText()),
                            String.valueOf(mChiffreMassGrais.getText()),
                            String.valueOf(mChiffreCalorie.getText()),
                            String.valueOf(mChiffreMassMuscu.getText()),
                            String.valueOf(mChiffreMassOs.getText()),
                            String.valueOf(mChiffreIMC.getText()),
                            String.valueOf(mChiffreMassHydr.getText())));
                    Toast.makeText(SaisiMesureActivity.this, "Mesure du jour ajoutée ! ", Toast.LENGTH_SHORT).show();
                }
                else{
                    db.modifMesure(new Mesure(String.valueOf(PseudoRecu),
                            JourRecu,
                            MoisRecu,
                            AnneeRecu,
                            String.valueOf(mChiffrePoids.getText()),
                            String.valueOf(mChiffreMassGrais.getText()),
                            String.valueOf(mChiffreCalorie.getText()),
                            String.valueOf(mChiffreMassMuscu.getText()),
                            String.valueOf(mChiffreMassOs.getText()),
                            String.valueOf(mChiffreIMC.getText()),
                            String.valueOf(mChiffreMassHydr.getText())));
                    Toast.makeText(SaisiMesureActivity.this, "Mesure du jour modifiée ! ", Toast.LENGTH_SHORT).show();
                }

                //Maj de la mesure du jour affichée
                BoardActivity.MajMesureJour();
                BoardActivity.MajGraph();
                finish();
                }
        });


        // Action lors du clic sur le bouton Valider
        mAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Maj de la mesure du jour affichée
                finish();
            }
        });

    }

    //Pour créer un effect sur le bouton quand on clic dessus
    public static void buttonEffect(View button) {
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