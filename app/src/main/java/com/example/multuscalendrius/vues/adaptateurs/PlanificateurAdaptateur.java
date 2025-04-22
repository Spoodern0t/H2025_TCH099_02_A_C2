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
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.modeles.entitees.Evenement;

import java.time.LocalDateTime;
import java.util.List;

public class PlanificateurAdaptateur extends ArrayAdapter<Element> {

    private List<Element> elements;
    private Context contexte;
    private int viewResourceId;
    private Resources resources;

    public PlanificateurAdaptateur(@NonNull Context context, int viewResourceId, @NonNull List<Element> elements) {
        super(context, viewResourceId, elements);
        this.contexte = context;
        this.viewResourceId = viewResourceId;
        this.resources = contexte.getResources();
        this.elements = elements;
    }

    @Override
    public int getCount() {
        return this.elements.size();
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

        final Element element = this.elements.get(position);

        if (element != null) {
            View couleurEvenement = view.findViewById(R.id.couleurEvenement);
            TextView tvNom = view.findViewById(R.id.tvNom);
            TextView tvDates = view.findViewById(R.id.tvDates);
            TextView tvHeures = view.findViewById(R.id.tvHeures);

            Evenement evenement = element.getEvenement();

            String titre = "";
            if (evenement != null) {
                couleurEvenement.setBackgroundColor(Color.parseColor("#" + evenement.getCouleur()));
                titre = evenement.getTitre() + " : ";
            } else {
                couleurEvenement.setBackground(null);
            }
            tvNom.setText(titre + element.getNom());

            String dateDebut = "";
            String heureDebut = "";
            LocalDateTime elementDebut = element.getDateDebut();
            if (elementDebut != null) {
                dateDebut = elementDebut.toLocalDate().toString();
                heureDebut = elementDebut.getHour() + "h" + elementDebut.getMinute() + " -> ";
            }
            LocalDateTime elementFin = element.getDateFin();
            String dateFin = elementFin.toLocalDate().toString();
            String heureFin = elementFin.getHour() + "h" + elementFin.getMinute();

            String date = !dateDebut.equals(dateFin) && !dateDebut.isEmpty() ? dateDebut + " -> " + dateFin : dateFin;
            tvDates.setText(date);
            tvHeures.setText(heureDebut + heureFin);
        }
        return view;
    }
}