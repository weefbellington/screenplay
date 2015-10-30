package com.davidstemmer.screenplay.sample.simple;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.weefbellington.screenplay.sample.simple.R;

/**
 * Created by weefbellington on 10/24/14.
 */
public class DrawerHelper {

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    public void bind(Activity activity, DrawerLayout layout) {

        drawerLayout = layout;
        drawerToggle = createDrawerToggle(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        
        inflater.inflate(R.layout.navigation_menu, getLayout());
        getLayout().setDrawerListener(drawerToggle);

        ActionBar actionBar = ((AppCompatActivity) activity).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void unbind() {
        drawerToggle = null;
    }

    private ActionBarDrawerToggle createDrawerToggle(final Activity activity) {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                activity,
                getLayout(),
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                activity.invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                activity.invalidateOptionsMenu();
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
        return drawerLayout;
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
}
