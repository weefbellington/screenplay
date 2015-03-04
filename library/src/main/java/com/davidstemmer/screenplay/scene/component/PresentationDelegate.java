package com.davidstemmer.screenplay.scene.component;

import android.view.View;

/**
 * Created by weefbellington on 3/4/15.
 */
public interface PresentationDelegate<V extends View> {
    public void afterSetUp(V view, boolean isStarting);
    public void beforeTearDown(V view, boolean isFinishing);
}
