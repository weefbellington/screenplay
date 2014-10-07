package com.davidstemmer.screenplay.sample.scene;

import android.os.Bundle;
import android.view.View;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transition.PopupTransition;
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
@Layout(R.layout.popup_screen)
public class PopupScene implements Scene {

    @Inject PopupTransition transformer;
    @Inject SimpleDirector director;

    @Override
    public Director getDirector() {
        return director;
    }

    @Override
    public Transition getTransition() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<View> {

        @Inject Screenplay screenplay;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            ButterKnife.inject(this, getView());
            super.onLoad(savedInstanceState);
        }

        @OnClick(R.id.ok) void dismiss() {
            screenplay.goBack();
        }
    }
}
