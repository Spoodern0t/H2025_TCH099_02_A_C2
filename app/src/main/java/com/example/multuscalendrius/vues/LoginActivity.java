package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.vuemodele.UserVueModele;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private UserVueModele userVueModele;
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Liaison des vues depuis le layout XML
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Définir les écouteurs de clic
        buttonLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);

        userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
        userVueModele.getUser().observe(this, user -> {
            Intent intent = new Intent(this, MenuCalendriersActivity.class);
            startActivity(intent);
            finish();
        });
        userVueModele.getErreur().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onClick(View v) {
        if (v.equals(buttonLogin)) {
            // Récupération des données saisies par l'utilisateur
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Vérification que tous les champs sont remplis
            if (email.isEmpty()) {
                editTextEmail.setError("Veuillez remplir l'email");
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                editTextPassword.setError("Veuillez remplir le mot de passe");
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            userVueModele.syncLogin(email, password);

        } else if (v.equals(textViewSignUp)) {
            // Redirection vers l'activité d'inscription
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}

