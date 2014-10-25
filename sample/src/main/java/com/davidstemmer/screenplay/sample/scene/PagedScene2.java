package com.davidstemmer.screenplay.sample.scene;

import android.widget.LinearLayout;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transformer.VerticalSlideTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.rigger.PageRigger;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_2)
@Singleton
public class PagedScene2 extends StandardScene {

    private final PageRigger rigger;
    private final VerticalSlideTransformer transformer;

    @Inject
    public PagedScene2(PageRigger rigger, VerticalSlideTransformer transformer) {
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

    public static class Presenter extends ViewPresenter<LinearLayout> {}
}
