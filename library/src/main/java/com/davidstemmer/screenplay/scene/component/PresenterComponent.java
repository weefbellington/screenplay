package com.davidstemmer.screenplay.scene.component;

import android.content.Context;
import android.view.View;

import com.davidstemmer.screenplay.scene.Scene;

import mortar.ViewPresenter;

/**
 * Binds a Mortar presenter to the scene's view by calling Presenter#takeView after the view has
 * attached to the window and Presenter#dropView after it has detached from the window.
 */
public class PresenterComponent implements Scene.Component {
    private final View.OnAttachStateChangeListener attachStateChangeListener;

    public <V extends View> PresenterComponent(final ViewPresenter<V> presenter) {
        attachStateChangeListener = new View.OnAttachStateChangeListener() {
            @Override @SuppressWarnings("unchecked")
            public void onViewAttachedToWindow(View v) {
                presenter.takeView((V) v);
            }

            @Override @SuppressWarnings("unchecked")
            public void onViewDetachedFromWindow(View v) {
                presenter.dropView((V) v);
                v.removeOnAttachStateChangeListener(this);
            }
        };
    }

    @Override
    public void afterSetUp(Context context, Scene scene) {
        scene.getView().addOnAttachStateChangeListener(attachStateChangeListener);
    }

    @Override
    public void beforeTearDown(Context context, Scene scene, boolean isFinishing) {}
}
