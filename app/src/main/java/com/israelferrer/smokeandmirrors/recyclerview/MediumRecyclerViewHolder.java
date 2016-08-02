package com.israelferrer.smokeandmirrors.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class MediumRecyclerViewHolder extends RecyclerView.ViewHolder{
    private final ImageView imageView;

    public MediumRecyclerViewHolder(ImageView itemView) {
        super(itemView);
        imageView = itemView;
    }

    public void setImageResource(int imageResource) {
        imageView.setImageResource(imageResource);
    }
}
