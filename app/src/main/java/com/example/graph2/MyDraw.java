package com.example.graph2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class MyDraw extends View
{
    private int x;
    private int y;
    private Canvas canvas;
    Paint paint;
    public MyDraw(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas=canvas;
        paint=new Paint();
        paint.setSubpixelText(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.argb(127,0,0,255));
 //       paint.setColor(Color.parseColor("#FFFF00"));
        paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawPaint(paint);
        paint.setColor(Color.GREEN);
        canvas.drawCircle(getWidth()/2,getHeight()/2,100,paint);
        paint.setColor(Color.BLACK);
        canvas.drawRect(10,10,getWidth()/2,200,paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.rotate(45,200,200);
        canvas.drawText("My Canvas",200,100,paint);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher_background);
        canvas.drawBitmap(bitmap,x,y,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int eventAction = event.getAction();
        if (eventAction == MotionEvent.ACTION_DOWN)  {
            x=(int)event.getX();
            y=(int)event.getY();
            update();
        }
        return true;
    }
    protected void update () {
        paint.setColor(getResources().getColor(R.color.colorAccent));
        canvas.drawPaint(paint);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_launcher_background);
        canvas.drawBitmap(bitmap,x,y,paint);
        invalidate();
    }
}
