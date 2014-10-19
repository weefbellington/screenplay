package com.davidstemmer.screenplay.sample.scene;

import android.widget.LinearLayout;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transformer.VerticalSlideTransformer;
import com.davidstemmer.screenplay.stage.StandardScene;
import com.davidstemmer.screenplay.stage.director.PagedDirector;

import javax.inject.Inject;

import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_2)
public class PagedScene2 extends StandardScene {

    private final PagedDirector director;
    private final VerticalSlideTransformer transformer;

    @Inject
    public PagedScene2(PagedDirector director, VerticalSlideTransformer transformer) {
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

    public static class Presenter extends ViewPresenter<LinearLayout> {}
}
