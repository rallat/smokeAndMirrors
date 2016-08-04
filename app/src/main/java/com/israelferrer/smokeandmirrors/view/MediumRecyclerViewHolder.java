package com.israelferrer.smokeandmirrors.view;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class MediumRecyclerViewHolder extends RecyclerView.ViewHolder {
    private final ImageView imageView;

    public MediumRecyclerViewHolder(@NonNull ImageView itemView, @Nullable View.OnClickListener listener) {
        super(itemView);
        imageView = itemView;
        imageView.setOnClickListener(listener);
    }

    public void setImageResource(int imageResource) {
        imageView.setImageResource(imageResource);
    }
}
