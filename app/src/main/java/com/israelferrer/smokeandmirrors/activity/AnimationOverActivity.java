package com.israelferrer.smokeandmirrors.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.israelferrer.smokeandmirrors.R;

public class AnimationOverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_over);
        final View profile = findViewById(R.id.profile);
        final View saturn = findViewById(R.id.uranus);
        assert saturn != null;
        assert profile != null;
        saturn.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop,
                    int oldRight,
                    int oldBottom) {
                saturn.removeOnLayoutChangeListener(this);
                profile.bringToFront();
                profile.animate().y(saturn.getMeasuredHeight()*2).setStartDelay(200).setDuration(2000).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        saturn.bringToFront();
                        saturn.animate().y(profile.getMeasuredHeight() * 3).setStartDelay(200).setDuration(2000);
                    }
                });
            }
        });
    }
}
