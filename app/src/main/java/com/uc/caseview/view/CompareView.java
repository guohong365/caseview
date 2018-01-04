package com.uc.caseview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SizeF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.uc.android.drawing.Appearance;
import com.uc.android.drawing.DrawObject;
import com.uc.android.drawing.ObjectsContainer;
import com.uc.android.drawing.impl.ImageObjectImpl;
import com.uc.android.drawing.impl.LineAppearanceImpl;
import com.uc.android.drawing.impl.ObjectsContainerImpl;
import com.uc.caseview.R;
import com.uc.caseview.utils.FileUtils;

public class CompareView extends SurfaceView
        implements SurfaceHolder.Callback,
        GestureDetector.OnGestureListener,
        View.OnTouchListener{
    private final Bitmap[] images;
    private final Drawable emptyImage;
    private final SurfaceHolder holder;
    private final RenderThread renderThread;
    private final Rectangle[] layout_rect;
    protected ObjectsContainer[] containers;
    protected  void onDrawUI(Canvas canvas){
        for(DrawObject item:containers){
            if(item!=null) item.draw(canvas);
        }
    };

    private boolean showGrid=true;

    public boolean isShowGrid() {
        return showGrid;
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
    }

    private int backgroundColor=Color.BLACK;

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    protected void drawUI(){
       Canvas canvas= holder.lockCanvas();
        canvas.drawColor(getBackgroundColor());
        if(showGrid){
            drawGrid(canvas);
        }
        try{
            onDrawUI(canvas);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            holder.unlockCanvasAndPost(canvas);
        }
    }
    private Appearance gridAppearance;
    private static Appearance defaultGridAppearance=new LineAppearanceImpl(Color.GRAY, 0.5f, 1.0f);
    public Appearance getGridAppearance() {
        if(gridAppearance==null) return defaultGridAppearance;
        return gridAppearance;
    }

    public void setGridAppearance(Appearance gridAppearance) {
        this.gridAppearance = gridAppearance;
    }

    private int gridInterval=100;
    protected void drawGrid(Canvas canvas){
        Paint paint=getGridAppearance().create();
        for(int i=gridInterval; i < getWidth(); i+=gridInterval){
            canvas.drawLine(i, 0, i, getHeight(), paint);
        }
        for(int i= gridInterval; i< getHeight(); i+=gridInterval){
            canvas.drawLine(0, i, getWidth(), i, paint);
        }
    }
    public CompareView(Context context) {
        super(context);
        emptyImage=getResources().getDrawable(R.drawable.main_action_gallery_48dp, null);
        holder=getHolder();
        holder.addCallback(this);
        images=new Bitmap[]{null,null};
        layout_rect=new Rectangle[]{null,null};
        renderThread=new RenderThread(this);
    }

    public CompareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        emptyImage=getResources().getDrawable(R.drawable.main_action_gallery_48dp, null);
        holder=getHolder();
        holder.addCallback(this);
        renderThread=new RenderThread(this);
        images=new Bitmap[]{null,null};
        layout_rect=new Rectangle[]{null,null};
    }

    public CompareView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        emptyImage=getResources().getDrawable(R.drawable.main_action_gallery_48dp, null);
        holder=getHolder();
        holder.addCallback(this);
        renderThread=new RenderThread(this);
        images=new Bitmap[]{null,null};
        layout_rect=new Rectangle[]{null,null};
    }

    public CompareView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        emptyImage=getResources().getDrawable(R.drawable.main_action_gallery_48dp, null);
        holder=getHolder();
        holder.addCallback(this);
        renderThread=new RenderThread(this);
        images=new Bitmap[]{null,null};
        layout_rect=new Rectangle[]{null,null};
    }

    public void setImages(String[] images) {
        this.images[0]=BitmapFactory.decodeFile(FileUtils.getImagePathName(getContext(), images[0]));
        this.images[1] =BitmapFactory.decodeFile(FileUtils.getImagePathName(getContext(), images[1]));
    }
    public Bitmap[] getImages(){
        return images;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if(containers!=null){
            containers[0].setWidth(width/2);
            containers[0].setHeight(height);
            containers[1].setWidth(width/2);
            containers[1].setHeight(height);
            containers[1].setPosition(width/2 + 1, 0);
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        renderThread.running=false;
    }

    private GestureDetector gestureDetector;
    public void init(){
        activeContainer=null;
        gestureDetector=new GestureDetector(getContext(), this);
        containers=new ObjectsContainer[]{new ObjectsContainerImpl(), new ObjectsContainerImpl()};
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
        setOnTouchListener(this);
    }
    private ObjectsContainer activeContainer=null;
    private PointF lastTouch;
    private int mode;
    @Override
    public boolean onDown(MotionEvent e) {
        lastTouch=new PointF(e.getX(),e.getY());
        mode=0;
        PointF pt;
        for(ObjectsContainer container:containers){
            pt=container.world2Local(lastTouch);
            if(container.hitTest(pt)!=0){
                container.setActive(true);
                container.selectAt(pt);
            }
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if(activeContainer==null)  return false;
        activeContainer.getSelectedItems().offset(distanceX, distanceY);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(gestureDetector.onTouchEvent(event))return true;
        return super.onTouchEvent(event);
    }

    class RenderThread extends Thread {
        CompareView owner;
        boolean running=false;

        RenderThread(@NonNull CompareView owner){
            this.owner=owner;
        }

        @Override
        public synchronized void start() {
            running=true;
            super.start();
        }
        @Override
        public void run() {
            while(running){
                owner.drawUI();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.v("RENDER:", "render is stoped.");
        }
    }
}
