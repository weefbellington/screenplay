package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public interface Scene {

    public Director getDirector();
    public Transition getTransition();

    public static interface Director {
        public View create(Context context, Object screen, ViewGroup parent);
        public View destroy(Context context, Object screen, ViewGroup parent);
        public View getView();
    }

    public static interface Transition {
        public void transform(Flow.Direction direction, Flow.Callback callback, Scene nextScene, Scene previousScene);
    }

}
