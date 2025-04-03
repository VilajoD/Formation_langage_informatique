package com.example.cafth.page;

import static com.example.cafth.page.GestionBoissonActivity.adapterGestionBoisson;
import static com.example.cafth.page.GestionBoissonActivity.valeurListGestionBoisson;
import static com.example.cafth.page.Menu_Liste_Activity.adapter;
import static com.example.cafth.page.Menu_Liste_Activity.valeurList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;
import com.example.cafth.objet.Boisson;
import com.example.cafth.objet.Categorie;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AjoutBoissonActivity extends AppCompatActivity {

    //Variable pour le fichier SharedPreferences
    private SharedPreferences sharedPreferences;

    //Déclaration des variables
    private TextView nomBoisson;
    private TextView quantiteBoisson;
    private TextView typeBoisson;
    private TextView categorieBoisson;
    private Spinner spinnerBoisson;
    private Spinner spinnerCategorie;
    private EditText saisiNomBoisson;
    private EditText saisiQuantiteBoisson;
    private ImageButton boutonAjoutBoisson;
    private ImageButton boutonAjoutBoissonSombre;
    private ImageButton boutonAnnuler;
    private ImageView image;
    private ImageView rotate;
    private TextView description;
    private EditText saisieDescription;

    private Switch switchUnitaireVrac;
    private TextView unitaire;
    private TextView enVrac;
    private TextView calculEnVrac;
    private EditText grammeTotal;
    private EditText grammeDose;
    private LinearLayout layoutQuant;
    private LinearLayout layoutTotal;
    private LinearLayout layoutDose;
    private ImageView info_Vrac;

    private static TextView textInfoVrac;
    private static ImageButton boutonAnnuleInfoVrac;

    //Variable de travail
    private Boolean nomOk;
    private Boolean quantiteOk;

    //Variable de travail pour le traitement de l'image (insertion en table ou affiche a l'ecran)
    private byte[] imageBlob;
    private Bitmap imageSelect;
    private int rotation;
    private String photoPath;

    //Variable pour la DB
    private CaftheDataBase db;
    private ArrayList selectBoisson;

    //Intente pour aller a l'activiyé Menu
    private String Type_Boisson;
    private String RecupOrigine;

    //Partie Spinner
    List ListCategorie;
    ArrayList <Categorie> ListeCategorieSelect;
    Boisson nouvelleBoisson;

    ArrayAdapter adapterSpinnerCategorie;

    //Constante
    private static final int RETOUR_GALERIE = 1;
    private static final int RETOUR_PRISE_PHOTO = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_boisson);


        //Lien entre les varaible et les zone XML
        nomBoisson = (TextView) findViewById(R.id.nom_boisson);
        quantiteBoisson = (TextView) findViewById(R.id.quantite_boisson);
        typeBoisson = (TextView) findViewById(R.id.type_de_boisson);
        categorieBoisson = (TextView) findViewById(R.id.categorie_de_boisson);
        spinnerBoisson = (Spinner) findViewById(R.id.spinner_type_boisson);
        spinnerCategorie = (Spinner) findViewById(R.id.spinner_categorie_boisson);
        saisiNomBoisson = (EditText) findViewById(R.id.saisie_nom_boisson);
        saisiQuantiteBoisson = (EditText) findViewById(R.id.saisie_quantite_boisson);
        boutonAjoutBoisson = (ImageButton) findViewById(R.id.bouton_ajout_categorie);
        boutonAjoutBoissonSombre = (ImageButton) findViewById(R.id.bouton_ajout_categorie_sombre);
        boutonAnnuler = (ImageButton) findViewById(R.id.bouton_annuler);
        image =  findViewById(R.id.Image);
        rotate =  findViewById(R.id.buttonRotate);
        saisieDescription = (EditText) findViewById(R.id.Creation_Saisie_Desciption);
        description = (TextView) findViewById(R.id.Creation_Description);

        switchUnitaireVrac =  findViewById(R.id.switchUnitaireVrac);
        unitaire =  findViewById(R.id.texte_Unitaire);
        enVrac =  findViewById(R.id.texte_EnVrac);
        calculEnVrac =  findViewById(R.id.saisie_quantite_gramme_unitaire);
        grammeTotal =  findViewById(R.id.saisie_quantite_gramme_total);
        grammeDose =  findViewById(R.id.saisie_quantite_gramme_dose);

        layoutQuant =  findViewById(R.id.linearLayoutQuant);
        layoutTotal =  findViewById(R.id.linearLayoutTotal);
        layoutDose =  findViewById(R.id.linearLayoutDose);

        info_Vrac =  findViewById(R.id.modif_info_Vrac);

        textInfoVrac =  findViewById(R.id.creationTextInfoVrac);
        boutonAnnuleInfoVrac =  findViewById(R.id.creationBoutonAnnuleInfoVrac);

        // Initialise le fichier preferenceZeroQuantite
        sharedPreferences = this.getSharedPreferences("Préférences", Context.MODE_PRIVATE);

        //rendre le bouton non cliquable
        boutonAjoutBoisson.setEnabled(false);

        //Init les variable
        nomOk = false;
        quantiteOk = false;
        selectBoisson = null;
        imageSelect = null;
        imageBlob = null;

        //Partie calcul de quantité
        grammeDose.setText(String.valueOf(sharedPreferences.getInt("Dose_Gramme",2)));


        //Partie Spinner
        spinnerCategorie.setVisibility(View.GONE);
        categorieBoisson.setVisibility(View.GONE);
        ListeCategorieSelect = new ArrayList<Categorie>();
        ListCategorie = new ArrayList();


        //Création d'une liste d'élément à mettre dans le Spinner Type de boisson
        List ListType = new ArrayList();
        ListType.add("Café");
        ListType.add("Thé");
        ListType.add("Chocolat");
        ListType.add("Infusion");

        //Renseigne la variable du nom de la base
        db = new CaftheDataBase(this);

        //Récupération de la donnée Type_Boisson --> On la garde pour savoir dans quelle type de boisson on est. (Pour le moment je ne sais pas
        // faire autre pour garder le titre sur l'activity precedente...
        Type_Boisson = getIntent().getStringExtra("Type_Boisson");
        RecupOrigine = getIntent().getStringExtra("Origine");

        //Active les effets de clic sur les boutons images
        buttonEffect(boutonAjoutBoisson);
        buttonEffect(boutonAnnuler);

        //Action quand ecrit dans une zone de text
        saisiNomBoisson.addTextChangedListener(new TextWatcher() {
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
                    nomOk = true;
                } else {
                    nomOk = false;
                }
                //Si nom et quantité alors bouton validable
                if (nomOk && quantiteOk) {
                    boutonAjoutBoisson.setEnabled(true);
                    boutonAjoutBoisson.setVisibility(View.VISIBLE);
                } else {
                    boutonAjoutBoisson.setEnabled(false);
                    boutonAjoutBoisson.setVisibility(View.INVISIBLE);
                }
            }
        });

        saisiQuantiteBoisson.addTextChangedListener(new TextWatcher() {
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
                    quantiteOk = true;
                } else {
                    quantiteOk = false;
                }
                //Si nom et quantité alors bouton validable
                if (nomOk && quantiteOk) {
                    boutonAjoutBoisson.setEnabled(true);
                    boutonAjoutBoisson.setVisibility(View.VISIBLE);
                } else {
                    boutonAjoutBoisson.setEnabled(false);
                    boutonAjoutBoisson.setVisibility(View.INVISIBLE);
                }
            }
        });

//Action quand ecrit dans une zone de text
        grammeTotal.addTextChangedListener(new TextWatcher() {
               @Override
               public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

               }

               @Override
               public void afterTextChanged(Editable editable) {
                   if (!grammeTotal.getText().toString().isEmpty() && !grammeTotal.getText().toString().equals("0")){
                        if (!grammeDose.getText().toString().equals("0") && !grammeDose.getText().toString().isEmpty()) {
                           int total = Integer.parseInt(grammeTotal.getText().toString());
                           int dose = Integer.parseInt(grammeDose.getText().toString());
                           calculEnVrac.setText(String.valueOf((total/dose)));
                           saisiQuantiteBoisson.setText(calculEnVrac.getText());
                           quantiteOk = true;
                       }
                       //           grammeDose.setText("3");
                   } else {
                       calculEnVrac.setText("0");
                       saisiQuantiteBoisson.setText(null);
                       quantiteOk = false;
                   }

                   //Si nom et quantité alors bouton validable
                   if (nomOk && quantiteOk) {
                       boutonAjoutBoisson.setEnabled(true);
                       boutonAjoutBoisson.setVisibility(View.VISIBLE);
                   } else {
                       boutonAjoutBoisson.setEnabled(false);
                       boutonAjoutBoisson.setVisibility(View.INVISIBLE);
                   }

               }
           });

        //Action quand ecrit dans une zone de text
        grammeDose.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              }

              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

              }

              @Override
              public void afterTextChanged(Editable editable) {
                if (!grammeDose.getText().toString().equals("0") && !grammeDose.getText().toString().isEmpty()) {
                    if (!grammeTotal.getText().toString().isEmpty()) {
                        int total = Integer.parseInt(grammeTotal.getText().toString());
                        int dose = Integer.parseInt(grammeDose.getText().toString());
                        calculEnVrac.setText(String.valueOf((total/dose)));
                        saisiQuantiteBoisson.setText(calculEnVrac.getText());
                        quantiteOk = true;
                    }
         //           grammeDose.setText("3");
                } else {
                    calculEnVrac.setText("0");
                    saisiQuantiteBoisson.setText(null);
                    quantiteOk = false;
                }

                  //Si nom et quantité alors bouton validable
                  if (nomOk && quantiteOk) {
                      boutonAjoutBoisson.setEnabled(true);
                      boutonAjoutBoisson.setVisibility(View.VISIBLE);
                  } else {
                      boutonAjoutBoisson.setEnabled(false);
                      boutonAjoutBoisson.setVisibility(View.INVISIBLE);
                  }

              }
        });
                                              //Action quand clic sur bouton
        boutonAjoutBoisson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Mise en forme de l'image pour insertion dans base
                if (imageSelect != null){
                    imageBlob = getBytes(imageSelect);
                }else {
                    imageBlob = null;
                }

                //Création des variable.
                //- nouvelleBoisson pour récuperer la nouvelle boisson a inserer dans la table
                //- resultat pour récuperer le code retour de l'insert et voir si tout c'est bien passé
                if (ListeCategorieSelect.size() != 0) {
                    nouvelleBoisson = new Boisson(String.valueOf(spinnerBoisson.getSelectedItem().toString()), String.valueOf(spinnerCategorie.getSelectedItem().toString()), String.valueOf(saisiNomBoisson.getText()), new Integer(String.valueOf(saisiQuantiteBoisson.getText())),imageBlob,String.valueOf(saisieDescription.getText()));
                } else {
                    nouvelleBoisson = new Boisson(String.valueOf(spinnerBoisson.getSelectedItem().toString()), null, String.valueOf(saisiNomBoisson.getText()), new Integer(String.valueOf(saisiQuantiteBoisson.getText())),imageBlob,String.valueOf(saisieDescription.getText()));
                }

                boolean resultat;

                //Verification que la boisson n'est pas deja existante
                selectBoisson = db.selectBoissonUnitaire(nouvelleBoisson);
                if (selectBoisson.size() == 0) {

                    //création nouvelle boisson (la clé est généré automatiquement). Pour le moment la clé de la "nouvelleBoisson" est a null
                    resultat = db.addBoisson(nouvelleBoisson);

                    //Récupértion de la boisson qui vient juste d'etre inserer mais avec ca nouvelle Clé
                    nouvelleBoisson = db.selectBoissonUnitaire(nouvelleBoisson).get(0);

                    if (resultat) {
                        if (RecupOrigine.contains("GestionBoisson")) {
                            //Action de modifier dans la liste puis actualisation de l'affichage
                            valeurListGestionBoisson.add(nouvelleBoisson);
                            if (valeurListGestionBoisson.size() == 1){
                                GestionBoissonActivity.ListGestionBoisson.setAdapter(adapterGestionBoisson);
                            }
                            GestionBoissonActivity.DebutText1.setVisibility(View.GONE);
                            GestionBoissonActivity.DebutText2.setVisibility(View.GONE);
                            GestionBoissonActivity.boutonRecherche.setVisibility(View.VISIBLE);
                            GestionBoissonActivity.textRecherche.setVisibility(View.VISIBLE);
                            adapterGestionBoisson.notifyDataSetChanged();

                        } else if (RecupOrigine.contains("ListBoisson")) {
                            //Regarde si le type correspond toujours a la liste qu'on regarde et verifie dans les preference si on affiche
                            // ou non la boisson si elle est a zero
                            if (Type_Boisson.contains(nouvelleBoisson.getType()) && (nouvelleBoisson.getQuantite() > 0 || sharedPreferences.getBoolean("Presence_Zero_Quantite", true))) {
                                valeurList.add(nouvelleBoisson);
                                //Se sont les textes de bienvenu, si quelque chose dans la liste, on les effaces
                                Menu_Liste_Activity.DebutText1.setVisibility(View.GONE);
                                Menu_Liste_Activity.DebutText2.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        Toast.makeText(AjoutBoissonActivity.this, "Vous avez ajouté : " + nouvelleBoisson.getNom() + " en tant que : " + nouvelleBoisson.getType(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AjoutBoissonActivity.this, "Oups, il y a eu un souci...", Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(AjoutBoissonActivity.this, "La boisson " + nouvelleBoisson.getNom() + " existe deja.", Toast.LENGTH_LONG).show();

                }
            }
        });

        boutonAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //Pour les clic sur image menupop
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Création d'un instance de popupMenu
                PopupMenu popuMenu = new PopupMenu(AjoutBoissonActivity.this,view);
                //On fait un Inflate dessu avec le lien vers le menu qui doit apparaitre
                popuMenu.inflate(R.menu.menu_ajout_photo);

                //Pour savoir quoi faire en cliquant sur les bouton du menu
                popuMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.photoApp){
                            //Intent pour lancher l'appareil photo
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            //Vérifier qu'il y a bien un appareil photo
                            if (intent.resolveActivity(getPackageManager()) != null ){
                                // On doit passer par un fichier temporaire pour avoir une photo correct
                                //Deja on réupere un TimeStamp actuelle
                                String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                // On récupe le chemin des photo
                                File photoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                                try {
                                    //On se créer un fichier temp
                                    File photoFile = File.createTempFile("photo"+time,".jpeg",photoDir);
                                    photoPath = photoFile.getAbsolutePath();
                                    // créer l'URI
                                    Uri photoUri = FileProvider.getUriForFile(AjoutBoissonActivity.this,
                                            AjoutBoissonActivity.this.getApplicationContext().getPackageName()+".provider",
                                            photoFile);
                                    //transfert Uri vers l'intent pour enregistrer photo dans fichier temporaire
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                                    //Ouvirir l'intent
                                    startActivityForResult(intent,RETOUR_PRISE_PHOTO);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (menuItem.getItemId() == R.id.photoGal) {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent,RETOUR_GALERIE);
                        }
                        return false;
                    }
                });
                // On fait en sorte que le menu soit vu
                popuMenu.show();
            }
        });


        rotate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Matrix mat = new Matrix();
                rotation =+ 90;
                mat.postRotate(rotation);
                imageSelect = Bitmap.createBitmap(imageSelect, 0, 0,imageSelect.getWidth(),imageSelect.getHeight(), mat, true);
                image.setImageBitmap(imageSelect);

                if (nomOk && quantiteOk) {
                    boutonAjoutBoisson.setEnabled(true);
                    boutonAjoutBoisson.setVisibility(View.VISIBLE);
                }
            }
        });


        switchUnitaireVrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (switchUnitaireVrac.isChecked()){

                    saisiQuantiteBoisson.setVisibility(View.INVISIBLE);
                    unitaire.setVisibility(View.INVISIBLE);
                    enVrac.setVisibility(View.VISIBLE);

                    layoutQuant.setVisibility(View.VISIBLE);
                    layoutTotal.setVisibility(View.VISIBLE);
                    layoutDose.setVisibility(View.VISIBLE);
                    info_Vrac.setVisibility(View.VISIBLE);

                }else{
                    saisiQuantiteBoisson.setVisibility(View.VISIBLE);
                    unitaire.setVisibility(View.VISIBLE);
                    enVrac.setVisibility(View.INVISIBLE);

                    layoutQuant.setVisibility(View.INVISIBLE);
                    layoutTotal.setVisibility(View.INVISIBLE);
                    layoutDose.setVisibility(View.INVISIBLE);
                    info_Vrac.setVisibility(View.INVISIBLE);
                }
            }
        });

        info_Vrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInfoVrac.setVisibility(View.VISIBLE);
                boutonAnnuleInfoVrac.setVisibility(View.VISIBLE);
            }
        });

        boutonAnnuleInfoVrac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInfoVrac.setVisibility(View.INVISIBLE);
                boutonAnnuleInfoVrac.setVisibility(View.INVISIBLE);
            }
        });

        //Parti pour le Spinner

        //Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
        //        un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        ArrayAdapter adapterSpinner = new ArrayAdapter(
                this,
                R.layout.spinner_list,
                ListType
        );

        adapterSpinnerCategorie  = new ArrayAdapter(
                this,
                R.layout.spinner_list,
                ListCategorie
        );

        /* On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item) */
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner
        spinnerBoisson.setAdapter(adapterSpinner);
        spinnerBoisson.setSelection(ListType.indexOf(Type_Boisson));

        //Ecouter ce qui est choisi dans le spinner spinnerBoisson. Quand choisi, regarde dans la base si existe des catégorie pour ce type.
        // Si existe des catégorie pour ce type, on fait apparaitre le spinner des catégories
        spinnerBoisson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                //Valorise la liste des info a afficher
                ListeCategorieSelect = db.selectListCategorie(spinnerBoisson.getSelectedItem().toString());

                //On verifie si on récuperer quelque chose
                if (ListeCategorieSelect.size() != 0) {
                    ListCategorie.clear();
                    for ( Categorie i: ListeCategorieSelect
                         ) {
                        ListCategorie.add(i.getCategorie().toString());
                    }

                    spinnerCategorie.setVisibility(View.VISIBLE);
                    categorieBoisson.setVisibility(View.VISIBLE);
                    adapterSpinnerCategorie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategorie.setAdapter(adapterSpinnerCategorie);

                } else {
                    spinnerCategorie.setVisibility(View.GONE);
                    categorieBoisson.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    } // Fin onCreate



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

            //Création InputeStream, c'est pour permettre d'ouvrire et lire un fichier qu'on a eu en retour
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(selectImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //On doit décoder l'info pour qu'elle puisse etre vu par l'utilisateur
            imageSelect = BitmapFactory.decodeStream(inputStream);
            image.setImageBitmap(imageSelect);
            rotate.setVisibility(View.VISIBLE);


        }else{
            Toast.makeText(this, "Rien récupéré", Toast.LENGTH_SHORT);
        }

        if (requestCode ==RETOUR_PRISE_PHOTO && resultCode==RESULT_OK) {
            imageSelect = BitmapFactory.decodeFile(photoPath);

            //Faire tourner l'image de 90°
            Matrix mat = new Matrix();
            mat.postRotate(90);
            imageSelect = Bitmap.createBitmap(imageSelect, 0, 0,imageSelect.getWidth(),imageSelect.getHeight(), mat, true);
            image.setImageBitmap(imageSelect);

            rotate.setVisibility(View.VISIBLE);
        }
    }

    //Permet de mettre en forme le fichier Image pour le sauvegarder en bases
    public byte[] getBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50, stream);
        return stream.toByteArray();
    }
}