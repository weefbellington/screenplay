package com.davidstemmer.screenplay.sample.mortar.module;

import android.app.Application;

import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.mortar.presenter.DrawerPresenter;
import com.davidstemmer.screenplay.sample.mortar.presenter.NavigationMenuPresenter;
import com.davidstemmer.screenplay.sample.mortar.scene.WelcomeScene;
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
    Application application();
    Flow flow();
    Screenplay screenplay();
    ActionDrawerTransformer actionDrawerTransformer();
    MutableStage stage();
    DrawerPresenter drawerPresenter();
    NavigationMenuPresenter menuPresenter();
    WelcomeScene initialScene();
}
