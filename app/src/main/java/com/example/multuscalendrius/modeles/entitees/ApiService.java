package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {

    private static final String BASE_URL = "https://api.example.com";
    private OkHttpClient client;
    private ObjectMapper mapper;

    public ApiService() {
        client = new OkHttpClient();
        mapper = new ObjectMapper();
    }

    // --------------------------
    // Utilisateur (inscription, connexion)
    // --------------------------

    public void register(String email, String username, String password, String confirmPassword, ApiCallback<LoginResponse> callback) {
        try {
            // Création d'un objet JSON avec les informations d'inscription
            JSONObject json = new JSONObject();
            json.put("adresse-courriel", email);
            json.put("nom-utilisateur", username);
            json.put("mot-de-passe", password);
            json.put("conf-mdp", confirmPassword);

            // Préparation du corps de la requête avec le type MIME JSON
            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));

            // Construction de la requête HTTP POST vers l'endpoint /register
            Request request = new Request.Builder()
                    .url(BASE_URL + "/register")
                    .post(body)
                    .build();

            // Exécution asynchrone de la requête
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        try {
                            // Mapping de la réponse JSON vers un objet LoginResponse via Jackson
                            LoginResponse loginResponse = mapper.readValue(responseBody, LoginResponse.class);
                            callback.onSuccess(loginResponse);
                        } catch (JsonProcessingException e) {
                            callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                        }
                    } else {
                        callback.onFailure("Échec de l'inscription: " + response.code());
                    }
                }
            });
        } catch(Exception e) {
            callback.onFailure("Erreur lors de la création de la requête: " + e.getMessage());
        }
    }


    public void login(String email, String username, String password, String confirmPassword, ApiCallback<LoginResponse> callback) {
        try {
            // Création d'un objet JSON avec toutes les informations de connexion
            JSONObject json = new JSONObject();
            json.put("adresse-courriel", email);
            json.put("nom-utilisateur", username);
            json.put("mot-de-passe", password);
            json.put("conf-mdp", confirmPassword);

            // Préparation du corps de la requête en JSON
            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/login")
                    .post(body)
                    .build();

            // Exécution asynchrone de la requête
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        try {
                            LoginResponse loginResponse = mapper.readValue(responseBody, LoginResponse.class);
                            callback.onSuccess(loginResponse);
                        } catch (JsonProcessingException e) {
                            callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                        }
                    } else {
                        callback.onFailure("Échec de la connexion: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création de la requête: " + e.getMessage());
        }
    }


    // --------------------------
    // Calendriers
    // --------------------------

    public void getCalendriers(String userToken, ApiCallback<List<Calendrier>> callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/calendriers")
                .addHeader("Authorization", "Bearer " + userToken)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Erreur réseau: " + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    try {
                        List<Calendrier> calendriers = mapper.readValue(json, new TypeReference<List<Calendrier>>(){});
                        callback.onSuccess(calendriers);
                    } catch (JsonProcessingException e) {
                        callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                    }
                } else {
                    callback.onFailure("Échec du chargement des calendriers: " + response.code());
                }
            }
        });
    }

    public void createCalendrier(Calendrier calendrier, String userToken, ApiCallback<Calendrier> callback) {
        try {
            String json = mapper.writeValueAsString(calendrier);
            RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers")
                    .addHeader("Authorization", "Bearer " + userToken)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful() && response.body() != null) {
                        String responseJson = response.body().string();
                        try {
                            Calendrier createdCalendrier = mapper.readValue(responseJson, Calendrier.class);
                            callback.onSuccess(createdCalendrier);
                        } catch (JsonProcessingException e) {
                            callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                        }
                    } else {
                        callback.onFailure("Échec de la création du calendrier: " + response.code());
                    }
                }
            });
        } catch (IOException e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    public void updateCalendrier(Calendrier calendrier, String userToken, ApiCallback<Calendrier> callback) {
        try {
            String json = mapper.writeValueAsString(calendrier);
            RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + calendrier.getId())
                    .addHeader("Authorization", "Bearer " + userToken)
                    .put(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful() && response.body() != null) {
                        String responseJson = response.body().string();
                        try {
                            Calendrier updatedCalendrier = mapper.readValue(responseJson, Calendrier.class);
                            callback.onSuccess(updatedCalendrier);
                        } catch (JsonProcessingException e) {
                            callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                        }
                    } else {
                        callback.onFailure("Échec de la mise à jour: " + response.code());
                    }
                }
            });
        } catch (IOException e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    public void deleteCalendrier(String calendrierId, String userToken, ApiCallback<Void> callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/calendriers/" + calendrierId)
                .addHeader("Authorization", "Bearer " + userToken)
                .delete()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Erreur réseau: " + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure("Échec de la suppression: " + response.code());
                }
            }
        });
    }

    // --------------------------
    // Evenements => CHANGER PAR USER ET USERCALENDAR
    // --------------------------

    public void getEvenements(String calendrierId, String userToken, ApiCallback<List<Evenement>> callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/calendriers/" + calendrierId + "/evenements")
                .addHeader("Authorization", "Bearer " + userToken)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Erreur réseau: " + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful() && response.body() != null) {
                    String json = response.body().string();
                    try {
                        List<Evenement> evenements = mapper.readValue(json, new TypeReference<List<Evenement>>() {});
                        callback.onSuccess(evenements);
                    } catch (JsonProcessingException e) {
                        callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                    }
                } else {
                    callback.onFailure("Échec du chargement des événements: " + response.code());
                }
            }
        });
    }

    public void createEvenement(String calendrierId, Evenement evenement, String userToken, ApiCallback<Evenement> callback) {
        try {
            String json = mapper.writeValueAsString(evenement);
            RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + calendrierId + "/evenements")
                    .addHeader("Authorization", "Bearer " + userToken)
                    .post(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful() && response.body() != null) {
                        String responseJson = response.body().string();
                        try {
                            Evenement createdEvenement = mapper.readValue(responseJson, Evenement.class);
                            callback.onSuccess(createdEvenement);
                        } catch (JsonProcessingException e) {
                            callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                        }
                    } else {
                        callback.onFailure("Échec de la création de l'événement: " + response.code());
                    }
                }
            });
        } catch (IOException e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    public void updateEvenement(String evenementId, Evenement evenement, String userToken, ApiCallback<Evenement> callback) {
        try {
            String json = mapper.writeValueAsString(evenement);
            RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/evenements/" + evenementId)
                    .addHeader("Authorization", "Bearer " + userToken)
                    .put(body)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful() && response.body() != null) {
                        String responseJson = response.body().string();
                        try {
                            Evenement updatedEvenement = mapper.readValue(responseJson, Evenement.class);
                            callback.onSuccess(updatedEvenement);
                        } catch (JsonProcessingException e) {
                            callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                        }
                    } else {
                        callback.onFailure("Échec de la mise à jour de l'événement: " + response.code());
                    }
                }
            });
        } catch (IOException e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    public void deleteEvenement(String evenementId, String userToken, ApiCallback<Void> callback) {
        Request request = new Request.Builder()
                .url(BASE_URL + "/evenements/" + evenementId)
                .addHeader("Authorization", "Bearer " + userToken)
                .delete()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure("Erreur réseau: " + e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onFailure("Échec de la suppression de l'événement: " + response.code());
                }
            }
        });
    }
}
