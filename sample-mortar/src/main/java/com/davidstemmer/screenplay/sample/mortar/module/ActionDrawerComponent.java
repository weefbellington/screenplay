package com.davidstemmer.screenplay.sample.mortar.module;

import com.davidstemmer.screenplay.sample.mortar.scene.ActionDrawerScene;

import dagger.Component;

/**
 * Created by weefbellington on 3/12/15.
 */

@ActionDrawerScene.Scope
@Component(dependencies = ApplicationComponent.class, modules = ActionDrawerModule.class)
public interface ActionDrawerComponent extends ApplicationComponent {
    ActionDrawerScene actionDrawerScene();
}