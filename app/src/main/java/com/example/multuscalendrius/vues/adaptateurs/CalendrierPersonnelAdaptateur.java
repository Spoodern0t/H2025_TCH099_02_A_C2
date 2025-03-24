package com.example.multuscalendrius.vues.adaptateurs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.vues.AccueilActivity;
import com.example.multuscalendrius.vues.fragments.CalendrierFragment;
import com.example.multuscalendrius.vues.fragments.PlanificateurFragment;

public class CalendrierPersonnelAdaptateur extends ArrayAdapter<Calendrier> {

    private Calendrier[] calendrier;
    private Context contexte;
    private int viewResourceId;
    private Resources resources;

    public CalendrierPersonnelAdaptateur(@NonNull Context context, int viewResourceId, @NonNull Calendrier[] calendrier) {
        super(context, viewResourceId, calendrier);
        this.contexte = context;
        this.viewResourceId = viewResourceId;
        this.resources = contexte.getResources();
        this.calendrier = calendrier;
    }

    @Override
    public int getCount() {
        return this.calendrier.length;
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

        final Calendrier calendrier = this.calendrier[position];

        if (calendrier != null) {
            final TextView nom = view.findViewById(R.id.nom);
            final ImageButton imgBtnCalendrier = view.findViewById(R.id.imgBtnCalendrier);
            final ImageButton imgBtnPlanificateur = view.findViewById(R.id.imgBtnPlanificateur);

            nom.setText(calendrier.getName());

            imgBtnCalendrier.setOnClickListener(v -> {

                Intent intent = new Intent(contexte, AccueilActivity.class);
                intent.putExtra("FRAGMENT", 0);
                intent.putExtra("ID", calendrier.getId());
                contexte.startActivity(intent);
            });

            imgBtnPlanificateur.setOnClickListener(v -> {

                Intent intent = new Intent(contexte, AccueilActivity.class);
                intent.putExtra("FRAGMENT", 1);
                intent.putExtra("ID", calendrier.getId());
                contexte.startActivity(intent);
            });
        }

        return view;
    }
}
