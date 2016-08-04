package com.israelferrer.smokeandmirrors.gesturedetector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.israelferrer.smokeandmirrors.R;

public class ItemTouchListenerDispatcher implements RecyclerView.OnItemTouchListener {
    @NonNull
    private final ScaleGestureDetector galleryGestureDirectory;
    @NonNull
    private final ScaleGestureDetector fullScreenGestureDirectory;
    private float currentSpan;

    public ItemTouchListenerDispatcher(@NonNull Context context,
            @NonNull final ScaleGestureDetector fullScreenGestureDirectory,
            @NonNull ScaleGestureDetector galleryGestureDirectory) {
        this.galleryGestureDirectory = galleryGestureDirectory;
        this.fullScreenGestureDirectory = fullScreenGestureDirectory;
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        currentSpan = getSpan(e);
        switch (rv.getId()) {
            case R.id.mediumRecyclerView: {
                if (currentSpan > 0) {
                    fullScreenGestureDirectory.onTouchEvent(e);
                } else {
                    galleryGestureDirectory.onTouchEvent(e);
                }
                break;
            }
            case R.id.smallRecyclerView: {
                galleryGestureDirectory.onTouchEvent(e);
                break;
            }
            default: {
                break;
            }

        }
    }

    private float getSpan(@NonNull MotionEvent e) {
        //TODO calculate span
        return 0;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
