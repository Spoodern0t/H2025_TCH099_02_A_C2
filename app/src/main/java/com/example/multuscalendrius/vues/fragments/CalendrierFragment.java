package com.example.multuscalendrius.vues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.multuscalendrius.R;

import java.util.ArrayList;
import java.util.List;

import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class CalendrierFragment extends Fragment implements WeekView.WeekViewLoader {

    private WeekView mWeekView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Mettre toutes les intéractions de la vue içi
        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);

        /*
        mWeekView = view.findViewById(R.id.weekView);

        // TODO: Observer les requetes du calendrier
        // Set le chargement des events.
        mWeekView.setWeekViewLoader(this);
        */

        view.findViewById(R.id.calendrierJour);

        return view;
    }

    @Override
    public List<? extends WeekViewEvent> onWeekViewLoad() {
        List<WeekViewEvent> elements = new ArrayList<>();

        // TODO: Get les éléments et les ajouter à la liste

        return elements;
    }
}