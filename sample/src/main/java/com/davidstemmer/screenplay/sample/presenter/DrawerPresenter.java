package com.davidstemmer.screenplay.sample.presenter;

import android.support.v4.widget.DrawerLayout;

import com.davidstemmer.screenplay.scene.LockingScene;

import javax.inject.Inject;
import javax.inject.Singleton;

import mortar.Mortar;
import mortar.MortarScope;
import mortar.Presenter;

/**
 * Created by weefbellington on 10/24/14.
 */
@Singleton
public class DrawerPresenter extends Presenter<DrawerLayout> implements LockingScene.Lockable {

    private final ActivityPresenter activityPresenter;

    @Inject
    public DrawerPresenter(ActivityPresenter activityPresenter) {
        this.activityPresenter = activityPresenter;
    }

    public DrawerLayout getLayout() {
        return getView();
    }

    @Override
    public void setLocked(boolean locked) {
        if (locked) {
            getLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            getLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @Override
    protected MortarScope extractScope(DrawerLayout view) {
        return Mortar.getScope(activityPresenter.getActivity());
    }


}
