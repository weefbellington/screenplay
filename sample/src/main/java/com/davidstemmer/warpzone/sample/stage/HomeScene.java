package com.davidstemmer.warpzone.sample.stage;

import android.os.Bundle;

import com.davidstemmer.warpzone.sample.R;
import com.davidstemmer.warpzone.sample.stage.transformer.HorizontalSlideTransformer;
import com.davidstemmer.warpzone.sample.view.HomeView;
import com.davidstemmer.warpzone.stage.Scene;
import com.davidstemmer.warpzone.stage.StandardScene;
import com.davidstemmer.warpzone.stage.director.PagedDirector;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.home)
public class HomeScene extends StandardScene {

    private final PagedDirector director;
    private final HorizontalSlideTransformer transformer;

    @Inject
    public HomeScene(PagedDirector director, HorizontalSlideTransformer transformer) {
        this.director = director;
        this.transformer = transformer;
    }

    @Override
    public Scene.Director getDirector() {
        return director;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<HomeView> {

        @Inject Flow flow;
        @Inject PopupScene popupScreen;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.floating_example) void onNextClicked() {
            flow.goTo(popupScreen);
        }
    }
}
