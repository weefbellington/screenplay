package com.davidstemmer.screenplay.sample.scene;

import android.os.Bundle;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transition.HorizontalSlideTransition;
import com.davidstemmer.screenplay.sample.view.HomeView;
import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.flowlistener.ModalFlowListener;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.director.SimpleDirector;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.home_screen)
public class HomeScene implements Scene {

    private final SimpleDirector director;
    private final HorizontalSlideTransition transformer;

    @Inject
    public HomeScene(SimpleDirector director, HorizontalSlideTransition transformer) {
        this.director = director;
        this.transformer = transformer;
    }

    @Override
    public Scene.Director getDirector() {
        return director;
    }

    @Override
    public Transition getTransition() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<HomeView> {

        @Inject Screenplay screenplay;
        @Inject PopupScene popupScreen;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.floating_example) void onNextClicked() {
            screenplay.changeFlow(popupScreen, new ModalFlowListener(screenplay));
        }
    }
}
