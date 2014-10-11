package com.davidstemmer.screenplay.scene.transition;

import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.screenplay.scene.Scene;

import flow.Flow;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenTransformer implements Scene.Transformer, Scene.TransitionListener {

    private final Params params;
    private final Context context;

    public static class Params {
        public Params() {}
        public int forwardIn;
        public int forwardOut;
        public int backIn;
        public int backOut;
    }

    public TweenTransformer(Context context, Params params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public void transform(Scene.Transition transition) {

        int out = transition.direction == Flow.Direction.FORWARD ? params.forwardOut : params.backOut;
        int in = transition.direction == Flow.Direction.FORWARD ? params.forwardIn : params.backIn;

        FlowAnimationListener animationListener = new FlowAnimationListener(transition, this);
        if (out != -1) {
            Animation anim = loadAnimation(context, out);
            animationListener.addAnimation(anim);
            transition.previousScene.getDirector().getView().setAnimation(anim);
        }
        if (in != -1) {
            Animation anim = loadAnimation(context, in);
            animationListener.addAnimation(anim);
            transition.nextScene.getDirector().getView().setAnimation(anim);
        }
    }

    @Override
    public void onTransitionComplete(Scene.Transition transition) {
        transition.listener.onTransitionComplete(transition);
    }
}
