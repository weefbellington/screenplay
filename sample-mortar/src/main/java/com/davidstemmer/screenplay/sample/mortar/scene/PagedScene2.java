package com.davidstemmer.screenplay.sample.mortar.scene;

import android.os.Bundle;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.HorizontalSlideTransformer;
import com.davidstemmer.screenplay.sample.mortar.view.PagedView2;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.rigger.PageRigger;

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

@Layout(R.layout.paged_scene_2)
@Singleton
public class PagedScene2 extends StandardScene {

    private final PageRigger rigger;
    private final HorizontalSlideTransformer transformer;

    @Inject
    public PagedScene2(PageRigger rigger, HorizontalSlideTransformer transformer) {
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

    public static class Presenter extends ViewPresenter<PagedView2> {

        @Inject PagedScene3 nextScene;
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
