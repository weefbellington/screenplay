package com.davidstemmer.screenplay.scene.component;

import android.content.Context;
import android.view.View;

import com.davidstemmer.screenplay.scene.Scene;

/**
 * Created by weefbellington on 3/4/15.
 */
public class PresentationComponent<V extends View> implements Scene.Component {

    private final PresentationDelegate<V> delegate;

    public PresentationComponent(PresentationDelegate<V> delegate) {
        this.delegate = delegate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterSetUp(Context context, Scene scene, boolean isStarting) {
        delegate.afterSetUp((V)scene.getView(), isStarting);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void beforeTearDown(Context context, Scene scene, boolean isFinishing) {
        delegate.beforeTearDown((V)scene.getView(), isFinishing);
    }
}
