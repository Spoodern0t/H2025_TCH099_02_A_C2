package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.entitees.LoginResponse;
import com.example.multuscalendrius.vuemodele.ApiCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView textViewLogin;

    private ApiService apiService;

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

        // Initialisation de l'ApiService
        apiService = new ApiService();

        // Action lors du clic sur le bouton d'inscription
        buttonSignUp.setOnClickListener(v -> registerUser());

        // Redirection vers la page de connexion si l'utilisateur est déjà inscrit
        textViewLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
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

        // Appel de la méthode inscription de l'ApiService
        apiService.inscription(email, username, password, confirmPassword, new ApiCallback<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse loginResponse) {
                // Vérification de la validité du token retourné
                if (loginResponse.getToken()) {  // Ici, getToken() doit retourner un booléen indiquant le succès
                    runOnUiThread(() -> {
                        Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        // Redirection vers la page de connexion
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(SignUpActivity.this, "Inscription échouée: token invalide", Toast.LENGTH_SHORT).show()
                    );
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() ->
                        Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}

