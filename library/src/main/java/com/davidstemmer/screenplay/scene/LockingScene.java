package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by weefbellington on 10/24/14.
 */
public abstract class LockingScene extends StandardScene {

    private final Lockable lockable;

    public LockingScene(Lockable lockable) {
        this.lockable = lockable;
    }

    @Override
    public View setUp(Context context, ViewGroup parent) {
        lockable.setLocked(true);
        return super.setUp(context, parent);
    }

    @Override
    public View tearDown(Context context, ViewGroup parent) {
        lockable.setLocked(false);
        return super.tearDown(context, parent);
    }

    public static interface Lockable {
        public void setLocked(boolean locked);
    }

}
