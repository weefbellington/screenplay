package com.davidstemmer.screenplay.scene.component;

import android.content.Context;

import com.davidstemmer.screenplay.scene.Scene;

import mortar.ViewPresenter;

/**
 * Binds a Mortar presenter to the scene's view by calling Presenter#takeView after the view has
 * attached to the window and Presenter#dropView after it has detached from the window.
 */
public class MortarPresenterComponent implements Scene.Component {

    private final ViewPresenter presenter;

    public MortarPresenterComponent(final ViewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterSetUp(Context context, Scene scene, boolean isStarting) {
        presenter.takeView(scene.getView());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void beforeTearDown(Context context, Scene scene, boolean isFinishing) {
        presenter.dropView(scene.getView());
    }


}
