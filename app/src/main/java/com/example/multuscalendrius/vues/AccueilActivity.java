package com.example.multuscalendrius.vues;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.multuscalendrius.R;

import java.util.ArrayList;
import java.util.List;

import me.jlurena.revolvingweekview.WeekView;
import me.jlurena.revolvingweekview.WeekViewEvent;

public class AccueilActivity extends AppCompatActivity implements WeekView.WeekViewLoader {

    private WeekView mWeekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.revolving_weekview);

        // Set an WeekViewLoader to draw the events on load.
        mWeekView.setWeekViewLoader(this);
    }

    @Override
    public List<? extends WeekViewEvent> onWeekViewLoad() {
        List<WeekViewEvent> elements = new ArrayList<>();

        // TODO: Get les éléments et les ajouter à la liste

        return elements;
    }
}
