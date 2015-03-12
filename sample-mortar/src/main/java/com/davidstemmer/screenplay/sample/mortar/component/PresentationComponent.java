package com.davidstemmer.screenplay.sample.mortar.component;

import android.view.View;

import com.davidstemmer.screenplay.scene.Scene;

import butterknife.ButterKnife;

/**
 * Created by weefbellington on 3/4/15.
 */
public class PresentationComponent<V extends View> implements Scene.Component {

    @Override
    @SuppressWarnings("unchecked")
    public final void afterSetUp(Scene scene, boolean isInitializing) {
        ButterKnife.inject(this, scene.getView());
        afterSetUp((V)scene.getView(), isInitializing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void beforeTearDown(Scene scene, boolean isFinishing) {
        beforeTearDown((V)scene.getView(), isFinishing);
        ButterKnife.reset(this);
    }

    public void afterSetUp(V view, boolean isInitializing) {}

    public void beforeTearDown(V view, boolean isFinishing) {}
}
