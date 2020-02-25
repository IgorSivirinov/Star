package com.example.graph2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.icu.text.CaseMap;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.graph2.Model.Bullet;
import com.example.graph2.Model.Enemy;
import com.example.graph2.Model.Player;
import com.example.graph2.Model.Star;

import java.util.ArrayList;

public class MyDraw extends SurfaceView implements Runnable
{
    volatile boolean playing;
    private Thread gameThread=null;
    private Player player;
    private Bullet bullet;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Star> stars=new ArrayList<>();
    public MyDraw(Context context,int screenX,int screenY) {
        super(context);
        player=new Player(context,screenX,screenY);
        bullet=new Bullet(player.getX(),player.getY(),screenX);
        surfaceHolder=getHolder();
        paint=new Paint();
        int starNums=100;

        for(int i=0;i<starNums;i++)
        {
            Star s=new Star(screenX,screenY);
            stars.add(s);
        }
        int enemyNums=50;
        for(int i=0;i<enemyNums;i++)
        {
            Enemy e=new Enemy(screenX,screenY);
            enemies.add(e);
        }
    }

    @Override
    public void run() {
        while (playing)
        {
            update();
            draw();
            control();
            Killing();
        }
    }
    public void  update()
    {
        bullet.Update(player.getX(),player.getY(),player.getSpeed());
        player.Update();
        for(Star s:stars)
        {
            s.Update(player.getSpeed());
        }
        for(Enemy e:enemies)
            {
        e.Update(player.getSpeed());
        }


    }

    public void draw()
    {
        if(surfaceHolder.getSurface().isValid())
        {
            canvas=surfaceHolder.lockCanvas();
                canvas.drawColor(Color.BLACK);
                paint.setColor(Color.WHITE);
                for(Star s:stars)
                {
                    paint.setStrokeWidth(s.getStarWith());
                    canvas.drawPoint(s.getX(),s.getY(),paint);
                }
                paint.setColor(Color.RED);
                for(Enemy e:enemies)
                {
                paint.setStrokeWidth(e.getEnemyWith());
                canvas.drawPoint(e.getX(),e.getY(),paint);
                }
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(bullet.getBulletWith());
                canvas.drawPoint(bullet.getX(),bullet.getY(),paint);
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
    public void pause()
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
    public void Killing(){
        for(Enemy e:enemies)
        {
            if((bullet.getX()>e.getX())&&(bullet.getY()<=e.getY()+50&&bullet.getY()>=e.getY()-50)){
                e.setX(-1);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_UP:
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                player.setBoosting();
                break;
        }
        return true;
    }
}
