package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vuemodele.UserVueModele;

public class CreerCalendrierActivity extends AppCompatActivity implements View.OnClickListener {

    private UserVueModele userVueModele;
    private UserCalendar calendrier = new UserCalendar();
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

        userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
        userVueModele.getSucces().observe(this, succes -> {
            if (succes) {
                setResult(RESULT_OK);
                finish();
            }
        });
        userVueModele.getErreur().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        btnCreer.setOnClickListener(this);
        btnAnnuler.setOnClickListener(this);

        Intent intent = getIntent();
        int calendrierId = intent.getIntExtra("ID", -1);
        if (calendrierId > 0) {
            calendrier = userVueModele.getCurrentUser().getUserCalendarById(calendrierId);
            modifierCalendrier(calendrier);
        }
    }

    private void modifierCalendrier(UserCalendar calendrier) {

        TextView textCreerCalendrier = findViewById(R.id.textCreerCalendrier);
        ImageButton imgBtnSuppCalendrier = findViewById(R.id.imgBtnSuppCalendrier);

        textCreerCalendrier.setText(R.string.modifiez_votre_calendrier);
        imgBtnSuppCalendrier.setVisibility(View.VISIBLE);
        btnCreer.setText(R.string.modifier);


        imgBtnSuppCalendrier.setOnClickListener(v -> {
            userVueModele.deleteCalendrier(calendrier);
        });

        nom = calendrier.getNomCalendrier() != null ? calendrier.getNomCalendrier() : "";
        description = calendrier.getDescription() != null ? calendrier.getDescription() : "";

        textNom.setText(nom);
        textDescription.setText(description);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreer) {
            nom = textNom.getText().toString().trim();
            description = textDescription.getText().toString().trim();

            // Vérification que tous les champs sont remplis
            if (nom.isEmpty()) {
                textNom.setError("Veuillez nommer votre calendrier");
                Toast.makeText(this, "Veuillez nommer votre calendrier", Toast.LENGTH_SHORT).show();
                return;
            }
            if (description.isEmpty()) {
                description = "";
            }

            calendrier.setNomCalendrier(nom);
            calendrier.setDescription(description);
            if (calendrier.getCalendarId() > 0) {
                userVueModele.updateCalendrier(calendrier);
            } else {
                userVueModele.creerCalendrier(calendrier);
            }
        } else if (v == btnAnnuler) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
