package com.example.multuscalendrius.vues.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Calendrier;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;
import com.example.multuscalendrius.vues.CreerElementActivity;
import com.example.multuscalendrius.vues.adaptateurs.PlanificateurAdaptateur;

import java.util.ArrayList;
import java.util.List;

public class PlanificateurFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private CalendrierVueModele calendrierVueModele;
    private ImageButton imgBtnFiltre;
    private ListView llPlanificateur;
    private View popupView;
    private PopupWindow popupWindow;
    private List<Element> elements;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Mettre toutes les intéractions de la vue içi
        View view = inflater.inflate(R.layout.fragment_planificateur, container, false);
        popupView = inflater.inflate(R.layout.popup_planif_filtre, container, false);

        imgBtnFiltre = view.findViewById(R.id.imgBtnFiltre);
        llPlanificateur = view.findViewById(R.id.llPlanificateur);

        llPlanificateur.setOnItemClickListener(this);
        imgBtnFiltre.setOnClickListener(this);

        calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        elements = calendrierVueModele.getCurrentCalendrier().getElements();

        calendrierVueModele.getCalendrier().observe(getViewLifecycleOwner(), calendrier -> {
            elements = calendrier.getElements();
            PlanificateurAdaptateur adaptateur = new PlanificateurAdaptateur(getContext(), R.layout.layout_planif, elements);
            llPlanificateur.setAdapter(adaptateur);
        });
        calendrierVueModele.getErreur().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
        creerPopUpFiltre();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Calendrier calendrier = calendrierVueModele.getCurrentCalendrier();
        if (calendrier != null)
            calendrierVueModele.chargerCalendrier(calendrier.getId());
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), CreerElementActivity.class);
        Element elementClique = (Element) parent.getAdapter().getItem(position);
        intent.putExtra("ID", elementClique.getId());
        startActivity(intent);
    }
}