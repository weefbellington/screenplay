package com.davidstemmer.screenplay.flow;

import android.content.Context;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.scene.Scene;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/14/14.
 */
public class Screenplay implements Flow.Listener {

    private final Context context;
    private final ViewGroup container;

    private Scene previousScene;
    private SceneState screenplay = SceneState.NORMAL;

    public Screenplay(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    @Override
    public void go(Backstack nextBackstack, Flow.Direction direction, Flow.Callback callback) {

        screenplay = SceneState.TRANSITIONING;

        Scene nextScene = (Scene) nextBackstack.current().getScreen();

        SceneCut pipe = new SceneCut.Builder()
                .setDirection(direction)
                .setNextScene(nextScene)
                .setPreviousScene(previousScene)
                .setCallback(callback).build();

        if (nextScene == previousScene) {
            callback.onComplete();
        }
        else if (direction == Flow.Direction.FORWARD || direction == Flow.Direction.REPLACE) {
            nextScene.getDirector().layoutNext(context, container, pipe);
            nextScene.getTransformer().applyAnimations(pipe, this);
        }
        else {
            previousScene.getDirector().layoutNext(context, container, pipe);
            previousScene.getTransformer().applyAnimations(pipe, this);
        }
        previousScene = nextScene;

    }

    public void endCut(SceneCut cut) {
        if (cut.direction == Flow.Direction.BACKWARD) {
            cut.previousScene.getDirector().layoutPrevious(context, container, cut);
        } else {
            cut.nextScene.getDirector().layoutPrevious(context, container,cut);
        }
        cut.callback.onComplete();
        screenplay = SceneState.NORMAL;
    }

    public SceneState getScreenState() {
        return screenplay;
    }
}
