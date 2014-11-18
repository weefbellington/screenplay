package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.scene.transformer.TweenTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/2/14.
 */
public class PopupTransformer extends TweenTransformer {
    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.pop_in;
        params.backIn       = -1;
        params.backOut      = R.anim.pop_out;
        params.forwardOut   = -1;
    }

    public PopupTransformer(Application context) {
        super(context, params);
    }

}
