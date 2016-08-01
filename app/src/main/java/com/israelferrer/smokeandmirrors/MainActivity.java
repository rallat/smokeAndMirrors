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
        final View view = findViewById(R.id.imageview);
        View parent = findViewById(R.id.parent);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        final int width = size.x;
        final int height = size.y;
        assert parent != null;
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert view != null;
                view.animate().x(width).y(height).setDuration(2000).start();
            }
        });
    }
}
