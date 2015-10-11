package com.davidstemmer.screenplay.scene.transformer;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.scene.Scene;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weefbellington on 10/16/14.
 */
public class AnimatorTransformer implements Scene.Transformer {

    private final AnimResources params;
    private final Context context;

    public AnimatorTransformer(Context context, AnimResources params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public void applyAnimations(SceneCut transition) {

        int out = params.getAnimationOut(transition.direction);
        int in = params.getAnimationIn(transition.direction);

        AnimatorSet animSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<Animator>();
        if (out != -1) {
            for (Scene outgoingScene : transition.outgoingScenes) {
                Animator animator = AnimatorInflater.loadAnimator(context, out);
                animator.setTarget(outgoingScene);
                animators.add(animator);
            }
        }
        if (in != -1) {
            for (Scene incomingScene : transition.incomingScenes) {
                Animator animator = AnimatorInflater.loadAnimator(context, in);
                animator.setTarget(incomingScene);
                animators.add(animator);
            }
        }
        animSet.addListener(new Listener(transition));
        animSet.playTogether(animators);
    }

    private class Listener implements Animator.AnimatorListener {

        private final SceneCut transition;

        private Listener(SceneCut transition) {
            this.transition = transition;
        }

        @Override
        public void onAnimationStart(Animator animation) {}

        @Override
        public void onAnimationEnd(Animator animation) {
            transition.end();
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    }

}
