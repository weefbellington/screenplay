package com.davidstemmer.screenplay.sample.mortar.presenter;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.davidstemmer.screenplay.SimpleActivityDirector;
import com.davidstemmer.screenplay.sample.mortar.R;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by weefbellington on 10/24/14.
 */
@Singleton
public class DrawerPresenter extends ViewPresenter<DrawerLayout> {

    private final SimpleActivityDirector director;

    private ActionBarDrawerToggle drawerToggle;

    @Inject
    public DrawerPresenter(SimpleActivityDirector director) {
        this.director = director;
    }

    public void take(DrawerLayout target) {

        drawerToggle = createDrawerToggle(target);
        target.setDrawerListener(drawerToggle);

        ActionBar actionBar = ((ActionBarActivity)director.getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void drop(DrawerLayout target) {
        drawerToggle = null;
    }

    private ActionBarDrawerToggle createDrawerToggle(DrawerLayout view) {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                director.getActivity(),
                view,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                director.getActivity().invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                director.getActivity().invalidateOptionsMenu();
            }
        };
        toggle.setDrawerIndicatorEnabled(true);
        return toggle;
    }

    public void syncToggleState() {
        drawerToggle.syncState();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void setLocked(boolean locked) {
        if (locked) {
            getTarget().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            getTarget().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public boolean isLockedShut() {
        return getTarget().getDrawerLockMode(Gravity.LEFT) == DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    public boolean isLockedOpen() {
        return getTarget().getDrawerLockMode(Gravity.LEFT) == DrawerLayout.LOCK_MODE_LOCKED_OPEN;
    }

    public boolean isDrawerVisible() {
        return getTarget().isDrawerVisible(Gravity.LEFT);
    }

    public void open() {
        getTarget().openDrawer(Gravity.LEFT);
    }

    public void close() {
        getTarget().closeDrawer(Gravity.LEFT);
    }

}
