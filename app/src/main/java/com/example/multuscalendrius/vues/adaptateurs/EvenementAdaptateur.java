package com.example.multuscalendrius.vues.adaptateurs;

import android.content.Context;
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
            TextView tvNom = view.findViewById(R.id.tvNom);

            couleurEvenement.setBackgroundColor(Integer.parseInt(evenement.getCouleur(), 16));
            tvNom.setText(evenement.getTitre());
        }
        return view;
    }
}
