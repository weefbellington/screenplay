package com.davidstemmer.screenplay.sample.simple;

import android.app.Application;

import com.davidstemmer.screenplay.sample.simple.scene.WelcomeStage;

import flow.Flow;
import flow.History;

/**
 * Created by weefbellington on 10/2/14.
 */
public class SampleApplication extends Application {

    private final DrawerHelper drawerHelper = new DrawerHelper();
    public final Flow mainFlow = new Flow(History.single(new WelcomeStage(this)));

    private static SampleApplication application;

    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static SampleApplication getInstance()       { return application; }
    public static Flow getMainFlow()                    { return getInstance().mainFlow; }
    public static DrawerHelper getDrawerHelper()        { return getInstance().drawerHelper; }
}
