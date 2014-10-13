package com.davidstemmer.warpzone.stage.director;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.flow.LayoutCompat;
import com.davidstemmer.warpzone.stage.Stage;

import javax.inject.Inject;

/**
 * Created by weefbellington on 10/2/14.
 */
public class SimpleDirector implements Stage.Director {

    private View view;

    @Inject
    public SimpleDirector() {}

    @Override
    public View create(Context context, Object screen, ViewGroup parent) {
        view = LayoutCompat.createView(context, parent, screen);
        return view;
    }

    @Override
    public View destroy(Context context, Object screen, ViewGroup parent) {
        View destroyed = view;
        view = null;
        return destroyed;
    }

    @Override
    public View getView() {
        return view;
    }
}
