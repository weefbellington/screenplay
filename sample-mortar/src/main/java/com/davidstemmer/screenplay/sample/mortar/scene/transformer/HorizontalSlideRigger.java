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
public class HorizontalSlideRigger extends TweenRigger {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_right;
        params.backIn       = R.anim.slide_in_left;
        params.backOut      = R.anim.slide_out_right;
        params.forwardOut   = R.anim.slide_out_left;
    }

    @Inject
    public HorizontalSlideRigger(Application context) {
        super(context, params);
    }
}