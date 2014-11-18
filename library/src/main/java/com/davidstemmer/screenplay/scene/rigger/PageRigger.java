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
    public void layoutIncoming(ViewGroup parent, View nextView, Flow.Direction direction) {
        parent.addView(nextView);
    }

    @Override
    public boolean layoutOutgoing(ViewGroup parent, View previousView, Flow.Direction direction) {
        parent.removeView(previousView);
        return true;
    }

}
