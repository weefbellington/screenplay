package com.davidstemmer.warpzone.stage.transformer;

import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.warpzone.WarpListener;
import com.davidstemmer.warpzone.WarpPipe;
import com.davidstemmer.warpzone.stage.Stage;

import flow.Flow;

import static android.view.animation.AnimationUtils.loadAnimation;

/**
 * Created by weefbellington on 10/2/14.
 */
public class TweenTransformer implements Stage.Transformer {

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
    public void transform(WarpPipe transition, WarpListener warpListener) {

        int out = transition.direction == Flow.Direction.FORWARD ? params.forwardOut : params.backOut;
        int in = transition.direction == Flow.Direction.FORWARD ? params.forwardIn : params.backIn;

        TweenAnimationListener animationListener = new TweenAnimationListener(transition, warpListener);
        if (out != -1) {
            Animation anim = loadAnimation(context, out);
            animationListener.addAnimation(anim);
            transition.previousStage.getDirector().getView().setAnimation(anim);
        }
        if (in != -1) {
            Animation anim = loadAnimation(context, in);
            animationListener.addAnimation(anim);
            transition.nextStage.getDirector().getView().setAnimation(anim);
        }
    }
}
