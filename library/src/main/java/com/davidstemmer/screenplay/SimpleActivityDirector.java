package com.davidstemmer.screenplay;

import android.app.Activity;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.flow.Screenplay;

/**
 * Created by weefbellington on 10/26/14.
 */
public class SimpleActivityDirector implements Screenplay.Director {

    private Activity activity;
    private ViewGroup container;

    public void bind(Activity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
    }

    public void unbind() {
        this.activity = null;
        this.container = null;
    }

    @Override
    public Activity getActivity() {
        return activity;
    }

    @Override
    public ViewGroup getContainer() {
        return container;
    }
}
