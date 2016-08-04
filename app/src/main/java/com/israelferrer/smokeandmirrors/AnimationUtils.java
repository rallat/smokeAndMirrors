package com.israelferrer.smokeandmirrors;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

public class AnimationUtils {
    public static void disableParentsClip(@NonNull View view) {
        while (view.getParent() != null && view.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            view = viewGroup;
        }
    }

    public static void enableParentsClip(@NonNull View view) {
        while (view.getParent() != null && view.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            viewGroup.setClipChildren(true);
            viewGroup.setClipToPadding(true);
            view = viewGroup;
        }
    }
}
