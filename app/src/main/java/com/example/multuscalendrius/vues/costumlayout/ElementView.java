package com.example.multuscalendrius.vues.costumlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.NonNull;

public class ElementView extends View {

    private final int NB_HEURE = 24;
    private Paint paint;
    private RectF blocElement;
    private int largeur, hauteur, blocHauteur;
    private int largeurColTemps, largeurColElement;

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        setWillNotDraw(false);
    }

    public ElementView(Context context) {
        super(context);
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

        //dessinerElement(canvas);
    }

}
