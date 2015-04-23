package com.davidstemmer.screenplay.sample.mortar;

import android.app.Application;

import com.davidstemmer.screenplay.sample.mortar.module.ApplicationComponent;
import com.davidstemmer.screenplay.sample.mortar.module.ApplicationModule;
import com.davidstemmer.screenplay.sample.mortar.module.DaggerApplicationComponent;

/**
 * Created by weefbellington on 3/12/15.
 */
public class MainApplication extends Application
{

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public static ApplicationComponent getComponent() {
        return component;
    }

}
