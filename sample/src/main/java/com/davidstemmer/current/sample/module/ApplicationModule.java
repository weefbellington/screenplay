package com.davidstemmer.current.sample.module;

import android.app.Application;

import com.davidstemmer.current.sample.screen.transformer.HorizontalSlideTransformer;

import dagger.Module;
import dagger.Provides;

/**
 * Created by weefbellington on 10/2/14.
 */
@Module(injects = {
        HorizontalSlideTransformer.class
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