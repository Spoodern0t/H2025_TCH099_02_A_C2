package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import okhttp3.*;

public class LoginActivity extends AppCompatActivity {

    // Déclaration des composants UI
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;

    // Objets pour les requêtes réseau et le traitement JSON
    private OkHttpClient client;
    private JSONObject obj;
    private ObjectMapper mapper;
    private LoginResponse rep;

    // URL de l'API (à adapter)
    private static final String LOGIN_URL = "https://api.exemple.com/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Liaison des vues depuis le layout
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Initialisation des objets nécessaires
        client = new OkHttpClient();
        obj = new JSONObject();
        mapper = new ObjectMapper();
        rep = new LoginResponse();

        // Définir l'action du bouton de connexion
        buttonLogin.setOnClickListener(v -> login());

        // Rediriger vers la page d'inscription en cas de clic sur "S'inscrire"
        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void login() {
        // Récupération et vérification des données saisies
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Création d'un objet JSON pour les informations de connexion
        JSONObject jsonLogin = new JSONObject();
        try {
            jsonLogin.put("email", email);
            jsonLogin.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        // Préparation du corps de la requête avec le type MIME JSON
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonLogin.toString(), JSON);

        // Construction de la requête POST
        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .post(body)
                .build();

        // Exécution asynchrone de la requête pour éviter le blocage du thread principal
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Mise à jour de l'UI en cas d'erreur réseau
                runOnUiThread(() ->
                        Toast.makeText(LoginActivity.this, "Erreur réseau", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Traitement de la réponse du serveur
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    try {
                        // Désérialisation de la réponse JSON en objet LoginResponse
                        rep = mapper.readValue(responseBody, LoginResponse.class);

                        // Vérification de la présence d'un token indiquant une connexion réussie
                        if (rep.getToken()) {
                            // Mise à jour de l'UI sur le thread principal
                            runOnUiThread(() -> {
                                /*

                                // Création de l'Intent pour rediriger vers l'activité principale
                                Intent intent = new Intent(LoginActivity.this, com.example.multuscalendrius.vues.MenuCalendrierActivity.class);
                                startActivity(intent);
                                finish(); // Fermer LoginActivity

                                 */
                            });
                        } else {
                            runOnUiThread(() ->
                                    Toast.makeText(LoginActivity.this, "Token manquant", Toast.LENGTH_SHORT).show()
                            );
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        runOnUiThread(() ->
                                Toast.makeText(LoginActivity.this, "Erreur de parsing", Toast.LENGTH_SHORT).show()
                        );
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(LoginActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}
