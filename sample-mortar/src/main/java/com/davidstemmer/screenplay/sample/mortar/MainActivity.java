package com.davidstemmer.screenplay.sample.mortar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.davidstemmer.screenplay.MortarActivityDirector;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.mortar.module.ActivityModule;
import com.davidstemmer.screenplay.sample.mortar.presenter.DrawerPresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;

public class MainActivity extends ActionBarActivity implements Blueprint {

    @Inject Flow flow;
    @Inject Screenplay screenplay;
    @Inject MortarActivityDirector activityDirector;
    @Inject DrawerPresenter drawerPresenter;

    private DrawerLayout navigationDrawer;

    private MortarActivityScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        MortarScope parentScope = Mortar.getScope(getApplication());
        activityScope = Mortar.requireActivityScope(parentScope, this);
        activityScope.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Mortar.inject(this, this);

        ButterKnife.inject(this, this);

        navigationDrawer = (DrawerLayout) findViewById(R.id.drawer_parent);
        activityDirector.takeView(this);
        drawerPresenter.takeView(navigationDrawer);

        screenplay.enter(flow);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerPresenter.syncToggleState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerPresenter.onConfigurationChanged(newConfig);
    }

    @Override public void onBackPressed() {
        if (!flow.goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (drawerPresenter.isLockedOpen() || drawerPresenter.isLockedShut()) {
               return true;
            }
            else if (drawerPresenter.onOptionsItemSelected(item)) {
               return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public Object getSystemService(String name) {
        if (Mortar.isScopeSystemService(name)) {

            return activityScope;
        }
        return super.getSystemService(name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        activityScope.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        // Drop the activity and drawer presenter every time the Activity is destroyed.
        // This allows them to release references to the Activity that is about to be destroyed.
        activityDirector.dropView(this);
        drawerPresenter.dropView(navigationDrawer);

        if (isFinishing()) {
            // Destroy the Activity scope only if the Activity is finishing (back button press).
            // This is so singletons, such as Scenes, can survive configuration changes.
            MortarScope parentScope = Mortar.getScope(getApplication());
            parentScope.destroyChild(activityScope);
            activityScope = null;
        }
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new ActivityModule();
    }


}
