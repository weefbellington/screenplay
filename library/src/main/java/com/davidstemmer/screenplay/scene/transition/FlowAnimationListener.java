package com.davidstemmer.screenplay.scene.transition;

import android.view.animation.Animation;

import java.util.HashSet;
import java.util.Set;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class FlowAnimationListener implements Animation.AnimationListener {

    private final Flow.Callback callback;

    private Set<Animation> animationSet = new HashSet<Animation>();

    public FlowAnimationListener(Flow.Callback callback) {
        this.callback = callback;
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
            callback.onComplete();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
