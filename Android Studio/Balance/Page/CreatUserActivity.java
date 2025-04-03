package com.pandart.balance.Page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pandart.balance.DB.BalanceDataBase;
import com.pandart.balance.Objet.User;
import com.pandart.balance.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreatUserActivity extends AppCompatActivity {

    //Infos utilisateurs affichées
    private ImageView mImageUser;
    private ImageView rotate;
    private TextView mTextPseudo;
    private EditText mPseudo_Saisi;
    private TextView mTextMdp;
    private EditText mMdp_Saisi;
    private TextView mTextNom;
    private EditText mNom_Saisi;
    private TextView mTextPrenom;
    private EditText mPrenom_Saisi;
    private TextView mTextAge;
    private EditText mAge_Saisi;
    private TextView mTextSexe;
    private Spinner mSexe_Saisi;
    private TextView mTextTaille;
    private EditText mTaille_Saisi;
    private TextView mTextObjectif;
    private EditText mObjectif_Saisi;
    private ImageButton mValider;
    private ImageButton mRetour;


    //Variable pour Intent
    Intent VersMainMenu;
    private String PseudoRecu;
    private String TypeTrait;

    // Variable base de donnée
    private BalanceDataBase db;


    //Variable de travail pour le traitement de l'image (insertion en table ou affiche a l'ecran)
    private byte[] imageBlob;
    private Bitmap imageSelect;
    private int rotation;
    private String photoPath;
    private static final int RETOUR_GALERIE = 1;
    private static final int RETOUR_PRISE_PHOTO = 2;
    private User UserAModif;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creat_user);

        //Lien entre les variable et les zone XML
        mImageUser = findViewById(R.id.PhotoProfil);
        rotate = findViewById(R.id.buttonRotate);
        mPseudo_Saisi = findViewById(R.id.Saisie_Pseudo);
        mMdp_Saisi = findViewById(R.id.Saisie_Mdp);
        mNom_Saisi = findViewById(R.id.Saisie_Nom);
        mPrenom_Saisi = findViewById(R.id.Saisie_Prenom);
        mAge_Saisi = findViewById(R.id.Saisie_Age);
        mSexe_Saisi = findViewById(R.id.Saisie_Sexe);
        mTaille_Saisi = findViewById(R.id.Saisie_Taille);
        mObjectif_Saisi = findViewById(R.id.Saisie_Objectif);
        mValider = findViewById(R.id.BoutonValider);
        mRetour = findViewById(R.id.BoutonRetour);

        //Init les variable
        imageSelect = null;
        imageBlob = null;

        //Valorisation de l'intente pour aller a une autre activity
        VersMainMenu = new Intent(CreatUserActivity.this, MainActivity.class);

        //Active les effets de clic sur les boutons images
        buttonEffect(mValider);
        buttonEffect(mRetour);

        //Création d'une liste d'élément à mettre dans le Spinner Pour le Sexe
        List ListSexe = new ArrayList();
        ListSexe.add("");
        ListSexe.add("M");
        ListSexe.add("F");
        ListSexe.add("Autre");
        ListSexe.add("Cheval de bataille de l'espace");


        //Parti pour les Spinners
        ArrayAdapter adapterSpinner = new ArrayAdapter(
                this,
                R.layout.spinner_list,
                ListSexe
        );
        /* On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item) */
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner
        mSexe_Saisi.setAdapter(adapterSpinner);


        //Renseigne la variable du nom de la base
        db = new BalanceDataBase(this);

        //Récupération des infos du User depuis l'activity Main.
        PseudoRecu = getIntent().getStringExtra("Pseudo");
        TypeTrait = getIntent().getStringExtra("Traitement");

        if (TypeTrait.equals("Modification")){
            UserAModif = db.selectUser(PseudoRecu);

            mPseudo_Saisi.setText(UserAModif.getPseudo());
            mPseudo_Saisi.setFocusable(false);
            mPseudo_Saisi.setBackgroundColor(android.R.color.darker_gray);

            mNom_Saisi.setText(UserAModif.getNom());
            mPrenom_Saisi.setText(UserAModif.getPrenom());
            mAge_Saisi.setText(String.valueOf(UserAModif.getAge()));
            mTaille_Saisi.setText(String.valueOf(UserAModif.getTaille()));
            mObjectif_Saisi.setText(String.valueOf(UserAModif.getObjectif()));

            mSexe_Saisi.setSelection(ListSexe.indexOf(UserAModif.getSexe()));

            if (UserAModif.getImage() != null){
                imageSelect = getBitmap(UserAModif.getImage());
                mImageUser.setImageBitmap(imageSelect);
                rotate.setVisibility(View.VISIBLE);
            }
        }

        // Action lors du clic sur le bouton Valider
        mValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(mPseudo_Saisi.getText()).isEmpty()) {
                    Toast.makeText(CreatUserActivity.this, "Le Pseudo est obligatoire", Toast.LENGTH_LONG).show();
                } else {
                        //Mise en forme de l'image pour insertion dans base
                        if (imageSelect != null) {
                            imageBlob = getBytes(imageSelect);
                        } else {
                            imageBlob = null;
                        }
                        if (TypeTrait.equals("Modification")) {
                            db.modifUser(new User(String.valueOf(mPseudo_Saisi.getText()),
                                    String.valueOf(mMdp_Saisi.getText()),
                                    String.valueOf(mNom_Saisi.getText()),
                                    String.valueOf(mPrenom_Saisi.getText()),
                                    imageBlob,
                                    Integer.parseInt(String.valueOf(mAge_Saisi.getText())),
                                    mSexe_Saisi.getSelectedItem().toString(),
                                    Integer.parseInt(String.valueOf(mTaille_Saisi.getText())),
                                    Integer.parseInt(String.valueOf(mObjectif_Saisi.getText()))));
                            Toast.makeText(CreatUserActivity.this, "Vous avez modifié l'utilsateur : " + String.valueOf(mPseudo_Saisi.getText()), Toast.LENGTH_SHORT).show();
                            startActivity(VersMainMenu);
                            finish();
                        }
                        if (TypeTrait.equals("Création")) {
                            if ((db.selectUser(String.valueOf(mPseudo_Saisi.getText()))) == null) {
                            db.addUser(new User(String.valueOf(mPseudo_Saisi.getText()),
                                    String.valueOf(mMdp_Saisi.getText()),
                                    String.valueOf(mNom_Saisi.getText()),
                                    String.valueOf(mPrenom_Saisi.getText()),
                                    imageBlob,
                                    Integer.parseInt(String.valueOf(mAge_Saisi.getText())),
                                    mSexe_Saisi.getSelectedItem().toString(),
                                    Integer.parseInt(String.valueOf(mTaille_Saisi.getText())),
                                    Integer.parseInt(String.valueOf(mObjectif_Saisi.getText()))));
                            Toast.makeText(CreatUserActivity.this, "Vous avez créé le nouvel utilsateur : " + String.valueOf(mPseudo_Saisi.getText()), Toast.LENGTH_SHORT).show();
                            startActivity(VersMainMenu);
                            finish();
                            } else {
                                Toast.makeText(CreatUserActivity.this, "Le Pseudo est deja utilisé", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        // Action lors du clic sur le bouton Retour
        mRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(VersMainMenu);
                finish();
                }
        });

//Pour les clic sur image menupop
        mImageUser.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Création d'un instance de popupMenu
                PopupMenu popuMenu = new PopupMenu(CreatUserActivity.this, view);
                //On fait un Inflate dessus avec le lien vers le menu qui doit apparaitre
                popuMenu.inflate(R.menu.menu_ajout_photo);

                //Pour savoir quoi faire en cliquant sur les bouton du menu
                popuMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.photoApp) {
                            //Intent pour lancher l'appareil photo
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //Vérifier qu'il y a bien un appareil photo
                            if (intent.resolveActivity(getPackageManager()) != null) {
                                // On doit passer par un fichier temporaire pour avoir une photo correct
                                //Deja on réupere un TimeStemp actuelle
                                String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                // On récupe le chemin des photo
                                File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                try {
                                    //On se créer un fichier temp
                                    File photoFile = File.createTempFile("photo" + time, ".jpeg", photoDir);
                                    photoPath = photoFile.getAbsolutePath();
                                    // créer l'URI
                                    Uri photoUri = FileProvider.getUriForFile(CreatUserActivity.this,
                                            CreatUserActivity.this.getApplicationContext().getPackageName() + ".provider",
                                            photoFile);
                                    //transfert Uri vers l'intent pour enregistrer photo dans fichier temporaire
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                                    //Ouvirir l'intent
                                    startActivityForResult(intent, RETOUR_PRISE_PHOTO);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (menuItem.getItemId() == R.id.Modification) {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, RETOUR_GALERIE);
                        }
                        return false;
                    }
                });
                // On fait en sorte que le menu soit vu
                popuMenu.show();
            }
        });

        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matrix mat = new Matrix();
                rotation = +90;
                mat.postRotate(rotation);
                imageSelect = Bitmap.createBitmap(imageSelect, 0, 0, imageSelect.getWidth(), imageSelect.getHeight(), mat, true);
                mImageUser.setImageBitmap(imageSelect);
            }
        });


    }// Fin onCreate


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

    // Quand il y a un retour apres un appel en 'startActivityForResult' le pgm attend un retour
    // requestCode --> Le code request qui est envoyer quand on fait le startActivityForResult pour faire la diff si on a fait plusiurs
    // appel de  startActivityForResult
    // resultCode c'est simplement le code retour
    // data c'est la donnée ramené
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Le Super c'est car cette methode est deja défini dans la class mere, donc il faut lui donner les info pour son constructeur
        super.onActivityResult(requestCode, resultCode, data);

        // Savoir de quelle retour il s'agit c'est bien le fait de récuperer une image ? et qu'on récupere bien quelque chose
        if (requestCode ==RETOUR_GALERIE && resultCode==RESULT_OK) {
            //accès a l'image a partir de data. Info de type URI
            Uri selectImage = data.getData();

            //Création InputeStream, c'est pour permettre d'ouvrir et lire un fichier qu'on a eu en retour
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(selectImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //On doit décoder l'info pour qu'elle puisse etre vu par l'utilisateur
            imageSelect = BitmapFactory.decodeStream(inputStream);
            mImageUser.setImageBitmap(imageSelect);
            rotate.setVisibility(View.VISIBLE);


        }else{
            Toast.makeText(this, "Rien récupéré...", Toast.LENGTH_SHORT);
        }

        if (requestCode ==RETOUR_PRISE_PHOTO && resultCode==RESULT_OK) {
            imageSelect = BitmapFactory.decodeFile(photoPath);

            //Faire tourner l'image de 90°
            Matrix mat = new Matrix();
            mat.postRotate(90);
            imageSelect = Bitmap.createBitmap(imageSelect, 0, 0,imageSelect.getWidth(),imageSelect.getHeight(), mat, true);
            mImageUser.setImageBitmap(imageSelect);

            rotate.setVisibility(View.VISIBLE);
        }
    }

    //Permet de mettre en forme le fichier Image pour le sauvegarder en bases
    public byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, stream);
        return stream.toByteArray();
    }

    //Permet de mettre en forme le fichier Image pour le visualiser
    public Bitmap getBitmap(byte[] imageByte){
        Bitmap imageBitmap  = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
        return imageBitmap;
    }

}