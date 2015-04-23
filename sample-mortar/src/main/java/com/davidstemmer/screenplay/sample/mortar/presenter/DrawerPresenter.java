package com.davidstemmer.screenplay.sample.mortar.presenter;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.sample.mortar.R;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by weefbellington on 10/24/14.
 */
@Singleton
public class DrawerPresenter {

    private final MutableStage mainStage;
    private ActionBarDrawerToggle drawerToggle;

    private DrawerLayout target;

    @Inject
    public DrawerPresenter(MutableStage mainStage) {
        this.mainStage = mainStage;
    }

    public void take(DrawerLayout target) {
        this.target = target;
        this.drawerToggle = createDrawerToggle(target);
        target.setDrawerListener(drawerToggle);

        ActionBar actionBar = ((ActionBarActivity)mainStage.getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void drop() {
        this.drawerToggle = null;
        this.target = null;
    }


    private ActionBarDrawerToggle createDrawerToggle(DrawerLayout view) {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                mainStage.getActivity(),
                view,
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mainStage.getActivity().invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                mainStage.getActivity().invalidateOptionsMenu();
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
            target.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            target.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public boolean isLockedShut() {
        return target.getDrawerLockMode(Gravity.LEFT) == DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
    }

    public boolean isLockedOpen() {
        return target.getDrawerLockMode(Gravity.LEFT) == DrawerLayout.LOCK_MODE_LOCKED_OPEN;
    }

    public boolean isDrawerVisible() {
        return target.isDrawerVisible(Gravity.LEFT);
    }

    public void open() {
        target.openDrawer(Gravity.LEFT);
    }

    public void close() {
        target.closeDrawer(Gravity.LEFT);
    }

}
