package com.davidstemmer.screenplay.sample.presenter;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;

import com.davidstemmer.screenplay.sample.R;

import javax.inject.Inject;
import javax.inject.Singleton;

import mortar.Mortar;
import mortar.MortarScope;
import mortar.Presenter;

/**
 * Created by weefbellington on 10/24/14.
 */
@Singleton
public class DrawerPresenter extends Presenter<DrawerLayout> {

    private final ActivityPresenter activityPresenter;

    @Inject
    public DrawerPresenter(ActivityPresenter activityPresenter) {
        this.activityPresenter = activityPresenter;
    }


    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);
        LayoutInflater inflater = activityPresenter.getActivity().getLayoutInflater();
        inflater.inflate(R.layout.navigation_drawer, getLayout());
        getLayout().invalidate();
    }

    public DrawerLayout getLayout() {
        return getView();
    }

    public void setLocked(boolean locked) {
        if (locked) {
            getLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            getLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public boolean isLockedShut() {
        return getLayout().getDrawerLockMode(Gravity.LEFT) == DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    public boolean isLockedOpen() {
        return getLayout().getDrawerLockMode(Gravity.LEFT) == DrawerLayout.LOCK_MODE_LOCKED_OPEN;
    }

    public boolean isDrawerVisible() {
        return getLayout().isDrawerVisible(Gravity.LEFT);
    }

    public void open() {
        getLayout().openDrawer(Gravity.LEFT);
    }

    public void close() {
        getLayout().closeDrawer(Gravity.LEFT);
    }

    @Override
    protected MortarScope extractScope(DrawerLayout view) {
        return Mortar.getScope(activityPresenter.getActivity());
    }


}
