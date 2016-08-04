package com.israelferrer.smokeandmirrors;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.israelferrer.smokeandmirrors.gesturedetector.ItemTouchListenerDispatcher;
import com.israelferrer.smokeandmirrors.gesturedetector.SmallMediumGestureDetector;
import com.israelferrer.smokeandmirrors.view.MediumRecyclerViewHolder;
import com.israelferrer.smokeandmirrors.view.SmallRecyclerViewHolder;

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
    private RecyclerView.OnItemTouchListener onItemTouchListener;
    private FrameLayout containerView;
    private FrameLayout fullScreenContainer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_photos);
        generateCollection();
        smallRecyclerView = (RecyclerView) findViewById(R.id.smallRecyclerView);
        mediumRecyclerView = (RecyclerView) findViewById(R.id.mediumRecyclerView);
        containerView = (FrameLayout) findViewById(R.id.container);
        fullScreenContainer = (FrameLayout) findViewById(R.id.fullScreenImageContainer);
        smallGridLayoutManager = new GridLayoutManager(this, SPAN_SMALL);
        smallRecyclerView.setLayoutManager(smallGridLayoutManager);
        mediumGridLayoutManager = new GridLayoutManager(this, SPAN_MEDIUM);
        mediumRecyclerView.setLayoutManager(mediumGridLayoutManager);
        setSmallAdapter();
        setMediumAdapter();
        smallRecyclerView.setPivotX(0);
        smallRecyclerView.setPivotY(0);
        mediumRecyclerView.setPivotY(0);
        mediumRecyclerView.setPivotX(0);

        final ScaleGestureDetector scaleGestureDetector =
                new ScaleGestureDetector(this, new SmallMediumGestureDetector(smallRecyclerView, mediumRecyclerView));
        onItemTouchListener = new ItemTouchListenerDispatcher(this, scaleGestureDetector, scaleGestureDetector);
        smallRecyclerView.addOnItemTouchListener(onItemTouchListener);
        mediumRecyclerView.addOnItemTouchListener(onItemTouchListener);
    }

    private void setMediumAdapter() {
        mediumAdapter = new RecyclerView.Adapter<MediumRecyclerViewHolder>() {
            @Override
            public MediumRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.medium_item, parent, false);
                return new MediumRecyclerViewHolder(imageView, new View.OnClickListener() {

                    @Override
                    public void onClick(@NonNull final View itemView) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            ViewCompat.setElevation(itemView, 1);
                        }
                        final float originX = itemView.getX();
                        final float originY = itemView.getY();
                        AnimationUtils.disableParentsClip(itemView);
                        float parentCenterX = (containerView.getWidth() + containerView.getX()) / 2;
                        float parentCenterY = (containerView.getHeight() + containerView.getY()) / 2;
                        final int deltaScale = containerView.getMeasuredWidth() / itemView.getMeasuredWidth();
                        itemView.animate().x(parentCenterX - itemView.getWidth() / 2)
                                .y(parentCenterY - itemView.getHeight() / 2)
                                .scaleX(deltaScale).scaleY(deltaScale).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                fullScreenContainer.setVisibility(View.VISIBLE);
                                fullScreenContainer.setBackgroundColor(
                                        getResources().getColor(android.R.color.black));
                                fullScreenContainer.getOverlay().add(itemView);
                                fullScreenContainer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        fullScreenContainer.setBackgroundColor(
                                                getResources().getColor(android.R.color.transparent));
                                        itemView.animate().x(originX).y(originY).scaleY(1).scaleX(1).withEndAction(
                                                new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        fullScreenContainer.setVisibility(View.GONE);
                                                        fullScreenContainer.getOverlay().remove(itemView);
                                                    }
                                                }).start();
                                    }
                                });
                            }
                        });
                    }
                });
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

    private void setSmallAdapter() {
        smallRecyclerView.setAdapter(new RecyclerView.Adapter<SmallRecyclerViewHolder>() {
            @Override
            public SmallRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.small_item, parent, false);
                return new SmallRecyclerViewHolder(imageView);
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
