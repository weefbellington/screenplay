package com.davidstemmer.screenplay.scene.component;

/**
 * Created by weefbellington on 11/9/14.
 */
public interface SceneCallback<R> {
    public void onExitScene(R result);
}
