package com.davidstemmer.screenplay.sample.scene.transition;

import android.app.Application;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.scene.transition.TweenTransition;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by weefbellington on 10/7/14.
 */
@Singleton
public class NavigationDrawerTransition extends TweenTransition {

    private static final Params params = new Params();

    static {
        params.forwardIn    = R.anim.slide_in_left;
        params.backIn       = -1;
        params.backOut      = R.anim.slide_out_left;
        params.forwardOut   = -1;
    }

    @Inject
    public NavigationDrawerTransition(Application context) {
        super(context, params);
    }
}
