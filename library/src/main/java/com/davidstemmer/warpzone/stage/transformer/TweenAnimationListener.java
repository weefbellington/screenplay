package com.davidstemmer.warpzone.stage.transformer;

import android.view.animation.Animation;

import com.davidstemmer.warpzone.WarpPipe;
import com.davidstemmer.warpzone.flow.WarpZone;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenAnimationListener implements Animation.AnimationListener {

    private final WarpPipe pipe;
    private final WarpZone listener;

    private Set<Animation> animationSet = new HashSet<Animation>();

    public TweenAnimationListener(WarpPipe pipe, WarpZone listener) {
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
            listener.notifyWarpComplete(pipe);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
