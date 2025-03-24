package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.multuscalendrius.R;
import com.example.multuscalendrius.vues.fragments.CalendrierFragment;
import com.example.multuscalendrius.vues.fragments.PlanificateurFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imgBtnCreate, imgBtnPhotoProfil;
    private BottomNavigationView bottomNavigationView;
    private long calendrierId;
    private Fragment calendrierFragment, planificateurFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        imgBtnCreate = findViewById(R.id.imgBtnCreate);
        imgBtnPhotoProfil = findViewById(R.id.imgBtnPhotoProfil);

        imgBtnCreate.setOnClickListener(this);
        imgBtnPhotoProfil.setOnClickListener(this);

        // Initier la barre de navigation
        initNavView();
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
        int fragmentNo = intent.getIntExtra("FRAGMENT", 0);
        calendrierId = intent.getLongExtra("ID", 0);

        // Envoyer vers la vue choisie
        if (fragmentNo == 0) {
            setCurrentFragment(calendrierFragment);
            bottomNavigationView.setSelectedItemId(R.id.calendrier);
        } else {
            setCurrentFragment(planificateurFragment);
            bottomNavigationView.setSelectedItemId(R.id.planificateur);
        }

        // Les actions des boutons de la barre de navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int ItemId = item.getItemId();
            if (ItemId == R.id.menu) {
                finish();
                return true;
            } else if (ItemId == R.id.calendrier) {
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
