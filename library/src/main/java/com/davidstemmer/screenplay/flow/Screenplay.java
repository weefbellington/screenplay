package com.davidstemmer.screenplay.flow;

import android.app.Activity;
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

    private final Director director;

    private Scene previousScene;
    private SceneState screenplay = SceneState.NORMAL;

    public Screenplay(Director director) {
        this.director = director;
    }

    @Override
    public void go(Backstack nextBackstack, Flow.Direction direction, Flow.Callback callback) {

        screenplay = SceneState.TRANSITIONING;

        Scene nextScene = (Scene) nextBackstack.current().getScreen();

        SceneCut sceneCut = new SceneCut.Builder()
                .setDirection(direction)
                .setNextScene(nextScene)
                .setPreviousScene(previousScene)
                .setCallback(callback).build();

        if (nextScene == previousScene) {
            callback.onComplete();
        }
        else if (direction == Flow.Direction.FORWARD || direction == Flow.Direction.REPLACE) {
            nextScene.getRigger().layoutNext(director.getActivity(), director.getContainer(), sceneCut);
            nextScene.getTransformer().applyAnimations(sceneCut, this);
        }
        else if (previousScene == null) {
            nextScene.getRigger().layoutNext(director.getActivity(), director.getContainer(), sceneCut);
            callback.onComplete();
        }
        else {
            previousScene.getRigger().layoutNext(director.getActivity(), director.getContainer(), sceneCut);
            previousScene.getTransformer().applyAnimations(sceneCut, this);
        }
        previousScene = nextScene;
    }


    public void endCut(SceneCut cut) {
        if (cut.direction == Flow.Direction.BACKWARD) {
            cut.previousScene.getRigger().layoutPrevious(director.getActivity(), director.getContainer(), cut);
        } else {
            cut.nextScene.getRigger().layoutPrevious(director.getActivity(), director.getContainer(), cut);
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
        boolean isSceneAttached = false;
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
                nextScene.getRigger().layoutNext(director.getActivity(), director.getContainer(), cut);
                previousScene = nextScene;
                isSceneAttached = true;
            }
        }
        if (!isSceneAttached) {
            flow.replaceTo(flow.getBackstack().current().getScreen());
        }
    }

    public interface Director {
        public Activity getActivity();
        public ViewGroup getContainer();
    }
}
