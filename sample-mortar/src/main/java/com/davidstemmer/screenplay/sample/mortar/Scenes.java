package com.davidstemmer.screenplay.sample.mortar;

import com.davidstemmer.screenplay.sample.mortar.module.ActionDrawerComponent;
import com.davidstemmer.screenplay.sample.mortar.module.ActionDrawerModule;
import com.davidstemmer.screenplay.sample.mortar.module.ApplicationComponent;
import com.davidstemmer.screenplay.sample.mortar.module.DaggerActionDrawerComponent;
import com.davidstemmer.screenplay.sample.mortar.scene.ActionDrawerScene;

/**
 * Created by weefbellington on 4/23/15.
 */
public class Scenes {

    public static ActionDrawerScene actionDrawer(ActionDrawerScene.Callback callback) {
        ApplicationComponent parentComponent = MainApplication.getComponent();
        ActionDrawerComponent component = DaggerActionDrawerComponent.builder()
                .applicationComponent(parentComponent)
                .actionDrawerModule(new ActionDrawerModule(callback))
                .build();
        return component.actionDrawerScene();
    }
}
