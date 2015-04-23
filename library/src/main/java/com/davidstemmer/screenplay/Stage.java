package com.davidstemmer.screenplay;

import android.app.Activity;
import android.view.ViewGroup;

import flow.Flow;

/**
 * Created by weefbellington on 4/2/15.
 */
public interface Stage {
    public Activity getActivity();
    public ViewGroup getContainer();
    public Flow getFlow();
}
