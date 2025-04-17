package com.example.multuscalendrius.modeles;

import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.modeles.entitees.Evenement;
import com.example.multuscalendrius.modeles.entitees.User;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vuemodele.ApiCallback;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {

    private static final String BASE_URL = "http://10.0.2.2:80/H2025_TCH099_02_A_API/index.php";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper;


    public ApiService() {
        JavaTimeModule module = new JavaTimeModule();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        mapper = new ObjectMapper().registerModule(module);
    }



    // --------------------------
    // Utilisateur (inscription, connexion)
    // --------------------------

    public void inscription(String email, String username, String password, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON avec les informations d'inscription
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("user-name", username);
            json.put("password", password);

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
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de l'inscription: " + response.code());
                    }
                }
            });
        } catch(Exception e) {
            callback.onFailure("Erreur lors de la création de la requête: " + e.getMessage());
        }
    }


    public void connexion(String email, String password, ApiCallback<User> callback) {
        try {
            // Création d'un objet JSON avec toutes les informations de connexion
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);


            // Préparation du corps de la requête en JSON
            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/connexion")
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

    public void decoUser(String token, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON contenant l'ID du calendrier
            JSONObject json = new JSONObject();
            json.put("token", token);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));

            // Construction de la requête DELETE en envoyant l'objet JSON dans le body
            Request request = new Request.Builder()
                    .url(BASE_URL + "/deconnexion")
                    .delete(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
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

    // --------------------------
    // UserCalendar
    // --------------------------

    public void getUserCalendar(String token, ApiCallback<List<UserCalendar>> callback) {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/usercalendars/" + token)
                    .get()
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if(response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        try {
                            List<UserCalendar> calendars = Arrays.asList(mapper.readValue(responseBody, UserCalendar[].class));
                            callback.onSuccess(calendars);
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

    public void getCalendrier(int calendrierId, String token, ApiCallback<Calendrier> callback) {
        try {
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendrier/" + calendrierId + "/token/"+ token) // Requête GET classique avec ID dans l’URL
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

    public void createCalendrier(UserCalendar userCalendar, String token, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON contenant les paramètres
            JSONObject json = new JSONObject();
            json.put("nom", userCalendar.getNomCalendrier());
            json.put("description", userCalendar.getDescription());
            json.put("token", token);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendrier")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de la création du calendrier: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    //PREND A PARAMETRE LE CALENDRIER PRÉ-MODIFIER
    public void updateCalendrier(UserCalendar userCalendar, String token, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON en extrayant les champs nécessaires du calendrier
            JSONObject json = new JSONObject();
            json.put("nom", userCalendar.getNomCalendrier());
            json.put("description", userCalendar.getDescription());
            json.put("token", token);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendrier/" + userCalendar.getCalendarId())
                    .put(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
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



    public void deleteCalendrier(UserCalendar calendrier, String token, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON contenant l'ID du calendrier
            JSONObject json = new JSONObject();
            json.put("token", token);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));

            // Construction de la requête DELETE en envoyant l'objet JSON dans le body
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendrier/" + calendrier.getCalendarId())
                    .delete(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
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
    public void createEvenement(Calendrier calendrier, Evenement evenement, String token, ApiCallback<Boolean> callback) {
        try {
            // Extraction de l'ID du calendrier et création d'un objet JSON avec les informations de l'événement
            JSONObject json = new JSONObject();
            json.put("calendrierId", calendrier.getId());
            json.put("titre", evenement.getTitre());
            json.put("description", evenement.getDescription());
            json.put("couleur", evenement.getCouleur());
            json.put("token", token);

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendrier/" + calendrier.getId() + "/evenement")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(true);
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
    public void updateEvenement(Evenement evenement, String token, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON en extrayant les données de l'objet Evenement
            JSONObject json = new JSONObject();
            json.put("token",token);
            json.put("calendrierId", evenement.getCalendrierId());
            json.put("titre", evenement.getTitre());
            json.put("description", evenement.getDescription());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/evenements/" + evenement.getId())
                    .put(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
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


    public void deleteEvenement(Evenement evenement, String token, ApiCallback<Boolean> callback) {
        try {
            // Créer un objet JSON contenant l'id de l'événement et l'id du calendrier
            JSONObject json = new JSONObject();
            json.put("token", token);
            json.put("calendrierId", evenement.getCalendrierId());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/evenements/" + evenement.getId())
                    .delete(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
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
    public void createElement(Calendrier calendrier, Element element, String token, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON avec les informations de la deadline
            JSONObject json = new JSONObject();
            json.put("token", token);
            json.put("nom", element.getNom());
            json.put("description", element.getDescription());
            json.put("evenement-id", element.getEvenementId());
            json.put("dateDebut", element.getDateDebut().toString());
            json.put("dateFin", element.getDateFin().toString());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/calendrier/" + calendrier.getId() + "/element")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
                    if(response.isSuccessful() && response.body() != null) {
                        callback.onSuccess(true);
                    } else {
                        callback.onFailure("Échec de la création de la deadline: " + response.code());
                    }
                }
            });
        } catch (Exception e) {
            callback.onFailure("Erreur lors de la création du JSON: " + e.getMessage());
        }
    }

    // Fonction pour mettre à jour un element existant
    public void updateElement(Element element, String token, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON en extrayant les données de l'objet Deadline
            JSONObject json = new JSONObject();
            json.put("token", token);
            json.put("calendrierId", element.getCalendrierId());
            json.put("nom", element.getNom());
            json.put("description", element.getDescription());
            json.put("evenement", element.getEvenement());
            json.put("dateDebut", element.getDateDebut().toString() );
            json.put("dateFin", element.getDateFin().toString() );

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/element/" + element.getId())
                    .put(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
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
    public void deleteElement(Element element, String token, ApiCallback<Boolean> callback) {
        try {
            // Création d'un objet JSON contenant l'id et le calendrierId de la deadline
            JSONObject json = new JSONObject();
            json.put("token", token);
            json.put("calendrierId", element.getCalendrierId());

            RequestBody body = RequestBody.create(json.toString(), MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(BASE_URL + "/element/" + element.getId())
                    .delete(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onFailure("Erreur réseau: " + e.getMessage());
                }
                @Override
                public void onResponse(Call call, Response response) {
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
