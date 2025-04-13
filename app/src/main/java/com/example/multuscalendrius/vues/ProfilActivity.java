package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.vuemodele.UserVueModele;

public class ProfilActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextNom, editTextEmail, editTextPassword;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        editTextNom = findViewById(R.id.editTextNom);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogout = findViewById(R.id.btnLogout);

        UserVueModele userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
        User user = userVueModele.getUser().getValue();
        editTextNom.setText(user.getUsername());
        editTextEmail.setText(user.getEmail());

        // Définir les écouteurs de clic
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnLogout)) {
            // TODO: Deconnecter Utilisateur
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            finish();
        }
    }
}
