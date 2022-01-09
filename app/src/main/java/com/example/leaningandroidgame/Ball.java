package com.example.leaningandroidgame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public class Ball implements GameObject{
    public Rect rectangle;
    private int color;
    private Point point;
    public int xPos;
    public int yPos;
    public int velX=0;
    public int velY=0;

    public RectF rectF = new RectF();
    /**
     * In order for the update to work with the velocity I need to pass point and that store the point values to x and y.
     * If I pass x and y then set point it will give errors
     */
    public Ball(Rect rectangle, int color, Point point) {
        this.rectangle = rectangle;
        this.color = color;
        this.point = point;
        this.xPos = point.x;
        this.yPos = point.y;
        /**
         * The rectangle must be used because it can be used for the collision detection and use the the wanted shape.
         * If you want to use the rectangle as a shape than you don't need to use anything extra be cause that rect can be passed to be draw
         * How ever if you want to use the circle then you must use rectF to retain the coordinate of the circle and then use rect for the collision detection.
         * Only the rect has got the built in method to detect the collision.
         */

        //Setting up the points of the rectangle shape. This will draw the four points of the rectangle
        //left, top, right, bottom
        //For collision will not be drawn
        rectangle.set(this.point.x - rectangle.width() / 2, this.point.y - rectangle.height()/2, this.point.x + rectangle.width() / 2, this.point.y + rectangle.height()/2);
        //For drawing the circle shape
        rectF.set(this.point.x - rectangle.width() / 2, this.point.y - rectangle.height()/2, this.point.x + rectangle.width() / 2, this.point.y + rectangle.height()/2);
    }


    @Override
    public void draw(Canvas canvas) {
        //Setting up the rectangle color and drawing it
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawOval(rectF,paint); //This is here you decide and chose the shape
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
        rectF.set((xPos - rectangle.width() / 2)+velX, (yPos - rectangle.height()/2)+velY, (xPos + rectangle.width() / 2)+velX, (yPos + rectangle.height()/2)+velY);
    }
}
