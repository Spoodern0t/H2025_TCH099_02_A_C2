package com.example.multuscalendrius.vues;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.dao.DaoCalendrier;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.vues.adaptateurs.CalendrierPersonnelAdaptateur;

public class MenuCalendriersActivity extends AppCompatActivity {

    private boolean estPartage; // true si l'utilisateur a été invité dans au moins 1 calendrier partagé
    private TextView tvCalendriersPersonnels, tvCalendriersPartages;
    private ListView lvCalendriersPersonnels, lvCalendriersPartages;
    private View breakLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_calendriers);

        estPartage = true; // TODO: Pour tester ce qu'un utilisateur voit

        if (estPartage) { initCalendriersPartages(); }

        initCalendriersPersonnels();
    }

    public void initCalendriersPersonnels() {

        lvCalendriersPersonnels = findViewById(R.id.lvCalendriersPersonnels);

        Calendrier[] calendriers = DaoCalendrier.getInstance().getCalendriersPersonnels();
        CalendrierPersonnelAdaptateur adaptateur = new CalendrierPersonnelAdaptateur(this, R.layout.layout_calendrier_personnel, calendriers);

        lvCalendriersPersonnels.setAdapter(adaptateur);
    }

    public void initCalendriersPartages() {

        tvCalendriersPersonnels = findViewById(R.id.tvCalendriersPersonnels);
        tvCalendriersPartages = findViewById(R.id.tvCalendriersPartages);
        lvCalendriersPartages = findViewById(R.id.lvCalendriersPartages);
        breakLine = findViewById(R.id.breakLine);

        breakLine.setVisibility(View.VISIBLE);
        lvCalendriersPartages.setVisibility(View.VISIBLE);
        tvCalendriersPartages.setVisibility(View.VISIBLE);
        tvCalendriersPersonnels.setText(R.string.liste_des_calendriers_personnels);

        Calendrier[] calendriers = DaoCalendrier.getInstance().getCalendriersPartages();
        CalendrierPersonnelAdaptateur adaptateur = new CalendrierPersonnelAdaptateur(this, R.layout.layout_calendrier_partage, calendriers);

        lvCalendriersPartages.setAdapter(adaptateur);
    }
}
