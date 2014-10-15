package com.davidstemmer.warpzone.stage.director;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.WarpPipe;
import com.davidstemmer.warpzone.flow.LayoutCompat;
import com.davidstemmer.warpzone.stage.Stage;

import javax.inject.Inject;

/**
 * Created by weefbellington on 10/13/14.
 */
public class PagedDirector implements Stage.Director {

    public View view;

    @Inject
    public PagedDirector() {}

    @Override
    public View createView(Context context, ViewGroup parent, Stage stage) {
        view = LayoutCompat.createView(context, parent, stage);
        return view;
    }

    @Override
    public View destroyView(Context context, ViewGroup parent, Stage stage) {
        View destroyed = view;
        view = null;
        return destroyed;
    }

    @Override
    public void layoutIncomingStage(Context context, ViewGroup parent, WarpPipe pipe) {
        parent.addView(pipe.nextStage.getDirector().createView(context, parent, pipe.nextStage));
    }

    @Override
    public void layoutOutgoingStage(Context context, ViewGroup parent, WarpPipe pipe) {
        if (pipe.previousStage != null) {
            parent.removeView(pipe.previousStage.getDirector().destroyView(context, parent, pipe.previousStage));
        }
    }

    @Override
    public View getView() {
        return view;
    }

}
