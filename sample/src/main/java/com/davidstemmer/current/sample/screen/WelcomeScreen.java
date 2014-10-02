package com.davidstemmer.current.sample.screen;

import android.os.Bundle;
import android.view.View;

import com.davidstemmer.current.sample.R;
import com.davidstemmer.current.sample.screen.transformer.HorizontalSlideTransformer;
import com.davidstemmer.flowcurrent.Current;
import com.davidstemmer.flowcurrent.screen.Screen;
import com.davidstemmer.flowcurrent.screen.directors.FlowDirector;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/2/14.
 */

@Layout(R.layout.welcome_screen)
public class WelcomeScreen implements Screen {

    private final FlowDirector director;
    private final HorizontalSlideTransformer transformer;

    @Inject
    public WelcomeScreen(FlowDirector director, HorizontalSlideTransformer transformer) {
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

        @Inject Current current;
        @Inject HomeScreen homeScreen;
        
        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.next) void onNextClicked() {
            current.goForward(homeScreen);
        }
    }

}
