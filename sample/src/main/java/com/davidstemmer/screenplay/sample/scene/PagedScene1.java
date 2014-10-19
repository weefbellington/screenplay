package com.davidstemmer.screenplay.sample.scene;

import android.widget.LinearLayout;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transformer.HorizontalSlideTransformer;
import com.davidstemmer.screenplay.stage.StandardScene;
import com.davidstemmer.screenplay.stage.director.PagedDirector;

import javax.inject.Inject;

import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_1)
public class PagedScene1 extends StandardScene {

    private final PagedDirector director;
    private final HorizontalSlideTransformer transformer;

    @Inject
    public PagedScene1(PagedDirector director, HorizontalSlideTransformer transformer) {
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

    public static class Presenter extends ViewPresenter<LinearLayout> {



    }
}
