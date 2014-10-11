package com.davidstemmer.screenplay.sample.scene;

import android.os.Bundle;
import android.view.View;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transition.HorizontalSlideTransformer;
import com.davidstemmer.screenplay.Screenplay;
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

@Layout(R.layout.welcome)
public class WelcomeScene implements Scene {

    private final SimpleDirector director;
    private final HorizontalSlideTransformer transformer;

    @Inject
    public WelcomeScene(SimpleDirector director, HorizontalSlideTransformer transformer) {
        this.director = director;
        this.transformer = transformer;
    }

    @Override
    public Director getDirector() {
        return director;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<View> {

        @Inject Screenplay screenplay;
        @Inject HomeScene homeScreen;
        
        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.next) void onNextClicked() {
            screenplay.goForward(homeScreen);
        }
    }

}
