package com.davidstemmer.screenplay.scene.rigger;

import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;

import flow.Flow;

/**
 * Created by weefbellington on 10/13/14.
 */
public class PageRigger implements Scene.Rigger {

    @Inject
    public PageRigger() {}

    @Override
    public void layoutIncoming(ViewGroup parent, Flow.Direction direction, View...incomingViews) {
        for (View incomingView : incomingViews) {
            parent.addView(incomingView);
        }
    }

    @Override
    public boolean layoutOutgoing(ViewGroup parent, Flow.Direction direction, View...outgoingViews) {
        for (View incomingView : outgoingViews) {
            parent.removeView(outgoingViews);
        }
        return true;
    }

}
