package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.scene.rigger.AnimResources;
import com.davidstemmer.screenplay.scene.rigger.TweenRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/2/14.
 */
public class VerticalSlideRigger extends TweenRigger {

    private static final AnimResources params = new AnimResources();

    static {
        params.forwardIn    = R.anim.slide_in_up;
        params.backIn       = R.anim.slide_in_down;
        params.backOut      = R.anim.slide_out_down;
        params.forwardOut   = R.anim.slide_out_up;
    }

    public VerticalSlideRigger(Application context) {
        super(context, params);
    }
}