package com.davidstemmer.screenplay.scene.transformer;

import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.scene.Scene;

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
        public int replaceIn = -1;
        public int replaceOut = -1;
    }

    public TweenTransformer(Context context, Params params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public void applyAnimations(SceneCut cut, Screenplay screenplay) {

        int out = getAnimationOut(cut.direction, params);
        int in = getAnimationIn(cut.direction, params);

        TweenAnimationListener animationListener = new TweenAnimationListener(cut, screenplay);

        if (in == -1 && out == -1) {
            screenplay.endCut(cut);
        }

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

    public int getAnimationIn(Screenplay.Direction direction, Params params) {
        switch (direction) {
            case FORWARD:
                return params.forwardIn;
            case BACKWARD:
                return params.backIn;
            case REPLACE:
                return params.replaceIn;
            default:
                return -1;
        }
    }

    public int getAnimationOut(Screenplay.Direction direction, Params params) {
        switch (direction) {
            case FORWARD:
                return params.forwardOut;
            case BACKWARD:
                return params.backOut;
            case REPLACE:
                return params.replaceOut;
            default:
                return -1;
        }
    }

}
