package com.davidstemmer.screenplay.sample.mortar.module;

import com.davidstemmer.screenplay.ImmutableStage;
import com.davidstemmer.screenplay.MutableStage;
import com.davidstemmer.screenplay.Stage;
import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.sample.mortar.MainActivity;
import com.davidstemmer.screenplay.sample.mortar.presenter.NavigationMenuPresenter;
import com.davidstemmer.screenplay.sample.mortar.scene.DialogStage;
import com.davidstemmer.screenplay.sample.mortar.scene.PagedStage1;
import com.davidstemmer.screenplay.sample.mortar.scene.PagedStage2;
import com.davidstemmer.screenplay.sample.mortar.scene.PagedStage3;
import com.davidstemmer.screenplay.sample.mortar.scene.StackedStage;
import com.davidstemmer.screenplay.sample.mortar.scene.WelcomeStage;
import com.davidstemmer.screenplay.sample.mortar.view.DialogSceneView;
import com.davidstemmer.screenplay.sample.mortar.view.ModalSceneView;
import com.davidstemmer.screenplay.sample.mortar.view.NavigationMenuView;
import com.davidstemmer.screenplay.sample.mortar.view.PagedView1;
import com.davidstemmer.screenplay.sample.mortar.view.PagedView2;
import com.davidstemmer.screenplay.sample.mortar.view.WelcomeView;

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
                DialogStage.class,
                DialogStage.Presenter.class,
                DialogSceneView.class,
                StackedStage.class,
                StackedStage.Presenter.class,
                ModalSceneView.class,
                MainActivity.class,
                NavigationMenuPresenter.class,
                NavigationMenuView.class,
                NavigationMenuView.class,
                PagedStage1.class,
                PagedStage1.Presenter.class,
                PagedView1.class,
                PagedStage2.class,
                PagedStage2.Presenter.class,
                PagedView2.class,
                PagedStage3.class,
                WelcomeStage.class,
                WelcomeStage.Presenter.class,
                WelcomeView.class
        })
public class ActivityModule {

    private ImmutableStage stage;


    @Provides @Singleton
    MutableStage mainStage() {
        return new MutableStage();
    }

    @Provides @Singleton
    Stage stageInterface(MutableStage mainStage) {
        return mainStage;
    }


    @Provides @Singleton
    Screenplay screenplay(Stage stage) {
        return new Screenplay(stage);
    }

    @Provides @Singleton
    Flow flow(WelcomeStage welcomeScene, Screenplay screenplay) {
        return new Flow(Backstack.single(welcomeScene), screenplay);
    }
}