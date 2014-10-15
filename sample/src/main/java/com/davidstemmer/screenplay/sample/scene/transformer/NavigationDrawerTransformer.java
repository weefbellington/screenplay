package com.davidstemmer.screenplay.sample.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.stage.transformer.TweenTransformer;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by weefbellington on 10/7/14.
 */
@Singleton
public class NavigationDrawerTransformer extends TweenTransformer {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_left;
        params.backIn       = -1;
        params.backOut      = R.anim.slide_out_left;
        params.forwardOut   = -1;
    }

    @Inject
    public NavigationDrawerTransformer(Application context) {
        super(context, params);
    }

}