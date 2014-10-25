package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.flow.LayoutCompat;

/**
 * Created by weefbellington on 10/15/14.
 */
public abstract class StandardScene implements Scene {

    private View view;

    @Override
    public View setUp(Context context, ViewGroup parent) {
        view = LayoutCompat.createView(context, parent, this);
        return view;
    }

    @Override
    public View tearDown(Context context, ViewGroup parent) {
        View destroyed = view;
        view = null;
        return destroyed;
    }

    @Override
    public View getView() {
        return view;
    }

}
