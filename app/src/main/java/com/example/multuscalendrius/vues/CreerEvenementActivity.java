package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;
import com.example.multuscalendrius.vuemodele.UserVueModele;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreerEvenementActivity extends AppCompatActivity implements View.OnClickListener {

    private CalendrierVueModele calendrierVueModele;
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
            // TODO: Méthode CreerEvenement
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
