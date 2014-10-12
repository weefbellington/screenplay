package com.davidstemmer.warpzone.stage.transformer;

import android.view.animation.Animation;

import com.davidstemmer.warpzone.WarpListener;
import com.davidstemmer.warpzone.WarpPipe;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenAnimationListener implements Animation.AnimationListener {

    private final WarpPipe pipe;
    private final WarpListener listener;

    private Set<Animation> animationSet = new HashSet<Animation>();

    public TweenAnimationListener(WarpPipe pipe, WarpListener listener) {
        this.pipe = pipe;
        this.listener = listener;
    }

    public void addAnimation(Animation animation) {
        animationSet.add(animation);
        animation.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {}

    @Override
    public void onAnimationEnd(Animation animation) {
        animationSet.remove(animation);
        if (animationSet.isEmpty()) {
            listener.onWarpComplete(pipe);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
