package com.davidstemmer.flowcurrent.transformer;

import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.flowcurrent.screen.Screen;

import flow.Flow;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenTransformer implements Screen.Transformer {

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
    public void transform(Flow.Direction direction, Flow.Callback callback, Screen nextScreen, Screen previousScreen) {
        int out = direction == Flow.Direction.FORWARD ? params.forwardOut : params.backOut;
        int in = direction == Flow.Direction.FORWARD ? params.forwardIn : params.backIn;

        FlowAnimationListener animationListener = new FlowAnimationListener(callback);
        if (out != -1) {
            Animation anim = loadAnimation(context, out);
            animationListener.addAnimation(anim);
            previousScreen.getDirector().getView().setAnimation(anim);
        }
        if (in != -1) {
            Animation anim = loadAnimation(context, in);
            animationListener.addAnimation(anim);
            nextScreen.getDirector().getView().setAnimation(anim);
        }

    }


}
