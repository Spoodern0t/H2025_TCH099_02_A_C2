package com.example.multuscalendrius.vues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;
import com.example.multuscalendrius.vues.adaptateurs.JourAdaptateur;
import com.example.multuscalendrius.vues.costumlayout.CalendrierView;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public class CalendrierFragment extends Fragment implements JourAdaptateur.OnItemClickListener {

    private CalendrierVueModele calendrierVueModele;
    private Button btnAnneeMois;
    private RecyclerView rvJours;
    private CalendrierView calendrierView;
    private View popupView;
    private NumberPicker pickerMonth, pickerYear;
    private int year, month;
    private PopupWindow popupWindow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Mettre toutes les intéractions de la vue içi
        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);
        popupView = inflater.inflate(R.layout.popup_year_month, container, false);

        btnAnneeMois = view.findViewById(R.id.btnAnneeMois);
        rvJours = view.findViewById(R.id.rvJours);
        calendrierView = view.findViewById(R.id.calendrierJour);

        calendrierVueModele = new ViewModelProvider(this).get(CalendrierVueModele.class);
        List<Element> elements = calendrierVueModele.getCurrentCalendrier().getElements();
        Element element = new Element();
        element.setNom("yess");
        element.setDateDebut(LocalDateTime.now());
        element.setDateFin(LocalDateTime.now().plusHours(1));
        elements.add(element);

        calendrierView.setElements(elements);

        btnAnneeMois.setOnClickListener(v -> {
            int anchorWidth = btnAnneeMois.getWidth();
            popupWindow.getContentView().measure(
                    View.MeasureSpec.UNSPECIFIED,
                    View.MeasureSpec.UNSPECIFIED
            );
            int popupWidth = popupWindow.getContentView().getMeasuredWidth();
            int offsetX = (anchorWidth - popupWidth) / 2;
            popupWindow.showAsDropDown(btnAnneeMois, offsetX, 0);
        });
        creerPopUp();
        dateListe();

        return view;
    }

    @Override
    public void onItemClick(TextView tvDate) {
        tvDate.setTextColor(getResources().getColor(R.color.couleur_primaire, null));
        tvDate.setBackgroundResource(R.drawable.round_background);

        LocalDate date = LocalDate.of(year, month, Integer.parseInt((String) tvDate.getText()));

        List<Element> elements = calendrierVueModele.getCurrentCalendrier().getElementsByDate(date);
        calendrierView.setElements(elements);
    }


    private void creerPopUp() {

        popupWindow = new PopupWindow(
                popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        pickerMonth = popupView.findViewById(R.id.pickerMonth);
        pickerYear = popupView.findViewById(R.id.pickerYear);
        Button btnCharger = popupView.findViewById(R.id.btnCharger);

        pickerMonth.setMinValue(1);
        pickerMonth.setMaxValue(12);
        pickerYear.setMinValue(2000);
        pickerYear.setMaxValue(2100);

        pickerMonth.setValue(LocalDate.now().getMonthValue());
        pickerYear.setValue(LocalDate.now().getYear());

        btnCharger.setOnClickListener(v -> {
            dateListe();
            popupWindow.dismiss();
        });
        popupWindow.setOnDismissListener(this::dateListe);
    }

    // Appliquer les filtres depuis le popup
    private void dateListe() {
        if (popupWindow != null) {
            DecimalFormat formatter = new DecimalFormat("00");
            month = pickerMonth.getValue();
            year = pickerYear.getValue();
            btnAnneeMois.setText(year + "/" + formatter.format(month));
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();
            JourAdaptateur jourAdaptateur = new JourAdaptateur(daysInMonth, this);
            rvJours.setAdapter(jourAdaptateur);
        }
    }
}