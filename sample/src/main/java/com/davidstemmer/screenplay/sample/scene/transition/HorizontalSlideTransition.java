package com.davidstemmer.screenplay.sample.scene.transition;

import android.app.Application;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.scene.transition.TweenTransition;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by weefbellington on 10/2/14.
 */

@Singleton
public class HorizontalSlideTransition extends TweenTransition {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_right;
        params.backIn       = R.anim.slide_in_left;
        params.backOut      = R.anim.slide_out_right;
        params.forwardOut   = R.anim.slide_out_left;
    }

    @Inject
    public HorizontalSlideTransition(Application context) {
        super(context, params);
    }
}