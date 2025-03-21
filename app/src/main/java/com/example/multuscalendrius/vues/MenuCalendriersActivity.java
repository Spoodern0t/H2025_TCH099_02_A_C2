package com.example.multuscalendrius.vues;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;

public class MenuCalendriersActivity extends AppCompatActivity {

    private boolean estPartage; // true si l'utilisateur a été invité dans au moins 1 calendrier partagé
    private TextView tvCalendriers;
    private ListView lvCalendriers, lvCalendriersPartages;
    private LinearLayout llCalendriersPartages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_calendriers);

        tvCalendriers = (TextView) findViewById(R.id.tvCalendriers);
        lvCalendriers = (ListView) findViewById(R.id.lvCalendriers);
        lvCalendriersPartages = (ListView) findViewById(R.id.lvCalendriersPartages);
        llCalendriersPartages = (LinearLayout) findViewById(R.id.llCalendriersPartages);

        if (estPartage) {

            tvCalendriers.setText(R.string.liste_des_calendriers_personnels);
            llCalendriersPartages.setVisibility(LinearLayout.VISIBLE);
        }
    }
}
