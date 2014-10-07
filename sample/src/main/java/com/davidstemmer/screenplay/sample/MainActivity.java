package com.davidstemmer.screenplay.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.flowlistener.ModalFlowListener;
import com.davidstemmer.screenplay.sample.module.ActivityModule;
import com.davidstemmer.screenplay.sample.scene.NavigationDrawerScene;
import com.davidstemmer.screenplay.sample.scene.WelcomeScene;

import javax.inject.Inject;

import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;

public class MainActivity extends Activity implements Blueprint {

    @Inject Screenplay screenplay;
    @Inject WelcomeScene welcomeScreen;
    @Inject NavigationDrawerScene navigationDrawerScene;

    private MortarScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActionBar().setHomeButtonEnabled(true);

        MortarScope parentScope = Mortar.getScope(getApplication());
        activityScope = Mortar.requireActivityScope(parentScope, this);
        Mortar.inject(this, this);

        screenplay.changeFlow(welcomeScreen);

    }

    @Override public void onBackPressed() {
        if (!screenplay.goBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                screenplay.changeFlow(navigationDrawerScene, new ModalFlowListener(screenplay));
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
