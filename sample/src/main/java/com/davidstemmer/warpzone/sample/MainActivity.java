package com.davidstemmer.warpzone.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.davidstemmer.warpzone.SceneState;
import com.davidstemmer.warpzone.flow.Screenplay;
import com.davidstemmer.warpzone.sample.module.ActivityModule;
import com.davidstemmer.warpzone.sample.stage.NavigationDrawerScene;
import com.davidstemmer.warpzone.sample.stage.WelcomeScene;

import javax.inject.Inject;

import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;

public class MainActivity extends Activity implements Blueprint {

    @Inject Flow flow;
    @Inject Screenplay screenplay;
    @Inject NavigationDrawerScene navigationDrawerScene;
    @Inject WelcomeScene welcomeStage;

    private MortarScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setHomeButtonEnabled(true);

        MortarScope parentScope = Mortar.getScope(getApplication());
        activityScope = Mortar.requireActivityScope(parentScope, this);
        Mortar.inject(this, this);

        flow.replaceTo(welcomeStage);
    }

    private boolean isNavigationDrawerOpen() {
        return findViewById(R.id.navigation_drawer) != null;
    }

    @Override public void onBackPressed() {
        if (!flow.goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Ignore menu click if stage is transitioning
        if (screenplay.getScreenplay() == SceneState.WARPING) return true;

        switch (item.getItemId()) {
            case android.R.id.home:
                if (isNavigationDrawerOpen()) {
                    flow.goBack();
                } else {
                    flow.goTo(navigationDrawerScene);
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
    protected void onDestroy() {
        super.onDestroy();
        MortarScope parentScope = Mortar.getScope(getApplication());
        parentScope.destroyChild(activityScope);
        activityScope = null;
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new ActivityModule(this);
    }


}
