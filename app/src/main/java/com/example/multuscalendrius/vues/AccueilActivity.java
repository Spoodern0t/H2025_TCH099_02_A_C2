package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;
import com.example.multuscalendrius.vues.adaptateurs.PlanificateurAdaptateur;
import com.example.multuscalendrius.vues.costumlayout.CalendrierView;
import com.example.multuscalendrius.vues.fragments.CalendrierFragment;
import com.example.multuscalendrius.vues.fragments.PlanificateurFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener {

    private CalendrierVueModele calendrierVueModele;
    private TextView titreCalendrier;
    private ImageButton imgBtnCreate;
    private BottomNavigationView bottomNavigationView;
    private Fragment calendrierFragment, planificateurFragment;
    private List<Element> elements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Les vues qui vont apparaitre dans le frame
        calendrierFragment = new CalendrierFragment();
        planificateurFragment = new PlanificateurFragment();
        setCurrentFragment(calendrierFragment);

        titreCalendrier = findViewById(R.id.titreCalendrier);
        imgBtnCreate = findViewById(R.id.btnCreer);

        imgBtnCreate.setOnClickListener(this);

        calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        calendrierVueModele.getCalendrier().observe(this, calendrier -> {
            titreCalendrier.setText(calendrier.getNom());
            elements = calendrier.getElements();
        });
        calendrierVueModele.getErreur().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // Charger le calendrier
        chargerCalendrier();
        initNavView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //calendrierVueModele.chargerCalendrier(calendrierVueModele.getCurrentCalendrier().getId());
    }

    private void chargerCalendrier() {
        Intent resultIntent = getIntent();
        int calendrierId = resultIntent.getIntExtra("ID", -1);
        if (calendrierId >= 0)
            calendrierVueModele.chargerCalendrier(calendrierId);
    }

    // Changer la vue du frame
    private void setCurrentFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flFragment, fragment)
                .commit();
    }

    // Les boutons de la barre du haut
    @Override
    public void onClick(View v) {
        if (v == imgBtnCreate) {
            Intent intent = new Intent(this, CreerElementActivity.class);
            startActivity(intent);
        }
    }

    // Initier la barre de navigation
    public void initNavView() {

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.calendrier);

        // Les actions des boutons de la barre de navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu){
                Intent intent = new Intent(this, MenuCalendriersActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.calendrier) {
                setCurrentFragment(calendrierFragment);
                return true;
            } else if (itemId == R.id.planificateur) {
                setCurrentFragment(planificateurFragment);
                return true;
            } else if (itemId == R.id.profil){
                Intent intent = new Intent(this, ProfilActivity.class);
                startActivity(intent);
            }
            return false;
        });
    }
}