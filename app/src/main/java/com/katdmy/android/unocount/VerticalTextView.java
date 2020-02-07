package com.katdmy.android.unocount;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

public class VerticalTextView extends AppCompatTextView {

    private String LOG_TAG = "VerticalTextView";
    TextPaint textPaint = getPaint();

    public VerticalTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
    @Override
    protected void onDraw(Canvas canvas) {

        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        textPaint.setTextAlign(Paint.Align.CENTER);

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        //Log.e(LOG_TAG, "Text: " + getText().toString() + ", width: " + getWidth() + ", height: " + getHeight() + ", x: " + x + ", y: " + y);

        canvas.rotate(-90, x, y);
        canvas.drawText(getText().toString(), x, y, textPaint);
    }
}
