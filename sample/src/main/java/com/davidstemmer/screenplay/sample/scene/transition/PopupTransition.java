package com.davidstemmer.screenplay.sample.scene.transition;

import android.app.Application;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.scene.transition.TweenTransition;

import javax.inject.Inject;

/**
 * Created by weefbellington on 10/2/14.
 */
public class PopupTransition extends TweenTransition {
    private static final TweenTransition.Params params = new TweenTransition.Params();

    static {
        params.forwardIn    = R.anim.pop_in;
        params.backIn       = -1;
        params.backOut      = R.anim.pop_out;
        params.forwardOut   = -1;
    }

    @Inject
    public PopupTransition(Application context) {
        super(context, params);
    }

}
