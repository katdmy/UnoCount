package com.katdmy.android.unocount;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class VerticalTextView extends AppCompatTextView {

    private String LOG_TAG = "VerticalTextView";
    TextPaint textPaint = getPaint();
    private String mText;

    public VerticalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int[] set = {
                android.R.attr.text        // idx 0
        };

        TypedArray a = context.obtainStyledAttributes(attrs, set);
        CharSequence t = a.getText(0);
        if (t != null)
            mText = t.toString();
        a.recycle();
    }

    public void setVerticalText(String text) {
        mText = text;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        textPaint.setColor(getCurrentTextColor());
        textPaint.drawableState = getDrawableState();
        textPaint.setTextAlign(Paint.Align.CENTER);

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        //Log.e(LOG_TAG, "Text: " + mText + ", width: " + getWidth() + ", height: " + getHeight() + ", x: " + x + ", y: " + y);

        canvas.rotate(-90, x, y);
        canvas.drawText(mText, x, y, textPaint);

    }
}
