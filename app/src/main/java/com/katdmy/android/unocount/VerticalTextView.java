package com.katdmy.android.unocount;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class VerticalTextView extends AppCompatTextView {

    TextPaint textPaint = getPaint();
    private String text;

    public VerticalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int[] set = {
                android.R.attr.text        // idx 0
        };

        TypedArray a = context.obtainStyledAttributes(attrs, set);
        CharSequence t = a.getText(0);
        text = t.toString();
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        textPaint.setTextAlign(Paint.Align.CENTER);

        int x = getWidth() / 2;
        int y = getHeight() / 2;
        canvas.rotate(-90, x, y);
        canvas.drawText(text, x, y, textPaint);

    }
}
