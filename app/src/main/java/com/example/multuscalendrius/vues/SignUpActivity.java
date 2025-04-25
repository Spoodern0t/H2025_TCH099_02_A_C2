package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.vuemodele.ApiCallback;
import com.example.multuscalendrius.vuemodele.UserVueModele;

public class SignUpActivity extends AppCompatActivity {

    private final String FORMAT_EMAIL = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    private UserVueModele userVueModele;
    private EditText editTextEmail, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Références aux éléments du layout
        editTextEmail = findViewById(R.id.editTextEmailSignUp);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPasswordSignUp);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.textViewLogin);

        userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
        userVueModele.getSucces().observe(this, user -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
        userVueModele.getErreur().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // Action lors du clic sur le bouton d'inscription
        buttonSignUp.setOnClickListener(v -> registerUser());

        // Redirection vers la page de connexion si l'utilisateur est déjà inscrit
        textViewLogin.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void registerUser() {
        // Récupération des données saisies
        String email = editTextEmail.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Vérification des champs obligatoires
        if (!email.matches(FORMAT_EMAIL)) {
            editTextEmail.setError("Veuillez remplir un format d'email valide");
            Toast.makeText(this, "Veuillez remplir un format d'email valide", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Veuillez remplir votre email");
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.isEmpty()) {
            editTextUsername.setError("Veuillez remplir votre nom d'utilisateur");
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Veuillez remplir votre mot de passe");
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Veuillez confirmer votre mot de passe");
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return;
        }

        userVueModele.inscription(email, username, password);
    }
}

