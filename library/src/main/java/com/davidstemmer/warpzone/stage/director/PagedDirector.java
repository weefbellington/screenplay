package com.davidstemmer.warpzone.stage.director;

import android.content.Context;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.SceneCut;
import com.davidstemmer.warpzone.stage.Scene;

import javax.inject.Inject;

/**
 * Created by weefbellington on 10/13/14.
 */
public class PagedDirector implements Scene.Director {

    @Inject
    public PagedDirector() {}

    @Override
    public void layoutNext(Context context, ViewGroup parent, SceneCut cut) {
        parent.addView(cut.nextScene.setUp(context, parent));
    }

    @Override
    public void layoutPrevious(Context context, ViewGroup parent, SceneCut cut) {
        if (cut.previousScene != null) {
            parent.removeView(cut.previousScene.tearDown(context, parent));
        }
    }

}
