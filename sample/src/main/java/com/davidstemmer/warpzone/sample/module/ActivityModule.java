package com.davidstemmer.warpzone.sample.module;

import android.app.Activity;
import android.widget.RelativeLayout;

import com.davidstemmer.warpzone.WarpZone;
import com.davidstemmer.warpzone.sample.MainActivity;
import com.davidstemmer.warpzone.sample.R;
import com.davidstemmer.warpzone.sample.stage.HomeStage;
import com.davidstemmer.warpzone.sample.stage.NavigationDrawerStage;
import com.davidstemmer.warpzone.sample.stage.PopupStage;
import com.davidstemmer.warpzone.sample.stage.WelcomeStage;
import com.davidstemmer.warpzone.sample.view.HomeView;
import com.davidstemmer.warpzone.sample.view.PopupView;
import com.davidstemmer.warpzone.sample.view.WelcomeView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by weefbellington on 10/2/14.
 */
@Module(addsTo = ApplicationModule.class,
        injects = {
                HomeStage.class,
                HomeStage.Presenter.class,
                HomeView.class,
                MainActivity.class,
                NavigationDrawerStage.class,
                PopupStage.class,
                PopupStage.Presenter.class,
                PopupView.class,
                WelcomeStage.class,
                WelcomeStage.Presenter.class,
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
    WarpZone provideWarpZone(Activity activity) {
        RelativeLayout content = (RelativeLayout) activity.findViewById(R.id.main);
        return new WarpZone(activity, content);
    }
}