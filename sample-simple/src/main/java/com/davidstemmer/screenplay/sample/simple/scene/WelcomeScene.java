package com.davidstemmer.screenplay.sample.simple.scene;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.scene.rigger.PageRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */

@Layout(R.layout.simple)
public class WelcomeScene extends IndexedScene {

    public WelcomeScene() {
        super(WelcomeScene.class.getName());
    }

    @Override
    public Rigger getRigger() {
        return new PageRigger();
    }

    @Override
    public Transformer getTransformer() {
        return new CrossfadeTransformer(SampleApplication.getInstance());
    }
}
