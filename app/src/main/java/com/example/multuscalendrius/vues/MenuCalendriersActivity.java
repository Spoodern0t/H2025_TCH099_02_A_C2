package com.example.multuscalendrius.vues;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;

public class MenuCalendriersActivity extends AppCompatActivity {

    private boolean estPartage; // true si l'utilisateur a été invité dans au moins 1 calendrier partagé
    private TextView tvCalendriers;
    private ListView lvCalendriers, lvCalendriersPartages;
    private View breakLine;
    private LinearLayout llCalendriersPartages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_calendriers);

        tvCalendriers = (TextView) findViewById(R.id.tvCalendriers);
        lvCalendriers = (ListView) findViewById(R.id.lvCalendriers);
        lvCalendriersPartages = (ListView) findViewById(R.id.lvCalendriersPartages);
        breakLine = (View) findViewById(R.id.breakLine);
        llCalendriersPartages = (LinearLayout) findViewById(R.id.llCalendriersPartages);

        if (estPartage) {

            breakLine.setVisibility(View.VISIBLE);
            tvCalendriers.setText(R.string.liste_des_calendriers_personnels);
            llCalendriersPartages.setVisibility(LinearLayout.VISIBLE);
        }
    }
}
