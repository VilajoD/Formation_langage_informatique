package com.example.cafth.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cafth.R;

public class AideMenuListeActivity extends AppCompatActivity {

    private ImageView Aide1;
    private ImageView Aide2;
    private ImageView Aide3;
    private ImageView Aide4;

    private ImageView flecheAvant;
    private ImageView flecheArriere;

    private ImageView retour;

    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aide_menu_liste);


        Aide1 = findViewById(R.id.Aide1);
        Aide2 = findViewById(R.id.Aide2);
        Aide3 = findViewById(R.id.Aide3);
        Aide4 = findViewById(R.id.Aide4);

        flecheAvant = findViewById(R.id.flecheAvant);
        flecheArriere = findViewById(R.id.flecheArriere);

        retour = findViewById(R.id.retour);

        page = 1;

        flecheAvant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (page) {
                    case 1:
                        flecheArriere.setVisibility(View.VISIBLE);
                        Aide1.setVisibility(View.INVISIBLE);
                        Aide2.setVisibility(View.VISIBLE);
                        page += 1;
                        break;
                    case 2:
                        Aide2.setVisibility(View.GONE);
                        Aide3.setVisibility(View.VISIBLE);
                        page += 1;
                        break;
                    case 3:
                        flecheAvant.setVisibility(View.GONE);
                        Aide3.setVisibility(View.GONE);
                        Aide4.setVisibility(View.VISIBLE);
                        page += 1;
                        break;
                }
            }
        });

        flecheArriere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (page) {
                    case 2:
                        flecheArriere.setVisibility(View.GONE);
                        Aide1.setVisibility(View.VISIBLE);
                        Aide2.setVisibility(View.GONE);
                        page -= 1;
                        break;
                    case 3:
                        Aide2.setVisibility(View.VISIBLE);
                        Aide3.setVisibility(View.GONE);
                        page -= 1;
                        break;
                    case 4:
                        flecheAvant.setVisibility(View.VISIBLE);
                        Aide3.setVisibility(View.VISIBLE);
                        Aide4.setVisibility(View.GONE);
                        page -= 1;
                        break;
                }
            }
        });

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}