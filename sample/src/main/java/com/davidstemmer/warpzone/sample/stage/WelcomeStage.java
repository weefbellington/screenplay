package com.davidstemmer.warpzone.sample.stage;

import android.os.Bundle;
import android.view.View;

import com.davidstemmer.warpzone.WarpZone;
import com.davidstemmer.warpzone.sample.R;
import com.davidstemmer.warpzone.sample.stage.transformer.HorizontalSlideTransformer;
import com.davidstemmer.warpzone.stage.Stage;
import com.davidstemmer.warpzone.stage.director.SimpleDirector;

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
public class WelcomeStage implements Stage {

    private final SimpleDirector director;
    private final HorizontalSlideTransformer transformer;

    @Inject
    public WelcomeStage(SimpleDirector director, HorizontalSlideTransformer transformer) {
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

        @Inject WarpZone warpZone;
        @Inject HomeStage homeScreen;
        
        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.next) void onNextClicked() {
            warpZone.goForward(homeScreen);
        }
    }

}
