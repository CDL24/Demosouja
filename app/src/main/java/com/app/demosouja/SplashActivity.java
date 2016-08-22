package com.app.demosouja;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

public class SplashActivity extends Activity {

    ImageView imgLogo;
    private int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniUIControls();
        initClassObjects();
        startAnimation();
        //endAnimation();
    }

    private void initClassObjects() {
    }

    private void endAnimation() {
    }

    private void startAnimation() {

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                loadAnimation();
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + 1500);
        handler.postDelayed(runnable, 1500);



    }

    private void loadAnimation() {
        imgLogo.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet(); //this is your animation set.
        //add as many Value animator to it as you like

        ValueAnimator scaleUp = ValueAnimator.ofFloat(0,(float)1.2);
        scaleUp.setDuration(1000);
        //scaleUp.setInterpolator(new BounceInterpolator()); //remove this if you prefer default interpolator
        scaleUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float newValue = (Float) valueAnimator.getAnimatedValue();
                imgLogo.setScaleY(newValue);
                imgLogo.setScaleX(newValue);
            }

        });

        /*ValueAnimator scaleDown = ValueAnimator.ofFloat((float)1.2,1);
        scaleUp.setDuration(1000);
        scaleUp.setInterpolator(new BounceInterpolator());
        scaleUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Float newValue = (Float) valueAnimator.getAnimatedValue();
                imgLogo.setScaleY(newValue);
                imgLogo.setScaleX(newValue);
            }
        });*/


        set.play(scaleUp);
        //set.play(scaleDown).after(scaleUp);
        set.start();

        onRedirect();
    }

    private void onRedirect() {
        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();

            }
        }, SPLASH_TIME_OUT);
    }

    private void iniUIControls() {
        imgLogo = (ImageView) findViewById(R.id.image_logo);
    }
}