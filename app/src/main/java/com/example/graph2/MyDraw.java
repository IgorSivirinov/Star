package com.example.graph2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.graph2.Model.Player;

public class MyDraw extends SurfaceView implements Runnable
{
    volatile boolean playing;
    private Thread gameThread=null;
    private Player player;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    public MyDraw(Context context) {
        super(context);
        player=new Player(context);
        surfaceHolder=getHolder();
        paint=new Paint();
    }

    @Override
    public void run() {
        while (playing)
        {
            update();
            draw();
            control();
        }
    }
    public void  update()
    {
        player.Update();
    }

    public void draw()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            canvas=surfaceHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                canvas.drawBitmap(player.getBitmap(),
                        player.getX(),
                        player.getY(),
                        paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    public void control()
    {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void  pause()
    {
        playing=false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume()
    {
        playing=true;
        gameThread=new Thread(this);
        gameThread.start();
    }
}
