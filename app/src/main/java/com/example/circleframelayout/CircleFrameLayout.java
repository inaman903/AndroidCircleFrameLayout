package com.example.circleframelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CircleFrameLayout extends FrameLayout {

    private int _width;
    private int _height;

    private final Paint _borderPaint = new Paint();

    private float _borderWidth = 0.0f;
    private int _borderColor = Color.TRANSPARENT;

    public float getBorderWidth() {
        return _borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        if (_borderWidth != borderWidth) {
            _borderWidth = borderWidth;
            invalidate();
        }
    }

    @ColorInt
    public int getBorderColor() {
        return _borderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        if (_borderColor != borderColor) {
            _borderColor = borderColor;
            invalidate();
        }
    }

    public CircleFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        _borderPaint.setStyle(Paint.Style.STROKE);
        _borderPaint.setAntiAlias(true);

        TypedArray params = null;
        try {
            params = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleFrameLayout, 0, 0);

            _borderWidth = params.getFloat(R.styleable.CircleFrameLayout_borderWidth, 0.0f);
            _borderColor = params.getColor(R.styleable.CircleFrameLayout_borderColor, Color.TRANSPARENT);
        } finally {
            if (params != null) {
                params.recycle();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        _width = w;
        _height = h;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        int saveCount = canvas.getSaveCount();

        Path clipPath = new Path();
        clipPath.addOval(0.0f, 0.0f, _width, _height, Path.Direction.CW);
        canvas.clipPath(clipPath);

        super.dispatchDraw(canvas);

        if (_borderWidth > 0.0f) {

            _borderPaint.setColor(_borderColor);
            _borderPaint.setStrokeWidth(_borderWidth);

            float half = _borderWidth / 2.0f;
            Path borderPath = new Path();
            borderPath.addOval(half, half, _width - half, _height - half, Path.Direction.CW);

            canvas.drawPath(borderPath, _borderPaint);
        }

        canvas.restoreToCount(saveCount);
    }
}
