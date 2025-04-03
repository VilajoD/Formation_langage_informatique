package com.example.cafth.Adapter;

import static com.example.cafth.page.Menu_Liste_Activity.valeurList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;
import com.example.cafth.objet.Boisson;
import com.example.cafth.page.AjoutBoissonActivity;
import com.example.cafth.page.Menu_Liste_Activity;
import com.example.cafth.page.ModifBoissonActivity;

import java.util.ArrayList;

// Notre adapater est un extend de l'adaptater basique proposer "ArrayAdapter"
public class ItemAdaptater extends ArrayAdapter {

    //Variable pour le fichier SharedPreferences
    private SharedPreferences sharedPreferences;

    //Déclaration des variables
    private Context context;
    private int ressource;
    private ArrayList<Boisson> value;

    private Boisson ligne;
    private View itemView;

    private TextView titre;
    private TextView quantite;
    private TextView cle;
    private TextView type;
    private TextView categorie;

    private ImageView moins;
    private ImageView menupop;


    //Variable pour la DB
    private static CaftheDataBase db;

    // On modifie un constructeur deja present de base dans "ArrayAdapter". En lui donnant les info qu'on veut en entrée
    // Les action sous appliquer avec une boucle a chaque ligne de la liste (on ne le voit pas ici car doit etre fait directement dans "ArrayAdapter"
    // Parametre :
    // - Le context ( c'est a dire ou se passe cette action)
    // - ressource c'est le format de la liste
    // - Les donnée a mettre en forme
    public ItemAdaptater(@NonNull Context context, int ressource, @NonNull ArrayList<Boisson> value) {
        super(context, ressource, value);

        this.context = context;
        this.ressource = ressource;
        this.value = value;
    }


    // Action de mise en forme de la liste
    // Parametre :
    // - La position ligne de la liste ou nous somme
    // - ...
    // - ...
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Initialise le fichier preferenceZeroQuantite
        sharedPreferences = context.getSharedPreferences("Préférences", Context.MODE_PRIVATE);

        // Récupere la Boisson qui se trouve a la ligne indiquré par "Position"
        ligne = value.get(position);

        // transforme le context en view. Besoin de faire ca pour travailler dessuis par la suite (pas tout comprit...)
        itemView = LayoutInflater.from(context).inflate(ressource,parent,false);

        // Lien entre les variable et les zone XML (zone qui sont dans itemView)
        titre = itemView.findViewById(R.id.Nom);
        quantite = itemView.findViewById(R.id.Nombre);
        cle = itemView.findViewById(R.id.Item_cle_boisson);
        type = itemView.findViewById(R.id.Item_Type_Boisson);
        categorie = itemView.findViewById(R.id.Item_catégorie_boisson);
        moins = itemView.findViewById(R.id.bouton_moins);
        menupop = itemView.findViewById(R.id.menu_pop);

        // Ajoute les info dans les XML a parti de ce qui est récuperer dans la liste
        titre.setText(ligne.getNom());
        quantite.setText(String.valueOf(ligne.getQuantite()));
        cle.setText(String.valueOf(ligne.getClé()));
        type.setText(ligne.getType());
        categorie.setText(ligne.getCategorie());

        db = new CaftheDataBase(context);

        //Faire disparaitre le bouton moins s'il n'y a plus de boisson
        if (value.get(position).getQuantite() <= 0){
            moins.setVisibility(View.INVISIBLE);
        }

        //Active les effets de clic sur les boutons images
    //    buttonEffect(moins);
    //    buttonEffect(menupop);

        if (sharedPreferences.getBoolean("Couleur_Menu_Liste", false)) {
            switch (value.get(position).getType()) {
                case "Thé":
                    //#B04B566A
                    itemView.setBackgroundColor(Color.parseColor("#704B566A"));
                    break;
                case "Café":
                    itemView.setBackgroundColor(Color.parseColor("#D7513333"));
                    break;
                case "Chocolat":
                    itemView.setBackgroundColor(Color.parseColor("#B04C6A4B"));
                    break;
                case "Infusion":
                    itemView.setBackgroundColor(Color.parseColor("#B06A654B"));
                    break;
            }
        }

        //Pour les clic sur image Moins
        moins.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                confirmeConsommation(value.get(position),position);
            }
        });

        //Pour les clic sur image menupop
            menupop.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    //Création d'un instance de popupMenu
                    PopupMenu popuMenu = new PopupMenu(context,view);
                    //On fait un Inflate dessu avec le lien vers le menu qui doit apparaitre
                    popuMenu.inflate(R.menu.menu_context_liste_boisson);
                    
                    //Pour savoir quoi faire en cliquant sur les bouton du menu
                    popuMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getItemId() == R.id.Modifier){
                                Intent VersModif = new Intent(context, ModifBoissonActivity.class);
                                VersModif.putExtra("Type",value.get(position).getType());
                                VersModif.putExtra("Cle",value.get(position).getClé());
                                VersModif.putExtra("Origine","ListBoisson");
                                VersModif.putExtra("Position",position);
                                context.startActivity(VersModif);
                            }
                            if (menuItem.getItemId() == R.id.Supprimer) {
                                 confirmeSuppression(value.get(position).getNom(),position);
                            }
                            return false;
                        }
                    });
                    // On fait en sorte que le menu soit vu
                    popuMenu.show();
                }
            });

        return itemView;
    }




    public void confirmeSuppression(String nomBoisson, int position){

        //Variable de l'alerte
        AlertDialog alertSuppr;

        //il faut un builder pour utiliser l'alerte.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Alimente les differentes parties de la boite de dialogue d'alerte
        builder.setTitle("Confirmation Suppression");
        builder.setMessage("Voulez-vous réellement supprimer : " + nomBoisson + " ?");
        builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Action de supprimer dans la base
                db.DeleteBoisson(value.get(position).getClé());

                //Action de supprimer dans la liste puis actualisation de l'affichage
                value.remove(position);
                if (value.size()==0){
                    //Se sont les textes de bienvenu, si quelque chose dans la liste, on les effaces
                    Menu_Liste_Activity.DebutText1.setVisibility(View.VISIBLE);
                    Menu_Liste_Activity.DebutText2.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        });

        alertSuppr = builder.create();
        alertSuppr.show();
    }

    public void confirmeConsommation(Boisson boisonConso, int position){

        //Variable de l'alerte
        AlertDialog alertConso;

        //il faut un builder pour utiliser l'alerte.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Alimente les differentes parties de la boite de dialogue d'alerte
        builder.setTitle("Confirmation ");
        builder.setMessage("Vous prendrez un : " + boisonConso.getNom() + " ?");
        builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //On verifie s'il y a encore de la boisson apres la consomation pour décrementer le compteur
                if (boisonConso.getQuantite() > 0){
                    boisonConso.setQuantite((boisonConso.getQuantite())-1);
                }
                db.modifBoisson(boisonConso);
                //On verifie si le compteur est a zero et s'il le fichier parametre nous dit de l'afficher ou pas donc le supprime de la liste
                if (value.get(position).getQuantite() == 0 && !sharedPreferences.getBoolean("Presence_Zero_Quantite", true)){
                    value.remove(position);
                    if (value.size()==0){
                        //Se sont les textes de bienvenu, si quelque chose dans la liste, on les effaces
                        Menu_Liste_Activity.DebutText1.setVisibility(View.VISIBLE);
                        Menu_Liste_Activity.DebutText2.setVisibility(View.VISIBLE);
                    }
                    notifyDataSetChanged();
                }
                notifyDataSetChanged();
            }
        });

        alertConso = builder.create();
        alertConso.show();
    }

    //Pour créer un effect sur le bouton quand on clic dessus
    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ResourceAsColor")
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        button.setVisibility(View.INVISIBLE);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        button.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                return false;
            }
        });
    }
}
