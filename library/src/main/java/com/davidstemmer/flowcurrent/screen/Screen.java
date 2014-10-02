package com.davidstemmer.flowcurrent.screen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public interface Screen {

    public Director getDirector();
    public Transformer getTransformer();

    public static interface Director {
        public View create(Context context, Object screen, ViewGroup parent);
        public View destroy(Context context, Object screen, ViewGroup parent);
        public View getView();
    }

    public static interface Transformer {
        public void transform(Flow.Direction direction, Screen nextScreen, Screen previousScreen);
    }

}
