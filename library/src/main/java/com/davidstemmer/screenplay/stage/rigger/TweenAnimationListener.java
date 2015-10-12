package com.davidstemmer.screenplay.stage.rigger;

import android.view.animation.Animation;

import com.davidstemmer.screenplay.Screenplay;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenAnimationListener implements Animation.AnimationListener {

    private final Screenplay.Transition transition;

    private Set<Animation> animationSet = new HashSet<Animation>();

    public TweenAnimationListener(Screenplay.Transition transition) {
        this.transition = transition;
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
            transition.end();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
