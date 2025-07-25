package com.example.uts.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class CircleChartView extends View {
    private int progress = 0;
    private Paint donePaint, remainingPaint;

    public CircleChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        donePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        donePaint.setColor(0xFF4CAF50);
        donePaint.setStyle(Paint.Style.STROKE);
        donePaint.setStrokeWidth(40f);

        remainingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        remainingPaint.setColor(0xFFE0E0E0);
        remainingPaint.setStyle(Paint.Style.STROKE);
        remainingPaint.setStrokeWidth(40f);
    }

    public void setProgress(int value) {
        this.progress = value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth(), height = getHeight();
        int radius = Math.min(width, height) / 2 - 50;

        float cx = width / 2f;
        float cy = height / 2f;

        float sweepAngle = 360f * progress / 100f;

        canvas.drawArc(cx - radius, cy - radius, cx + radius, cy + radius, 0, 360, false, remainingPaint);
        canvas.drawArc(cx - radius, cy - radius, cx + radius, cy + radius, -90, sweepAngle, false, donePaint);
    }
}
