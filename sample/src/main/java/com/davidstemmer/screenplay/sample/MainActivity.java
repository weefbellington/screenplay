package com.davidstemmer.screenplay.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.module.ActivityModule;
import com.davidstemmer.screenplay.sample.presenter.ActivityPresenter;
import com.davidstemmer.screenplay.sample.presenter.DrawerPresenter;
import com.davidstemmer.screenplay.sample.scene.NavigationDrawerScene;
import com.davidstemmer.screenplay.sample.scene.SimpleScene;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;

public class MainActivity extends Activity implements Blueprint {

    @Inject Flow flow;
    @Inject Screenplay screenplay;
    @Inject ActivityPresenter activityPresenter;
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

        getActionBar().setHomeButtonEnabled(true);
        ButterKnife.inject(this, this);

        navigationDrawer = (DrawerLayout) findViewById(R.id.drawer_parent);
        activityPresenter.takeView(this);
        drawerPresenter.takeView(navigationDrawer);

        getLayoutInflater().inflate(R.layout.navigation_drawer, navigationDrawer);
        navigationDrawer.invalidate();

        screenplay.enter(flow);
    }

    @Override public void onBackPressed() {
        if (!flow.goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (navigationDrawer.isDrawerVisible(Gravity.LEFT)) {
                    navigationDrawer.closeDrawer(Gravity.LEFT);
                } else {
                    navigationDrawer.openDrawer(Gravity.LEFT);
                }
                return true;
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
        if (isFinishing()) {
            activityPresenter.dropView(this);
            drawerPresenter.dropView(navigationDrawer);
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
