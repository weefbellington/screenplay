package com.davidstemmer.screenplay.sample.mortar.module;

import android.app.Application;

import com.davidstemmer.screenplay.SimpleActivityDirector;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.mortar.presenter.DrawerPresenter;
import com.davidstemmer.screenplay.sample.mortar.presenter.NavigationMenuPresenter;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.ActionDrawerTransformer;

import javax.inject.Singleton;

import dagger.Component;
import flow.Flow;

/**
 * Created by weefbellington on 3/12/15.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    // application class
    Application application();
    // screenplay boilerplate
    SimpleActivityDirector director();
    Flow flow();
    Screenplay screenplay();
    // screenplay transformers
    ActionDrawerTransformer actionDrawerTransformer();
    // presenter classes
    DrawerPresenter drawerPresenter();
    NavigationMenuPresenter menuPresenter();

}
