package com.davidstemmer.screenplay.sample.mortar.component;

import android.os.Parcelable;
import android.util.SparseArray;

import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;

/**
 * Created by weefbellington on 2/23/15.
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
                scene.getView().restoreHierarchyState(savedState);
                savedState.clear();
            }
        });
    }

    @Override
    public void beforeTearDown(final Scene scene, boolean isFinishing) {
        scene.getView().saveHierarchyState(savedState);
    }
}
