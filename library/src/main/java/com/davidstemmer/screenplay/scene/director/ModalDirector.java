package com.davidstemmer.screenplay.scene.director;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.R;
import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;

import flow.Flow;

/**
 * Created by weefbellington on 10/14/14.
 */
public class ModalDirector implements Scene.Director {

    private boolean showsBackgroundOverlay = true;

    @Inject
    public ModalDirector() {}

    @Override
    public void layoutNext(Context context, ViewGroup parent, SceneCut cut) {
        if (cut.direction == Flow.Direction.FORWARD || cut.direction == Flow.Direction.REPLACE) {
            if (showsBackgroundOverlay) {
                parent.addView(createBackgroundOverlay(parent));
            }
            parent.addView(cut.nextScene.setUp(context, parent));
        }
    }

    @Override
    public void layoutPrevious(Context context, ViewGroup parent, SceneCut cut) {
        if (cut.direction == Flow.Direction.BACKWARD) {
            if (showsBackgroundOverlay) {
                parent.removeView(parent.findViewById(getOverlayId()));
            }
            parent.removeView(cut.previousScene.tearDown(context, parent));
        }
    }

    public void showsBackgroundOverlay(boolean show) {
        this.showsBackgroundOverlay = show;
    }

    protected View createBackgroundOverlay(ViewGroup parent) {
        View overlay = new View(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        overlay.setLayoutParams(params);
        overlay.setBackgroundColor(0xA0000000);
        overlay.setId(getOverlayId());
        overlay.setClickable(true);
        return overlay;
    }

    protected int getOverlayId() {
        return R.id.modal_overlay;
    }
}
