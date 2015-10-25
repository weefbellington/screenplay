package com.davidstemmer.screenplay.stage.rigger;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;

import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weefbellington on 10/16/14.
 */
public class AnimatorRigger implements Stage.Rigger {

    private final AnimResources params;
    private final Context context;

    public AnimatorRigger(Context context, AnimResources params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public void applyAnimations(Screenplay.Transition transition) {

        int out = params.getAnimationOut(transition.direction);
        int in = params.getAnimationIn(transition.direction);

        AnimatorSet animSet = new AnimatorSet();
        List<Animator> animators = new ArrayList<Animator>();
        if (out != -1) {
            for (Stage outgoingStage : transition.outgoingStages) {
                Animator animator = AnimatorInflater.loadAnimator(context, out);
                animator.setTarget(outgoingStage);
                animators.add(animator);
            }
        }
        if (in != -1) {
            for (Stage incomingStage : transition.incomingStages) {
                Animator animator = AnimatorInflater.loadAnimator(context, in);
                animator.setTarget(incomingStage);
                animators.add(animator);
            }
        }
        animSet.addListener(new Listener(transition));
        animSet.playTogether(animators);
    }

    private class Listener implements Animator.AnimatorListener {

        private final Screenplay.Transition transition;

        private Listener(Screenplay.Transition transition) {
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
