package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.stacked_scene)
public class StackedScene extends IndexedScene {

    private final CrossfadeTransformer transformer;

    public StackedScene() {
        super(StackedScene.class.getName());
        this.transformer = new CrossfadeTransformer(SampleApplication.getInstance());
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
