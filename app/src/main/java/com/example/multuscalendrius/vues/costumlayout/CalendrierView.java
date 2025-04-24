package com.example.multuscalendrius.vues.costumlayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.multuscalendrius.R;
import com.example.multuscalendrius.modeles.entitees.Element;
import com.example.multuscalendrius.vues.CreerElementActivity;

import java.time.LocalDate;
import java.util.List;

public class CalendrierView extends ViewGroup {

    private Paint paint;
    public static final int NB_HEURE = 24;
    private float largeur, hauteur, blocHauteur, largeurColTemps;

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
        paint.setColor(getResources().getColor(R.color.couleur_icon, null));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(36);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec) * 2;

        setMeasuredDimension(width, height);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams) child.getLayoutParams();

            int left = params.leftMargin;
            int top = params.topMargin;
            int right = left + child.getMeasuredWidth();
            int bottom = top + child.getMeasuredHeight();

            child.layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(largeurColTemps, 0, largeurColTemps, hauteur, paint);

        for (int i = 0; i < NB_HEURE; i++) {
            float y = i * blocHauteur;
            canvas.drawLine(0, y, largeur, y, paint);
            String heure = String.format("%02d:00", i);
            float textY = y + paint.getTextSize() + 10;
            canvas.drawText(heure, largeurColTemps / 2f, textY, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        largeur = w;
        hauteur = h;
        blocHauteur = (float) h / NB_HEURE;
        largeurColTemps = (float) w / 5;
    }

    public void setElements(LocalDate date, List<Element> elements) {
        removeAllViews();

        for (Element element : elements) {
            ElementView elementView = new ElementView(getContext(), date, element, blocHauteur, largeur, largeurColTemps);
            addView(elementView);
        }
        requestLayout();
        invalidate();
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }
}
