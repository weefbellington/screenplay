package com.davidstemmer.screenplay.sample.simple.component;

import com.davidstemmer.screenplay.sample.simple.DrawerHelper;
import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.stage.Stage;

/**
 * Created by weefbellington on 10/24/14.
 */
public class DrawerLockingComponent implements Stage.Component {

    private final DrawerHelper drawer;

    public DrawerLockingComponent() {
        this.drawer = SampleApplication.getDrawerHelper();
    }

    @Override
    public void afterSetUp(Stage stage, boolean isStarting) {
        drawer.setLocked(true);
    }

    @Override
    public void beforeTearDown(Stage stage, boolean isFinishing) {
        drawer.setLocked(false);
    }
}
