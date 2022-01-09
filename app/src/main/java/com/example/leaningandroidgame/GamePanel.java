package com.example.leaningandroidgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

/**
 * SurfaceHolder.Callback
 * A client may implement this interface to receive information about changes to the surface.
 * When used with a SurfaceView, the Surface being held is only available between calls to
 * surfaceCreated(android.view.SurfaceHolder) and surfaceDestroyed(android.view.SurfaceHolder).
 * The Callback is set with SurfaceHolder.addCallback method.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private MainThread thread;


    //Player that we made
    private RectPlayer playerOne;
    private Point playerPointOne;
    private double playerOneOldYPos = 0;
    private int playerOneSpeed = 8;
    private int[] scoreCounterPlayerOne = {0, 0};

    private RectPlayer playerTwo;
    private Point playerPointTwo;
    private int playerTwoSpeed = 3;
    private int[] scoreCounterPlayerTwo = {0, 0};

    private Ball ball;
    private Point ballPoint;
    private int ballSpeed = 3;

    private Paint paintScoreTextView;
    private Paint paintLine;
    private Paint paintCircle;

    private boolean screenTouched;


    public GamePanel(Context context) {
        super(context);

        //Access to the underlying surface is provided via the SurfaceHolder interface,
        // which can be retrieved by calling getHolder()
        getHolder().addCallback(this);

        //Instantiating MainThread class that we made
        thread = new MainThread(getHolder(), this);
        //range x and y
        // x 0 to 1080
        // y 0 to 1980
        playerPointOne = new Point(45, 500);
        playerOne = new RectPlayer(new Rect(0, 0, 25, 150), Color.rgb(0, 0, 0), playerPointOne);
        //2130, 450
        playerPointTwo = new Point(2130, 500);
        playerTwo = new RectPlayer(new Rect(0, 0, 25, 150), Color.rgb(0, 0, 0), playerPointTwo);

        ballPoint = new Point(450, 450);
        ball = new Ball(new Rect(0, 0, 30, 30), Color.rgb(0, 0, 0), ballPoint);
        ball.velX = ballSpeed;
        ball.velY = ballSpeed;


        //Creating styling ofr the shapes
        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.FILL); //Solid shape
        paintLine.setColor(Color.BLACK);
        paintLine.setStrokeWidth(5);


        paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);//Only parameter
        paintCircle.setColor(Color.BLACK);
        paintCircle.setStrokeWidth(5);


        setFocusable(true);

    }


    public void scoreBoard() {
        //Paint: The paint used for the text (e.g. color, size, style) This value cannot be null.
        paintScoreTextView = new Paint();
        paintScoreTextView.setStyle(Paint.Style.FILL);
        paintScoreTextView.setColor(Color.BLACK);
        paintScoreTextView.setTextSize(50);
    }

    /**
     * This method is for the ball movement
     */
    //TODO ball collision works
    public void ballMove() {
        if ((int) ball.xPos == 0) {//This condition suppose to bounce the ball from the left and the right side wall, but instead I am using it to update the score
            scoreCounterPlayerTwo[0] += 1;
            //Setting the values for the ball to stop as well as go to the wanted location
            ball.velX = +ballSpeed;

        }
        if ((int) ball.xPos == 2170) {//This condition suppose to bounce the ball from the left and the right side wall, but instead I am using it to update the score
            scoreCounterPlayerOne[0] += 1;
            //Setting the values for the ball to stop as well as go to the wanted location
            ball.velX = -ballSpeed;

        }
        if (ball.yPos < 0) {//This condition bounces the ball from the top and the bottom walls
            ball.velY = +ballSpeed;
        }
        if (ball.yPos > 1000) {//This condition bounces the ball from the top and the bottom walls
            ball.velY = -ballSpeed;
        }


        //Collision detection when ball bouncing off the players

        if (Rect.intersects(playerOne.rectangle, ball.rectangle)) {
            ball.velX = +ballSpeed;

            if (playerTwo.yPos > 300) {
                ball.velY = +ballSpeed;
            }
            if (playerTwo.yPos < 300) {
                ball.velY = -ballSpeed;

            }
        }

        if (Rect.intersects(playerTwo.rectangle, ball.rectangle)) {
            ball.velX = -ballSpeed;

            if (playerTwo.yPos > 300) {
                ball.velY = +ballSpeed;
            }
            if (playerTwo.yPos < 300) {
                ball.velY = -ballSpeed;

            }
        }
    }

    /**
     * This method is for the auto movement
     */
    public void autoPlayerMove() {
        playerTwo.velY = 0;
        if (ball.xPos >= 1600) {//This condition is only to start the movement one the player
            if (ball.yPos >= playerTwo.yPos && ball.yPos <= 1000) {
                playerTwo.velY = +playerTwoSpeed; //+
            }
            if (ball.yPos <= playerTwo.yPos && ball.yPos >= 70) {
                playerTwo.velY = -playerTwoSpeed; //-
            }
        }


    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);


        thread.setRunning(true);
        thread.start();
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //In order to move any object this is the method that will detect any touch on the surfaceView
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:


                double yPos = event.getY();


                //Checking if the older values of the y-axis was greater and current value is smaller that means velocity must be subtracted and if its vice versa I must increase it
                if (yPos >= playerOneOldYPos && playerOne.yPos <= 1000) {
                    playerOne.velY = +playerOneSpeed;
                }
                if (yPos <= playerOneOldYPos && playerOne.yPos >= 70) { //Checking if the player goes out of bound
                    playerOne.velY = -playerOneSpeed;
                }

                //storing the older values to compare it in the conditions
                playerOneOldYPos = event.getY();
                playerOne.update(playerOne.velX, playerOne.velY);
                playerOne.velY = 0;

                break;
        }


        //By making it true it will detect all the touches
        return true;

    }

    //If the object needs to move or it is need to constantly check for collision must use this method.
    public void update() {
        autoPlayerMove();
        ballMove();
        scoreBoard();

        playerTwo.update(playerTwo.velX, playerTwo.velY);
        ball.update(ball.velX, ball.velY);


    }


    //This method will add any object on the surfaceView
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);


        //Setting update the colour of the canvas
        canvas.drawColor(Color.WHITE);
        //adding the player to the canvas
        playerOne.draw(canvas);
        playerTwo.draw(canvas);
        ball.draw(canvas);

        //To draw the text on the screen
        //Drawing the paint on the surface
        // canvas.drawPaint(paint);
        //Drawing the text on the surface
        canvas.drawText("Score " + scoreCounterPlayerOne[0] + " -- " + scoreCounterPlayerTwo[0], 906, 50, paintScoreTextView);

        //start is the starting point and the stop and point
        //In order to keep the line straight horizontally
        canvas.drawLine(1100, 0, 1100, 1400, paintLine);
        canvas.drawOval(900, 300, 1300, 700, paintCircle);
    }

}


