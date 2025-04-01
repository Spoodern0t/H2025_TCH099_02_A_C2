package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;
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

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView textViewLogin;
    private OkHttpClient client;
    private ObjectMapper objectMapper;

    // URL de l'API d'inscription (à adapter à votre serveur)
    private static final String SIGNUP_URL = "https://api.example.com/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Références aux éléments du layout
        editTextEmail = findViewById(R.id.editTextEmailSignUp);
        editTextPassword = findViewById(R.id.editTextPasswordSignUp);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.textViewLogin);

        // Initialisation d'OkHttp et de Jackson ObjectMapper
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();

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
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        // Vérification des champs obligatoires
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Création d'un objet JSON pour les données d'inscription
        JSONObject jsonSignUp = new JSONObject();
        try {
            jsonSignUp.put("email", email);
            jsonSignUp.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la création du JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        // Préparation du corps de la requête avec le type MIME JSON
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonSignUp.toString(), JSON);

        // Construction de la requête HTTP POST
        Request request = new Request.Builder()
                .url(SIGNUP_URL)
                .post(body)
                .build();

        /*

        // Exécution asynchrone de la requête
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(SignUpActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    try {
                        // Mapping de la réponse JSON vers un objet LoginResponse à l'aide de Jackson
                        LoginResponse loginResponse = objectMapper.readValue(responseBody, LoginResponse.class);

                        // Vérification de la validité du token retourné
                        if (loginResponse.getToken()) {
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
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        runOnUiThread(() ->
                                Toast.makeText(SignUpActivity.this, "Erreur de parsing", Toast.LENGTH_SHORT).show()
                        );
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(SignUpActivity.this, "Échec de l'inscription", Toast.LENGTH_SHORT).show()
                    );


                }
            }
        });

         */
    }
}
