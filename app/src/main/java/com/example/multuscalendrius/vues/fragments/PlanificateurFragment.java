package com.example.multuscalendrius.vues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;
import com.example.multuscalendrius.vues.adaptateurs.CalendrierAdaptateur;
import com.example.multuscalendrius.vues.adaptateurs.PlanificateurAdaptateur;

import java.time.LocalDateTime;
import java.util.List;

public class PlanificateurFragment extends Fragment implements View.OnClickListener {

    private CalendrierVueModele calendrierVueModele;
    private ImageButton imgBtnFiltre;
    private ListView voyagesListView;
    private View popupView;
    private PopupWindow popupWindow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Mettre toutes les intéractions de la vue içi
        View view = inflater.inflate(R.layout.fragment_planificateur, container, false);
        popupView = inflater.inflate(R.layout.popup_planif_filtre, container, false);

        imgBtnFiltre = view.findViewById(R.id.imgBtnFiltre);
        voyagesListView = view.findViewById(R.id.voyagesListView);

        imgBtnFiltre.setOnClickListener(this);

        calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        List<Element> elements = calendrierVueModele.getCurrentCalendrier().getElements();
        Element element = new Element();
        element.setNom("yess");
        element.setDateDebut(LocalDateTime.now());
        element.setDateFin(LocalDateTime.now().plusHours(1));
        elements.add(element);

        PlanificateurAdaptateur adaptateur = new PlanificateurAdaptateur(inflater.getContext(), R.layout.layout_planif, elements);
        voyagesListView.setAdapter(adaptateur);

        creerPopUpFiltre();

        return view;
    }

    private void creerPopUpFiltre() {

        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );

        CheckBox cbPeriodes = popupView.findViewById(R.id.cbPeriodes);
        CheckBox cbDeadlines = popupView.findViewById(R.id.cbDeadlines);
        Button btnCharger = popupView.findViewById(R.id.btnCharger);

        btnCharger.setOnClickListener(v -> {

            popupWindow.dismiss();
        });
    }

    @Override
    public void onClick(View v) {
        if (v == imgBtnFiltre) {
            popupWindow.showAsDropDown(imgBtnFiltre, 0, 0);
        }
    }
}