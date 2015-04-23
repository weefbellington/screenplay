package com.davidstemmer.screenplay;

import android.app.Activity;
import android.view.ViewGroup;

import flow.Flow;

/**
 * Created by weefbellington on 4/23/15.
 */
public class MutableStage implements Stage {

    private Activity activity;
    private ViewGroup container;
    private Flow flow;

    public void bind(Activity activity, ViewGroup container, Flow flow) {
        this.activity = activity;
        this.container = container;
        this.flow = flow;
    }

    public void unbind() {
        activity = null;
        container = null;
        flow = null;
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public ViewGroup getContainer() {
        return container;
    }

    @Override
    public Flow getFlow() {
        return flow;
    }
}
