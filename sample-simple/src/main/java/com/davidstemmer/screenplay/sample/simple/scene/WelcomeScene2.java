package com.davidstemmer.screenplay.sample.simple.scene;

import android.app.Application;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */

@Layout(R.layout.new_backstack_scene_1)
public class WelcomeScene2 extends IndexedScene {

    private final CrossfadeTransformer transformer;

    public WelcomeScene2(Application application) {
        super(WelcomeScene2.class.getName());
        this.transformer = new CrossfadeTransformer(application);
    }

    public WelcomeScene2() {
        this(SampleApplication.getInstance());
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }
}
