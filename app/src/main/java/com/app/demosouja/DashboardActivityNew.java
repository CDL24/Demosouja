package com.app.demosouja;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fragments.AppearFragment;

/**
 * Created by C.limbachiya on 8/22/2016.
 */
public class DashboardActivityNew extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_new);

        getFragmentManager().beginTransaction()
                .add(R.id.container, new AppearFragment())
                .commit();
    }
}