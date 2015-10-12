package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.stage.rigger.AnimResources;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/7/14.
 */
public class ActionDrawerRigger extends TweenRigger {

    private static final AnimResources params = new AnimResources();

    static {
        params.forwardIn    = R.anim.slide_in_up;
        params.backIn       = -1;
        params.backOut      = R.anim.slide_out_down;
        params.forwardOut   = -1;
    }

    public ActionDrawerRigger(Application context) {
        super(context, params);
    }
}
