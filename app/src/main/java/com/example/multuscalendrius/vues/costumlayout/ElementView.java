package com.example.multuscalendrius.vues.costumlayout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Element;

import java.time.LocalDateTime;

public class ElementView extends FrameLayout {

    private TextView tvNom;
    private TextView tvHeures;

    public ElementView(Context context, Element element, int blocHauteur, int largeurTotal, int largeurColTemps) {
        super(context);

        // Inflate the custom layout
        LayoutInflater.from(context).inflate(R.layout.layout_element, this, true);

        // Initialize TextViews
        tvNom = findViewById(R.id.tvNom);
        tvHeures = findViewById(R.id.tvHeures);

        // Set up background and click behavior
        setBackgroundColor(Color.parseColor("#2196F3")); // Default color
        setClickable(true);

        // Set element name
        tvNom.setText(element.getNom());

        // Calculate and set the time duration
        String timeRange = formatTimeRange(element);
        tvHeures.setText(timeRange);

        // Set LayoutParams based on calculations
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                calculerHauteur(blocHauteur, element)
        );
        params.leftMargin = largeurColTemps;
        params.topMargin = calculerTop(blocHauteur, element);
        setLayoutParams(params);
    }

    private String formatTimeRange(Element element) {
        LocalDateTime debut = element.getDateDebut();
        LocalDateTime fin = element.getDateFin();

        if (debut == null || fin == null) {
            return "Invalid times";
        }

        String debutTime = String.format("%02d:%02d", debut.getHour(), debut.getMinute());
        String finTime = String.format("%02d:%02d", fin.getHour(), fin.getMinute());

        return debutTime + " - " + finTime;
    }

    private int calculerTop(int blocHauteur, Element element) {
        LocalDateTime debut = element.getDateDebut() != null ? element.getDateDebut() : element.getDateFin();
        return (int) ((debut.getHour() + debut.getMinute() / 60f) * blocHauteur);
    }

    private int calculerHauteur(int blocHauteur, Element element) {
        LocalDateTime debut = element.getDateDebut();
        LocalDateTime fin = element.getDateFin();

        if (debut == null || fin == null) return blocHauteur; // height by default

        float dureeHeures = (float) (fin.getHour() + fin.getMinute() / 60f)
                - (debut.getHour() + debut.getMinute() / 60f);

        return (int) (dureeHeures * blocHauteur);
    }
}
