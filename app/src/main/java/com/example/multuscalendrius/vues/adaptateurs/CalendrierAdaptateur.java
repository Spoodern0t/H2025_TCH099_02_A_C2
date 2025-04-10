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

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vues.AccueilActivity;

import java.util.List;

public class CalendrierAdaptateur extends ArrayAdapter<UserCalendar> {

    private List<UserCalendar> calendrier;
    private Context contexte;
    private int viewResourceId;
    private Resources resources;

    public CalendrierAdaptateur(@NonNull Context context, int viewResourceId, @NonNull List<UserCalendar> calendriers) {
        super(context, viewResourceId, calendriers);
        this.contexte = context;
        this.viewResourceId = viewResourceId;
        this.resources = contexte.getResources();
        this.calendrier = calendriers;
    }

    @Override
    public int getCount() {
        return this.calendrier.size();
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

        final UserCalendar calendrier = this.calendrier.get(position);

        if (calendrier != null) {
            final TextView nom = view.findViewById(R.id.nom);
            final ImageButton imgBtnCalendrier = view.findViewById(R.id.imgBtnCalendrier);
            final ImageButton imgBtnPlanificateur = view.findViewById(R.id.imgBtnPlanificateur);

            nom.setText(calendrier.getNomCalendrier());

            imgBtnCalendrier.setOnClickListener(v -> {

                Intent intent = new Intent(contexte, AccueilActivity.class);
                intent.putExtra("FRAGMENT", 0);
                intent.putExtra("ID", calendrier.getCalendarId());
                contexte.startActivity(intent);
            });

            imgBtnPlanificateur.setOnClickListener(v -> {

                Intent intent = new Intent(contexte, AccueilActivity.class);
                intent.putExtra("FRAGMENT", 1);
                intent.putExtra("ID", calendrier.getCalendarId());
                contexte.startActivity(intent);
            });
        }

        return view;
    }
}
