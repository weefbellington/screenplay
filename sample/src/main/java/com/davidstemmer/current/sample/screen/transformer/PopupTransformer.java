package com.davidstemmer.current.sample.screen.transformer;

import android.app.Application;

import com.davidstemmer.current.sample.R;
import com.davidstemmer.flowcurrent.transformer.TweenTransformer;

import javax.inject.Inject;

/**
 * Created by weefbellington on 10/2/14.
 */
public class PopupTransformer extends TweenTransformer {
    private static final TweenTransformer.Params params = new TweenTransformer.Params();

    static {
        params.forwardIn    = R.anim.pop_in;
        params.backIn       = -1;
        params.backOut      = R.anim.pop_out;
        params.forwardOut   = -1;
    }

    @Inject
    public PopupTransformer(Application context) {
        super(context, params);
    }

}
