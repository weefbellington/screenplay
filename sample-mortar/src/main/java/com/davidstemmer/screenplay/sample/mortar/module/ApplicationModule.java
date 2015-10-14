package com.davidstemmer.screenplay.sample.mortar.module;

import android.app.Application;

import com.davidstemmer.screenplay.sample.mortar.scene.transformer.HorizontalSlideRigger;

import dagger.Module;
import dagger.Provides;

/**
 * Created by weefbellington on 10/2/14.
 */
@Module(injects = {
        HorizontalSlideRigger.class
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

}