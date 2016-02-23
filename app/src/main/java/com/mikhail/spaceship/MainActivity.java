package com.mikhail.spaceship;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getName();
    ImageView image;
    ImageView bullets;
    public RelativeLayout layout;
    Button left;
    Button right;
    Button shoot;
    RocketMover rocketMover;
    boolean goRight = false;
    boolean goLeft = false;
    Point size = new Point();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.rocket);
        right = (Button) findViewById(R.id.right_button);
        left = (Button) findViewById(R.id.left_button);
        shoot = (Button) findViewById(R.id.bottom_button);
        layout = (RelativeLayout) findViewById(R.id.rootRL);
        bullets = (ImageView) findViewById(R.id.bullets);

        image.setScaleX(2);
        image.setScaleY(2);

        Display display = getWindowManager().getDefaultDisplay();

        display.getSize(size);

        rocketMover = new RocketMover();
        rocketMover.start();


        shoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final int amountToMoveDown = -1050;
                TranslateAnimation anim = new TranslateAnimation(0, 0, 0, amountToMoveDown);
                anim.setFillEnabled(true);
                anim.setFillAfter(true);
                anim.setDuration(1000);


                anim.setAnimationListener(new TranslateAnimation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bullets.getLayoutParams();
                        params.setMarginStart((int)image.getX()-1);
                        bullets.setLayoutParams(params);



                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bullets.getLayoutParams();
                        params.topMargin += amountToMoveDown;
                        bullets.setLayoutParams(params);
                    }
                });

                bullets.startAnimation(anim);
            }
        });

        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    goRight = true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    stopRocker();
                }
                return false;
            }
        });


        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    goLeft = true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopRocker();
                }

                return false;
            }
        });

    }

    public void stopRocker() {
        goRight = false;
        goLeft = false;

    }

    public class RocketMover extends Thread {
        @Override
        public void run() {

            while (true) {
                if (goRight || goLeft) {
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (goRight) {
                                if (image.getX() < size.x - image.getWidth() - 30)
                                    image.setX(image.getX() + 15);
                            } else if (goLeft) {
                                if (image.getX() > 30)
                                    image.setX(image.getX() - 15);
                            }
                        }
                    });
                }
            }


        }


    }

//    public class Fire extends Thread{
//
//
//        shoot.setOnClickListener(new View.OnClickListener()
//
//        {
//
//            public void onClick (View v; image){
//
//            final ImageView bullets = new ImageView(getApplicationContext());
//
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            bullets.setImageResource(R.drawable.bullets);
//            layout.addView(bullets, params);
//            int xPos = (int) image.getX() + image.getWidth() / 4;
//            TranslateAnimation animation = new TranslateAnimation(xPos, xPos, (int) image.getY() - bullets.getHeight() / 2, -10);
//            animation.setDuration(500);
//            animation.setFillAfter(false);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    Log.e(TAG, "onAnimationStart");
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    Log.e(TAG, "onAnimationEnd");
//                    bullets.clearAnimation();
//                    layout.removeView(bullets);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//
//            bullets.startAnimation(animation);
//
//
//        }
//
//        });


//    }

}












