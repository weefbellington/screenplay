package com.davidstemmer.warpzone.sample.stage.transformer;

import android.app.Application;

import com.davidstemmer.warpzone.sample.R;
import com.davidstemmer.warpzone.stage.transformer.TweenTransformer;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by weefbellington on 10/2/14.
 */

@Singleton
public class HorizontalSlideTransformer extends TweenTransformer {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_right;
        params.backIn       = R.anim.slide_in_left;
        params.backOut      = R.anim.slide_out_right;
        params.forwardOut   = R.anim.slide_out_left;
    }

    @Inject
    public HorizontalSlideTransformer(Application context) {
        super(context, params);
    }
}