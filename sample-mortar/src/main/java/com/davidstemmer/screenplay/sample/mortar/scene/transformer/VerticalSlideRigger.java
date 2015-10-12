package com.davidstemmer.screenplay.sample.mortar.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by weefbellington on 10/2/14.
 */

@Singleton
public class VerticalSlideRigger extends TweenRigger {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_up;
        params.backIn       = R.anim.slide_in_down;
        params.backOut      = R.anim.slide_out_down;
        params.forwardOut   = R.anim.slide_out_up;
    }

    @Inject
    public VerticalSlideRigger(Application context) {
        super(context, params);
    }
}