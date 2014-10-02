package com.davidstemmer.current.sample.module;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.davidstemmer.current.sample.MainActivity;
import com.davidstemmer.current.sample.R;
import com.davidstemmer.current.sample.screen.HomeScreen;
import com.davidstemmer.current.sample.screen.WelcomeScreen;
import com.davidstemmer.current.sample.view.WelcomeView;
import com.davidstemmer.flowcurrent.Current;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by weefbellington on 10/2/14.
 */
@Module(addsTo = ApplicationModule.class,
        injects = {
                HomeScreen.class,
                MainActivity.class,
                WelcomeScreen.class,
                WelcomeView.class,
                WelcomeScreen.Presenter.class
        })
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Activity provideActivity() {
        return activity;
    }

    @Provides @Singleton
    Current provideCurrent(Activity activity) {
        RelativeLayout content = (RelativeLayout) activity.findViewById(R.id.main);
        return new Current(activity, content);
    }
}