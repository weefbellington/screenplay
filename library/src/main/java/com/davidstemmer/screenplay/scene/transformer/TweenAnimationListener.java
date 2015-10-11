package com.davidstemmer.screenplay.scene.transformer;

import android.view.animation.Animation;

import com.davidstemmer.screenplay.SceneCut;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenAnimationListener implements Animation.AnimationListener {

    private final SceneCut stageTransition;

    private Set<Animation> animationSet = new HashSet<Animation>();

    public TweenAnimationListener(SceneCut transition) {
        this.stageTransition = transition;
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
            stageTransition.end();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
