package com.davidstemmer.screenplay.sample.mortar.module;

import com.davidstemmer.screenplay.sample.mortar.scene.ActionDrawerScene;
import com.davidstemmer.screenplay.scene.component.CallbackComponent;
import com.davidstemmer.screenplay.scene.component.ResultHandler;

import dagger.Module;
import dagger.Provides;

/**
 * Created by weefbellington on 3/12/15.
 */
@Module
public class ActionDrawerModule {

    private final ActionDrawerScene.Callback callback;

    public ActionDrawerModule(ActionDrawerScene.Callback callback) {
        this.callback = callback;
    }

    @Provides @ActionDrawerScene.Scope
    ResultHandler<ActionDrawerScene.Result> provideResultHandler() {
        return new ResultHandler<ActionDrawerScene.Result>(ActionDrawerScene.Result.CANCELLED);
    }

    @Provides @ActionDrawerScene.Scope
    CallbackComponent<ActionDrawerScene.Result> provideCallbackComponent(ResultHandler<ActionDrawerScene.Result> resultHandler) {
        return new CallbackComponent<ActionDrawerScene.Result>(callback, resultHandler);
    }
}