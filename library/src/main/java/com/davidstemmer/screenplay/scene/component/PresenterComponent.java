package com.davidstemmer.screenplay.scene.component;

import android.content.Context;
import android.view.View;

import com.davidstemmer.screenplay.scene.Scene;

import mortar.ViewPresenter;

/**
 * This Component is provided as a simple way to attach a Scene to a View that needs a presenter
 * to take and drop the view as it is acquired, but doesn't need to do anything more complicated
 * than that with it.
 *
 * To use it, simply create a SimpleViewBindingComponent in your Scene, passing it the presenter
 * for your view, and then supply this component to the StandardScene's constructor.
 */
public class PresenterComponent implements Scene.Component {
    private final View.OnAttachStateChangeListener attachStateChangeListener;

    public PresenterComponent(final ViewPresenter<View> presenter) {
        attachStateChangeListener = new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                presenter.takeView(v);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                presenter.dropView(v);
            }
        };
    }

    @Override
    public void afterSetUp(Context context, Scene scene, View view) {
        view.addOnAttachStateChangeListener(attachStateChangeListener);
    }

    @Override
    public void beforeTearDown(Context context, Scene scene, View view) {
        view.removeOnAttachStateChangeListener(attachStateChangeListener);
    }
}
