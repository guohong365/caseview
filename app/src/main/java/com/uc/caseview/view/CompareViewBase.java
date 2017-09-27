package com.uc.caseview.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.uc.caseview.R;

public abstract class CompareViewBase extends SurfaceView implements SurfaceHolder.Callback {
    private final BitmapDrawable[] images;
    private final Drawable emptyImage;
    private final SurfaceHolder holder;
    private final RenderThread renderThread;

    protected abstract void drawUI();

    public CompareViewBase(Context context) {
        super(context);
        emptyImage=getResources().getDrawable(R.drawable.main_action_gallery_48dp, null);
        holder=getHolder();
        holder.addCallback(this);
        renderThread=new RenderThread(this);
        images=new BitmapDrawable[]{null,null};
    }

    public CompareViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        emptyImage=getResources().getDrawable(R.drawable.main_action_gallery_48dp, null);
        holder=getHolder();
        holder.addCallback(this);
        renderThread=new RenderThread(this);
        images=new BitmapDrawable[]{null,null};
    }

    public CompareViewBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        emptyImage=getResources().getDrawable(R.drawable.main_action_gallery_48dp, null);
        holder=getHolder();
        holder.addCallback(this);
        renderThread=new RenderThread(this);
        images=new BitmapDrawable[]{null,null};
    }

    public CompareViewBase(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        emptyImage=getResources().getDrawable(R.drawable.main_action_gallery_48dp, null);
        holder=getHolder();
        holder.addCallback(this);
        renderThread=new RenderThread(this);
        images=new BitmapDrawable[]{null,null};
    }

    public void setImages(String[] images) {
        this.images[0] =new BitmapDrawable(getResources(),images[0]);
        this.images[1] =new BitmapDrawable(getResources(), images[1]);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        renderThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        renderThread.running=false;
    }

    class RenderThread extends Thread {
        CompareViewBase owner;
        boolean running=false;

        RenderThread(@NonNull CompareViewBase owner){
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
            }
            super.run();
        }
    }
}
