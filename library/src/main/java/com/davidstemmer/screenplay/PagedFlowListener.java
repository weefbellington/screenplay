package com.davidstemmer.screenplay;

import android.view.View;

import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.ScreenTransitions;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/1/14.
 */
public class PagedFlowListener implements Flow.Listener, Scene.TransitionListener {

    private Scene previousScene;

    private final Screenplay screenplay;

    public PagedFlowListener(Screenplay screenplay) {
        this.screenplay = screenplay;
    }

    @Override
    public final void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {
        screenplay.setSceneState(SceneState.TRANSITIONING);
        Scene nextScene = (Scene) entries.current().getScreen();

        View newChild = nextScene.getDirector().create(screenplay.context, nextScene, screenplay.container);

        Scene.Transition.Builder transitionBuilder = new Scene.Transition.Builder();
        if (previousScene != null) {
            transitionBuilder.setDirection(direction)
                    .setNextScene(nextScene)
                    .setPreviousScene(previousScene)
                    .setCallback(callback)
                    .setListener(this);
            ScreenTransitions.start(transitionBuilder.build());
            View oldChild = previousScene.getDirector().destroy(
                    screenplay.context,
                    previousScene,
                    screenplay.container);
            screenplay.container.removeView(oldChild);
        } else {
            transitionBuilder.setDirection(direction)
                    .setCallback(callback)
                    .setListener(this);
            onTransitionComplete(transitionBuilder.build());
        }

        screenplay.container.addView(newChild);
        previousScene = nextScene;

    }

    @Override
    public void onTransitionComplete(Scene.Transition transition) {
        transition.callback.onComplete();
        screenplay.setSceneState(SceneState.NORMAL);
    }
}
