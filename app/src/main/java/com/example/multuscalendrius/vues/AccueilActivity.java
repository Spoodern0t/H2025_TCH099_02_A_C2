package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.vues.fragments.CalendrierFragment;
import com.example.multuscalendrius.vues.fragments.PlanificateurFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imgBtnCreate, imgBtnPhotoProfil;
    private BottomNavigationView bottomNavigationView;
    private int calendrierId;
    private Fragment calendrierFragment, planificateurFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        imgBtnCreate = findViewById(R.id.imgBtnMenu);
        imgBtnPhotoProfil = findViewById(R.id.imgBtnPhotoProfil);

        imgBtnCreate.setOnClickListener(this);
        imgBtnPhotoProfil.setOnClickListener(this);

        // Initier la barre de navigation
        initNavView();

        // Charger le calendrier
        chargerCalendrier();
    }

    private void chargerCalendrier() {
        Calendrier calendrier = Calendrier.getInstance();

        // Observation du LiveData pour être notifié des mises à jour
        calendrier.getLiveData().observe(this, updatedCalendrier -> {
            if (updatedCalendrier != null) {
                switch (updatedCalendrier.getOperation()) {
                    case CREATION:
                        Toast.makeText(this, "Calendrier créé: " + updatedCalendrier.getNom(), Toast.LENGTH_SHORT).show();
                        break;
                    case MISE_A_JOUR:
                        Toast.makeText(this, "Calendrier mis à jour: " + updatedCalendrier.getNom(), Toast.LENGTH_SHORT).show();
                        break;
                    case SUPPRESSION:
                        Toast.makeText(this, "Calendrier supprimé", Toast.LENGTH_SHORT).show();
                        break;
                    case AJOUT_EVENEMENT:
                        Toast.makeText(this, "Événement ajouté", Toast.LENGTH_SHORT).show();
                        break;
                    case MISE_A_JOUR_EVENEMENT:
                        Toast.makeText(this, "Événement mis à jour", Toast.LENGTH_SHORT).show();
                        break;
                    case SUPPRESSION_EVENEMENT:
                        Toast.makeText(this, "Événement supprimé", Toast.LENGTH_SHORT).show();
                        break;
                    case AJOUT_ELEMENT:
                        Toast.makeText(this, "Élément ajouté", Toast.LENGTH_SHORT).show();
                        break;
                    case MISE_A_JOUR_ELEMENT:
                        Toast.makeText(this, "Élément mis à jour", Toast.LENGTH_SHORT).show();
                        break;
                    case SUPPRESSION_ELEMENT:
                        Toast.makeText(this, "Élément supprimé", Toast.LENGTH_SHORT).show();
                        break;
                    case ERREUR:
                        Toast.makeText(this, "Erreur: " + updatedCalendrier.getErrorMessage(), Toast.LENGTH_SHORT).show();
                        break;
                    case AUTRE:
                        Toast.makeText(this, "Opération non spécifiée", Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {
                Toast.makeText(this, "Aucun calendrier disponible", Toast.LENGTH_SHORT).show();
            }
        });
        calendrier.fetchById(calendrierId);
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
            // TODO: Vers interface de création
        } else if (v == imgBtnPhotoProfil) {
            // TODO: Menu de l'utilisateur
        }
    }

    // Initier la barre de navigation
    public void initNavView() {

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Les vues qui vont apparaitre dans le frame
        calendrierFragment = new CalendrierFragment();
        planificateurFragment = new PlanificateurFragment();

        Intent intent = getIntent();
        calendrierId = intent.getIntExtra("ID", 0);

        setCurrentFragment(calendrierFragment);
        bottomNavigationView.setSelectedItemId(R.id.calendrier);

        // Les actions des boutons de la barre de navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int ItemId = item.getItemId();
            if (ItemId == R.id.calendrier) {
                setCurrentFragment(calendrierFragment);
                return true;
            } else if (ItemId == R.id.planificateur) {
                setCurrentFragment(planificateurFragment);
                return true;
            }
            return false;
        });
    }
}
