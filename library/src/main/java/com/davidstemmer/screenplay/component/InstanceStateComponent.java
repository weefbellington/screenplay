package com.davidstemmer.screenplay.component;

import android.os.Parcelable;
import android.util.SparseArray;

import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;

/**
 * Saves the instance state of a Scene's View when it is torn down and restores it when it
 * is destroyed.
 */
public class InstanceStateComponent implements Scene.Component {

    private final SparseArray<Parcelable> savedState = new SparseArray<>();

    @Inject
    public InstanceStateComponent() {}

    @Override
    public void afterSetUp(final Scene scene, boolean isStarting) {
        scene.getView().post(new Runnable() {
            @Override
            public void run() {
                if (scene.getView() != null) {
                    scene.getView().restoreHierarchyState(savedState);
                    savedState.clear();
                }
            }
        });
    }

    @Override
    public void beforeTearDown(final Scene scene, boolean isFinishing) {
        scene.getView().saveHierarchyState(savedState);
    }
}
