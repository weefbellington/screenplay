package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.scene.transformer.TweenTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/7/14.
 */
public class NavigationDrawerTransformer extends TweenTransformer {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_left_drawer;
        params.backIn       = -1;
        params.backOut      = R.anim.slide_out_left_drawer;
        params.forwardOut   = -1;
    }

    public NavigationDrawerTransformer(Application context) {
        super(context, params);
    }
}
