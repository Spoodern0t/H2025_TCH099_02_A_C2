package com.example.multuscalendrius.vues;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.ApiService;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.vuemodele.ApiCallback;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Liaison des vues depuis le layout XML
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Initialisation de l'ApiService
        apiService = new ApiService();

        // Définir les écouteurs de clic
        buttonLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);
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

            // Appel de la méthode connexion de l'ApiService
            apiService.connexion(email, password, new ApiCallback<User>() {
                @Override
                public void onSuccess(User user) {
                    // Vérification de la validité du token (supposant que User possède une méthode getToken())
                    if (user != null && user.getToken() != null && !user.getToken().isEmpty()) {
                        runOnUiThread(() -> {
                            Intent intent = new Intent(LoginActivity.this, MenuCalendriersActivity.class);
                            intent.putExtra("user", (Serializable) user);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(LoginActivity.this, "Connexion échouée: token invalide", Toast.LENGTH_SHORT).show()
                        );
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    runOnUiThread(() ->
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show()
                    );
                }
            });
        } else if (v.equals(textViewSignUp)) {
            // Redirection vers l'activité d'inscription
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}

