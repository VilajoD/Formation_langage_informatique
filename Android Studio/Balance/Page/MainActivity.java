package com.pandart.balance.Page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pandart.balance.DB.BalanceDataBase;
import com.pandart.balance.Objet.User;
import com.pandart.balance.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button mAnnul_Modif;
    private TextView mModeModif;

    private Button mAnnul_Suppr;
    private TextView mModeSuppr;

    //Image des utilisateurs
    private ImageView mUser1;
    private ImageView mUser2;
    private ImageView mUser3;
    private ImageView mUser4;
    private ImageView mUser5;
    private ImageView mUser6;

    //image des ajouts nouveaux utilisateurs
    private ImageView mAdd1;
    private ImageView mAdd2;
    private ImageView mAdd3;
    private ImageView mAdd4;
    private ImageView mAdd5;
    private ImageView mAdd6;

    //Nom des Utilsateur
    private TextView mPseudo1;
    private TextView mPseudo2;
    private TextView mPseudo3;
    private TextView mPseudo4;
    private TextView mPseudo5;
    private TextView mPseudo6;

    // Variable base de donnée
    private BalanceDataBase db;
    public static ArrayList<User> ListeUser = new ArrayList<>();

    // Création des intent
    Intent VersAjoutUser;
    Intent VersBoard;
    Intent Reactualiser;

    //Variable pour l'image
    private Bitmap imageSelect;

    //Variable de travail
    private boolean ModeModif;
    private boolean ModeSuppr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAnnul_Modif = findViewById(R.id.Annul_Modif);
        mModeModif = findViewById(R.id.Modif);

        mAnnul_Suppr = findViewById(R.id.Annul_Suppr);
        mModeSuppr = findViewById(R.id.Suppr);

        //Lien entre les variable et les zone XML
        mUser1 = findViewById(R.id.User1);
        mUser2 = findViewById(R.id.User2);
        mUser3 = findViewById(R.id.User3);
        mUser4 = findViewById(R.id.User4);
        mUser5 = findViewById(R.id.User5);
        mUser6 = findViewById(R.id.User6);

        mAdd1 = findViewById(R.id.Add1);
        mAdd2 = findViewById(R.id.Add2);
        mAdd3 = findViewById(R.id.Add3);
        mAdd4 = findViewById(R.id.Add4);
        mAdd5 = findViewById(R.id.Add5);
        mAdd6 = findViewById(R.id.Add6);

        mPseudo1 = findViewById(R.id.Pseudo1);
        mPseudo2 = findViewById(R.id.Pseudo2);
        mPseudo3 = findViewById(R.id.Pseudo3);
        mPseudo4 = findViewById(R.id.Pseudo4);
        mPseudo5 = findViewById(R.id.Pseudo5);
        mPseudo6 = findViewById(R.id.Pseudo6);

        imageSelect= null;
        ModeModif = false;
        ModeSuppr = false;

        //Renseigne la variable du nom de la base
        db = new BalanceDataBase(this);

        //Récupération des infos User en base
    //    db.addUser(new User("Tortue","bbb","Melin","Lucie",null,33,"F",165,55));
        ListeUser = db.selectTotalUser();
    //    ListeUser.add(new User("David",null,null,null,null,0,null,0,0));
    //    ListeUser.add(new User("Lucie",null,null,null,null,0,null,0,0));
    //    ListeUser.add(new User("Yuna",null,null,null,null,0,null,0,0));
    //    ListeUser.add(new User("Zelda",null,null,null,null,0,null,0,0));
    //    ListeUser.add(new User("Zelda2",null,null,null,null,0,null,0,0));
    //    ListeUser.add(new User("Zelda3",null,null,null,null,0,null,0,0));

        //Rend visible les ImageView et TextViex en fonction du nombre de User
        switch (ListeUser.size()){
            case 6 :
                mPseudo6.setText(ListeUser.get(5).getPseudo());
                mUser6.setVisibility(View.VISIBLE);
                mPseudo6.setVisibility(View.VISIBLE);
                if (ListeUser.get(5).getImage() != null) {
                    imageSelect = getBitmap(ListeUser.get(5).getImage());
                    mUser6.setImageBitmap(imageSelect);
                }
            case 5 :
                mPseudo5.setText(ListeUser.get(4).getPseudo());
                mUser5.setVisibility(View.VISIBLE);
                mPseudo5.setVisibility(View.VISIBLE);
                if (ListeUser.get(4).getImage() != null) {
                    imageSelect = getBitmap(ListeUser.get(4).getImage());
                    mUser5.setImageBitmap(imageSelect);
                }
            case 4 :
                mPseudo4.setText(ListeUser.get(3).getPseudo());
                mUser4.setVisibility(View.VISIBLE);
                mPseudo4.setVisibility(View.VISIBLE);
                if (ListeUser.get(3).getImage() != null) {
                    imageSelect = getBitmap(ListeUser.get(3).getImage());
                    mUser4.setImageBitmap(imageSelect);
                }
            case 3 :
                mPseudo3.setText(ListeUser.get(2).getPseudo());
                mUser3.setVisibility(View.VISIBLE);
                mPseudo3.setVisibility(View.VISIBLE);
                if (ListeUser.get(2).getImage() != null) {
                            imageSelect = getBitmap(ListeUser.get(2).getImage());
                            mUser3.setImageBitmap(imageSelect);
                }
            case 2 :
                mPseudo2.setText(ListeUser.get(1).getPseudo());
                mUser2.setVisibility(View.VISIBLE);
                mPseudo2.setVisibility(View.VISIBLE);
                if (ListeUser.get(1).getImage() != null) {
                            imageSelect = getBitmap(ListeUser.get(1).getImage());
                             mUser2.setImageBitmap(imageSelect);
                }
            case 1 :
                mPseudo1.setText(ListeUser.get(0).getPseudo());
                mUser1.setVisibility(View.VISIBLE);
                mPseudo1.setVisibility(View.VISIBLE);
                if (ListeUser.get(0).getImage() != null) {
                            imageSelect = getBitmap(ListeUser.get(0).getImage());
                            mUser1.setImageBitmap(imageSelect);
                }
        }

        //Rend visible un bouton ajout en fonction du nombre de User
        switch (ListeUser.size()){
            case 0 :
                mAdd1.setVisibility(View.VISIBLE);
                buttonEffect(mAdd1);
                break;
            case 1 :
                mAdd2.setVisibility(View.VISIBLE);
                buttonEffect(mAdd2);
                break;
            case 2 :
                mAdd3.setVisibility(View.VISIBLE);
                buttonEffect(mAdd3);
                break;
            case 3 :
                mAdd4.setVisibility(View.VISIBLE);
                buttonEffect(mAdd4);
                break;
            case 4 :
                mAdd5.setVisibility(View.VISIBLE);
                buttonEffect(mAdd5);
                break;
            case 5 :
                mAdd6.setVisibility(View.VISIBLE);
                buttonEffect(mAdd6);
                break;
        }

        //Valorisation de l'intente pour aller a une autre activity
        VersAjoutUser = new Intent(MainActivity.this, CreatUserActivity.class);
        VersBoard = new Intent(MainActivity.this, BoardActivity.class);
        Reactualiser = new Intent(MainActivity.this, MainActivity.class);

        // Action lors des clic bouton
        mAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersAjoutUser.putExtra("Traitement", "Création");
                startActivity(VersAjoutUser);
                finish();
            }
        });
        mAdd2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersAjoutUser.putExtra("Traitement", "Création");
                startActivity(VersAjoutUser);
                finish();
            }
        });
        mAdd3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersAjoutUser.putExtra("Traitement", "Création");
                startActivity(VersAjoutUser);
                finish();
            }
        });
        mAdd4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersAjoutUser.putExtra("Traitement", "Création");
                startActivity(VersAjoutUser);
                finish();
            }
        });
        mAdd5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersAjoutUser.putExtra("Traitement", "Création");
                startActivity(VersAjoutUser);
                finish();
            }
        });
        mAdd6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersAjoutUser.putExtra("Traitement", "Création");
                startActivity(VersAjoutUser);
                finish();
            }
        });


        // Action lors des clic sur une User
        mUser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ModeModif){
                    VersAjoutUser.putExtra("Traitement", "Modification");
                    VersAjoutUser.putExtra("Pseudo", ListeUser.get(0).getPseudo());
                    startActivity(VersAjoutUser);
                    finish();
                }else if (ModeSuppr) {
                    confirmeSuppression(ListeUser.get(0).getPseudo(),MainActivity.this);
                }else{
                    VersBoard.putExtra("Pseudo", ListeUser.get(0).getPseudo());
                    startActivity(VersBoard);
                }
            }
        });
        mUser2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ModeModif){
                    VersAjoutUser.putExtra("Traitement", "Modification");
                    VersAjoutUser.putExtra("Pseudo", ListeUser.get(1).getPseudo());
                    startActivity(VersAjoutUser);
                    finish();
                }else if (ModeSuppr) {
                    confirmeSuppression(ListeUser.get(1).getPseudo(),MainActivity.this);
                }else{
                    VersBoard.putExtra("Pseudo", ListeUser.get(1).getPseudo());
                    startActivity(VersBoard);
                }
            }
        });
        mUser3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ModeModif){
                    VersAjoutUser.putExtra("Traitement", "Modification");
                    VersAjoutUser.putExtra("Pseudo", ListeUser.get(2).getPseudo());
                    startActivity(VersAjoutUser);
                    finish();
                }else if (ModeSuppr) {
                    confirmeSuppression(ListeUser.get(2).getPseudo(),MainActivity.this);
                }else {
                    VersBoard.putExtra("Pseudo", ListeUser.get(2).getPseudo());
                    startActivity(VersBoard);
                }
            }
        });
        mUser4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ModeModif){
                    VersAjoutUser.putExtra("Traitement", "Modification");
                    VersAjoutUser.putExtra("Pseudo",ListeUser.get(3).getPseudo());
                    startActivity(VersAjoutUser);
                    finish();
                }else if (ModeSuppr) {
                    confirmeSuppression(ListeUser.get(3).getPseudo(),MainActivity.this);
                }else {
                VersBoard.putExtra("Pseudo",ListeUser.get(3).getPseudo());
                    startActivity(VersBoard);
                }
            }
        });
        mUser5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ModeModif){
                    VersAjoutUser.putExtra("Traitement", "Modification");
                    VersAjoutUser.putExtra("Pseudo", ListeUser.get(4).getPseudo());
                    startActivity(VersAjoutUser);
                    finish();
                }else if (ModeSuppr) {
                    confirmeSuppression(ListeUser.get(4).getPseudo(),MainActivity.this);
                }else {
                    VersBoard.putExtra("Pseudo", ListeUser.get(4).getPseudo());
                    startActivity(VersBoard);
                }
            }
        });
        mUser6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ModeModif){
                    VersAjoutUser.putExtra("Traitement", "Modification");
                    VersAjoutUser.putExtra("Pseudo", ListeUser.get(5).getPseudo());
                    startActivity(VersAjoutUser);
                    finish();
                }else if (ModeSuppr) {
                    confirmeSuppression(ListeUser.get(5).getPseudo(),MainActivity.this);
                }else {
                    VersBoard.putExtra("Pseudo", ListeUser.get(5).getPseudo());
                    startActivity(VersBoard);
                }
            }
        });

        // Action lors des clic bouton
    //    Suppr.setOnClickListener(new View.OnClickListener() {
    //        @Override
    //        public void onClick(View view) {
     //           db.DeleteTotalUser();
      //      }
    //    });

        mAnnul_Modif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModeModif.setVisibility(View.INVISIBLE);
                mAnnul_Modif.setVisibility(View.INVISIBLE);
                ModeModif = false;
            }
        });

        mAnnul_Suppr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mModeSuppr.setVisibility(View.INVISIBLE);
                mAnnul_Suppr.setVisibility(View.INVISIBLE);
                ModeSuppr = false;
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

    public void confirmeSuppression(String Pseudo, Context context){

        //Variable de l'alerte
        AlertDialog alertSuppr;

        //il faut un builder pour utiliser l'alerte.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Alimente les differentes parties de la boite de dialogue d'alerte
        builder.setTitle("Confirmation Suppression");
        builder.setMessage("Voulez-vous réellement supprimer : " + Pseudo + " ?");
        builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Action de supprimer dans la base
                db.DeleteUser(Pseudo);
                db.DeleteMesureUser(Pseudo);
                startActivity(Reactualiser);
                finish();
            }
        });

        alertSuppr = builder.create();
        alertSuppr.show();
    }


    //Permet de mettre en forme le fichier Image pour le visualiser
    public Bitmap getBitmap(byte[] imageByte){
        Bitmap imageBitmap  = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
        return imageBitmap;
    }

    //Partie :  création du menu en haut a droite
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.Modification) {
            mModeModif.setVisibility(View.VISIBLE);
            mAnnul_Modif.setVisibility(View.VISIBLE);
            ModeModif = true;

            mModeSuppr.setVisibility(View.INVISIBLE);
            mAnnul_Suppr.setVisibility(View.INVISIBLE);
            ModeSuppr = false;
        }
        if (item.getItemId() == R.id.Suppression) {
            mModeSuppr.setVisibility(View.VISIBLE);
            mAnnul_Suppr.setVisibility(View.VISIBLE);
            ModeSuppr = true;

            mModeModif.setVisibility(View.INVISIBLE);
            mAnnul_Modif.setVisibility(View.INVISIBLE);
            ModeModif = false;
        }
        return super.onOptionsItemSelected(item);
    }

}