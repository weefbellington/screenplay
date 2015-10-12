package com.davidstemmer.screenplay.sample.mortar.scene;

import android.os.Bundle;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.CrossfadeRigger;
import com.davidstemmer.screenplay.sample.mortar.view.PagedView1;
import com.davidstemmer.screenplay.stage.XmlStage;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_1)
public class PagedStage1 extends XmlStage {

    private final CrossfadeRigger transformer;

    @Inject
    public PagedStage1(CrossfadeRigger transformer) {
        this.transformer = transformer;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<PagedView1> {

        @Inject PagedStage2 nextScene;
        @Inject Flow flow;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.next) void nextClicked() {
            flow.goTo(nextScene);
        }
    }
}
