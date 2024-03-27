package com.example.appdefault.Custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class MacronutrientProgressView extends View {
    private int maxProgress = 100; // Giá trị tiến trình tối đa
    private int currentProgress = 50; // Giá trị tiến trình hiện tại
    private int progressColor = Color.BLUE;

    public MacronutrientProgressView(Context context) {
        super(context);
    }

    public MacronutrientProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MacronutrientProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        Paint progressPaint = new Paint();
        progressPaint.setColor(progressColor);
        progressPaint.setStyle(Paint.Style.FILL);

        // Tính chiều rộng của thanh tiến trình dựa trên giá trị tiến trình hiện tại
        float progressWidth = (float) currentProgress / maxProgress * viewWidth;

        // Vẽ thanh tiến trình
        canvas.drawRect(0, 0, progressWidth, viewHeight, progressPaint);
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    public void setCurrentProgress(int currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();
    }
}
