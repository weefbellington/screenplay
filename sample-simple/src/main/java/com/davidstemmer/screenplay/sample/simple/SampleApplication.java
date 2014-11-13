package com.davidstemmer.screenplay.sample.simple;

import android.app.Application;

import com.davidstemmer.screenplay.SimpleActivityDirector;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.simple.scene.WelcomeScene;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class SampleApplication extends Application {

    public final SimpleActivityDirector director = new SimpleActivityDirector();
    public final Screenplay screenplay = new Screenplay(director);
    public final DrawerHelper drawerHelper = new DrawerHelper(director);
    public final Flow mainFlow = new Flow(Backstack.single(new WelcomeScene(this)), screenplay);

    private static SampleApplication application;

    public void onCreate() { application = this; }

    public static SampleApplication getInstance()       { return application; }
    public static SimpleActivityDirector getDirector()  { return getInstance().director; }
    public static Screenplay getScreenplay()            { return getInstance().screenplay; }
    public static Flow getMainFlow()                    { return getInstance().mainFlow; }
    public static DrawerHelper getDrawerHelper()        { return getInstance().drawerHelper; }
}
