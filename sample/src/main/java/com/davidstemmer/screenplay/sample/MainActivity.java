package com.davidstemmer.screenplay.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.module.ActivityModule;
import com.davidstemmer.screenplay.sample.scene.NavigationDrawerScene;
import com.davidstemmer.screenplay.sample.scene.WelcomeScene;

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

        flow.resetTo(welcomeStage);
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
        if (screenplay.getScreenState() == SceneState.TRANSITIONING) return true;

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
