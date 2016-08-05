package com.israelferrer.smokeandmirrors.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.israelferrer.smokeandmirrors.AnimationUtils;
import com.israelferrer.smokeandmirrors.R;

public class ClipChildrenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_children);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;

        ViewGroup root = (ViewGroup) findViewById(R.id.root);
        final View view = findViewById(R.id.imageView);
        final ToggleButton toggleClipButton = (ToggleButton)findViewById(R.id.clipToggle);
        assert toggleClipButton != null;
        toggleClipButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AnimationUtils.enableParentsClip(view);
                } else {
                    AnimationUtils.disableParentsClip(view);
                }
            }
        });

        assert root != null;
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                assert view != null;
                view.animate().x(width).y(height).setDuration(2000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        view.setX(0);
                        view.setY(0);
                    }
                }).start();
            }
        });
    }
}
