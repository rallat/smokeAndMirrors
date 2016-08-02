package com.israelferrer.smokeandmirrors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final View clipChildren = findViewById(R.id.clipChildrenButton);
        final View clipPadding = findViewById(R.id.clipPaddingButton);
        final View animationOverlay = findViewById(R.id.animationOverlayButton);
        final View googlePhotosDemo = findViewById(R.id.googlePhotosDemoButton);
        assert clipChildren != null;
        clipChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ClipChildrenActivity.class));
            }
        });
        assert clipPadding != null;
        clipPadding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ClipPaddingActivity.class));
            }
        });
        assert animationOverlay != null;
        animationOverlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AnimationOverActivity.class));
            }
        });
        assert googlePhotosDemo != null;
        googlePhotosDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GooglePhotosActivity.class));
            }
        });

    }
}
