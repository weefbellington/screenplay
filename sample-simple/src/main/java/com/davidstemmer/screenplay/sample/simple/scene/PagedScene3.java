package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.VerticalSlideTransformer;
import com.davidstemmer.screenplay.scene.rigger.PageRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */
@Layout(R.layout.paged_scene_3)
public class PagedScene3 extends IndexedScene {

    private final PageRigger rigger;
    private final VerticalSlideTransformer transformer;

    public PagedScene3() {
        super(PagedScene2.class.getName());
        this.rigger = new PageRigger();
        this.transformer = new VerticalSlideTransformer(SampleApplication.getInstance());
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
