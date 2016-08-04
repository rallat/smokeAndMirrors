package com.israelferrer.smokeandmirrors.gesturedetector;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;

public class SmallMediumGestureDetector implements ScaleGestureDetector.OnScaleGestureListener {
    private static final float TRANSITION_BOUNDARY = 1.09f;
    private static final float SMALL_MAX_SCALE_FACTOR = 1.25f;

    @NonNull private final RecyclerView smallRecyclerView;
    @NonNull private final RecyclerView mediumRecyclerView;

    private float scaleFactor;
    private float scaleFactorMedium;
    public boolean isInProgress;

    public SmallMediumGestureDetector(@NonNull RecyclerView smallRecyclerView, @NonNull RecyclerView mediumRecyclerView) {
        this.smallRecyclerView = smallRecyclerView;
        this.mediumRecyclerView = mediumRecyclerView;
    }

    @Override
    public boolean onScale(@NonNull ScaleGestureDetector detector) {
        if (gestureTolerance(detector)) {
            //small
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(1f, Math.min(scaleFactor, SMALL_MAX_SCALE_FACTOR));
            isInProgress = scaleFactor > 1;
            smallRecyclerView.setScaleX(scaleFactor);
            smallRecyclerView.setScaleY(scaleFactor);

            //medium
            scaleFactorMedium *= detector.getScaleFactor();
            scaleFactorMedium = Math.max(0.8f, Math.min(scaleFactorMedium, 1f));
            mediumRecyclerView.setScaleX(scaleFactorMedium);
            mediumRecyclerView.setScaleY(scaleFactorMedium);

            //alpha
            mediumRecyclerView.setAlpha((scaleFactor - 1) / (0.25f));
            smallRecyclerView.setAlpha(1 - (scaleFactor - 1) / (0.25f));

        }
        return true;
    }

    private boolean gestureTolerance(@NonNull ScaleGestureDetector detector) {
        return detector.getCurrentSpan() - detector.getPreviousSpan() > 7 ||
                detector.getCurrentSpan() - detector.getPreviousSpan() < -7;
    }

    @Override
    public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
        mediumRecyclerView.setVisibility(View.VISIBLE);
        smallRecyclerView.setVisibility(View.VISIBLE);
        return true;
    }

    @Override
    public void onScaleEnd(@NonNull ScaleGestureDetector detector) {
        if (IsScaleInProgress()) {
            if (scaleFactor < TRANSITION_BOUNDARY) {
                transitionFromMediumToSmall();
                scaleFactor = 0;
                scaleFactorMedium = 0;
            } else {
                transitionFromSmallToMedium();
                scaleFactor = SMALL_MAX_SCALE_FACTOR;
                scaleFactorMedium = 1f;
            }
        }
    }

    private boolean IsScaleInProgress() {
        return scaleFactor < SMALL_MAX_SCALE_FACTOR && scaleFactor > 1f;
    }

    private void transitionFromSmallToMedium() {
        Log.d("Scale", "transitionFromSmallToMedium: ");
        mediumRecyclerView.animate().scaleX(1f).scaleY(1f).alpha(1f).withStartAction(new Runnable() {
            @Override
            public void run() {
                smallRecyclerView.animate().scaleY(SMALL_MAX_SCALE_FACTOR).scaleX(SMALL_MAX_SCALE_FACTOR).alpha(0f)
                        .start();
            }
        }).withEndAction(new Runnable() {
            @Override
            public void run() {
//                smallRecyclerView.setVisibility(View.INVISIBLE);
            }
        }).start();
    }

    private void transitionFromMediumToSmall() {
        Log.d("Scale", "transitionFromMediumToSmall: ");
        smallRecyclerView.animate().scaleX(1f).scaleY(1f).alpha(1f).withStartAction(new Runnable() {
            @Override
            public void run() {
                mediumRecyclerView.animate().scaleX(0.8f).scaleY(0.8f).alpha(0).start();
            }
        }).withEndAction(new Runnable() {
            @Override
            public void run() {
//                mediumRecyclerView.setVisibility(View.INVISIBLE);
            }
        }).start();
    }

}
