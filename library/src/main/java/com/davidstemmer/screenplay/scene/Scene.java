package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;

/**
 * Created by weefbellington on 10/2/14.
 */
public interface Scene {

    public View setUp(Context context, ViewGroup parent);
    public View tearDown(Context context, ViewGroup parent);
    public View getView();

    public Director getDirector();
    public Transformer getTransformer();

    public static interface Component {
        public void afterSetUp(Context context, Scene scene);
        public void beforeTearDown(Context context, Scene scene);
    }

    public static interface Director {
        public void layoutNext(Context context, ViewGroup parent, SceneCut cut);
        public void layoutPrevious(Context context, ViewGroup parent, SceneCut cut);
    }

    public static interface Transformer {
        public void applyAnimations(SceneCut cut, Screenplay listener);
    }

}
