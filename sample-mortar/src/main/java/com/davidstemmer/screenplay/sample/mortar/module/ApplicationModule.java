package com.davidstemmer.screenplay.sample.mortar.module;

import android.app.Application;

import com.davidstemmer.screenplay.SimpleActivityDirector;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.mortar.MainActivity;
import com.davidstemmer.screenplay.sample.mortar.presenter.NavigationMenuPresenter;
import com.davidstemmer.screenplay.sample.mortar.scene.DialogScene;
import com.davidstemmer.screenplay.sample.mortar.scene.PagedScene1;
import com.davidstemmer.screenplay.sample.mortar.scene.PagedScene2;
import com.davidstemmer.screenplay.sample.mortar.scene.PagedScene3;
import com.davidstemmer.screenplay.sample.mortar.scene.StackedScene;
import com.davidstemmer.screenplay.sample.mortar.scene.WelcomeScene;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.HorizontalSlideTransformer;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
@Module(injects = {
        HorizontalSlideTransformer.class,
        DialogScene.class,
        DialogScene.Presenter.class,
        StackedScene.class,
        StackedScene.Presenter.class,
        MainActivity.class,
        NavigationMenuPresenter.class,
        PagedScene1.class,
        PagedScene1.Presenter.class,
        PagedScene2.class,
        PagedScene2.Presenter.class,
        PagedScene3.class,
        WelcomeScene.class,
})
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides @Singleton
    SimpleActivityDirector provideActivityDirector() {
        return new SimpleActivityDirector();
    }

    @Provides @Singleton
    Screenplay provideScreenplay(SimpleActivityDirector director) {
        return new Screenplay(director);
    }

    @Provides @Singleton
    Flow provideFlow(WelcomeScene welcomeScene, Screenplay screenplay) {
        return new Flow(Backstack.single(welcomeScene), screenplay);
    }

}