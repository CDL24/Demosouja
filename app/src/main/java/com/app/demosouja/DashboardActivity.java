package com.app.demosouja;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.tumblr.backboard.Actor;
import com.tumblr.backboard.imitator.ToggleImitator;
import com.tumblr.backboard.performer.MapPerformer;

import customclasses.HexagonMaskView;

/**
 * Created by C.limbachiya on 8/22/2016.
 */
public class DashboardActivity extends AppCompatActivity {

    private static final int OPEN = 1;
    private static final int CLOSED = 0;
    private static int DIAMETER = 50; //50 for hdpi - 80 for xhdpi
    private static int RING_DIAMETER = 4 * DIAMETER; //3 for hdpi - 5 for xhdpi
    final int interval = 500; // 1 Second
    RelativeLayout mRootView;
    View mCircle;
    private View[] mCircles;
    HexagonMaskView img1, img2, img3, img4;
    ToggleImitator imitator = null;


    SpringSystem springSystem;
    // create spring
    Spring spring ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initUIControls();

        springSystem = SpringSystem.create();
        // create spring
        spring = springSystem.createSpring();

        loadDashboardAnimation();

        eventListener();
    }

    private void eventListener() {
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /*final SpringSystem springSystem = SpringSystem.create();
                // create spring
                final Spring spring = springSystem.createSpring();*/
                // add listeners along arc
                final double arc = 2 * Math.PI / mCircles.length;

                for (int i = 0; i < mCircles.length; i++) {
                    View view1 = mCircles[i];
                    // map spring to a line segment from the center to the edge of the ring
                    spring.addListener(new MapPerformer(view, View.TRANSLATION_X, 0, 1,
                            0, (float) (RING_DIAMETER * Math.cos(i * arc))));
                    spring.addListener(new MapPerformer(view, View.TRANSLATION_Y, 0, 1,
                            0, (float) (RING_DIAMETER * Math.sin(i * arc))));
                    spring.setEndValue(CLOSED);
                }

                imitator = new ToggleImitator(spring, CLOSED, CLOSED);
                imitator.release(null);//actually release animation
                Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    public void run() {
                        Toast.makeText(DashboardActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                    }
                };
                handler.postAtTime(runnable, System.currentTimeMillis() + 1000);
                handler.postDelayed(runnable, 1000);
            }
        });

    }

    private void initUIControls() {
        mRootView = (RelativeLayout) findViewById(R.id.root_layout);
        mCircle = findViewById(R.id.circle);
        img1 = (HexagonMaskView) findViewById(R.id.image_1);
        img2 = (HexagonMaskView) findViewById(R.id.image_2);
        img3 = (HexagonMaskView) findViewById(R.id.image_3);
        img4 = (HexagonMaskView) findViewById(R.id.image_4);
    }

    /*Load animation in separate thread*/
    private void loadDashboardAnimation() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                DIAMETER = 0;
                RING_DIAMETER = 0;
                getDisplayMetrics();
                setAnimationLayoutSize();
                makeDashboardAnimation();
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }

    //Set Animation height/width here
    private void setAnimationLayoutSize() {
        if (mRootView != null) {
            try {
                mRootView.getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
                mRootView.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
                //mRootView.setRotation(270.0f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //Method for getting DisplayMetrics of device resolution
    private void getDisplayMetrics() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;
        if (density == DisplayMetrics.DENSITY_HIGH) {
            //Toast.makeText(DashboardActivity.this, "DENSITY_HIGH", Toast.LENGTH_SHORT).show();
            DIAMETER = 55;
            RING_DIAMETER = 3;
        } else if (density == DisplayMetrics.DENSITY_MEDIUM) {
            DIAMETER = 45;
            RING_DIAMETER = 3;
        } else if (density == DisplayMetrics.DENSITY_LOW) {
            DIAMETER = 45;
            RING_DIAMETER = 3;
        } else if (density == DisplayMetrics.DENSITY_XHIGH) {
            //Toast.makeText(DashboardActivity.this, "DENSITY_XHIGH", Toast.LENGTH_SHORT).show();
            DIAMETER = 65;
            RING_DIAMETER = 4;
        } else if (density == DisplayMetrics.DENSITY_XXHIGH) {
            //Toast.makeText(DashboardActivity.this, "DENSITY_XXHIGH", Toast.LENGTH_SHORT).show();
            DIAMETER = 95;
            RING_DIAMETER = 4;
        } else if (density == DisplayMetrics.DENSITY_XXXHIGH) {
            //Toast.makeText(DashboardActivity.this, "DENSITY_XXXHIGH", Toast.LENGTH_SHORT).show();
            DIAMETER = 95;
            RING_DIAMETER = 4;
        } else if(density == DisplayMetrics.DENSITY_420){
            Toast.makeText(DashboardActivity.this, "DENSITY_420", Toast.LENGTH_SHORT).show();
            DIAMETER = 95;
            RING_DIAMETER = 4;
        }
        else if(density == DisplayMetrics.DENSITY_560){
            Toast.makeText(DashboardActivity.this, "DENSITY_560", Toast.LENGTH_SHORT).show();
            DIAMETER = 95;
            RING_DIAMETER = 5;
        }
        else { //Any other resolution

            //Toast.makeText(DashboardActivity.this, "ELSE", Toast.LENGTH_SHORT).show();
            DIAMETER = 100;
            RING_DIAMETER = 5;
        }
    }

    //Load dashboard Jovia menu animation
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void makeDashboardAnimation() {
        createAndLoadAnimation();
    }

    //here we are actually creating loading animation
    private void createAndLoadAnimation() {

        RING_DIAMETER = RING_DIAMETER * DIAMETER;
        final float diameter = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DIAMETER,
                getResources().getDisplayMetrics());
        final TypedArray circles = getResources().obtainTypedArray(R.array.circles);
        // layout params
        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) diameter,
                (int) diameter);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        mCircles = new View[4];
        createArcMenuElements();
        circles.recycle();
        /* Animations! */
        //final SpringSystem springSystem = SpringSystem.create();
        // create spring
        //final Spring spring = springSystem.createSpring();
        // add listeners along arc
        final double arc = 2 * Math.PI / mCircles.length;

        for (int i = 0; i < mCircles.length; i++) {
            View view = mCircles[i];
            // map spring to a line segment from the center to the edge of the ring
            spring.addListener(new MapPerformer(view, View.TRANSLATION_X, 0, 1,
                    0, (float) (RING_DIAMETER * Math.cos(i * arc))));

            spring.addListener(new MapPerformer(view, View.TRANSLATION_Y, 0, 1,
                    0, (float) (RING_DIAMETER * Math.sin(i * arc))));

            spring.setEndValue(CLOSED);

        }
        imitator = new ToggleImitator(spring, OPEN, OPEN);
        imitator.release(null);//actually release animation
        new Actor.Builder(SpringSystem.create(), mCircle)
                .build();
    }

    //Create arc menu elements in view array
    private void createArcMenuElements() {
        mCircles[0] = img1;
        mCircles[1] = img2;
        mCircles[2] = img3;
        mCircles[3] = img4;

        //rotateArcMenuElements();
    }
}