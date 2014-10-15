package com.davidstemmer.warpzone.stage.director;

import android.content.Context;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.SceneCut;
import com.davidstemmer.warpzone.stage.Scene;

import javax.inject.Inject;

import flow.Flow;

/**
 * Created by weefbellington on 10/14/14.
 */
public class ModalDirector implements Scene.Director {

    @Inject
    public ModalDirector() {}

    @Override
    public void layoutNext(Context context, ViewGroup parent, SceneCut cut) {
        if (cut.direction == Flow.Direction.FORWARD || cut.direction == Flow.Direction.REPLACE) {
            parent.addView(cut.nextScene.setUp(context, parent));
        }
    }


    @Override
    public void layoutPrevious(Context context, ViewGroup parent, SceneCut cut) {
        if (cut.direction == Flow.Direction.BACKWARD) {
            parent.removeView(cut.previousScene.tearDown(context, parent));
        }
    }
}
