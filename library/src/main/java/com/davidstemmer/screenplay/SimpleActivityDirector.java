package com.davidstemmer.screenplay;

import android.app.Activity;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.flow.Screenplay;

/**
 * @version 1.0.0
 * @author  David Stemmer
 * @since   1.0.0
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
