package com.israelferrer.smokeandmirrors.gesturedetector;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.israelferrer.smokeandmirrors.R;

public class ItemTouchListenerDispatcher implements RecyclerView.OnItemTouchListener {
    @NonNull
    private final ScaleGestureDetector galleryGestureDetector;
    @NonNull
    private final ScaleGestureDetector fullScreenGestureDetector;
    private float currentSpan;

    public ItemTouchListenerDispatcher(@NonNull Context context,
            @NonNull final ScaleGestureDetector fullScreenGestureDetector,
            @NonNull ScaleGestureDetector galleryGestureDetector) {
        this.galleryGestureDetector = galleryGestureDetector;
        this.fullScreenGestureDetector = fullScreenGestureDetector;
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
                if (currentSpan < 0) {
                    galleryGestureDetector.onTouchEvent(e);
                } else if (currentSpan == 0) {
                    final View childViewUnder = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childViewUnder != null) {
                        childViewUnder.performClick();
                    }
                }
                break;
            }
            case R.id.smallRecyclerView: {
                galleryGestureDetector.onTouchEvent(e);
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
