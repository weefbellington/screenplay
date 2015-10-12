package com.davidstemmer.screenplay.sample.mortar.scene;

import android.os.Bundle;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.HorizontalSlideRigger;
import com.davidstemmer.screenplay.sample.mortar.view.PagedView2;
import com.davidstemmer.screenplay.scene.XmlStage;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_2)
public class PagedStage2 extends XmlStage {

    private final HorizontalSlideRigger transformer;

    @Inject
    public PagedStage2(HorizontalSlideRigger transformer) {
        this.transformer = transformer;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }

    public static class Presenter extends ViewPresenter<PagedView2> {

        @Inject PagedStage3 nextScene;
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
