package com.israelferrer.smokeandmirrors;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View parent = findViewById(R.id.parent);
        View view = findViewById(R.id.imageview);
        view.animate().x(parent.getMeasuredWidth()).y(parent.getMeasuredHeight()).setStartDelay(800).setDuration(2000).start();
    }

}
