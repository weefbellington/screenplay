package com.davidstemmer.screenplay.stage.rigger;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;

import android.view.animation.Animation;
import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weefbellington on 10/16/14.
 */
public class XmlAnimatorRigger implements Stage.Rigger {

    private final AnimResources params;
    private final Context context;

    public XmlAnimatorRigger(Context context, AnimResources params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public Animator animator(Stage stage, Screenplay.Direction direction, Screenplay.LifecycleEvent event) {
        final int resource = event == Screenplay.LifecycleEvent.TEARDOWN?
                params.getAnimationOut(direction) :
                params.getAnimationIn(direction);
        Animator animator = AnimatorInflater.loadAnimator(context, resource);
        animator.setTarget(stage.getView());
        return animator;
    }

    @Override
    public Animation animation(Stage stage, Screenplay.Direction direction, Screenplay.LifecycleEvent event) {
        return null;
    }

}
