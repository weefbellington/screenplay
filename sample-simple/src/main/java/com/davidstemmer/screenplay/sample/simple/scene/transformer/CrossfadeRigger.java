package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.stage.rigger.AnimResources;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/28/14.
 */
public class CrossfadeRigger extends TweenRigger {
    private static final AnimResources params = new AnimResources();

    static {
        params.forwardIn    = R.anim.fade_in;
        params.backIn       = R.anim.fade_in;
        params.backOut      = R.anim.fade_out;
        params.forwardOut   = R.anim.fade_out;
        params.replaceIn    = R.anim.fade_in;
        params.replaceOut   = R.anim.fade_out;
    }

    public CrossfadeRigger(Application context) {
        super(context, params);
    }
}
