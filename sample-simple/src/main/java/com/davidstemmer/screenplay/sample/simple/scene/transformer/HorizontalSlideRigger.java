package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.scene.rigger.AnimResources;
import com.davidstemmer.screenplay.scene.rigger.TweenRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/2/14.
 */
public class HorizontalSlideRigger extends TweenRigger {

    private static final AnimResources params = new AnimResources();

    static {
        params.forwardIn    = R.anim.slide_in_right;
        params.backIn       = R.anim.slide_in_left;
        params.backOut      = R.anim.slide_out_right;
        params.forwardOut   = R.anim.slide_out_left;
    }

    public HorizontalSlideRigger(Application context) {
        super(context, params);
    }
}