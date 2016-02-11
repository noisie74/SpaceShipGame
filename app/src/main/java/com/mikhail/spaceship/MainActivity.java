package com.mikhail.spaceship;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {
    public static final String TAG  = MainActivity.class.getName();
    ImageView image;
    Button left;
    Button right;
    RocketMover rocketMover;
    boolean goRight = false;
    boolean goLeft = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.rocket);
        right = (Button) findViewById(R.id.right_button);
        left = (Button) findViewById(R.id.left_button);

        image.setScaleX(2);
        image.setScaleY(2);

        rocketMover = new RocketMover();

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rootRL);

        SpaceRocket spaceRocket = new SpaceRocket("battleship");
        spaceRocket.fire(100, 100, this, relativeLayout);
        Log.e(TAG, "error");

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goRight = true;
                    moveRocket();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){

                    stopRocker();
                }
                return false;
            }
        });

        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goLeft = true;
                    moveRocket();
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    stopRocker();
                }
                return false;
            }
        });

    }

    public void moveRocket(){
        rocketMover = new RocketMover();
        rocketMover.start();

    }

    public void stopRocker(){
        rocketMover.interrupt();
        goRight = false;
        goLeft = false;
    }

    public class RocketMover extends Thread{
        @Override
        public void run() {

            while(goRight || goLeft) {
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(goRight) {
                            image.setX(image.getX() + 15);
                        } else if(goLeft){
                            image.setX(image.getX() - 15);
                        }
                    }
                });
            }


        }
    }


    public class SpaceRocket {

        public int bullets = 0;
        public String name;

        public SpaceRocket(String name){
            this.name = name;
        }

        public void fire(int x, int y, Context context, ViewGroup viewGroup) {
            ImageView bulletsImageView = new ImageView(context);
            viewGroup.addView(bulletsImageView);
            bullets++;
        }
    }

}