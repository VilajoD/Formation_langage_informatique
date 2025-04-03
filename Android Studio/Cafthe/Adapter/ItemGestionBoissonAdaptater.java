package com.example.cafth.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;
import com.example.cafth.objet.Boisson;
import com.example.cafth.page.GestionBoissonActivity;
import com.example.cafth.page.ModifBoissonActivity;

import java.util.ArrayList;

public class ItemGestionBoissonAdaptater extends ArrayAdapter {

    //Variable pour le fichier SharedPreferences
    private SharedPreferences sharedPreferences;

    //Déclaration des variables
    private Context context;
    private int ressource;
    private ArrayList<Boisson> value;

    private Boisson ligne;
    private View itemView;

    private TextView nom;
    private TextView quantite;
    private TextView cle;
    private TextView type;
    private TextView categorie;

    private ImageView menupop;

    //Variable pour la DB
    private static CaftheDataBase db;

    // On modifie un constructeur deja present de base dans "ArrayAdapter". En lui donnant les info qu'on veut en entrée
    // Les action sous appliquer avec une boucle a chaque ligne de la liste (on ne le voit pas ici car doit etre fait directement dans "ArrayAdapter"
    // Parametre :
    // - Le context ( c'est a dire ou se passe cette action)
    // - ressource c'est le format de la liste
    // - Les donnée a mettre en forme
    public ItemGestionBoissonAdaptater(@NonNull Context context, int ressource, @NonNull ArrayList<Boisson> value) {
        super(context, ressource, value);

        this.context = context;
        this.ressource = ressource;
        this.value = value;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Initialise le fichier preferenceZeroQuantite
        sharedPreferences = context.getSharedPreferences("Préférences", Context.MODE_PRIVATE);

        // Récupere la Boisson qui se trouve a la ligne indiquré par "Position"
        ligne = value.get(position);

        // transforme le context en view. Besoin de faire ca pour travailler dessuis par la suite (pas tout comprit...)
        itemView = LayoutInflater.from(context).inflate(ressource, parent, false);

        // Lien entre les variable et les zone XML (zone qui sont dans itemView)
        nom = itemView.findViewById(R.id.Nom);
        quantite = itemView.findViewById(R.id.Nombre);
        cle = itemView.findViewById(R.id.Item_cle_boisson);
        type = itemView.findViewById(R.id.Item_Type_Boisson);
        categorie = itemView.findViewById(R.id.Item_catégorie_boisson);
        menupop = itemView.findViewById(R.id.menu_pop);

        // Ajoute les info dans les XML a parti de ce qui est récuperer dans la liste
        nom.setText(ligne.getNom());
        quantite.setText(String.valueOf(ligne.getQuantite()));
        cle.setText(String.valueOf(ligne.getClé()));
        type.setText(ligne.getType());
        categorie.setText(ligne.getCategorie());

        db = new CaftheDataBase(context);

        if (sharedPreferences.getBoolean("Couleur_Menu_Gestion", true)) {
            switch (value.get(position).getType()) {
                case "Thé":
                    itemView.setBackgroundColor(Color.parseColor("#B04B566A"));
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
                            VersModif.putExtra("Origine","GestionBoisson");
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
                    GestionBoissonActivity.DebutText1.setVisibility(View.VISIBLE);
                    GestionBoissonActivity.DebutText2.setVisibility(View.VISIBLE);
                }
                notifyDataSetChanged();
            }
        });

        alertSuppr = builder.create();
        alertSuppr.show();
    }
}
