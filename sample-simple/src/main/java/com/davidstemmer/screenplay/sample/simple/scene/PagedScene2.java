package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.HorizontalSlideTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_2)
public class PagedScene2 extends IndexedScene {

    private final HorizontalSlideTransformer transformer;

    public PagedScene2() {
        super(PagedScene2.class.getName());
        this.transformer = new HorizontalSlideTransformer(SampleApplication.getInstance());
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
