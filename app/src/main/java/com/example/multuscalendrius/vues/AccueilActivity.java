package com.example.multuscalendrius.vues;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;

import java.util.ArrayList;
import java.util.List;

import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener, WeekView.WeekViewLoader {

    private ImageButton imgBtnCreate, imgBtnLogo, imgBtnPhotoProfil, imgBtnMenu, imgBtnPlanner;
    private WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        imgBtnCreate = (ImageButton) findViewById(R.id.imgBtnCreate);
        imgBtnLogo = (ImageButton) findViewById(R.id.imgBtnLogo);
        imgBtnPhotoProfil = (ImageButton) findViewById(R.id.imgBtnPhotoProfil);
        imgBtnMenu = (ImageButton) findViewById(R.id.imgBtnMenu);
        imgBtnPlanner = (ImageButton) findViewById(R.id.imgBtnPlanner);
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set le chargement des events.
        mWeekView.setWeekViewLoader(this);

        imgBtnCreate.setOnClickListener(this);
        imgBtnLogo.setOnClickListener(this);
        imgBtnPhotoProfil.setOnClickListener(this);
        imgBtnMenu.setOnClickListener(this);
        imgBtnPlanner.setOnClickListener(this);
        imgBtnCreate.setOnClickListener(this);
    }

    @Override
    public List<? extends WeekViewEvent> onWeekViewLoad() {
        List<WeekViewEvent> elements = new ArrayList<>();

        // TODO: Get les éléments et les ajouter à la liste

        return elements;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(imgBtnCreate)) {
            // TODO: Renvoyer à l'interface de création
        } else if (v.equals(imgBtnLogo)) {
            // TODO: Fonction maybe
        } else if (v.equals(imgBtnPhotoProfil)) {
            // TODO: Offrir les fonctionnalités de modif de compte et déconnexion
        } else if (v.equals(imgBtnMenu)) {
            // TODO: Renvoyer à l'interface Menu des calendriers
        } else if (v.equals(imgBtnPlanner)) {
            // TODO: Renvoyer à l'interface Planner
        }
    }
}
