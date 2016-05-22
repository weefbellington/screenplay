package com.davidstemmer.screenplay.stage.rigger;

import android.animation.Animator;
import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.stage.Stage;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenRigger implements Stage.Rigger<Animation> {

    private final AnimResources params;
    private final Context context;


    public TweenRigger(Context context, AnimResources params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public Animation animation(Screenplay.Transition transition) {
        int resource = transition.lifecycleEvent == Screenplay.LifecycleEvent.TEARDOWN?
                params.getAnimationOut(transition.direction) :
                params.getAnimationIn(transition.direction);
        View.get
        return loadAnimation(context, resource).set;
    }

    @Override
    public Animator animator(Screenplay.Transition transition) {
        return null;
    }

    @Override
    public void applyAnimations(Screenplay.Transition transition) {

        int out = params.getAnimationOut(transition.direction);
        int in = params.getAnimationIn(transition.direction);

        TweenAnimationListener animationListener = new TweenAnimationListener(transition);

        if (in == -1 && out == -1) {
            transition.end();
        }

        if (out != -1) {
            Animation anim = loadAnimation(context, out);
            for (Stage outgoingStage : transition.outgoingStages) {
                animationListener.addAnimation(anim);
                outgoingStage.getView().startAnimation(anim);
            }
        }
        if (in != -1) {
            Animation anim = loadAnimation(context, in);
            for (Stage incomingStage : transition.incomingStages) {
                animationListener.addAnimation(anim);
                incomingStage.getView().startAnimation(anim);
            }
        }

    }

}
