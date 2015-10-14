package com.davidstemmer.screenplay.sample.simple.scene;

import android.app.Application;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeRigger;
import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/2/14.
 */

public class WelcomeStage2 extends IndexedStage {

    private final CrossfadeRigger transformer;

    public WelcomeStage2(Application application) {
        super(WelcomeStage2.class.getName());
        this.transformer = new CrossfadeRigger(application);
    }

    public WelcomeStage2() {
        this(SampleApplication.getInstance());
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
