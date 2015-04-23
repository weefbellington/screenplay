package com.davidstemmer.screenplay.sample.mortar.module;

import android.app.Application;

import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.scene.StandardScene;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides @Singleton
    Flow provideFlow(Screenplay screenplay) {
        return new Flow(Backstack.single(new StandardScene() {
            @Override
            public Transformer getTransformer() {
                return null;
            }
        }), screenplay);
    }

    @Provides @Singleton
    Screenplay screenplay() {
        return new Screenplay();
    }


    @Provides @Singleton
    MutableStage provideStage() {
        return new MutableStage();
    }

}