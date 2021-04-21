package com.katdmy.android.unocount.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class VerticalTextView(
        context: Context?,
        attributeSet: AttributeSet?
) : AppCompatTextView(context!!, attributeSet) {
    private val LOG_TAG = "VerticalTextView"
    var textPaint: TextPaint = paint
    override fun onDraw(canvas: Canvas) {
        textPaint.color = currentTextColor
        textPaint.drawableState = drawableState
        textPaint.textAlign = Paint.Align.CENTER
        val x = width / 2
        val y = height / 2

        //Log.e(LOG_TAG, "Text: " + getText().toString() + ", width: " + getWidth() + ", height: " + getHeight() + ", x: " + x + ", y: " + y);
        canvas.rotate(-90f, x.toFloat(), y.toFloat())
        canvas.drawText(text.toString(), x.toFloat(), y.toFloat(), textPaint)
    }
}