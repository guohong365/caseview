/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uc.android.camera.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.uc.android.camera.debug.Log;
import com.uc.caseview.R;

/**
 * GridLines is a view which directly overlays the preview and draws
 * evenly spaced grid lines.
 */
public class GridLines extends View
    implements PreviewStatusListener.PreviewAreaChangedListener {
    public enum RulerStyle {
        NONE(0),
        BIG(1),
        SMALL(2);

        public int getStyleCode() {
            return styleCode;
        }

        private final int styleCode;

        RulerStyle(int style) {
            styleCode = style;
        }
    }

    private static final float ratio = 0.787f;
    private RectF mDrawBounds;
    private final Paint mPaint = new Paint();
    private final Paint mBorderPaint = new Paint();
    private final float offset;

    public boolean isGridOn() {
        return gridOn;
    }

    public void setGridOn(boolean gridOn) {
        this.gridOn = gridOn;
    }

    public RulerStyle getRulerStyle() {
        return rulerStyle;
    }

    public void setRulerType(RulerStyle rulerType) {
        this.rulerStyle = rulerType;
    }

    private boolean gridOn = false;
    private RulerStyle rulerStyle = RulerStyle.NONE;

    public GridLines(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.grid_line_width));
        mPaint.setColor(getResources().getColor(R.color.grid_line, null));
        mBorderPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.grid_border_width));
        mBorderPaint.setColor(getResources().getColor(R.color.grid_border, null));
        mBorderPaint.setStyle(Paint.Style.STROKE);
        offset = getResources().getDimensionPixelSize(R.dimen.grid_border_width) / 2;
    }

    private void drawGrid(Canvas canvas, RectF bounds) {
        float thirdWidth = bounds.width() / 3;
        float thirdHeight = bounds.height() / 3;
        for (int i = 1; i < 3; i++) {
            // Draw the vertical lines.
            final float x = thirdWidth * i;
            canvas.drawLine(bounds.left + x, bounds.top, bounds.left + x, bounds.bottom, mPaint);
            // Draw the horizontal lines.
            final float y = thirdHeight * i;
            canvas.drawLine(bounds.left, bounds.top + y, bounds.right, bounds.top + y, mPaint);
        }
    }


    private void drawBorder(Canvas canvas, RectF border){
        canvas.drawRect(border.left, border.top,border.right, border.bottom, mBorderPaint);
    }
    private void drawRuler(Canvas canvas, RectF rulerBounds){
        android.util.Log.d("GridLines", String.format("draw ruler:%s", rulerStyle));
    }
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDrawBounds != null) {
            float minus = offset * 2;
            float width = mDrawBounds.width();
            float height = mDrawBounds.height() * ratio;
            RectF bounds = new RectF(mDrawBounds.left + minus , mDrawBounds.top + minus,
                    mDrawBounds.right - minus, mDrawBounds.top + height - minus);
            if(isGridOn()){
                drawGrid(canvas, bounds);
            }
            RectF border = new RectF(mDrawBounds.left + offset, mDrawBounds.top + offset,
                    mDrawBounds.right -offset, mDrawBounds.top + height - offset );
            drawBorder(canvas, border);
            switch (rulerStyle){
                case BIG:
                    drawRuler(canvas, bounds);
                    break;
                case SMALL:
                    drawRuler(canvas, bounds);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onPreviewAreaChanged(final RectF previewArea) {
        setDrawBounds(previewArea);
    }

    private void setDrawBounds(final RectF previewArea) {
        mDrawBounds = new RectF(previewArea);
        invalidate();
    }
}
