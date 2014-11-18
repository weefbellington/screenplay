package com.davidstemmer.screenplay.sample.mortar.scene;

import android.os.Bundle;
import android.view.View;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.rigger.PageRigger;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/2/14.
 */

@Layout(R.layout.welcome_scene)
@Singleton
public class WelcomeScene extends StandardScene {

    private final PageRigger rigger;
    private final CrossfadeTransformer transformer;

    @Inject
    public WelcomeScene(PageRigger rigger, CrossfadeTransformer transformer) {
        this.rigger = rigger;
        this.transformer = transformer;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<View> {

        @Inject public Presenter() {}

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }
    }

}
