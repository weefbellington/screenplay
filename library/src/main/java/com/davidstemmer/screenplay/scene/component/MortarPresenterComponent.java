package com.davidstemmer.screenplay.scene.component;

import com.davidstemmer.screenplay.scene.Stage;

import mortar.ViewPresenter;

/**
 * Binds a Mortar presenter to the scene's view by calling Presenter#takeView after the view has
 * attached to the window and Presenter#dropView after it has detached from the window.
 */
public class MortarPresenterComponent implements Stage.Component {

    private final ViewPresenter presenter;

    public MortarPresenterComponent(final ViewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterSetUp(Stage stage, boolean isStarting) {
        presenter.takeView(stage.getView());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void beforeTearDown(Stage stage, boolean isFinishing) {
        presenter.dropView(stage.getView());
    }


}
