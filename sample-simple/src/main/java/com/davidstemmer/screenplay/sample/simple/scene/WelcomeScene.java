package com.davidstemmer.screenplay.sample.simple.scene;

import android.app.Application;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.scene.rigger.PageRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */

@Layout(R.layout.welcome_scene)
public class WelcomeScene extends IndexedScene {

    private final PageRigger rigger;
    private final CrossfadeTransformer transformer;

    public WelcomeScene(Application application) {
        super(WelcomeScene.class.getName());
        this.rigger = new PageRigger();
        this.transformer = new CrossfadeTransformer(application);
    }

    public WelcomeScene() {
        this(SampleApplication.getInstance());
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
