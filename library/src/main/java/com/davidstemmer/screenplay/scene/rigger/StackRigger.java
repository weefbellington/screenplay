package com.davidstemmer.screenplay.scene.rigger;

import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.R;
import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;

import flow.Flow;

/**
 * Created by weefbellington on 10/14/14.
 */
public class StackRigger implements Scene.Rigger {

    private boolean showsBackgroundOverlay = true;

    @Inject
    public StackRigger() {}

    @Override
    public void layoutIncoming(ViewGroup parent, View nextView, Flow.Direction direction) {
        if (direction == Flow.Direction.FORWARD || direction == Flow.Direction.REPLACE) {
            if (showsBackgroundOverlay) {
                parent.addView(createBackgroundOverlay(parent));
            }
            parent.addView(nextView);
        }
    }

    @Override
    public boolean layoutOutgoing(ViewGroup parent, View previousView, Flow.Direction direction) {
        if (direction == Flow.Direction.BACKWARD) {
            if (showsBackgroundOverlay) {
                parent.removeView(parent.findViewById(getOverlayId()));
            }
            parent.removeView(previousView);
            return true;
        }
        return false;
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
