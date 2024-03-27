package com.example.appdefault.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class CalorieProgressView extends View {
    private int consumedCalories = 100; // Số calo đã nạp
    private int goalCalories = 2000; // Số calo cần nạp
    private int circleColor = Color.argb(128, 0, 0, 255);
    private int progressColor = Color.argb(128, 0, 255, 0);

    public CalorieProgressView(Context context) {
        super(context);
    }

    public CalorieProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalorieProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        int centerX = viewWidth / 2;
        int centerY = viewHeight / 2;

        int radius = Math.min(viewWidth, viewHeight) / 2 - 20;

        Paint circlePaint = new Paint();
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(20);
        canvas.drawCircle(centerX, centerY, radius, circlePaint);

        RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        Paint progressPaint = new Paint();
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(20);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);

        float sweepAngle = (float) consumedCalories / goalCalories * 360;
        canvas.drawArc(rectF, -90, sweepAngle, false, progressPaint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(consumedCalories + " / " + goalCalories + " kcal", centerX, centerY, textPaint);
    }

    public void setConsumedCalories(int consumedCalories) {
        this.consumedCalories = consumedCalories;
        invalidate();
    }

    public void setGoalCalories(int goalCalories) {
        this.goalCalories = goalCalories;
        invalidate();
    }
}
