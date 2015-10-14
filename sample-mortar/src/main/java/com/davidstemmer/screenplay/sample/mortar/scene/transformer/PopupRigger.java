package com.davidstemmer.screenplay.sample.mortar.scene.transformer;

import android.app.Application;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.stage.rigger.TweenRigger;

import javax.inject.Inject;

/**
 * Created by weefbellington on 10/2/14.
 */
public class PopupRigger extends TweenRigger {
    private static final TweenRigger.Params params = new TweenRigger.Params();

    static {
        params.forwardIn    = R.anim.pop_in;
        params.backIn       = -1;
        params.backOut      = R.anim.pop_out;
        params.forwardOut   = -1;
    }

    @Inject
    public PopupRigger(Application context) {
        super(context, params);
    }

}
