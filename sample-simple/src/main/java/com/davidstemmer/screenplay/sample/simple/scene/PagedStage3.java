package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.VerticalSlideRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/17/14.
 */
public class PagedStage3 extends IndexedStage {

    private final VerticalSlideRigger transformer;

    public PagedStage3() {
        super(PagedStage2.class.getName());
        this.transformer = new VerticalSlideRigger(SampleApplication.getInstance());
    }


    @Override
    public int getLayoutId() {
        return R.layout.paged_scene_3;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }
}
