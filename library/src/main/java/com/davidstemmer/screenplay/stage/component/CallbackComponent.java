package com.davidstemmer.screenplay.stage.component;

import com.davidstemmer.screenplay.stage.Stage;

/**
 * Created by weefbellington on 11/10/14.
 */
public class CallbackComponent<R> implements Stage.Component {

    private final SceneCallback<R> callback;
    private final ResultHandler<R> resultHandler;

    public CallbackComponent(SceneCallback<R> callback, ResultHandler<R> resultHandler) {
        this.callback = callback;
        this.resultHandler = resultHandler;
    }

    @Override
    public void afterSetUp(Stage stage, boolean isInitializing) {}

    @Override
    public void beforeTearDown(Stage stage, boolean isFinishing) {
        if (isFinishing) {
            callback.onExitScene(resultHandler.getResult());
            resultHandler.reset();
        }
    }
}
