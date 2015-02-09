package com.davidstemmer.screenplay.scene.transformer;

import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.scene.Scene;

import flow.Flow;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenTransformer implements Scene.Transformer {

    private final Params params;
    private final Context context;

    public static class Params {
        public Params() {}
        public int forwardIn = -1;
        public int forwardOut = -1;
        public int backIn = -1;
        public int backOut = -1;
    }

    public TweenTransformer(Context context, Params params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public void applyAnimations(SceneCut cut, Screenplay screenplay) {

        int out = cut.direction == Flow.Direction.BACKWARD ? params.backOut : params.forwardOut;
        int in = cut.direction == Flow.Direction.BACKWARD ? params.backIn : params.forwardIn;

        TweenAnimationListener animationListener = new TweenAnimationListener(cut, screenplay);
        if (out != -1) {
            Animation anim = loadAnimation(context, out);
            for (Scene outgoingScene : cut.outgoingScenes) {
                animationListener.addAnimation(anim);
                outgoingScene.getView().startAnimation(anim);
            }
        }
        if (in != -1) {
            Animation anim = loadAnimation(context, in);
            for (Scene incomingScene : cut.incomingScenes) {
                animationListener.addAnimation(anim);
                incomingScene.getView().startAnimation(anim);
            }
        }

    }
}
