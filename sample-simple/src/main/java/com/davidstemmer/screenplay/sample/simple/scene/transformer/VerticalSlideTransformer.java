package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.scene.transformer.TweenTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

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

    public VerticalSlideTransformer(Application context) {
        super(context, params);
    }
}