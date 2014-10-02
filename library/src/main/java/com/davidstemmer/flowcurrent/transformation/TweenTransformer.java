package com.davidstemmer.flowcurrent.transformation;

import android.content.Context;

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
    public void transform(Flow.Direction direction, Screen nextScreen, Screen previousScreen) {
        int out = direction == Flow.Direction.FORWARD ? params.forwardOut : params.backOut;
        int in = direction == Flow.Direction.FORWARD ? params.forwardIn : params.backIn;

        if (out != -1) {
            previousScreen.getDirector().getView().setAnimation(loadAnimation(context, out));
        }
        if (in != -1) {
            nextScreen.getDirector().getView().setAnimation(loadAnimation(context, in));
        }
    }


}
