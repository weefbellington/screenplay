package com.davidstemmer.screenplay.scene.transformer;

import android.view.animation.Animation;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenAnimationListener implements Animation.AnimationListener {

    private final SceneCut cut;
    private final Screenplay listener;

    private Set<Animation> animationSet = new HashSet<Animation>();

    public TweenAnimationListener(SceneCut cut, Screenplay listener) {
        this.cut = cut;
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
            listener.endCut(cut);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}
}
