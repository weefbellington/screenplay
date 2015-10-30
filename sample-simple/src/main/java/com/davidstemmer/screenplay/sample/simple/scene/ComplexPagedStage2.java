package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.HorizontalSlideRigger;
import com.example.weefbellington.screenplay.sample.simple.R;


public class ComplexPagedStage2 extends IndexedStage {

    private final HorizontalSlideRigger transformer;

    public ComplexPagedStage2() {
        super(ComplexPagedStage2.class.getName());
        this.transformer = new HorizontalSlideRigger(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.complex_paged_scene_2;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }
}
