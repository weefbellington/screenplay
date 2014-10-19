package com.davidstemmer.screenplay.sample.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.stage.transformer.TweenTransformer;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by weefbellington on 10/2/14.
 */

@Singleton
public class VerticalSlideTransformer extends TweenTransformer {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_up;
        params.backIn       = R.anim.slide_in_down;
        params.backOut      = R.anim.slide_out_down;
        params.forwardOut   = R.anim.slide_out_up;
    }

    @Inject
    public VerticalSlideTransformer(Application context) {
        super(context, params);
    }
}