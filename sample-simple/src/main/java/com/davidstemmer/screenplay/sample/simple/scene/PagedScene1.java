package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_1)
public class PagedScene1 extends IndexedScene {

    private final CrossfadeTransformer transformer;

    public PagedScene1() {
        super(PagedScene1.class.getName());
        this.transformer = new CrossfadeTransformer(SampleApplication.getInstance());
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
