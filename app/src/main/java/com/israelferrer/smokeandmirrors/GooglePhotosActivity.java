package com.israelferrer.smokeandmirrors;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.israelferrer.smokeandmirrors.recyclerview.MediumRecyclerViewHolder;
import com.israelferrer.smokeandmirrors.recyclerview.SmallRecyclerViewHolder;

import java.util.ArrayList;

public class GooglePhotosActivity extends AppCompatActivity {
    private static final int SPAN_SMALL = 4;
    private static final int SPAN_MEDIUM = 3;
    private static final Photo PROFILE = new Photo(R.drawable.profile);
    private static final Photo SATURN = new Photo(R.drawable.saturn);
    private static final Photo URANUS = new Photo(R.drawable.uranus);
    private static final Photo[] PHOTOS = new Photo[] {
            PROFILE,
            SATURN,
            URANUS
    };
    private RecyclerView smallRecyclerView;
    private RecyclerView mediumRecyclerView;
    private GridLayoutManager smallGridLayoutManager;
    private GridLayoutManager mediumGridLayoutManager;
    private ArrayList<Photo> itemCollection;
    private RecyclerView.Adapter mediumAdapter;
    private float scaleFactor;
    private float scaleFactorMedium;
    private float alphaFactorMedium;
    private boolean fromSmallToMedium =true;
    private float lastScaleFactor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_photos);
        generateCollection();
        smallRecyclerView = (RecyclerView) findViewById(R.id.smallRecyclerView);
        mediumRecyclerView = (RecyclerView) findViewById(R.id.mediumRecyclerView);
        smallGridLayoutManager = new GridLayoutManager(this, SPAN_SMALL);
        smallRecyclerView.setLayoutManager(smallGridLayoutManager);
        mediumGridLayoutManager = new GridLayoutManager(this, SPAN_MEDIUM);
        mediumRecyclerView.setLayoutManager(mediumGridLayoutManager);
        final View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transition();
            }
        };
        setSmallAdapter(listener);
        setMediumAdapter();
        smallRecyclerView.setPivotX(0);
        smallRecyclerView.setPivotY(0);
        mediumRecyclerView.setPivotY(0);
        mediumRecyclerView.setPivotX(0);

        final ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(this,
                new ScaleGestureDetector.OnScaleGestureListener() {
                    public boolean isInProgress;

                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        if (gestureTolerance(detector)) {
                            //small
                            scaleFactor *= detector.getScaleFactor();
                            scaleFactor = Math.max(1f, Math.min(scaleFactor, 1.25f));
                            isInProgress = scaleFactor > 1;
                            smallRecyclerView.setScaleX(scaleFactor);
                            smallRecyclerView.setScaleY(scaleFactor);

                            //medium
                            scaleFactorMedium *= detector.getScaleFactor();
                            scaleFactorMedium = Math.max(0.8f, Math.min(scaleFactorMedium, 1f));
                            mediumRecyclerView.setScaleX(scaleFactorMedium);
                            mediumRecyclerView.setScaleY(scaleFactorMedium);

                            //alpha
                            mediumRecyclerView.setAlpha((scaleFactor-1)/(0.25f));
                            smallRecyclerView.setAlpha(1-(scaleFactor-1)/(0.25f));

                        }
                        return true;
                    }

                    private boolean gestureTolerance(ScaleGestureDetector detector) {
                        return detector.getCurrentSpan() - detector.getPreviousSpan() > 10 ||
                                detector.getCurrentSpan() - detector.getPreviousSpan() < -10;
                    }

                    @Override
                    public boolean onScaleBegin(ScaleGestureDetector detector) {
                        return true;
                    }

                    @Override
                    public void onScaleEnd(ScaleGestureDetector detector) {
//                        if (scaleFactor < 1.11f) {
//                            scaleFactor = 0;
//                            smallRecyclerView.animate().scaleX(1f).scaleY(1f).alpha(1f).withStartAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    mediumRecyclerView.animate().alpha(0);
//                                    mediumRecyclerView.setScaleX(1f);
//                                    mediumRecyclerView.setScaleY(1f);
//                                }
//                            }).start();
//                        } else {
//                            mediumRecyclerView.animate().scaleX(1f).scaleY(1f).alpha(1f).withStartAction(new Runnable() {
//                                @Override
//                                public void run() {
//                                    smallRecyclerView.animate().alpha(0);
//                                    smallRecyclerView.setScaleX(1f);
//                                    smallRecyclerView.setScaleY(1f);
//                                }
//                            }).start();
//                        }
                    }
                });

        final GestureDetector tapGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        transition();
                        return true;
                    }
                });
        smallRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                scaleGestureDetector.onTouchEvent(e);
                return tapGestureDetector.onTouchEvent(e) || scaleGestureDetector.isInProgress();
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                if (scaleGestureDetector.isInProgress()) {
                    scaleGestureDetector.onTouchEvent(e);
                } else {
                    tapGestureDetector.onTouchEvent(e);
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private void setMediumAdapter() {
        mediumAdapter = new RecyclerView.Adapter<MediumRecyclerViewHolder>() {
            @Override
            public MediumRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.medium_item, parent, false);
                return new MediumRecyclerViewHolder(imageView);
            }

            @Override
            public void onBindViewHolder(MediumRecyclerViewHolder holder, int position) {
                holder.setImageResource(itemCollection.get(position).resId);
            }

            @Override
            public int getItemCount() {
                return itemCollection.size();
            }
        };
        mediumRecyclerView.setAdapter(mediumAdapter);
        mediumRecyclerView.setAlpha(0);
    }

    private void setSmallAdapter(final View.OnClickListener listener) {
        smallRecyclerView.setAdapter(new RecyclerView.Adapter<SmallRecyclerViewHolder>() {
            @Override
            public SmallRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.small_item, parent, false);
                return new SmallRecyclerViewHolder(imageView, null);
            }

            @Override
            public void onBindViewHolder(SmallRecyclerViewHolder holder, int position) {
                holder.setImageResource(itemCollection.get(position).resId);
            }

            @Override
            public int getItemCount() {
                return itemCollection.size();
            }
        });
    }

    private void transition() {
        View item = smallGridLayoutManager.findViewByPosition(smallGridLayoutManager.findFirstVisibleItemPosition());
        smallRecyclerView.setPivotX(0);
        smallRecyclerView.setPivotY(0);
        smallRecyclerView.animate().scaleX(1.25f).scaleY(1.25f).alpha(0).withStartAction(new Runnable() {
            @Override
            public void run() {
                mediumRecyclerView.setPivotY(0);
                mediumRecyclerView.setPivotX(0);
                mediumRecyclerView.setAlpha(0);
                mediumRecyclerView.setAdapter(mediumAdapter);
                mediumRecyclerView.setScaleX(0.8f);
                mediumRecyclerView.setScaleY(0.8f);
                mediumRecyclerView.animate().scaleX(1).scaleY(1).setDuration(700).alpha(1).start();
            }
        }).setDuration(700).start();
    }

    private void generateCollection() {
        itemCollection = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            for (Photo photo : PHOTOS) {
                itemCollection.add(photo);
                i++;
            }
        }
    }
}
