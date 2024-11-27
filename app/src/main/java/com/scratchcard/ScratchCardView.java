package com.scratchcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ScratchCardView extends View {

    private Paint overlayPaint;
    private Path scratchPath;
    private Bitmap overlayBitmap;
    private Canvas overlayCanvas;

    private String randomText ="";

    public ScratchCardView(Context context) {
        super(context);
        init();
    }

    public ScratchCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        overlayPaint = new Paint();
        overlayPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        overlayPaint.setStyle(Paint.Style.STROKE);
        overlayPaint.setStrokeJoin(Paint.Join.ROUND);
        overlayPaint.setStrokeCap(Paint.Cap.ROUND);
        overlayPaint.setStrokeWidth(80);

        scratchPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        overlayBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        overlayCanvas = new Canvas(overlayBitmap);

        // Draw the overlay (scratchable area)
        overlayCanvas.drawColor(Color.BLUE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                scratchPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                scratchPath.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        // Redraw the view to update the scratch effect
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Draw the actual content beneath the scratch layer
        drawContent(canvas);

        // Draw the scratch layer (scratched portion)
        canvas.drawBitmap(overlayBitmap, 0, 0, null);

        // Draw the scratch path
        overlayCanvas.drawPath(scratchPath, overlayPaint);
    }

    private void drawContent(Canvas canvas) {
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(100);
        canvas.drawText(randomText, getWidth() / 4, getHeight() / 2, textPaint);
    }

    // Set the random text to be displayed
    public void setRandomText(String text) {
        this.randomText = text;
        invalidate();  // Redraw the view with the new text
    }

}
