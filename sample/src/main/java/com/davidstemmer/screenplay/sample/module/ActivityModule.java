package com.davidstemmer.screenplay.sample.module;

import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.MainActivity;
import com.davidstemmer.screenplay.sample.presenter.ActivityPresenter;
import com.davidstemmer.screenplay.sample.presenter.NavigationMenuPresenter;
import com.davidstemmer.screenplay.sample.scene.ActionDrawerScene;
import com.davidstemmer.screenplay.sample.scene.DialogScene;
import com.davidstemmer.screenplay.sample.scene.ModalScene;
import com.davidstemmer.screenplay.sample.scene.PagedScene1;
import com.davidstemmer.screenplay.sample.scene.PagedScene2;
import com.davidstemmer.screenplay.sample.scene.SimpleScene;
import com.davidstemmer.screenplay.sample.view.ActionDrawerView;
import com.davidstemmer.screenplay.sample.view.DialogSceneView;
import com.davidstemmer.screenplay.sample.view.ModalSceneView;
import com.davidstemmer.screenplay.sample.view.NavigationMenuView;
import com.davidstemmer.screenplay.sample.view.PagedView1;
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
                ActionDrawerScene.class,
                ActionDrawerScene.Presenter.class,
                ActionDrawerView.class,
                DialogScene.class,
                DialogScene.Presenter.class,
                DialogSceneView.class,
                ModalScene.class,
                ModalScene.Presenter.class,
                ModalSceneView.class,
                MainActivity.class,
                NavigationMenuPresenter.class,
                NavigationMenuView.class,
                NavigationMenuView.class,
                PagedScene1.class,
                PagedScene1.Presenter.class,
                PagedView1.class,
                PagedScene2.class,
                SimpleScene.class,
                SimpleScene.Presenter.class,
                WelcomeView.class
        })
public class ActivityModule {

    @Provides @Singleton
    Screenplay provideScreenplay(ActivityPresenter presenter) {
        return new Screenplay(presenter);
    }

    @Provides @Singleton
    Flow provideFlow(SimpleScene welcomeStage, Screenplay screenplay) {
        return new Flow(Backstack.single(welcomeStage), screenplay);
    }
}