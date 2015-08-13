package com.davidstemmer.screenplay.sample.simple;

import android.app.Application;

import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.simple.scene.WelcomeScene;

import flow.Flow;
import flow.History;

/**
 * Created by weefbellington on 10/2/14.
 */
public class SampleApplication extends Application {

    public final MutableStage stage = new MutableStage();
    public final Screenplay screenplay = new Screenplay(stage);
    public final DrawerHelper drawerHelper = new DrawerHelper(stage);
    public final Flow mainFlow = new Flow(History.single(new WelcomeScene(this)));

    private static SampleApplication application;

    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static SampleApplication getInstance()       { return application; }
    public static MutableStage getStage()               { return getInstance().stage; }
    public static Screenplay getScreenplay()            { return getInstance().screenplay; }
    public static Flow getMainFlow()                    { return getInstance().mainFlow; }
    public static DrawerHelper getDrawerHelper()        { return getInstance().drawerHelper; }
}
