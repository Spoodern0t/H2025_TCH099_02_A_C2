package com.example.multuscalendrius.vues.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.vuemodele.CalendrierVueModele;
import com.example.multuscalendrius.vues.adaptateurs.JourAdaptateur;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;

public class CalendrierFragment extends Fragment implements JourAdaptateur.OnItemClickListener {

    private CalendrierVueModele calendrierVueModele;
    private Button btnAnneeMois;
    private RecyclerView rvJours;

    private View popupView;
    private NumberPicker pickerMonth, pickerYear;
    private PopupWindow popupWindow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Mettre toutes les intéractions de la vue içi
        View view = inflater.inflate(R.layout.fragment_calendrier, container, false);
        popupView = inflater.inflate(R.layout.popup_year_month, container, false);

        btnAnneeMois = view.findViewById(R.id.btnAnneeMois);
        rvJours = view.findViewById(R.id.rvJours);

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
    public void onItemClick(TextView date) {
        date.setTextColor(getResources().getColor(R.color.couleur_primaire, null));
        date.setBackgroundResource(R.drawable.round_background);
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
            int month = pickerMonth.getValue();
            int year = pickerYear.getValue();
            btnAnneeMois.setText(year + "/" + formatter.format(month));
            YearMonth yearMonth = YearMonth.of(year, month);
            int daysInMonth = yearMonth.lengthOfMonth();
            JourAdaptateur jourAdaptateur = new JourAdaptateur(daysInMonth, this);
            rvJours.setAdapter(jourAdaptateur);
        }
    }
}