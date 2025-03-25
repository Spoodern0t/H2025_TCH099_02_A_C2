package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.ApiCallback;
import com.example.multuscalendrius.modeles.entitees.ApiService;
import com.example.multuscalendrius.modeles.entitees.LoginResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextUsername, editTextPassword, editTextConfirmPassword;
    private Button buttonSignUp;
    private TextView textViewLogin;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Liaison des vues depuis le layout XML
        editTextEmail = findViewById(R.id.editTextEmailSignUp);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPasswordSignUp);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.textViewLogin);

        // Initialisation de l'ApiService
        apiService = new ApiService();

        // Définir les écouteurs de clic
        buttonSignUp.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(buttonSignUp)) {
            String email = editTextEmail.getText().toString().trim();
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();

            // Vérification des champs
            if (email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
                return;
            }

            // Appel de l'API via ApiService.register pour créer un nouvel utilisateur
            apiService.register(email, username, password, confirmPassword, new ApiCallback<LoginResponse>() {
                @Override
                public void onSuccess(LoginResponse result) {
                    if (result.getToken()) {
                        runOnUiThread(() -> {
                            Toast.makeText(SignUpActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                            // Redirection vers l'activité de connexion
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
        } else if (v.equals(textViewLogin)) {
            // Redirection vers LoginActivity
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
