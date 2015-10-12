package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.scene.rigger.AnimResources;
import com.davidstemmer.screenplay.scene.rigger.TweenRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/2/14.
 */
public class PopupRigger extends TweenRigger {
    private static final AnimResources params = new AnimResources();

    static {
        params.forwardIn    = R.anim.pop_in;
        params.backIn       = -1;
        params.backOut      = R.anim.pop_out;
        params.forwardOut   = -1;
    }

    public PopupRigger(Application context) {
        super(context, params);
    }

}
