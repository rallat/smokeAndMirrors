package com.israelferrer.smokeandmirrors.view;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class SmallRecyclerViewHolder extends RecyclerView.ViewHolder{
    private final ImageView imageView;

    public SmallRecyclerViewHolder(ImageView imageView) {
        super(imageView);
        this.imageView = imageView;
    }

    public void setImageResource(@DrawableRes int id) {
        imageView.setImageResource(id);
    }
}
