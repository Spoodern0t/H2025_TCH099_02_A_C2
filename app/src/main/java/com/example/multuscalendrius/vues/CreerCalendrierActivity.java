package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;

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
        if (intent != null) {
            nom = intent.getStringExtra("NOM");
            description = intent.getStringExtra("DESCRIPTION");

            textNom.setText(nom);
            textDescription.setText(description);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreer) {
            nom = textNom.getText().toString().trim();
            description = textDescription.getText().toString().trim();
            // TODO: Méthode CreerCalendrier
            setResult(RESULT_OK);
            finish();
        } else if (v == btnAnnuler) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
