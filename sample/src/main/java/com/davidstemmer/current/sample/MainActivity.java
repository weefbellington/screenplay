package com.davidstemmer.current.sample;

import android.app.Activity;
import android.os.Bundle;

import com.davidstemmer.current.sample.module.ActivityModule;
import com.davidstemmer.current.sample.screen.WelcomeScreen;
import com.davidstemmer.flowcurrent.Current;

import javax.inject.Inject;

import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;

public class MainActivity extends Activity implements Blueprint{

    @Inject Current current;
    @Inject WelcomeScreen welcomeScreen;

    private MortarScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MortarScope parentScope = Mortar.getScope(getApplication());
        activityScope = Mortar.requireActivityScope(parentScope, this);
        Mortar.inject(this, this);

        current.switchFlow(welcomeScreen);

    }

    @Override public void onBackPressed() {
        if (!current.goBack()) {
            super.onBackPressed();
        }
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
        // no need to call Current.stopFlow since activity scope is being destroyed?
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
