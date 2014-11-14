package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.scene.transformer.TweenTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/28/14.
 */
public class CrossfadeTransformer extends TweenTransformer {
    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.fade_in;
        params.backIn       = R.anim.fade_in;
        params.backOut      = R.anim.fade_out;
        params.forwardOut   = R.anim.fade_out;
    }

    public CrossfadeTransformer(Application context) {
        super(context, params);
    }
}
