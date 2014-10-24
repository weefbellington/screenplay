package com.davidstemmer.screenplay.flow;

import android.content.Context;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.scene.Scene;

import java.util.Iterator;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/14/14.
 */
public class Screenplay implements Flow.Listener {

    private Context context;
    private ViewGroup container;

    private Scene previousScene;
    private SceneState screenplay = SceneState.NORMAL;

    public Screenplay(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    public void reset(Context newContext, ViewGroup newContainer) {
        this.context = newContext;
        this.container = newContainer;
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
            if (!nextScene.getView().isAttachedToWindow()) {
                nextScene.getDirector().layoutNext(context, container, pipe);
            }
            callback.onComplete();
        }
        else if (direction == Flow.Direction.FORWARD || direction == Flow.Direction.REPLACE) {
            nextScene.getDirector().layoutNext(context, container, pipe);
            nextScene.getTransformer().applyAnimations(pipe, this);
        }
        else if (previousScene == null) {
            nextScene.getDirector().layoutNext(context, container, pipe);
            callback.onComplete();
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

    public void enter(Flow flow) {
        if (flow.getBackstack().size() == 0) {
            throw new IllegalStateException("Backstack is empty");
        }
        if (flow.getBackstack().size() == 1) {
            flow.replaceTo(flow.getBackstack().current().getScreen());
        } else {
            Iterator<Backstack.Entry> iterator = flow.getBackstack().reverseIterator();
            Scene previousScene = null;
            while (iterator.hasNext()) {
                Scene nextScene = (Scene) iterator.next().getScreen();
                if (nextScene.getView() != null) {
                    SceneCut cut = new SceneCut.Builder()
                            .setNextScene(nextScene)
                            .setPreviousScene(previousScene)
                            .setDirection(Flow.Direction.FORWARD)
                            .build();
                    nextScene.getDirector().layoutNext(context, container, cut);
                    previousScene = nextScene;
                }
            }
        }
    }
}
