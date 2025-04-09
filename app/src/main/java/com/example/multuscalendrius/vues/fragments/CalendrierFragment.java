package com.example.multuscalendrius.vues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.vues.costumlayout.CalendrierView;
import com.example.multuscalendrius.modeles.dao.DaoJour;
import com.example.multuscalendrius.vues.adaptateurs.JourAdaptateur;

import java.util.List;

public class CalendrierFragment extends Fragment {

    private RecyclerView rvJours;
    private CalendrierView calendrierView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Mettre toutes les intéractions de la vue içi
        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);

        rvJours = view.findViewById(R.id.rvJours);
        calendrierView = view.findViewById(R.id.calendrierJour);

        List<String> jours = DaoJour.getInstance().getJours();

        JourAdaptateur adaptateur = new JourAdaptateur(jours);
        rvJours.setAdapter(adaptateur);

        return view;
    }
}