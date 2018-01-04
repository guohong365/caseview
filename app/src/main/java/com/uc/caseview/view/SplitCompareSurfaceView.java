package com.uc.caseview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.SizeF;

import com.uc.android.drawing.impl.FillAppearanceImpl;
import com.uc.android.drawing.impl.ImageObjectImpl;
import com.uc.android.drawing.impl.LineAppearanceImpl;
import com.uc.android.drawing.impl.LineImpl;
import com.uc.android.drawing.impl.RectangleImpl;

public class SplitCompareSurfaceView extends CompareView {

    public SplitCompareSurfaceView(Context context) {
        super(context);
    }

    public SplitCompareSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SplitCompareSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public SplitCompareSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    public void init() {
        super.init();
        initObjects();
    }

    private void initObjects(){
        LineImpl line=new LineImpl(new PointF(100,200), new PointF(200,500));
        line.setAppearance(new LineAppearanceImpl(Color.RED, 1.0f, 3));
        //objects.add(line);
        RectangleImpl rect=new RectangleImpl(new PointF(300,300), new SizeF(300,300));
        rect.setShowBorder(true);
        rect.setShowTrack(true);
        rect.setFilled(true);
        rect.setBorderAppearance(new LineAppearanceImpl(Color.RED, 1.0f, 5));
        rect.setBackgroundAppearance(new FillAppearanceImpl(Color.CYAN, 0.5f));
        rect.setShowBorder(true);
        rect.rotate(30);
        containers[0].add(rect);
        ImageObjectImpl imageObject=new ImageObjectImpl(new PointF(700,300), new SizeF(300,400), getImages()[0]);
        imageObject.setShowBorder(false);
        //imageObject.rotate(-30);
        containers[0].add(imageObject);
        imageObject=new ImageObjectImpl(new PointF(1300,300), new SizeF(300,400), getImages()[0]);
        imageObject.rotate(-30);
        imageObject.scale(0.5f, 0.5f);
        imageObject.setShowBorder(true);
        imageObject.setBorderAppearance(new LineAppearanceImpl(Color.YELLOW, 1, 5));
        containers[0].add(imageObject);
    }

    @Override
    protected void onDrawUI(Canvas canvas) {
        containers[0].draw(canvas);
        containers[1].draw(canvas);
    }
}
