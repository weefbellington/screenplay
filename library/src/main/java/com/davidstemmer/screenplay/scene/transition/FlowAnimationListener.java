package com.davidstemmer.screenplay.scene.transition;

import android.view.animation.Animation;

import com.davidstemmer.screenplay.scene.Scene;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by weefbellington on 10/2/14.
 */
public class FlowAnimationListener implements Animation.AnimationListener {

    private final Scene.Transition transition;
    private final Scene.TransitionListener listener;

    private Set<Animation> animationSet = new HashSet<Animation>();

    public FlowAnimationListener(Scene.Transition transformation, Scene.TransitionListener listener) {
        this.transition = transformation;
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
            listener.onTransitionComplete(transition);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
