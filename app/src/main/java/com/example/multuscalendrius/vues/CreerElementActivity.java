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
import android.widget.Toast;

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
import com.example.multuscalendrius.vues.adaptateurs.EvenementAdaptateur;

import java.time.LocalDateTime;
import java.util.List;

public class CreerElementActivity extends AppCompatActivity implements View.OnClickListener {

    private CalendrierVueModele calendrierVueModele;
    private Element element = new Element();
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
    private Integer calendrierId, evenementId;
    private List<Evenement> evenements;
    private EvenementAdaptateur adaptateur;

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
                            evenementId = data.getIntExtra("POSITION", -1);
                            if (evenementId >= 0) {
                                // TODO: ADD evenement dans adaptor
                                //evenementSpinner.setSelection(evenementId);
                            }
                        }
                    }
                });

        calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        calendrierVueModele.getSucces().observe(this, succes -> {
            if (succes) {
                setResult(RESULT_OK);
                finish();
            }
        });
        calendrierVueModele.getErreur().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // Change le picker pour être sur 24 heures
        debutTP.setIs24HourView(true);
        finTP.setIs24HourView(true);

        btnCreer.setOnClickListener(this);
        btnAnnuler.setOnClickListener(this);
        btnAddEvenement.setOnClickListener(this);

        CalendrierVueModele calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        Calendrier calendrier = calendrierVueModele.getCurrentCalendrier();
        calendrierId = calendrier.getId();

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

        evenements = calendrierVueModele.getCurrentCalendrier().getEvenements();
        adaptateur = new EvenementAdaptateur(this, R.layout.layout_evenement, evenements);
        evenementSpinner.setAdapter(adaptateur);

        Intent intent = getIntent();
        int elementId = intent.getIntExtra("ID", -1);
        if (elementId > 0) {

            TextView textCreerElement = findViewById(R.id.textCreerElement);
            ImageButton imgBtnSuppElement = findViewById(R.id.imgBtnSuppElement);

            textCreerElement.setText(R.string.modifiez_votre_element);
            imgBtnSuppElement.setVisibility(View.VISIBLE);
            btnCreer.setText(R.string.modifier);

            element = calendrier.getElementById(elementId);
            imgBtnSuppElement.setOnClickListener(v -> {
                calendrierVueModele.deleteElement(element);
            });

            nom = element.getNom();
            description = element.getDescription();

            debutElement = element.getDateDebut();
            finElement = element.getDateFin();
            deadline = (debutElement == null);
            evenementId = element.getEvenementId();

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
    protected void onResume() {
        super.onResume();
        evenements = calendrierVueModele.getCurrentCalendrier().getEvenements();
        adaptateur = new EvenementAdaptateur(this, R.layout.layout_evenement, evenements);
        evenementSpinner.setAdapter(adaptateur);
        //evenementSpinner.setSelection(adaptateur.getCount() - 1);
    }

    @Override
    public void onClick(View v) {
        if (v == btnCreer) {
            nom = textNom.getText().toString().trim();
            description = textDescription.getText().toString().trim();

            // Vérification que tous les champs sont remplis
            if (nom.isEmpty()) {
                textNom.setError("Veuillez nommer votre element");
                Toast.makeText(this, "Veuillez nommer votre element", Toast.LENGTH_SHORT).show();
                return;
            }
            if (description.isEmpty()) {
                description = "";
            }

            deadline = elementSwitch.isChecked();
            if (!deadline) {
                debutElement = LocalDateTime.of(debutDP.getYear(), debutDP.getMonth(), debutDP.getDayOfMonth(), debutTP.getHour(), debutTP.getMinute());
            } else {
                debutElement = null;
            }
            finElement = LocalDateTime.of(finDP.getYear(), finDP.getMonth(), finDP.getDayOfMonth(), finTP.getHour(), finTP.getMinute());

            Evenement selectedEvenement = (Evenement) evenementSpinner.getSelectedItem();
            evenementId = selectedEvenement != null ? selectedEvenement.getId() : null;

            element.setCalendrierId(calendrierId);
            element.setNom(nom);
            element.setDescription(description);
            element.setEvenementId(evenementId);
            element.setDateDebut(debutElement);
            element.setDateFin(finElement);
            if (element.getId() > 0) {
                calendrierVueModele.updateElement(element);
            } else {
                calendrierVueModele.addElement(element);
            }
        } else if (v == btnAnnuler) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (v == btnAddEvenement) {
            Intent intent = new Intent(this, CreerEvenementActivity.class);
            launcher.launch(intent);
        }
    }
}