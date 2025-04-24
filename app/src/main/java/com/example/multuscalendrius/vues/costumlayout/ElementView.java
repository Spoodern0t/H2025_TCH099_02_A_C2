package com.example.multuscalendrius.vues.costumlayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.modeles.entitees.Evenement;
import com.example.multuscalendrius.vues.CreerElementActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ElementView extends FrameLayout {

    private final Element element;
    private final LocalDate date;
    private LocalDateTime debut, fin;
    private final float blocHauteur;
    private RelativeLayout llElement;
    private TextView tvNom;
    private TextView tvHeures;
    private View elementRect;
    private LayoutParams params;

    public ElementView(Context context, LocalDate date, Element element, float blocHauteur, float largeurTotal, float largeurColTemps) {
        super(context);
        this.element = element;
        this.date = date;
        this.blocHauteur = blocHauteur;

        LayoutInflater.from(context).inflate(R.layout.layout_element, this, true);

        llElement = findViewById(R.id.llElement);
        tvNom = findViewById(R.id.tvNom);
        tvHeures = findViewById(R.id.tvHeures);
        elementRect = findViewById(R.id.elementRect);

        String couleur = element.getEvenement() != null ? element.getEvenement().getCouleur() : null;
        if (couleur != null) {
            elementRect.setBackgroundColor(Color.parseColor("#99" + couleur));
        } else {
            elementRect.setBackgroundResource(R.color.element_defaut);
        }
        setClickable(true);

        tvNom.setText(element.getNom());

        String timeRange = formatTimeRange();
        tvHeures.setText(timeRange);

        elementRect.setMinimumHeight(calculerHauteur());

        params = new LayoutParams(
                (int) (largeurTotal - largeurColTemps),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        params.leftMargin = (int) largeurColTemps;
        setLayoutParams(params);

        post(() -> {
            int height = getHeight();
            params.topMargin = calculerTop(height);
            setLayoutParams(params);
        });

        elementRect.setOnClickListener(v -> ecouteurClick());
        tvNom.setOnClickListener(v -> ecouteurClick());
    }

    private void ecouteurClick() {
        Intent intent = new Intent(getContext(), CreerElementActivity.class);
        intent.putExtra("ID", element.getId());
        getContext().startActivity(intent);
    }

    private String formatTimeRange() {

        debut = element.getDateDebut() != null
                ? element.getDateDebut()
                : element.getDateFin().minusMinutes(5);
        fin = element.getDateFin();

        LocalDateTime elementDebut = element.getDateDebut();
        LocalDateTime elementFin = element.getDateFin();

        String heureDebut, heureFin, dateDebut, dateFin;
        heureDebut = heureFin = dateDebut = dateFin = "";

        if (elementDebut != null) {
            heureDebut = String.format("%02d:%02d", elementDebut.getHour(), elementDebut.getMinute()) +" -> ";
            if (!elementDebut.toLocalDate().isEqual(elementFin.toLocalDate())){
                dateDebut += String.format("%02d/%02d", elementDebut.getMonthValue(), elementDebut.getDayOfMonth());
                dateFin += String.format("%02d/%02d", elementFin.getMonthValue(), elementFin.getDayOfMonth());
                if (elementDebut.getYear() != elementFin.getYear()){
                    dateDebut += "/" + String.format("%02d", elementDebut.getYear());
                    dateFin += "/" + String.format("%02d", elementFin.getYear());
                }
                dateDebut += " ";
                dateFin += " ";
            }
        }
        heureFin += String.format("%02d:%02d", elementFin.getHour(), elementFin.getMinute());

        return dateDebut + heureDebut + dateFin + heureFin;
    }

    private int calculerTop(float hauteur) {
        LocalDate debutDate = debut.toLocalDate();
        LocalDate finDate = fin.toLocalDate();
        if (date.isEqual(debutDate)) {
            float vueDebut = (debut.getHour() + debut.getMinute() / 60f) * blocHauteur;
            float vueFin = (blocHauteur * CalendrierView.NB_HEURE);
            if (date.isEqual(finDate))
                vueFin = (fin.getHour() + fin.getMinute() / 60f) * blocHauteur;
            float marge = hauteur - (vueFin - vueDebut);
            return (int) (vueDebut - marge);
        } else {
            return 0;
        }
    }

    private int calculerHauteur() {

        LocalDate debutDate = debut.toLocalDate();
        LocalDate finDate = fin.toLocalDate();
        float vueDebut = (debut.getHour() + debut.getMinute() / 60f) * blocHauteur;
        float vueFin = (fin.getHour() + fin.getMinute() / 60f) * blocHauteur;
        if (date.isBefore(finDate) && date.isAfter(debutDate)) {
            return (int) (blocHauteur * CalendrierView.NB_HEURE);
        } else if (date.isBefore(finDate)) {
            return (int) (blocHauteur * CalendrierView.NB_HEURE - vueDebut);
        } else if (date.isAfter(debutDate)) {
            return (int) vueFin;
        } else {
            return (int) (vueFin - vueDebut);
        }
    }
}
