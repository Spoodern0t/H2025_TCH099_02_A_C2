package com.example.multuscalendrius;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private Button test;
    private TextView textViewSignUp;
    private OkHttpClient client;
    private JSONObject obj;
    private ObjectMapper mapper;
    private static final String LOGIN_URL = "https://api.exemple.com/login"; // Remplacer URL d'API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        client = new OkHttpClient();
        obj = new JSONObject();
        mapper =  new ObjectMapper();

        // Action du bouton de connexion
        buttonLogin.setOnClickListener(v -> login());
        test.setOnClickListener(v -> login());

        // Action pour accéder à la page d'inscription
        textViewSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void login() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject jsonLogin = new JSONObject();
        try {
            jsonLogin.put("email", email);
            jsonLogin.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur JSON", Toast.LENGTH_SHORT).show();
            return;
        }
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(jsonLogin.toString(), JSON);

        Request request = new Request.Builder()
                .url(LOGIN_URL)
                .post(body)
                .build();





        try(Response response = client.newCall(request).execute()){
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                try {
                    // Supposons que l'API renvoie un objet JSON avec un champ "token"
                    LoginResponse loginResponse = mapper.readValue(responseBody, LoginResponse.class);
                    runOnUiThread(() -> {

                        // Rediriger vers le Dashboard (ou l'écran principal)
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        finish();
                    });
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Erreur de parsing", Toast.LENGTH_SHORT).show());
                }
            } else {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Échec de la connexion", Toast.LENGTH_SHORT).show());
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
