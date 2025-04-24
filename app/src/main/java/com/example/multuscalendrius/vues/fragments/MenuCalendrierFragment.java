package com.example.multuscalendrius.vues.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.UserCalendar;
import com.example.multuscalendrius.vuemodele.UserVueModele;
import com.example.multuscalendrius.vues.CreerCalendrierActivity;
import com.example.multuscalendrius.vues.adaptateurs.CalendrierAdaptateur;

import java.util.ArrayList;
import java.util.List;

public class MenuCalendrierFragment extends Fragment implements View.OnClickListener {

    private View view;
    private UserVueModele userVueModele;
    private ImageButton imgBtnAddCalendrier;
    private TextView tvCalendriersPersonnels, tvCalendriersPartages;
    private ListView lvCalendriersPersonnels, lvCalendriersPartages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu_calendrier, container, false);

        lvCalendriersPersonnels = view.findViewById(R.id.lvCalendriersPersonnels);
        imgBtnAddCalendrier = view.findViewById(R.id.imgBtnAddCalendrier);

        imgBtnAddCalendrier.setOnClickListener(this);

        userVueModele = new ViewModelProvider(this).get(UserVueModele.class);
        userVueModele.getUserCalendars().observe(getViewLifecycleOwner(), this::initCalendriersPersonnels);
        userVueModele.getErreur().observe(getViewLifecycleOwner(), message ->
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
        userVueModele.chargerUserCalendars();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        userVueModele.chargerUserCalendars();
    }

    public void initCalendriersPersonnels(List<UserCalendar> calendriers) {

        String username = userVueModele.getCurrentUser().getUsername();
        List<UserCalendar> calendriersPersonnels = new ArrayList<>();
        List<UserCalendar> calendriersPartages = new ArrayList<>();
        for (UserCalendar calendrier: calendriers) {
            if (calendrier.getAuteur().equals(username)) {
                calendriersPersonnels.add(calendrier);
            } else {
                calendriersPartages.add(calendrier);
            }
        }

        CalendrierAdaptateur calendrierPersonnelAdaptateur = new CalendrierAdaptateur(getContext(), R.layout.layout_calendrier, calendriersPersonnels);
        lvCalendriersPersonnels.setAdapter(calendrierPersonnelAdaptateur);

        if (!calendriersPartages.isEmpty()) {
            tvCalendriersPersonnels = view.findViewById(R.id.tvCalendriersPersonnels);
            tvCalendriersPartages = view.findViewById(R.id.tvCalendriersPartages);
            lvCalendriersPartages = view.findViewById(R.id.lvCalendriersPartages);
            View breakLine = view.findViewById(R.id.breakLine);

            breakLine.setVisibility(View.VISIBLE);
            lvCalendriersPartages.setVisibility(View.VISIBLE);
            tvCalendriersPartages.setVisibility(View.VISIBLE);
            tvCalendriersPersonnels.setText(R.string.liste_des_calendriers_personnels);

            CalendrierAdaptateur calendrierPartageAdaptateur = new CalendrierAdaptateur(getContext(), R.layout.layout_calendrier, calendriers);
            lvCalendriersPartages.setAdapter(calendrierPartageAdaptateur);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == imgBtnAddCalendrier) {
            Intent intent = new Intent(getContext(), CreerCalendrierActivity.class);
            startActivity(intent);
        }
    }
}