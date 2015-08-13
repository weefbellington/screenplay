package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.VerticalSlideTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/17/14.
 */
public class PagedScene3 extends IndexedScene {

    private final VerticalSlideTransformer transformer;

    public PagedScene3() {
        super(PagedScene2.class.getName());
        this.transformer = new VerticalSlideTransformer(SampleApplication.getInstance());
    }


    @Override
    public int getLayoutId() {
        return R.layout.paged_scene_3;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
