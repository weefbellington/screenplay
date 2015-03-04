package com.davidstemmer.screenplay.sample.simple.component;

import com.davidstemmer.screenplay.sample.simple.DrawerHelper;
import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.scene.Scene;

/**
 * Created by weefbellington on 10/24/14.
 */
public class DrawerLockingComponent implements Scene.Component {

    private final DrawerHelper drawer;

    public DrawerLockingComponent() {
        this.drawer = SampleApplication.getDrawerHelper();
    }

    @Override
    public void afterSetUp(Scene scene, boolean isStarting) {
        drawer.setLocked(true);
    }

    @Override
    public void beforeTearDown(Scene scene, boolean isFinishing) {
        drawer.setLocked(false);
    }
}
