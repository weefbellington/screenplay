package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeRigger;
import com.example.weefbellington.screenplay.sample.simple.R;


/**
 * Created by weefbellington on 6/18/15.
 */
public class NewBackstackStage1 extends IndexedStage {

    private final CrossfadeRigger transformer;

    public NewBackstackStage1() {
        super(NewBackstackStage1.class.getName());
        this.transformer = new CrossfadeRigger(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.new_backstack_scene_1;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }
}
