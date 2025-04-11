package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vuemodele.UserVueModele;
import com.example.multuscalendrius.vues.adaptateurs.CalendrierAdaptateur;

import java.util.ArrayList;
import java.util.List;

public class MenuCalendriersActivity extends AppCompatActivity implements ListView.OnItemClickListener, View.OnClickListener {

    private UserVueModele userVueModele;
    private ImageButton imgBtnAddCalendrier;
    private TextView tvCalendriersPersonnels, tvCalendriersPartages;
    private ListView lvCalendriersPersonnels, lvCalendriersPartages;
    private View breakLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_calendriers);

        lvCalendriersPersonnels = findViewById(R.id.lvCalendriersPersonnels);
        imgBtnAddCalendrier = findViewById(R.id.imgBtnAddCalendrier);

        imgBtnAddCalendrier.setOnClickListener(this);
        lvCalendriersPersonnels.setOnItemClickListener(this);

        userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
        userVueModele.getUserCalendars().observe(this, this::initCalendriersPersonnels);
        userVueModele.getErreur().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
        userVueModele.syncUserCalendars();
    }

    public void initCalendriersPersonnels(List<UserCalendar> calendriers) {

        lvCalendriersPersonnels = findViewById(R.id.lvCalendriersPersonnels);

        String username = userVueModele.getUsername();
        List<UserCalendar> calendriersPersonnels = new ArrayList<>();
        List<UserCalendar> calendriersPartages = new ArrayList<>();
        for (UserCalendar calendrier: calendriers) {
            if (calendrier.getAuteur().equals(username)) {
                calendriersPersonnels.add(calendrier);
            } else {
                calendriersPartages.add(calendrier);
            }
        }

        CalendrierAdaptateur calendrierPersonnelAdaptateur = new CalendrierAdaptateur(this, R.layout.layout_calendrier, calendriersPersonnels);
        lvCalendriersPersonnels.setAdapter(calendrierPersonnelAdaptateur);

        if (!calendriersPartages.isEmpty()) {
            tvCalendriersPersonnels = findViewById(R.id.tvCalendriersPersonnels);
            tvCalendriersPartages = findViewById(R.id.tvCalendriersPartages);
            lvCalendriersPartages = findViewById(R.id.lvCalendriersPartages);
            breakLine = findViewById(R.id.breakLine);

            breakLine.setVisibility(View.VISIBLE);
            lvCalendriersPartages.setVisibility(View.VISIBLE);
            tvCalendriersPartages.setVisibility(View.VISIBLE);
            tvCalendriersPersonnels.setText(R.string.liste_des_calendriers_personnels);

            CalendrierAdaptateur calendrierPartageAdaptateur = new CalendrierAdaptateur(this, R.layout.layout_calendrier, calendriers);
            lvCalendriersPartages.setAdapter(calendrierPartageAdaptateur);

            lvCalendriersPartages.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, AccueilActivity.class);
        UserCalendar userCalendar = (UserCalendar) parent.getAdapter().getItem(position);
        intent.putExtra("ID", userCalendar.getCalendarId());
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v == imgBtnAddCalendrier) {
            Intent intent = new Intent(this, CreerCalendrierActivity.class);
            startActivity(intent);
        }
    }
}
