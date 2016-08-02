package com.israelferrer.smokeandmirrors.recyclerview;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class SmallRecyclerViewHolder extends RecyclerView.ViewHolder{
    private final ImageView imageView;

    public SmallRecyclerViewHolder(ImageView imageView, View.OnClickListener listener) {
        super(imageView);
        this.imageView = imageView;
        this.imageView.setOnClickListener(listener);
    }

    public void setImageResource(@DrawableRes int id) {
        imageView.setImageResource(id);
    }
}
