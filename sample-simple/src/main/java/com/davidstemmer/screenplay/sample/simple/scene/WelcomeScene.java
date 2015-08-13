package com.davidstemmer.screenplay.sample.simple.scene;

import android.app.Application;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/2/14.
 */

public class WelcomeScene extends IndexedScene {

    private final CrossfadeTransformer transformer;

    public WelcomeScene(Application application) {
        super(WelcomeScene.class.getName());
        this.transformer = new CrossfadeTransformer(application);
    }

    public WelcomeScene() {
        this(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome_scene;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
