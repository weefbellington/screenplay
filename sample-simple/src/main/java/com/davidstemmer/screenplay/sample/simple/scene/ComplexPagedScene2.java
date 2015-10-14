package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.HorizontalSlideTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;


public class ComplexPagedScene2 extends IndexedScene {

    private final HorizontalSlideTransformer transformer;

    public ComplexPagedScene2() {
        super(ComplexPagedScene2.class.getName());
        this.transformer = new HorizontalSlideTransformer(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.complex_paged_scene_2;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
