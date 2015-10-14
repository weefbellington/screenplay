package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;


/**
 * Created by weefbellington on 6/18/15.
 */
public class NewBackstackScene1 extends IndexedScene {

    private final CrossfadeTransformer transformer;

    public NewBackstackScene1() {
        super(NewBackstackScene1.class.getName());
        this.transformer = new CrossfadeTransformer(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.new_backstack_scene_1;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
