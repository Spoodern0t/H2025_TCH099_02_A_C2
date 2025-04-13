package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.vuemodele.UserVueModele;

public class CreerCalendrierActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textNom, textDescription;
    private Button btnCreer, btnAnnuler;
    private String nom, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_calendrier);

        textNom = findViewById(R.id.textNom);
        textDescription = findViewById(R.id.textDescription);
        btnCreer = findViewById(R.id.btnCreer);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        btnCreer.setOnClickListener(this);
        btnAnnuler.setOnClickListener(this);

        Intent intent = getIntent();
        long elementId = intent.getLongExtra("ID", -1);
        if (elementId >= 0) {

            TextView textCreerCalendrier = findViewById(R.id.textCreerCalendrier);
            ImageButton imgBtnSuppCalendrier = findViewById(R.id.imgBtnSuppCalendrier);

            textCreerCalendrier.setText(R.string.modifiez_votre_calendrier);
            imgBtnSuppCalendrier.setVisibility(View.VISIBLE);
            btnCreer.setText(R.string.modifier);

            imgBtnSuppCalendrier.setOnClickListener(v -> {
                // TODO: Delete Calendrier
            });
            UserVueModele userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
            //UserCalendar calendrier = userVueModele.getUserCalendarById();
            //nom = calendrier.getNomCalendrier();
            //description = calendrier.getDescription();

            textNom.setText(nom);
            textDescription.setText(description);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreer) {
            nom = textNom.getText().toString().trim();
            description = textDescription.getText().toString().trim();
            // TODO: Créer Calendrier
            // TODO: Modifier Calendrier
            setResult(RESULT_OK);
            finish();
        } else if (v == btnAnnuler) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
