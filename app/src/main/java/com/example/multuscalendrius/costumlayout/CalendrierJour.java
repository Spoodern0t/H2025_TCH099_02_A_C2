package com.example.multuscalendrius.costumlayout;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Element;

import java.util.Calendar;
import java.util.Date;

public class CalendrierJour extends View {

    private final int NB_HEURE = 24;
    private Paint paint;
    private RectF blocElement;
    private int largeur, hauteur, blocHauteur;
    private int largeurColTemps, largeurColElement;
    private Element[] elements;


    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        setWillNotDraw(false);
    }

    public CalendrierJour(Context context) {
        super(context);
        init();
    }

    public CalendrierJour(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec) * 2;
        int width = MeasureSpec.getSize(widthMeasureSpec);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        largeur = w;
        hauteur = h;
        blocHauteur = h/NB_HEURE;
        largeurColTemps = w/5;
        largeurColElement = w - largeurColTemps;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        dessinerBloc(canvas);

        //dessinerElement(canvas);
    }

    private void dessinerBloc(Canvas canvas) {

        float x1 = 0;
        float x2 = largeurColTemps;

        paint = new Paint();
        paint.setTextSize(36);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(getResources().getColor(R.color.couleur_icon));

        // Ligne verticale
        canvas.drawLine(x2, 0, x2, hauteur, paint);

        for (int i = 0; i < NB_HEURE; i++) {

            float y1 = i * blocHauteur;

            float pointX = (x1 + x2)/2;
            float pointY = ((i + 0.1f) * blocHauteur) + paint.getTextSize();

            // Format d'heure
            String heure = String.format("%02d:00", i);

            canvas.drawText(heure, pointX, pointY, paint);
            // Ligne horizontale
            canvas.drawLine(0, y1, largeur, y1, paint);
        }
    }

    public void dessinerElement(Canvas canvas) {

        for (Element element : elements) {

            Paint paint = new Paint();
            paint.setColor(Color.BLUE);

            /*
            LocalDateTime debut = element.getDateDebut() != null
                    ? element.getDateDebut()
                    : element.getDateFin();

            float elementDebut = tempsDeDate(debut);
            float elementFin = tempsDeDate(element.getDateFin());

            float y1 = elementDebut * blocHauteur;
            float y2 = elementFin * blocHauteur;



            blocElement = new RectF(largeurColTemps, y1, largeur, y2);
            canvas.drawRect(blocElement, paint);
             */
        }
    }

    private float tempsDeDate(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY) + (calendar.get(Calendar.MINUTE)/60f);
    }
}
