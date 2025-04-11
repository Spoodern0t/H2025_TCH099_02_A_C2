package com.example.multuscalendrius.vuemodele;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.modeles.entitees.Evenement;

import java.time.LocalDateTime;

public class CalendarVueModele {

    private final MutableLiveData<Calendrier> calendrierLiveData = new MutableLiveData<>(new Calendrier());

    public LiveData<Calendrier> getCalendrier() {
        return calendrierLiveData;
    }

    public void setCalendrier(Calendrier calendrier) {
        calendrierLiveData.setValue(calendrier);
    }

    private void notifyCalendrierObservers() {
        calendrierLiveData.setValue(calendrierLiveData.getValue());
    }

    public void fetchById(int id) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.fetchById(id, this::notifyCalendrierObservers);
        }
    }

    public void createCalendrier(String nom, String description) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.syncCreate(nom, description, this::notifyCalendrierObservers);
        }
    }

    public void updateCalendrier(String nom, String description, String auteur) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.syncUpdate(nom, description, auteur, this::notifyCalendrierObservers);
        }
    }

    public void deleteCalendrier() {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.syncDelete(this::notifyCalendrierObservers);
        }
    }

    public void ajouterEvenement(String titre, String description) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.addEvenement(titre, description, this::notifyCalendrierObservers);
        }
    }

    public void modifierEvenement(Evenement evenement, String titre, String description) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.updateEvenement(evenement, titre, description, this::notifyCalendrierObservers);
        }
    }

    public void supprimerEvenement(Evenement evenement) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.deleteEvenement(evenement, this::notifyCalendrierObservers);
        }
    }

    public void ajouterElement(String nom, String description, Evenement evenement, LocalDateTime dateDebut, LocalDateTime dateFin) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.addElement(nom, description, evenement, dateDebut, dateFin, this::notifyCalendrierObservers);
        }
    }

    public void modifierElement(Element element, String nom, String description, int evenementId) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.updateElement(element, nom, description, evenementId, this::notifyCalendrierObservers);
        }
    }

    public void supprimerElement(Element element) {
        Calendrier cal = calendrierLiveData.getValue();
        if (cal != null) {
            cal.deleteElement(element, this::notifyCalendrierObservers);
        }
    }
}

/*
private CalendrierViewModel viewModel;
private TextView statusTextView; // Assure-toi de lier ça à ta vue dans le layout

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    viewModel = new ViewModelProvider(requireActivity()).get(CalendrierViewModel.class);

    statusTextView = view.findViewById(R.id.statusTextView); // ou ton ID réel

    // Observer les changements du calendrier
    viewModel.getCalendrier().observe(getViewLifecycleOwner(), calendrier -> {
        if (calendrier == null) {
            statusTextView.setText("Aucun calendrier disponible");
            return;
        }

        switch (calendrier.getOperation()) {
            case CREATION:
                statusTextView.setText("✅ Calendrier créé : " + calendrier.getNom());
                ajouterElementExemple(); // Ajoute un élément après création
                break;
            case MISE_A_JOUR:
                statusTextView.setText("✅ Calendrier mis à jour : " + calendrier.getNom());
                break;
            case SUPPRESSION:
                statusTextView.setText("❌ Calendrier supprimé");
                break;
            case AJOUT_EVENEMENT:
                statusTextView.setText("🗓️ Événement ajouté");
                break;
            case MISE_A_JOUR_EVENEMENT:
                statusTextView.setText("✏️ Événement mis à jour");
                break;
            case SUPPRESSION_EVENEMENT:
                statusTextView.setText("🗑️ Événement supprimé");
                break;
            case AJOUT_ELEMENT:
                statusTextView.setText("📌 Élément ajouté");
                break;
            case MISE_A_JOUR_ELEMENT:
                statusTextView.setText("✏️ Élément mis à jour");
                break;
            case SUPPRESSION_ELEMENT:
                statusTextView.setText("🗑️ Élément supprimé");
                break;
            case ERREUR:
                statusTextView.setText("❌ Erreur : " + calendrier.getErrorMessage());
                break;
            case AUTRE:
            default:
                statusTextView.setText("ℹ️ Opération non spécifiée");
                break;
        }
    });

    // Démarrer la création du calendrier
    Calendrier calendrierActuel = viewModel.getCalendrier().getValue();
    if (calendrierActuel != null && calendrierActuel.getId() == 0) {
        viewModel.createCalendrier("Mon projet", "Calendrier de travail");
    }
}

private void ajouterElementExemple() {
    Calendrier calendrier = viewModel.getCalendrier().getValue();
    if (calendrier != null) {
        List<Evenement> evenements = calendrier.getEvenements();
        if (!evenements.isEmpty()) {
            Evenement evenement = evenements.get(0);
            viewModel.ajouterElement(
                "Créer le backend",
                "Tâche initiale",
                evenement,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(2)
            );
        } else {
            statusTextView.setText("⚠️ Aucun événement pour associer un élément.");
        }
    }

}

 */
