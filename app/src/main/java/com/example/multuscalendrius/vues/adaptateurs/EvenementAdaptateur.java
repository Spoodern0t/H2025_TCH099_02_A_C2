package com.example.multuscalendrius.vues.adaptateurs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Evenement;
import com.example.multuscalendrius.vues.CreerEvenementActivity;

import java.util.List;

public class EvenementAdaptateur extends ArrayAdapter<Evenement> {

    private List<Evenement> evenements;
    private Context contexte;
    private int viewResourceId;
    private Resources resources;

    public EvenementAdaptateur(@NonNull Context context, int viewResourceId, @NonNull List<Evenement> evenements) {
        super(context, viewResourceId, evenements);
        this.contexte = context;
        this.viewResourceId = viewResourceId;
        this.resources = contexte.getResources();
        this.evenements = evenements;
    }

    @Override
    public int getCount() {
        return this.evenements.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.
                    LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        final Evenement evenement = this.evenements.get(position);

        if (evenement != null) {
            View couleurEvenement = view.findViewById(R.id.couleurEvenement);
            TextView tvEvenementNom = view.findViewById(R.id.tvEvenementNom);

            if (evenement.getCouleur() != null) {
                couleurEvenement.setBackgroundColor(Color.parseColor("#" + evenement.getCouleur()));
            } else {
                couleurEvenement.setBackground(null);
            }
            tvEvenementNom.setText(evenement.getTitre());

            couleurEvenement.setOnClickListener(v -> {
                Intent intent = new Intent(contexte, CreerEvenementActivity.class);
                intent.putExtra("ID", evenement.getId());
                contexte.startActivity(intent);
            });
        }
        return view;
    }

    @NonNull
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) contexte.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(this.viewResourceId, parent, false);
        }

        final Evenement evenement = this.evenements.get(position);

        if (evenement != null) {
            View couleurEvenement = view.findViewById(R.id.couleurEvenement);
            TextView tvEvenementNom = view.findViewById(R.id.tvEvenementNom);
            if (evenement.getCouleur() != null) {
                couleurEvenement.setBackgroundColor(Color.parseColor("#" + evenement.getCouleur()));
            } else {
                couleurEvenement.setBackground(null);
            }
            tvEvenementNom.setText(evenement.getTitre());
            view.setBackgroundResource(R.color.couleur_primaire);
        }

        return view;
    }
}
