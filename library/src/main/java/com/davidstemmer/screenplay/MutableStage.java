package com.davidstemmer.screenplay;

import android.app.Activity;
import android.view.ViewGroup;

import flow.Flow;

/**
 * Created by weefbellington on 4/2/15.
 */
public class MutableStage implements Stage {

    private Activity activity;
    private ViewGroup container;
    private Flow flow;

    public Activity getActivity() {
        return activity;
    }

    public ViewGroup getContainer() {
        return container;
    }

    public Flow getFlow() {
        return flow;
    }


    public void bind(Activity activity, ViewGroup container, Flow flow) {
        this.activity = activity;
        this.container = container;
        this.flow = flow;
    }

    public void unbind() {
        this.activity = null;
        this.container = null;
        this.flow = null;
    }
}
