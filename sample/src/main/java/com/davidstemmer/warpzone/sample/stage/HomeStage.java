package com.davidstemmer.warpzone.sample.stage;

import android.os.Bundle;

import com.davidstemmer.warpzone.flow.ModalFlow;
import com.davidstemmer.warpzone.WarpZone;
import com.davidstemmer.warpzone.sample.R;
import com.davidstemmer.warpzone.sample.stage.transformer.HorizontalSlideTransformer;
import com.davidstemmer.warpzone.sample.view.HomeView;
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
@Layout(R.layout.home)
public class HomeStage implements Stage {

    private final SimpleDirector director;
    private final HorizontalSlideTransformer transformer;

    @Inject
    public HomeStage(SimpleDirector director, HorizontalSlideTransformer transformer, ModalFlow.Whistle modalFlow) {
        this.director = director;
        this.transformer = transformer;
    }

    @Override
    public Stage.Director getDirector() {
        return director;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<HomeView> {

        @Inject WarpZone warpZone;
        @Inject PopupStage popupScreen;
        @Inject ModalFlow.Whistle modalWhistle;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.floating_example) void onNextClicked() {
            warpZone.forkFlow(popupScreen, modalWhistle);
        }
    }
}
