package com.davidstemmer.screenplay.sample.module;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.MainActivity;
import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.HomeScene;
import com.davidstemmer.screenplay.sample.scene.NavigationDrawerScene;
import com.davidstemmer.screenplay.sample.scene.PopupScene;
import com.davidstemmer.screenplay.sample.scene.WelcomeScene;
import com.davidstemmer.screenplay.sample.view.HomeView;
import com.davidstemmer.screenplay.sample.view.PopupView;
import com.davidstemmer.screenplay.sample.view.WelcomeView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
@Module(addsTo = ApplicationModule.class,
        injects = {
                HomeScene.class,
                HomeScene.Presenter.class,
                HomeView.class,
                MainActivity.class,
                NavigationDrawerScene.class,
                PopupScene.class,
                PopupScene.Presenter.class,
                PopupView.class,
                WelcomeScene.class,
                WelcomeScene.Presenter.class,
                WelcomeView.class
        })
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides @Singleton
    Activity provideActivity() {
        return activity;
    }

    @Provides @Singleton
    Screenplay provideWarpZone(Activity activity) {
        RelativeLayout container = (RelativeLayout) activity.findViewById(R.id.main);
        return new Screenplay(activity, container);
    }

    @Provides @Singleton
    Flow provideFlow(WelcomeScene welcomeStage, Screenplay screenplay) {
        return new Flow(Backstack.single(welcomeStage), screenplay);
    }
}