package com.davidstemmer.screenplay.sample.mortar;

import android.app.Application;

import com.davidstemmer.screenplay.sample.mortar.module.ApplicationComponent;
import com.davidstemmer.screenplay.sample.mortar.module.ApplicationModule;
import com.davidstemmer.screenplay.sample.mortar.module.Dagger_ApplicationComponent;

/**
 * Created by weefbellington on 3/12/15.
 */
public class MainApplication extends Application
{

    private static ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = Dagger_ApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        component.binder().take(component);
    }

    public static ApplicationComponent getComponent() {
        return component;
    }

}
