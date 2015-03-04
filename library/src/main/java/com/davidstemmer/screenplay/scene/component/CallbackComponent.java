package com.davidstemmer.screenplay.scene.component;

import com.davidstemmer.screenplay.scene.Scene;

/**
 * Created by weefbellington on 11/10/14.
 */
public class CallbackComponent<R> implements Scene.Component {

    private final SceneCallback<R> callback;
    private final ResultHandler<R> resultHandler;

    public CallbackComponent(SceneCallback<R> callback, ResultHandler<R> resultHandler) {
        this.callback = callback;
        this.resultHandler = resultHandler;
    }

    @Override
    public void afterSetUp(Scene scene, boolean isInitializing) {}

    @Override
    public void beforeTearDown(Scene scene, boolean isFinishing) {
        if (isFinishing) {
            callback.onExitScene(resultHandler.getResult());
            resultHandler.reset();
        }
    }
}
