package com.example.multuscalendrius.modeles.entitees;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
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
                    .url(BASE_URL + "/inscription")
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


    public void login(String username, String password, ApiCallback<User> callback) {
        try {
            // Création d'un objet JSON avec toutes les informations de connexion
            JSONObject json = new JSONObject();
            json.put("nom-utilisateur", username);
            json.put("mot-de-passe", password);

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
                            User user = mapper.readValue(responseBody, User.class);
                            callback.onSuccess(user);
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

    public void getCalendrier(Long calendrierId, ApiCallback<Calendrier> callback) {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + calendrierId) // Requête GET classique avec ID dans l’URL
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
                        String jsonResponse = response.body().string();
                        try {
                            Calendrier calendrier = mapper.readValue(jsonResponse, Calendrier.class);
                            callback.onSuccess(calendrier);
                        } catch (JsonProcessingException e) {
                            callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                        }
                    } else {
                        callback.onFailure("Échec du chargement du calendrier: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création de la requête: " + e.getMessage());
        }
    }




    public void createCalendrier(String nom, String description, String auteur, ApiCallback<Calendrier> callback) {
        try {
            // Création d'un objet JSON contenant les paramètres
            JSONObject json = new JSONObject();
            json.put("nom", nom);
            json.put("description", description);
            json.put("auteur", auteur);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
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
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }


    public void updateCalendrier(Calendrier calendrier, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON en extrayant les champs nécessaires du calendrier
            JSONObject json = new JSONObject();
            json.put("id", calendrier.getId());
            json.put("nom", calendrier.getNom());
            json.put("description", calendrier.getDescription());
            json.put("auteur", calendrier.getAuteur());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + calendrier.getId())
                    .put(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // On s'attend à recevoir uniquement un code HTTP 200 OK comme réponse
                    if (response.code() == 200) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de la mise à jour: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }



    public void deleteCalendrier(Calendrier calendrier, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON contenant l'ID du calendrier
            JSONObject json = new JSONObject();
            json.put("id", calendrier.getId());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));

            // Construction de la requête DELETE en envoyant l'objet JSON dans le body
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/delete")
                    .delete(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200 || response.code() == 204) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de la suppression: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }


    // ---------- Evenements ----------

    public void createEvenement(Calendrier calendrier, String titre, String description, ApiCallback<Evenement> callback) {
        try {
            // Extraction de l'ID du calendrier et création d'un objet JSON avec les informations de l'événement
            JSONObject json = new JSONObject();
            json.put("calendrierId", calendrier.getId());
            json.put("titre", titre);
            json.put("description", description);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + calendrier.getId() + "/evenements")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful() && response.body() != null) {
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
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

   //On modifie l'evenement avant et le place en parametre pour pouvoir effectuer ce changement
    public void updateEvenement(Evenement evenement, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON en extrayant les données de l'objet Evenement
            JSONObject json = new JSONObject();
            json.put("id", evenement.getId());
            json.put("calendrierId", evenement.getCalendrierId());
            json.put("titre", evenement.getTitre());
            json.put("description", evenement.getDescription());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + evenement.getCalendrierId() + "/evenements/" + evenement.getId())
                    .put(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // On s'attend à recevoir uniquement un code HTTP 200 OK
                    if (response.code() == 200) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de la mise à jour de l'événement: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }


    public void deleteEvenement(Evenement evenement, ApiCallback<Boolean> callback) {
        try {
            // Créer un objet JSON contenant l'id de l'événement et l'id du calendrier
            JSONObject json = new JSONObject();
            json.put("id", evenement.getId());
            json.put("calendrierId", evenement.getCalendrierId());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + evenement.getCalendrierId() + "/evenements/" + evenement.getId())
                    .delete(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.code() == 200) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de la suppression de l'événement: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    // ---------- ELEMENTS ----------

    // Fonction pour créer une nouvelle element dans un calendrier
    public void createElement(Calendrier calendrier, String nom, String description, Evenement evenement, LocalDateTime dateDebut,
            LocalDateTime dateFin, ApiCallback<Element> callback) {
        try {
            // Création d'un objet JSON avec les informations de la deadline
            JSONObject json = new JSONObject();
            json.put("calendrierId", calendrier.getId());
            json.put("nom",nom );
            json.put("description",description );
            // Convertir l'objet Evenement en JSON via le mapper
            json.put("evenement",new JSONObject(mapper.writeValueAsString(evenement)));
            // Convertir la deadlineDateTime en chaîne ISO 8601
            json.put("dateDebut", dateDebut.toString() );
            json.put("dateFin", dateDebut.toString() );

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + calendrier.getId() + "/deadline")
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
                            Element createdElement = mapper.readValue(responseJson, Element.class);
                            callback.onSuccess(createdElement);
                        } catch (JsonProcessingException e) {
                            callback.onFailure("Erreur de parsing JSON: " + e.getMessage());
                        }
                    } else {
                        callback.onFailure("Échec de la création de la deadline: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    // Fonction pour mettre à jour une Deadline existant
    public void updateElement(Element element, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON en extrayant les données de l'objet Deadline
            JSONObject json = new JSONObject();
            json.put("id", element.getId());
            json.put("calendrierId", element.getCalendrierId());
            json.put("nom", element.getNom());
            json.put("description", element.getDescription());
            json.put("evenement", new JSONObject(mapper.writeValueAsString(element.getEvenement())));
            json.put("dateDebut", element.getDateDebut().toString() );
            json.put("dateFin", element.getDateFin().toString() );

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + element.getCalendrierId() + "/deadline/" + element.getId())
                    .put(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    // On s'attend à recevoir uniquement un code HTTP 200 OK
                    if (response.code() == 200) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de la mise à jour de la deadline: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    // Fonction pour supprimer un element
    public void deleteElement(Element element, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON contenant l'id et le calendrierId de la deadline
            JSONObject json = new JSONObject();
            json.put("id", element.getId());
            json.put("calendrierId", element.getCalendrierId());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendriers/" + element.getCalendrierId() + "/deadline/" + element.getId())
                    .delete(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.code() == 200) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de la suppression de la deadline: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }


}
