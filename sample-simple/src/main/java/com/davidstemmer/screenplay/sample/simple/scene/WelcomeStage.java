package com.davidstemmer.screenplay.sample.simple.scene;

import android.app.Application;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/2/14.
 */

public class WelcomeStage extends IndexedStage {

    private final Rigger rigger;

    public WelcomeStage(Application application) {
        super(WelcomeStage.class.getName());
        this.rigger = new CrossfadeRigger(application);
    }

    public WelcomeStage() {
        this(SampleApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.welcome_scene;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }
}
