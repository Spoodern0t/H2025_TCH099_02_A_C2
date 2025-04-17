package com.example.multuscalendrius.vues.costumlayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.vues.CreerElementActivity;

import java.util.List;

public class CalendrierView extends FrameLayout {

    private Paint paint;
    private final int NB_HEURE = 24;
    private int largeur, hauteur, blocHauteur;
    private int largeurColTemps;

    public CalendrierView(Context context) {
        super(context);
        init();
    }

    public CalendrierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
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
        blocHauteur = h / NB_HEURE;
        largeurColTemps = w / 5;
    }

    @Override
    protected void onDraw(@NonNull android.graphics.Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(getResources().getColor(R.color.couleur_icon, null));
        paint.setTextSize(36);
        paint.setTextAlign(Paint.Align.CENTER);

        // Ligne verticale de séparation
        canvas.drawLine(largeurColTemps, 0, largeurColTemps, hauteur, paint);

        // Heures
        for (int i = 0; i < NB_HEURE; i++) {
            float y = i * blocHauteur;
            canvas.drawLine(0, y, largeur, y, paint);
            String heure = String.format("%02d:00", i);
            float textY = y + paint.getTextSize() + 10;
            canvas.drawText(heure, largeurColTemps / 2f, textY, paint);
        }
    }

    public void setElements(List<Element> elements) {
        removeAllViews();

        for (Element element : elements) {
            ElementView ev = new ElementView(getContext(), element, blocHauteur, largeur, largeurColTemps);
            ev.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), CreerElementActivity.class);
                intent.putExtra("ID", element.getId());
                getContext().startActivity(intent);
            });
            addView(ev);
        }
    }
}
