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
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Evenement;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreerEvenementActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textNom, textDescription;
    private Button btnCreer, btnAnnuler;
    private String nom, description;
    private View viewCouleur;
    private int courantCouleur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_evenement);

        textNom = findViewById(R.id.textNom);
        textDescription = findViewById(R.id.textDescription);
        viewCouleur = findViewById(R.id.viewCouleur);
        btnCreer = findViewById(R.id.btnCreer);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        btnCreer.setOnClickListener(this);
        btnAnnuler.setOnClickListener(this);
        viewCouleur.setOnClickListener(this);

        CalendrierVueModele calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        Calendrier calendrier = calendrierVueModele.getCurrentCalendrier();
        //calendrierId = calendrier.getId();

        Intent intent = getIntent();
        int evenementId = intent.getIntExtra("ID", -1);
        if (evenementId >= 0) {

            TextView textCreerEvenement = findViewById(R.id.textCreerEvenement);
            ImageButton imgBtnSuppEvenement = findViewById(R.id.imgBtnSuppEvenement);

            textCreerEvenement.setText(R.string.modifiez_votre_evenement);
            imgBtnSuppEvenement.setVisibility(View.VISIBLE);
            btnCreer.setText(R.string.modifier);

            imgBtnSuppEvenement.setOnClickListener(v -> {
                // TODO: Delete Evenement
            });

            Evenement evenement = calendrier.getEvenementById(evenementId);

            nom = evenement.getTitre();
            description = evenement.getDescription();

            textNom.setText(nom);
            textDescription.setText(description);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreer) {
            nom = textNom.getText().toString().trim();
            description = textDescription.getText().toString().trim();
            // TODO: Creer Evenement
            // TODO: Modifier Element
            setResult(RESULT_OK);
            finish();
        } else if (v == btnAnnuler) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (v == viewCouleur) {
            openColorPickerDialogue();
        }
    }

    public void openColorPickerDialogue() {
        final AmbilWarnaDialog colorPickerDialogue = new AmbilWarnaDialog(this, courantCouleur,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {
                        // Ne rien faire
                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int couleur) {
                        courantCouleur = couleur;
                        viewCouleur.setBackgroundColor(courantCouleur);
                    }
                });
        colorPickerDialogue.show();
    }
}
