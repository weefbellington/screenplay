package com.davidstemmer.warpzone.stage.transformer;

import android.content.Context;
import android.view.animation.Animation;

import com.davidstemmer.warpzone.WarpPipe;
import com.davidstemmer.warpzone.flow.WarpZone;
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
    public void applyAnimations(WarpPipe pipe, WarpZone warpZone) {

        int out = pipe.direction == Flow.Direction.FORWARD ? params.forwardOut : params.backOut;
        int in = pipe.direction == Flow.Direction.FORWARD ? params.forwardIn : params.backIn;

        TweenAnimationListener animationListener = new TweenAnimationListener(pipe, warpZone);
        if (out != -1 && pipe.previousStage != null) {
            Animation anim = loadAnimation(context, out);
            animationListener.addAnimation(anim);
            pipe.previousStage.getDirector().getView().startAnimation(anim);
        }
        if (in != -1 && pipe.nextStage != null) {
            Animation anim = loadAnimation(context, in);
            animationListener.addAnimation(anim);
            pipe.nextStage.getDirector().getView().startAnimation(anim);
        }

    }
}
