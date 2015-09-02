package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.HorizontalSlideTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 6/18/15.
 */
@Layout(R.layout.new_backstack_scene_2)
public class NewBackstackScene2 extends IndexedScene {

    private final HorizontalSlideTransformer transformer;

    public NewBackstackScene2() {
        super(NewBackstackScene2.class.getName());
        this.transformer = new HorizontalSlideTransformer(SampleApplication.getInstance());
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}