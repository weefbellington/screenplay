package com.davidstemmer.screenplay.scene.transition;

import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.screenplay.scene.Scene;

import flow.Flow;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenTransition implements Scene.Transition {

    private final Params params;
    private final Context context;

    public static class Params {
        public Params() {}
        public int forwardIn;
        public int forwardOut;
        public int backIn;
        public int backOut;
    }

    public TweenTransition(Context context, Params params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public void transform(Flow.Direction direction, Flow.Callback callback, Scene nextScene, Scene previousScene) {
        int out = direction == Flow.Direction.FORWARD ? params.forwardOut : params.backOut;
        int in = direction == Flow.Direction.FORWARD ? params.forwardIn : params.backIn;

        FlowAnimationListener animationListener = new FlowAnimationListener(callback);
        if (out != -1) {
            Animation anim = loadAnimation(context, out);
            animationListener.addAnimation(anim);
            previousScene.getDirector().getView().setAnimation(anim);
        }
        if (in != -1) {
            Animation anim = loadAnimation(context, in);
            animationListener.addAnimation(anim);
            nextScene.getDirector().getView().setAnimation(anim);
        }

    }


}
