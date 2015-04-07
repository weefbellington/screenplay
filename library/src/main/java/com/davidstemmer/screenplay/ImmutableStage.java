package com.davidstemmer.screenplay;

import android.app.Activity;
import android.view.ViewGroup;

import flow.Flow;

/**
 * @version 1.0.0
 * @author  David Stemmer
 * @since   1.0.0
 */
public class ImmutableStage implements Stage {

    private final Activity activity;
    private final ViewGroup container;
    private final Flow flow;

    public ImmutableStage(Activity activity, ViewGroup container, Flow flow) {
        this.activity = activity;
        this.container = container;
        this.flow = flow;
    }

    public Activity getActivity() {
        return activity;
    }

    public ViewGroup getContainer() {
        return container;
    }

    public Flow getFlow() {
        return flow;
    }
}
