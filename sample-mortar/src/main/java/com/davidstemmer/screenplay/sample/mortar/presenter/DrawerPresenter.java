package com.davidstemmer.screenplay.sample.mortar.presenter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.davidstemmer.screenplay.MortarActivityDirector;
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

    private final MortarActivityDirector director;
    private ActionBarDrawerToggle drawerToggle;

    @Inject
    public DrawerPresenter(MortarActivityDirector director) {
        this.director = director;
    }

    @Override
    protected void onLoad(Bundle savedInstanceState) {
        super.onLoad(savedInstanceState);

        drawerToggle = createDrawerToggle();

        LayoutInflater inflater = director.getActivity().getLayoutInflater();
        inflater.inflate(R.layout.navigation_menu, getLayout());
        getLayout().setDrawerListener(drawerToggle);

        ActionBar actionBar = ((ActionBarActivity)director.getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void dropView(DrawerLayout view) {
        super.dropView(view);
        drawerToggle = null;
    }

    private ActionBarDrawerToggle createDrawerToggle() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                director.getActivity(),
                getLayout(),
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
        return Mortar.getScope(director.getActivity());
    }


}
