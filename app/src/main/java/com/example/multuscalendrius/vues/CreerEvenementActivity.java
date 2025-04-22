package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Evenement;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreerEvenementActivity extends AppCompatActivity implements View.OnClickListener {

    private CalendrierVueModele calendrierVueModele;
    private Calendrier calendrier;
    private Evenement evenement = new Evenement();
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

        calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        calendrier = calendrierVueModele.getCurrentCalendrier();

        calendrierVueModele.getSucces().observe(this, succes -> {
            Intent  intent = new Intent();
            intent.putExtra("POSITION", -1);
            setResult(RESULT_OK);
            finish();
        });

        Intent intent = getIntent();
        int evenementId = intent.getIntExtra("ID", -1);
        if (evenementId > 0) {
            evenement = calendrier.getEvenementById(evenementId);
            modifierEvenement(evenement);
        }
    }

    private void modifierEvenement(Evenement evenement) {
        TextView textCreerEvenement = findViewById(R.id.textCreerEvenement);
        ImageButton imgBtnSuppEvenement = findViewById(R.id.imgBtnSuppEvenement);

        textCreerEvenement.setText(R.string.modifiez_votre_evenement);
        imgBtnSuppEvenement.setVisibility(View.VISIBLE);
        btnCreer.setText(R.string.modifier);

        imgBtnSuppEvenement.setOnClickListener(v -> {
            calendrierVueModele.deleteEvenement(evenement);
        });

        nom = evenement.getTitre() != null ? evenement.getTitre() : "";
        description = evenement.getDescription() != null ? evenement.getDescription() : "";
        if (evenement.getCouleur() != null) {
            viewCouleur.setBackgroundColor(Color.parseColor("#" + evenement.getCouleur()));
        } else {
            viewCouleur.setBackgroundColor(1);
        }

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
                textNom.setError("Veuillez nommer votre evenement");
                Toast.makeText(this, "Veuillez nommer votre evenement", Toast.LENGTH_SHORT).show();
                return;
            }
            if (description.isEmpty()) {
                description = "";
            }

            evenement.setCalendrierId(calendrier.getId());
            evenement.setTitre(nom);
            evenement.setDescription(description);
            String hexColor = String.format("%06X", (0xFFFFFF & courantCouleur));
            evenement.setCouleur(hexColor);
            if (evenement.getId() > 0) {
                calendrierVueModele.updateEvenement(evenement);
            } else {
                calendrierVueModele.addEvenement(evenement);
            }
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
