package com.davidstemmer.screenplay.sample.mortar.component;

import android.content.Context;

import com.davidstemmer.screenplay.sample.mortar.presenter.DrawerPresenter;
import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;

/**
 * Created by weefbellington on 10/24/14.
 */
public class DrawerLockingComponent implements Scene.Component {

    private final DrawerPresenter drawer;

    @Inject
    public DrawerLockingComponent(DrawerPresenter drawer) {
        this.drawer = drawer;
    }

    @Override
    public void afterSetUp(Context context, Scene scene, boolean isStarting) {
        drawer.setLocked(true);
    }

    @Override
    public void beforeTearDown(Context context, Scene scene, boolean isFinishing) {
        drawer.setLocked(false);
    }
}
