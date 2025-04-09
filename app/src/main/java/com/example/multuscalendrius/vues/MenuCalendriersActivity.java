package com.example.multuscalendrius.vues;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.dao.UserDao;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vuemodele.UserVueModele;
import com.example.multuscalendrius.vues.adaptateurs.CalendrierAdaptateur;

import java.util.ArrayList;
import java.util.List;

public class MenuCalendriersActivity extends AppCompatActivity {

    private UserVueModele userVueModele;
    private TextView tvCalendriersPersonnels, tvCalendriersPartages;
    private ListView lvCalendriersPersonnels, lvCalendriersPartages;
    private View breakLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_calendriers);

        userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
        userVueModele.getUserCalendars().observe(this, this::initCalendriersPersonnels);
        userVueModele.getErreur().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        userVueModele.syncUserCalendars();

        UserDao instance = UserDao.getInstance();
        // Observation du LiveData pour être notifié des mises à jour
        /*instance.getLiveData().observe(this, user -> {
            if (user != null) {

                switch (user.getOperation()) {
                    case LOGIN:
                        Toast.makeText(this, "Calendrier créé: " + user.getUsername(), Toast.LENGTH_SHORT).show();
                        break;
                    case FETCH_USER_CALENDARS:
                        Toast.makeText(this, "Calendrier mis à jour: " + user.getUsername(), Toast.LENGTH_SHORT).show();
                        initCalendriersPersonnels();

                        estPartage = true; // TODO: Pour tester ce qu'un utilisateur voit
                        if (estPartage) initCalendriersPartages();
                        break;
                    case ERREUR:
                        Toast.makeText(this, "Erreur: " + user.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        break;
                    case AUTRE:
                        Toast.makeText(this, "Opération non spécifiée", Toast.LENGTH_SHORT).show();
                        break;



            } else {
                Toast.makeText(this, "Aucun calendrier disponible", Toast.LENGTH_SHORT).show();
            }
        });
        instance.syncUserCalendars();}*/
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

        CalendrierAdaptateur calendrierPersonnelAdaptateur = new CalendrierAdaptateur(this, R.layout.layout_calendrier_personnel, calendriersPersonnels);
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

            CalendrierAdaptateur calendrierPartageAdaptateur = new CalendrierAdaptateur(this, R.layout.layout_calendrier_partage, calendriers);
            lvCalendriersPartages.setAdapter(calendrierPartageAdaptateur);
        }
    }
}
