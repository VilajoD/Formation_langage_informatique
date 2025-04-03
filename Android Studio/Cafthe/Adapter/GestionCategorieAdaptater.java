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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cafth.R;
import com.example.cafth.db.CaftheDataBase;
import com.example.cafth.objet.Boisson;
import com.example.cafth.objet.Categorie;
import com.example.cafth.page.GestionBoissonActivity;
import com.example.cafth.page.GestionCategorieActivity;
import com.example.cafth.page.Menu_Liste_Activity;
import com.example.cafth.page.ModifBoissonActivity;
import com.example.cafth.page.ModifCategorieActivity;

import java.util.ArrayList;

public class GestionCategorieAdaptater extends ArrayAdapter {

    //Variable pour le fichier SharedPreferences
    private SharedPreferences sharedPreferences;

    //Déclaration des variables
    private Context context;
    private int ressource;
    private ArrayList<Categorie> value;

    private Categorie ligne;
    private View itemView;

    private TextView cle;
    private TextView type;
    private TextView categorie;

    private ImageView menupop;

    //Variable pour la DB
    private static CaftheDataBase db;

    public GestionCategorieAdaptater(@NonNull Context context, int ressource, @NonNull ArrayList<Categorie> value) {
        super(context, ressource, value);

        this.context = context;
        this.ressource = ressource;
        this.value = value;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Initialise le fichier
        sharedPreferences = context.getSharedPreferences("Préférences", Context.MODE_PRIVATE);

        // Récupere la Boisson qui se trouve a la ligne indiquré par "Position"
        ligne = value.get(position);

        // transforme le context en view. Besoin de faire ca pour travailler dessuis par la suite (pas tout comprit...)
        itemView = LayoutInflater.from(context).inflate(ressource, parent, false);

        // Lien entre les variable et les zone XML (zone qui sont dans itemView)

        cle = itemView.findViewById(R.id.Item_cle_categorie);
        type = itemView.findViewById(R.id.Type_Categorie);
        categorie = itemView.findViewById(R.id.Nom_Categorie);
        menupop = itemView.findViewById(R.id.menu_pop_categorie);

        // Ajoute les info dans les XML a parti de ce qui est récuperer dans la liste
        cle.setText(String.valueOf(ligne.getClé()));
        type.setText(ligne.getType());
        categorie.setText(ligne.getCategorie());

        db = new CaftheDataBase(context);

        if (sharedPreferences.getBoolean("Couleur_Menu_Gestion", false)) {
            switch (ligne.getType()) {
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
                PopupMenu popuMenu = new PopupMenu(context, view);
                //On fait un Inflate dessu avec le lien vers le menu qui doit apparaitre
                popuMenu.inflate(R.menu.menu_context_liste_boisson);

                //Pour savoir quoi faire en cliquant sur les bouton du menu
                popuMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.Modifier) {
                            Intent VersModif = new Intent(context, ModifCategorieActivity.class);
                            VersModif.putExtra("Cle", value.get(position).getClé());
                            VersModif.putExtra("Origine", "GestionBoisson");
                            VersModif.putExtra("Position", position);
                            context.startActivity(VersModif);
                        }
                        if (menuItem.getItemId() == R.id.Supprimer) {
                            confirmeSuppression(value.get(position),position);
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

    public void confirmeSuppression(Categorie categorie, int position){

        //Variable de l'alerte
        AlertDialog alertSuppr;

        //il faut un builder pour utiliser l'alerte.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Alimente les differentes parties de la boite de dialogue d'alerte
        builder.setTitle("Confirmation Suppression");
        builder.setMessage("Voulez-vous réellement supprimer : " + categorie.getCategorie() + " ?");
        builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ArrayList listBoisson = db.selectListCategorieBoisson(categorie.getType(), categorie.getCategorie());
                if (listBoisson.size() == 0 ){
                    //Action de supprimer dans la base
                    db.DeleteCategorie(categorie.getClé());

                    //Action de supprimer dans la liste puis actualisation de l'affichage
                    value.remove(position);
                    if (value.size()==0){
                        //Se sont les textes de bienvenu, si quelque chose dans la liste, on les effaces
                        GestionCategorieActivity.DebutText1.setVisibility(View.VISIBLE);
                        GestionCategorieActivity.DebutText2.setVisibility(View.VISIBLE);
                    }
                    notifyDataSetChanged();

                }else{
                    confirmeModificationBoissonAssocie(categorie,listBoisson,position);
                }

            }
        });

        alertSuppr = builder.create();
        alertSuppr.show();
    }


    // Si la catégorie modifiée est utiliser dans des boisson. On previens l'utilisateur que ces boisson seront aussi modifier
    public void confirmeModificationBoissonAssocie(Categorie categorieSelect, ArrayList<Boisson> boissonLierCategorie, int position){

        //Variable de l'alerte
        AlertDialog alertModif;

        //il faut un builder pour utiliser l'alerte.
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Alimente les differentes parties de la boite de dialogue d'alerte
        builder.setTitle("Attention !");
        builder.setMessage("La catégorie " + categorieSelect.getCategorie() + " que vous essayez de supprimer est utilisée pour certaines boissons. Si vous continuez, les boissons faisant partie de cette catégorie se verront retirées leur catégorie. Souhaitez-vous continuer ?");
        builder.setPositiveButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Modification de la partie categorie
                //Action de supprimer dans la base
                db.DeleteCategorie(categorieSelect.getClé());

                //Action de supprimer dans la liste puis actualisation de l'affichage
                value.remove(position);
                notifyDataSetChanged();

                    for (Boisson boisson : boissonLierCategorie) {
                        boisson.setCategorie(null);
                        db.modifBoisson(boisson);
                        Toast.makeText(context,"Suppression reussi",Toast.LENGTH_SHORT).show();
                    }
            }
        });

        alertModif = builder.create();
        alertModif.show();
    }
}
