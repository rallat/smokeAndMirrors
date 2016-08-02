package com.israelferrer.smokeandmirrors;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    }

    private void setSmallAdapter(final View.OnClickListener listener) {
        smallRecyclerView.setAdapter(new RecyclerView.Adapter<SmallRecyclerViewHolder>() {
            @Override
            public SmallRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.small_item, parent, false);
                return new SmallRecyclerViewHolder(imageView, listener);
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
        }).withEndAction(new Runnable() {
            @Override
            public void run() {
                smallRecyclerView.setVisibility(View.INVISIBLE);
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
