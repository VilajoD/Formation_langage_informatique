package com.pandart.balance.Page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.pandart.balance.DB.BalanceDataBase;
import com.pandart.balance.Objet.Mesure;
import com.pandart.balance.Objet.User;
import com.pandart.balance.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TimeZone;

public class BoardActivity extends AppCompatActivity {

    //Variable lien avec XML
    private  Button mDate;
    public static TextView mTextBonjour;
    private static TextView mTextMesureJour;
    private static Button mBouttonMesure;
    private static Spinner mSpinnerTypeGraph;
    private static Spinner mSpinnerTempo;
    private static GraphView mGraph;
    private ImageButton mBouttonGauche;
    private ImageButton mBouttonDroit;
    private TextView mTextpoids;
    public static TextView mChiffrePoids;
    private TextView mTextMassGrai;
    public static TextView mChiffreMassGrais;
    private TextView mTextCalorie;
    public static TextView mChiffreCalorie;
    private TextView mTextMassMuscu;
    public static TextView mChiffreMassMuscu;
    private TextView mTextMassOs;
    public static TextView mChiffreMassOs;
    private TextView mTextIMC;
    public static TextView mChiffreIMC;
    private TextView mTextMassHydr;
    public static TextView mChiffreMassHydr;

    //Variable pour Intente
    private static String PseudoRecu;
    Intent VersNouvelleMesure;

    //Variable de travail
    private static int JourSaisi;
    private static int MoisSaisi;
    private static int AnneeSaisi;
    private static int Plusgrand;

    // Variable base de donnée
    private static BalanceDataBase db;

    //Variable de travail
    private static Mesure MesureSelect;
    public static ArrayList<Mesure> MesureList;
    public static LineGraphSeries<DataPoint> series;
    //public static LineGraphSeries<DataPoint> seriesObjectif;
    public static LineGraphSeries<DataPoint> seriesJour;
    public static LineGraphSeries<DataPoint> PointeurSoloHor;
    public static LineGraphSeries<DataPoint> PointeurSoloVert;
    public static LineGraphSeries<DataPoint> PointeurVertic;
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //Lien entre les varaible et les zone XML
        mDate = findViewById(R.id.Date);
        mTextBonjour = findViewById(R.id.TextBonjour);
        mTextMesureJour = findViewById(R.id.TextMesureJour);
        mBouttonMesure = findViewById(R.id.BouttonMesure);
        mSpinnerTypeGraph = findViewById(R.id.SpinnerTypeGraph);
        mSpinnerTempo = findViewById(R.id.SpinnerTempo);
        mGraph = findViewById(R.id.Graph);
        mBouttonGauche = findViewById(R.id.BouttonGauche);
        mBouttonDroit = findViewById(R.id.BouttonDroit);
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

        //Récupération des infos du User depuis l'activity Main.
        PseudoRecu = getIntent().getStringExtra("Pseudo");
        mTextBonjour.setText("Bonjour " +PseudoRecu);

        //Maj de la date
        initDatePicker();
        mDate.setText(getTodaysDate());

        //Renseigne la variable du nom de la base
        db = new BalanceDataBase(this);

        //Création d'une liste d'élément à mettre dans le Spinner pour la temporalité
        List ListTempo = new ArrayList();
        ListTempo.add("Jour");
        ListTempo.add("Mois");
        ListTempo.add("Année");

        //Création d'une liste d'élément à mettre dans le Spinner pour le type de graphique
        List ListTypeGraph = new ArrayList();
        ListTypeGraph.add("Poids");
        ListTypeGraph.add("Masse Graisseuse");
        ListTypeGraph.add("Masse Musculaire");
        ListTypeGraph.add("Masse Osseuse");
        ListTypeGraph.add("Masse Hydrique");
        ListTypeGraph.add("Calorie");
        ListTypeGraph.add("IMC");


        //Parti pour les Spinners
        //Le Spinner a besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
        // un fichier de presentation par défaut( android.R.layout.simple_spinner_item)
        ArrayAdapter adapterSpinnerTempo = new ArrayAdapter(
                this,
                R.layout.spinner_list,
                ListTempo
        );
        ArrayAdapter adapterSpinnerTypeGraph = new ArrayAdapter(
                this,
                R.layout.spinner_list,
                ListTypeGraph
        );

        /* On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item) */
        adapterSpinnerTempo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner
        mSpinnerTempo.setAdapter(adapterSpinnerTempo);

        /* On definit une présentation du spinner quand il est déroulé (android.R.layout.simple_spinner_dropdown_item) */
        adapterSpinnerTypeGraph.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Enfin on passe l'adapter au Spinner
        mSpinnerTypeGraph.setAdapter(adapterSpinnerTypeGraph);


        //Affichage mesure du jour si deja saisi
        MajMesureJour();

        //Mise à jour du graphique
        MajGraph();

        //Valorisation de l'intente pour aller a une autre activity
        VersNouvelleMesure = new Intent(BoardActivity.this, SaisiMesureActivity.class);


        mBouttonMesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersNouvelleMesure.putExtra("Pseudo",PseudoRecu);
                VersNouvelleMesure.putExtra("Jour",JourSaisi);
                VersNouvelleMesure.putExtra("Mois",MoisSaisi);
                VersNouvelleMesure.putExtra("Annee",AnneeSaisi);
                startActivity(VersNouvelleMesure);
            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(mDate);
            }
        });


        mBouttonDroit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSpinnerTempo.getSelectedItem().toString() == "Jour") {
                    AddDay(AnneeSaisi,MoisSaisi,JourSaisi);
                }

                if (mSpinnerTempo.getSelectedItem().toString() == "Mois"){
                    if (MoisSaisi == 12) {
                        AnneeSaisi =AnneeSaisi + 1;
                        MoisSaisi = 0;
                    }
                    MoisSaisi = MoisSaisi + 1;
                    mDate.setText(makeDateString(JourSaisi,MoisSaisi,AnneeSaisi));
                }

                if (mSpinnerTempo.getSelectedItem().toString() == "Année"){
                    AnneeSaisi = AnneeSaisi + 1;
                    mDate.setText(makeDateString(JourSaisi,MoisSaisi,AnneeSaisi));
                }
                //Affichage mesure du jour si deja saisi
                MajMesureJour();
                //Mise à jour du graphique
                MajGraph();
            }
        });

        mBouttonGauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mSpinnerTempo.getSelectedItem().toString() == "Jour") {
                    RemoveDay(AnneeSaisi,MoisSaisi,JourSaisi);
                }
                if (mSpinnerTempo.getSelectedItem().toString() == "Mois"){
                    if (MoisSaisi == 1) {
                        AnneeSaisi =AnneeSaisi - 1;
                        MoisSaisi = 13;
                    }
                    MoisSaisi = MoisSaisi - 1;
                    mDate.setText(makeDateString(JourSaisi,MoisSaisi,AnneeSaisi));
                }
                if (mSpinnerTempo.getSelectedItem().toString() == "Année"){
                    AnneeSaisi = AnneeSaisi - 1;
                    mDate.setText(makeDateString(JourSaisi,MoisSaisi,AnneeSaisi));
                }
                //Affichage mesure du jour si deja saisi
                MajMesureJour();
                //Mise à jour du graphique
                MajGraph();
            }
        });

        mSpinnerTypeGraph.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#FF000000"));
                MajGraph();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });

        mSpinnerTempo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#FF000000"));
                MajGraph();
                MajMesureJour();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }


    public static void MajMesureJour() {
        //Selection mesure du jour
        MesureSelect = db.MeusureDuJour(PseudoRecu, JourSaisi, MoisSaisi, AnneeSaisi);

        mChiffrePoids.setVisibility(View.INVISIBLE);
        mChiffreMassGrais.setVisibility(View.INVISIBLE);
        mChiffreCalorie.setVisibility(View.INVISIBLE);
        mChiffreMassMuscu.setVisibility(View.INVISIBLE);
        mChiffreMassOs.setVisibility(View.INVISIBLE);
        mChiffreIMC.setVisibility(View.INVISIBLE);
        mChiffreMassHydr.setVisibility(View.INVISIBLE);
        mTextMesureJour.setText("N'oublie pas ta saisie du jour !");
        mBouttonMesure.setText("Enregister les mesures !");

        if (MesureSelect != null) {
            mTextMesureJour.setText("Saisie faite pour aujourd'hui !");
            mBouttonMesure.setText("Modifier ma saisie");
        }

        switch (mSpinnerTempo.getSelectedItem().toString()) {
            case "Jour":
                if (MesureSelect != null) {
                    mChiffrePoids.setVisibility(View.VISIBLE);
                    mChiffreMassGrais.setVisibility(View.VISIBLE);
                    mChiffreCalorie.setVisibility(View.VISIBLE);
                    mChiffreMassMuscu.setVisibility(View.VISIBLE);
                    mChiffreMassOs.setVisibility(View.VISIBLE);
                    mChiffreIMC.setVisibility(View.VISIBLE);
                    mChiffreMassHydr.setVisibility(View.VISIBLE);

                    mChiffrePoids.setText(MesureSelect.getPoids());
                    mChiffreMassGrais.setText(MesureSelect.getMass_Grais());
                    mChiffreCalorie.setText(MesureSelect.getCalorie());
                    mChiffreMassMuscu.setText(MesureSelect.getMass_Musc());
                    mChiffreMassOs.setText(MesureSelect.getMass_Oss());
                    mChiffreIMC.setText(MesureSelect.getIMC());
                    mChiffreMassHydr.setText(MesureSelect.getMass_hydr());
                }
                break;

            case "Mois":
                mChiffrePoids.setVisibility(View.VISIBLE);
                mChiffreMassGrais.setVisibility(View.VISIBLE);
                mChiffreCalorie.setVisibility(View.VISIBLE);
                mChiffreMassMuscu.setVisibility(View.VISIBLE);
                mChiffreMassOs.setVisibility(View.VISIBLE);
                mChiffreIMC.setVisibility(View.VISIBLE);
                mChiffreMassHydr.setVisibility(View.VISIBLE);

                ArrayList MesureMois = db.MeusureDuMois(PseudoRecu, MoisSaisi, AnneeSaisi);
                mChiffrePoids.setText(String.valueOf(MoyenneListString(CréatListeAffich("Poids", MesureMois))));
                mChiffreMassGrais.setText(String.valueOf(MoyenneListString(CréatListeAffich("Masse Graisseuse", MesureMois))));
                mChiffreCalorie.setText(String.valueOf(MoyenneListString(CréatListeAffich("Masse Musculaire", MesureMois))));
                mChiffreMassMuscu.setText(String.valueOf(MoyenneListString(CréatListeAffich("Masse Osseuse", MesureMois))));
                mChiffreMassOs.setText(String.valueOf(MoyenneListString(CréatListeAffich("Masse Hydrique", MesureMois))));
                mChiffreIMC.setText(String.valueOf(MoyenneListString(CréatListeAffich("Calorie", MesureMois))));
                mChiffreMassHydr.setText(String.valueOf(MoyenneListString(CréatListeAffich("IMC", MesureMois))));
                break;

            case "Année":
                mChiffrePoids.setVisibility(View.VISIBLE);
                mChiffreMassGrais.setVisibility(View.VISIBLE);
                mChiffreCalorie.setVisibility(View.VISIBLE);
                mChiffreMassMuscu.setVisibility(View.VISIBLE);
                mChiffreMassOs.setVisibility(View.VISIBLE);
                mChiffreIMC.setVisibility(View.VISIBLE);
                mChiffreMassHydr.setVisibility(View.VISIBLE);

                mChiffrePoids.setText(String.valueOf(MoyenneList(ListeMesureAnnee("Poids", PseudoRecu, AnneeSaisi))));
                mChiffreMassGrais.setText(String.valueOf(MoyenneList(ListeMesureAnnee("Masse Graisseuse", PseudoRecu, AnneeSaisi))));
                mChiffreCalorie.setText(String.valueOf(MoyenneList(ListeMesureAnnee("Masse Musculaire", PseudoRecu, AnneeSaisi))));
                mChiffreMassMuscu.setText(String.valueOf(MoyenneList(ListeMesureAnnee("Masse Osseuse", PseudoRecu, AnneeSaisi))));
                mChiffreMassOs.setText(String.valueOf(MoyenneList(ListeMesureAnnee("Masse Hydrique", PseudoRecu, AnneeSaisi))));
                mChiffreIMC.setText(String.valueOf(MoyenneList(ListeMesureAnnee("Calorie", PseudoRecu, AnneeSaisi))));
                mChiffreMassHydr.setText(String.valueOf(MoyenneList(ListeMesureAnnee("IMC", PseudoRecu, AnneeSaisi))));
                break;
        }
    }

    public static void MajGraph(){

        List ListMoyenneAnnee;
        List MoyenneMoisPrecedent;

        // Suppression des sériés pour le graph
        mGraph.removeSeries(series);
    //    mGraph.removeSeries(seriesObjectif);
        mGraph.removeSeries(seriesJour);
        mGraph.removeSeries(PointeurSoloVert);
        mGraph.removeSeries(PointeurSoloHor);
        mGraph.removeSeries(PointeurVertic);

        //Création des série pour le graph
        series = new LineGraphSeries<DataPoint>();

        if (mSpinnerTempo.getSelectedItem().toString() == "Année") {
            ListMoyenneAnnee = ListeMesureAnnee(mSpinnerTypeGraph.getSelectedItem().toString(),PseudoRecu,AnneeSaisi);
            for (int i = 0; i < 12; i++){
                series.appendData(new DataPoint(i+1, (Integer) ListMoyenneAnnee.get(i)), true, 12);
                series.setColor(R.color.black);
            //    if (mSpinnerTypeGraph.getSelectedItem().toString() == "Poids"){
            //        seriesObjectif = new LineGraphSeries<DataPoint>(new DataPoint[]{
            //                new DataPoint(0, (db.selectUser(PseudoRecu)).getObjectif()),
            //                new DataPoint(12, (db.selectUser(PseudoRecu)).getObjectif())
            //        });
            //    }

            }
        }else{
            // Partie graph quotidien et mensuel
            //Selection mesure du jour
            MesureSelect = db.MeusureDuJour(PseudoRecu, JourSaisi, MoisSaisi, AnneeSaisi);
            MesureList = db.MeusureDuMois(PseudoRecu, MoisSaisi, AnneeSaisi);
            List ListeAffich = CréatListeAffich(mSpinnerTypeGraph.getSelectedItem().toString(), MesureList);

            //    Plusgrand = 0;
            for (Mesure mesure : MesureList) {
                //      series.appendData(new DataPoint(Integer.parseInt(mesure.getJour()), Integer.parseInt(mesure.getPoids())),true,31);
                series.appendData(new DataPoint(mesure.getJour(), Integer.parseInt((String) ListeAffich.get(MesureList.indexOf(mesure)))), true, 31);
                series.setColor(R.color.black);
            //    if (mSpinnerTypeGraph.getSelectedItem().toString() == "Poids"){
            //        seriesObjectif = new LineGraphSeries<DataPoint>(new DataPoint[]{
            //                new DataPoint(0, (db.selectUser(PseudoRecu)).getObjectif()),
            //                new DataPoint(31, (db.selectUser(PseudoRecu)).getObjectif())
            //        });
             //   }
                if (MesureList.size()==1){
                    PointeurSoloHor = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(mesure.getJour()-0.3, Integer.parseInt((String) ListeAffich.get(MesureList.indexOf(mesure)))),
                            new DataPoint(mesure.getJour()+0.3, Integer.parseInt((String) ListeAffich.get(MesureList.indexOf(mesure))))
                    });
                    PointeurSoloVert = new LineGraphSeries<DataPoint>(new DataPoint[]{
                            new DataPoint(mesure.getJour(), Integer.parseInt((String) ListeAffich.get(MesureList.indexOf(mesure)))+1),
                            new DataPoint(mesure.getJour(), Integer.parseInt((String) ListeAffich.get(MesureList.indexOf(mesure)))-1)
                    });
                    mGraph.addSeries(PointeurSoloHor);
                    mGraph.addSeries(PointeurSoloVert);
                    PointeurSoloHor.setColor(R.color.black);
                    PointeurSoloVert.setColor(R.color.black);
                }
                //    series.appendData(new DataPoint(mesure.getJour(),10),true,31);
                //       if (Integer.parseInt((String) ListeAffich.get(MesureList.indexOf(mesure))) >= Plusgrand){
                //           Plusgrand = Integer.parseInt((String) ListeAffich.get(MesureList.indexOf(mesure)));
                //      }
            }

            seriesJour = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    // on below line we are adding
                    // each point on our x and y axis.
                    new DataPoint(0, 0),
                    new DataPoint(31, 0)
            });


            if (mSpinnerTempo.getSelectedItem().toString() == "Jour") {
                PointeurVertic = new LineGraphSeries<DataPoint>(new DataPoint[]{
                        // on below line we are adding
                        // each point on our x and y axis.
                        new DataPoint(JourSaisi, 0),
                        new DataPoint(JourSaisi, (100))
                });
                mGraph.addSeries(PointeurVertic);
            }
            mGraph.addSeries(seriesJour);
            //    mGraph.addSeries(PointeurHoriz);

            mGraph.getGridLabelRenderer().setNumHorizontalLabels(7);
            mGraph.getGridLabelRenderer().setNumVerticalLabels(6);
            mGraph.getGridLabelRenderer().setHorizontalLabelsColor(R.color.black);
            mGraph.getGridLabelRenderer().setVerticalLabelsColor(R.color.black);
            mGraph.getGridLabelRenderer().setGridColor(R.color.black);
        }



    /*   PointeurHoriz = new LineGraphSeries<DataPoint>(new DataPoint[]{
                // on below line we are adding
                // each point on our x and y axis.
                new DataPoint(Integer.parseInt(JourSaisi)-1, 10),
               new DataPoint(Integer.parseInt(JourSaisi)+1, 10)
       //        new DataPoint(Integer.parseInt(JourSaisi)-1, Integer.parseInt((String) ListeAffich.get(Integer.parseInt(JourSaisi)))),
       //        new DataPoint(Integer.parseInt(JourSaisi)+1, Integer.parseInt((String) ListeAffich.get(Integer.parseInt(JourSaisi))))

       });*/



        seriesJour.setColor(R.color.black);
     //   PointeurHoriz.setColor(R.color.white);
        PointeurVertic.setColor(R.color.white);
        // on below line we are adding
        // data series to our graph view.
        mGraph.addSeries(series);
    //    mGraph.addSeries(seriesObjectif);


        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
       // mGraph.setTitle("My Graph View");

        // on below line we are setting
        // text color to our graph view.
       // mGraph.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
      //  mGraph.setTitleTextSize(43);


       // mGraph.getGridLabelRenderer().setHorizontalAxisTitle("Jours");
        //   graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        //    graphView.getGridLabelRenderer().setVerticalAxisTitle("Kg");
    }

    private static List CréatListeAffich(String typeGraph, ArrayList<Mesure> ListeMesure)
    {
        List ListeRetour = new ArrayList();

        switch (typeGraph){
            case "Poids" :
                for (Mesure mesure:ListeMesure){
                    ListeRetour.add(mesure.getPoids());
                }
                break;
            case "Masse Graisseuse" :
                for (Mesure mesure:ListeMesure){
                    ListeRetour.add(mesure.getMass_Grais());
                }
                break;
            case "Masse Musculaire" :
                for (Mesure mesure:ListeMesure){
                    ListeRetour.add(mesure.getMass_Musc());
                }
                break;
            case "Masse Osseuse" :
                for (Mesure mesure:ListeMesure){
                    ListeRetour.add(mesure.getMass_Oss());
                }
                break;
            case "Masse Hydrique":
                for (Mesure mesure:ListeMesure){
                    ListeRetour.add(mesure.getMass_hydr());
                }
                break;
            case "Calorie":
                for (Mesure mesure:ListeMesure){
                    ListeRetour.add(mesure.getCalorie());
                }
                break;
            case "IMC" :
                for (Mesure mesure:ListeMesure){
                    ListeRetour.add(mesure.getIMC());
                }
                break;
        }

        return ListeRetour;
    }


    private static List ListeMesureAnnee(String typeGraph, String pseudo, int annee)
    {
        List ListeRetour = new ArrayList();
        ArrayList<Mesure> ListduMois;
        List ListdesMesure;
        int Resultat;
        int i;

        for (int mois = 1; mois <=12; mois++) {
            ListduMois = db.MeusureDuMois(pseudo,mois,annee);
            ListdesMesure = CréatListeAffich(typeGraph,ListduMois);
            Resultat = 0;
            if (ListdesMesure.size() != 0){
                for(i = 0; i < ListdesMesure.size(); i++)
                    Resultat += Integer.valueOf((String) ListdesMesure.get(i));
                Resultat = Resultat/ListdesMesure.size();
            }
            ListeRetour.add(Resultat);

         /*   Aller chercher les mesure pour le mois X
              Appel a CréatListeAffich avec le typeGraph et resultat de la chercher precedente
              Si div de 0
                additionner toutes les mesure et les diviser par le total
              Sinon = 0
              Ajouter le resultat a la liste
         */
        }

        return ListeRetour;
    }


    private static int MoyenneList(List List) {
        int Resultat = 0;
        int NbSaisi = 0;
        int i;

        if (List.size() != 0) {
            for (i = 0; i < List.size(); i++) {
                Resultat += (int) List.get(i);
                if ((int) List.get(i) != 0){
                    NbSaisi += 1;
                }
            }
            if (NbSaisi != 0){
                Resultat = Resultat / NbSaisi;
            }
        }
        return Resultat;
    }

    private static int MoyenneListString(List List) {
        int Resultat = 0;
        int i;

        if (List.size() != 0) {
            for (i = 0; i < List.size(); i++)
                Resultat += Integer.valueOf((String) List.get(i));
            Resultat = Resultat / List.size();
        }
        return Resultat;
    }


private void AddDay (int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        calendar.add(Calendar.DATE, 1);
        AnneeSaisi = calendar.get(Calendar.YEAR);
        MoisSaisi = calendar.get(Calendar.MONTH)+1;
        JourSaisi = calendar.get(Calendar.DAY_OF_MONTH);
        String date = makeDateString(JourSaisi, MoisSaisi, AnneeSaisi);
        mDate.setText(date);
    }


    private void RemoveDay (int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        AnneeSaisi = calendar.get(Calendar.YEAR);
        MoisSaisi = calendar.get(Calendar.MONTH)+1;
        JourSaisi = calendar.get(Calendar.DAY_OF_MONTH);
        String date = makeDateString(JourSaisi, MoisSaisi, AnneeSaisi);
        mDate.setText(date);
    }


    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        JourSaisi = day;
        MoisSaisi = month;
        AnneeSaisi = year;
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                JourSaisi = day;
                MoisSaisi = month;
                AnneeSaisi = year;
                String date = makeDateString(day, month, year);
                mDate.setText(date);

                //Affichage mesure du jour si deja saisi
                MajMesureJour();
                //Mise à jour du graphique
                MajGraph();

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEV";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "AVR";
        if(month == 5)
            return "MAI";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AOU";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

}