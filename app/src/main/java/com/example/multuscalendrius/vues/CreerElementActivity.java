package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.modeles.entitees.Evenement;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;

import java.time.LocalDateTime;

public class CreerElementActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText textNom, textDescription;
    private TextView debutTV;
    private ActivityResultLauncher<Intent> launcher;
    private ConstraintLayout debutCL;
    private DatePicker debutDP, finDP;
    private TimePicker debutTP, finTP;
    private SwitchCompat elementSwitch;
    private Spinner evenementSpinner;
    private Button btnCreer, btnAnnuler;
    private ImageButton btnAddEvenement;
    private String nom, description;
    private boolean deadline;
    private LocalDateTime debutElement, finElement;
    private int calendrierId, evenementId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_element);

        textNom = findViewById(R.id.textNom);
        textDescription = findViewById(R.id.textDescription);
        debutTV = findViewById(R.id.debutTV);
        debutCL = findViewById(R.id.debutCL);
        debutDP = findViewById(R.id.debutDP);
        debutTP = findViewById(R.id.debutTP);
        finDP = findViewById(R.id.finDP);
        finTP = findViewById(R.id.finTP);
        elementSwitch = findViewById(R.id.elementSwitch);
        evenementSpinner = findViewById(R.id.evenementSpinner);
        btnAddEvenement = findViewById(R.id.btnAddEvenement);
        btnCreer = findViewById(R.id.btnCreer);
        btnAnnuler = findViewById(R.id.btnAnnuler);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            evenementId = data.getIntExtra("EVENT", -1);
                            if (evenementId >= 0) {
                                // TODO: ADD evenement dans adaptor
                                evenementSpinner.setSelection(evenementId);
                            }
                        }
                    }
                });

        // Change le picker pour être sur 24 heures
        debutTP.setIs24HourView(true);
        finTP.setIs24HourView(true);

        btnCreer.setOnClickListener(this);
        btnAnnuler.setOnClickListener(this);
        btnAddEvenement.setOnClickListener(this);

        CalendrierVueModele calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        Calendrier calendrier = calendrierVueModele.getCurrentCalendrier();
        //calendrierId = calendrier.getId();



        // Masquer le date picker du début pour une date limite
        elementSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                debutTV.setVisibility(View.GONE);
                debutCL.setVisibility(View.GONE);
            } else {
                debutTV.setVisibility(View.VISIBLE);
                debutCL.setVisibility(View.VISIBLE);
            }
        });

        Intent intent = getIntent();
        int elementId = intent.getIntExtra("ID", -1);
        if (elementId >= 0) {

            TextView textCreerElement = findViewById(R.id.textCreerElement);
            ImageButton imgBtnSuppElement = findViewById(R.id.imgBtnSuppElement);

            textCreerElement.setText("Modifiez votre élément !");
            imgBtnSuppElement.setVisibility(View.VISIBLE);
            btnCreer.setText("Modifier");

            imgBtnSuppElement.setOnClickListener(v -> {
                // TODO: Delete Element
            });

            Element element = calendrier.getElementById(elementId);
            nom = element.getNom();
            description = element.getDescription();

            debutElement = element.getDateDebut();
            finElement = element.getDateFin();
            deadline = (debutElement == null);
            evenementId = element.getEvenement();

            textNom.setText(nom);
            textDescription.setText(description);
            debutDP.updateDate(debutElement.getYear(), debutElement.getMonthValue(), debutElement.getDayOfMonth());
            elementSwitch.setChecked(deadline);
            if (!deadline) {
                debutTP.setHour(debutElement.getHour());
                debutTP.setMinute(debutElement.getMinute());
            }
            finTP.setHour(finElement.getHour());
            finTP.setMinute(debutElement.getMinute());

            evenementSpinner.setSelection(evenementId);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreer) {
            nom = textNom.getText().toString().trim();
            description = textDescription.getText().toString().trim();
            deadline = elementSwitch.isChecked();
            if (!deadline) {
                debutElement = LocalDateTime.of(debutDP.getYear(), debutDP.getMonth(), debutDP.getDayOfMonth(), debutTP.getHour(), debutTP.getMinute());
            } else {
                debutElement = null;
            }
            finElement = LocalDateTime.of(finDP.getYear(), finDP.getMonth(), finDP.getDayOfMonth(), finTP.getHour(), finTP.getMinute());

            Evenement selectedEvenement = (Evenement) evenementSpinner.getSelectedItem();
            evenementId = selectedEvenement.getId();
            Element newElement = new Element(0, calendrierId, nom, description, evenementId, debutElement, finElement);
            // TODO: Creer Element
            // TODO: Modifier Element
            setResult(RESULT_OK);
            finish();
        } else if (v == btnAnnuler) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (v == btnAddEvenement) {
            Intent intent = new Intent(this, CreerEvenementActivity.class);
            startActivity(intent);
        }
    }
}