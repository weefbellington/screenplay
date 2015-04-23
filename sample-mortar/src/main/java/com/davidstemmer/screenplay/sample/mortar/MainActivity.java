package com.davidstemmer.screenplay.sample.mortar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.mortar.module.ApplicationComponent;
import com.davidstemmer.screenplay.sample.mortar.presenter.DrawerPresenter;
import com.davidstemmer.screenplay.sample.mortar.presenter.NavigationMenuPresenter;

import flow.Flow;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout navigationDrawer;

    private Flow flow;
    private Screenplay screenplay;
    private MutableStage stage;
    private DrawerPresenter drawerPresenter;
    private NavigationMenuPresenter navMenuPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ApplicationComponent app = MainApplication.getComponent();
        flow = app.flow();
        screenplay = app.screenplay();
        stage = app.stage();
        drawerPresenter = app.drawerPresenter();
        navMenuPresenter = app.menuPresenter();

        stage.bind(this, (ViewGroup) findViewById(R.id.main), flow);

        navigationDrawer = (DrawerLayout) findViewById(R.id.drawer_parent);
        View navigationMenu = getLayoutInflater().inflate(R.layout.navigation_menu, navigationDrawer, false);
        navigationMenu.addOnAttachStateChangeListener(navMenuPresenter);
        navigationDrawer.addView(navigationMenu);
        drawerPresenter.take(navigationDrawer);

        screenplay.enter(stage, app.initialScene());
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
            screenplay.exit();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawerPresenter.drop();
    }

}
