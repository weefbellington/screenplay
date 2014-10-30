package com.davidstemmer.screenplay.sample.scene;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transformer.VerticalSlideTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.rigger.PageRigger;

import javax.inject.Inject;

import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */
@Layout(R.layout.paged_scene_3)
public class PagedScene3 extends StandardScene {

    private final PageRigger rigger;
    private final VerticalSlideTransformer transformer;

    @Inject
    public PagedScene3(PageRigger rigger, VerticalSlideTransformer transformer) {
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
}
