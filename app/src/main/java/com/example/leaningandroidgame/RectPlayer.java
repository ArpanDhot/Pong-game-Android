package com.example.leaningandroidgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class RectPlayer implements GameObject {

    public Rect rectangle;
    private int color;
    private Point point;
    public int xPos;
    public int yPos;
    public int velX=0;
    public int velY=0;

    /**
     * In order for the update to work with the velocity I need to pass point and that store the point values to x and y.
     * If I pass x and y then set point it will give errors
     */
    public RectPlayer(Rect rectangle, int color, Point point) {
        this.rectangle = rectangle;
        this.color = color;
        this.point = point;
        this.xPos = point.x;
        this.yPos = point.y;

        //Setting up the points of the rectangle shape. This will draw the four points of the rectangle
        //left, top, right, bottom

        rectangle.set(this.point.x - rectangle.width() / 2, this.point.y - rectangle.height()/2, this.point.x + rectangle.width() / 2, this.point.y + rectangle.height()/2);
    }


    @Override
    public void draw(Canvas canvas) {
        //Setting up the rectangle color and drawing it
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    /**
     * I just had to (getXPos() - rectangle.width() / 2)+velX  if I do it the way I code in java fx
     */
    public void update(int velX, int velY) {
        xPos+=velX;
        yPos+=velY;
        rectangle.set((xPos - rectangle.width() / 2)+velX, (yPos - rectangle.height()/2)+velY, (xPos + rectangle.width() / 2)+velX, (yPos + rectangle.height()/2)+velY);
    }
}
