package com.davidstemmer.warpzone.stage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.SceneCut;
import com.davidstemmer.warpzone.flow.Screenplay;

/**
 * Created by weefbellington on 10/2/14.
 */
public interface Scene {

    public View setUp(Context context, ViewGroup parent);
    public View tearDown(Context context, ViewGroup parent);
    public View getView();

    public Director getDirector();
    public Transformer getTransformer();

    public static interface Director {
        public void layoutNext(Context context, ViewGroup parent, SceneCut cut);
        public void layoutPrevious(Context context, ViewGroup parent, SceneCut cut);
    }

    public static interface Transformer {
        public void applyAnimations(SceneCut pipe, Screenplay listener);
    }

}
