package com.davidstemmer.current.sample;

import android.app.Application;

import com.davidstemmer.current.sample.module.ApplicationModule;

import dagger.ObjectGraph;
import mortar.Mortar;
import mortar.MortarScope;

/**
 * Created by weefbellington on 10/2/14.
 */
public class SampleApplication extends Application {
    private MortarScope applicationScope;

    @Override public void onCreate() {
        super.onCreate();
        // Eagerly validate development builds (too slow for production).
        ObjectGraph rootGraph = ObjectGraph.create(new ApplicationModule(this));
        applicationScope = Mortar.createRootScope(BuildConfig.DEBUG, rootGraph);
    }

    @Override public Object getSystemService(String name) {
        if (Mortar.isScopeSystemService(name)) {
            return applicationScope;
        }
        return super.getSystemService(name);
    }
}
