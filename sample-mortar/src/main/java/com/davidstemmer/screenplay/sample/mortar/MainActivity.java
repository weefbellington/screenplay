package com.davidstemmer.screenplay.sample.mortar;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SimpleActivityDirector;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.mortar.module.ApplicationComponent;
import com.davidstemmer.screenplay.sample.mortar.module.ApplicationModule;
import com.davidstemmer.screenplay.sample.mortar.module.Dagger_ApplicationComponent;
import com.davidstemmer.screenplay.sample.mortar.presenter.DrawerPresenter;
import com.davidstemmer.screenplay.sample.mortar.presenter.NavigationMenuPresenter;

import butterknife.ButterKnife;
import flow.Flow;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout navigationDrawer;
    private ApplicationComponent component;

    private Flow flow;
    private Screenplay screenplay;
    private DrawerPresenter drawerPresenter;
    private SimpleActivityDirector director;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        component = Dagger_ApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .build();

        component.binder().take(component);
        flow = component.flow();
        screenplay = component.screenplay();
        drawerPresenter = component.drawerPresenter();
        director = component.director();
        NavigationMenuPresenter navigationPresenter = component.menuPresenter();

        setContentView(R.layout.activity_main);
        ButterKnife.inject(this, this);

        navigationDrawer = (DrawerLayout) findViewById(R.id.drawer_parent);

        View navigationMenu = getLayoutInflater().inflate(R.layout.navigation_menu, navigationDrawer, false);
        navigationMenu.addOnAttachStateChangeListener(navigationPresenter);
        navigationDrawer.addView(navigationMenu);

        director.bind(this, (ViewGroup) findViewById(R.id.main));
        drawerPresenter.onViewAttachedToWindow(navigationDrawer);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        drawerPresenter.onViewDetachedFromWindow(navigationDrawer);
    }
}
