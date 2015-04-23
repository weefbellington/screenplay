package com.davidstemmer.screenplay.sample.mortar.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.scene.transformer.TweenTransformer;

import javax.inject.Inject;

/**
 * Created by weefbellington on 10/2/14.
 */
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