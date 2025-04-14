package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.vuemodele.UserVueModele;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    private UserVueModele userVueModele;
    private EditText editTextNom, editTextEmail, editTextPassword;
    private Button btnModifier, btnAnnuler;
    private ImageButton imgBtnDeco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        editTextNom = findViewById(R.id.editTextNom);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnModifier = findViewById(R.id.btnModifier);
        btnAnnuler = findViewById(R.id.btnAnnuler);
        imgBtnDeco = findViewById(R.id.imgBtnDeco);

        userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
        userVueModele.getSucces().observe(this, succes -> {
            setResult(RESULT_OK);
            finish();
        });
        userVueModele.getErreur().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        User user = userVueModele.getCurrentUser();
        editTextNom.setText(user.getUsername());
        editTextEmail.setText(user.getEmail());

        imgBtnDeco.setOnClickListener(this);
        btnModifier.setOnClickListener(this);
        btnAnnuler.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(imgBtnDeco)) {
            userVueModele.decoUser();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btnModifier) {
            //TODO: Update User
        } else if (v == btnAnnuler) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
