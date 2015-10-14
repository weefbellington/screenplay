package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.HorizontalSlideRigger;
import com.example.weefbellington.screenplay.sample.simple.R;


/**
 * Created by weefbellington on 6/18/15.
 */
public class NewBackstackStage2 extends IndexedStage {

    private final HorizontalSlideRigger transformer;

    public NewBackstackStage2() {
        super(NewBackstackStage2.class.getName());
        this.transformer = new HorizontalSlideRigger(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.new_backstack_scene_2;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }
}
