package com.davidstemmer.warpzone.sample.stage;

import android.os.Bundle;
import android.view.View;

import com.davidstemmer.warpzone.WarpZone;
import com.davidstemmer.warpzone.sample.R;
import com.davidstemmer.warpzone.sample.stage.transformer.PopupTransformer;
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
@Layout(R.layout.popup)
public class PopupStage implements Stage {

    @Inject PopupTransformer transformer;
    @Inject SimpleDirector director;

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

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            ButterKnife.inject(this, getView());
            super.onLoad(savedInstanceState);
        }

        @OnClick(R.id.ok) void dismiss() {
            warpZone.goBack();
        }
    }
}
