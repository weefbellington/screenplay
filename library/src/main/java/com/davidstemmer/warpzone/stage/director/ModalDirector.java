package com.davidstemmer.warpzone.stage.director;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.WarpPipe;
import com.davidstemmer.warpzone.flow.LayoutCompat;
import com.davidstemmer.warpzone.stage.Stage;

import javax.inject.Inject;

import flow.Flow;

/**
 * Created by weefbellington on 10/14/14.
 */
public class ModalDirector implements Stage.Director {

    @Inject
    public ModalDirector() {}

    private View view;

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
        if (pipe.direction == Flow.Direction.FORWARD || pipe.direction == Flow.Direction.REPLACE) {
            parent.addView(createView(context, parent, pipe.nextStage));
        }
    }


    @Override
    public void layoutOutgoingStage(Context context, ViewGroup parent, WarpPipe pipe) {
        if (pipe.direction == Flow.Direction.BACKWARD) {
            parent.removeView(pipe.previousStage.getDirector().destroyView(context, parent, pipe.previousStage));
        }
    }

    @Override
    public View getView() {
        return view;
    }
}
