package com.davidstemmer.screenplay.scene.component;

import android.os.Parcelable;
import android.util.SparseArray;

import com.davidstemmer.screenplay.scene.Stage;

import javax.inject.Inject;

/**
 * Saves the instance state of a Scene's View when it is torn down and restores it when it
 * is destroyed.
 */
public class InstanceStateComponent implements Stage.Component {

    private final SparseArray<Parcelable> savedState = new SparseArray<>();

    @Inject
    public InstanceStateComponent() {}

    @Override
    public void afterSetUp(final Stage stage, boolean isStarting) {
        stage.getView().post(new Runnable() {
            @Override
            public void run() {
                if (stage.getView() != null) {
                    stage.getView().restoreHierarchyState(savedState);
                    savedState.clear();
                }
            }
        });
    }

    @Override
    public void beforeTearDown(final Stage stage, boolean isFinishing) {
        stage.getView().saveHierarchyState(savedState);
    }
}
