package com.example.multuscalendrius.vues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.multuscalendrius.R;

public class PlanificateurFragment extends Fragment implements View.OnClickListener {

    private ImageButton imgBtnFiltre;
    private LayoutInflater inflater;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Mettre toutes les intéractions de la vue içi
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_planificateur, container, false);

        imgBtnFiltre = view.findViewById(R.id.imgBtnFiltre);

        imgBtnFiltre.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == imgBtnFiltre) {
            // TODO: Créer un popUpWindows qui permet de filtrer les éléments
        }
    }
}