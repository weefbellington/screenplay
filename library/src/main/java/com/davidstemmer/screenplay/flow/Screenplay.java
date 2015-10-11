package com.davidstemmer.screenplay.flow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.util.CollectionUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import flow.Flow;


/**
 * The Screenplay object handles the navigation logic for a Screenplay application.
 */
public class Screenplay {

    private SceneState screenState = SceneState.NORMAL;

    private final Activity activity;
    private final ViewGroup container;

    public Screenplay(Activity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
    }

    public enum Direction {
        NONE, FORWARD, BACKWARD, REPLACE
    }

    public void dispatch(Deque<Scene> origin,
                         Deque<Scene> destination,
                         Direction direction,
                         Flow.TraversalCallback callback) {

        Deque<Scene> animatedScenesIn;
        Deque<Scene> animatedScenesOut;

        SceneCut.Builder sceneCut = new SceneCut.Builder()
                .setDirection(direction)
                .setCallback(callback);

        Deque<Scene> difference = direction == Direction.BACKWARD ?
                CollectionUtils.difference(origin, destination, CollectionUtils.emptyQueue(Scene.class)) :
                CollectionUtils.difference(destination, origin, CollectionUtils.emptyQueue(Scene.class));

        if (direction == Direction.BACKWARD) {
            animatedScenesOut = getLastSceneBlock(difference);
            animatedScenesIn = moveToNewSceneBlock(difference) ?
                    getLastSceneBlock(destination) :
                    new ArrayDeque<Scene>();
        }
        else {
            animatedScenesIn = getLastSceneBlock(difference);
            animatedScenesOut = moveToNewSceneBlock(difference) ?
                    getLastSceneBlock(origin) :
                    new ArrayDeque<Scene>();

        }

        Scene.Transformer delegatedTransformer = direction == Direction.BACKWARD ?
                animatedScenesOut.getFirst().getTransformer():
                animatedScenesIn.getFirst().getTransformer();

        sceneCut.setIncomingScenes(animatedScenesIn);
        sceneCut.setOutgoingScenes(animatedScenesOut);

        beginCut(sceneCut.build(), delegatedTransformer);
    }

    private boolean moveToNewSceneBlock(Deque<Scene> difference) {
        for (Scene scene : difference) {
            if (!scene.isStacking()) {
                return true;
            }
        }
        return false;
    }

    private Deque<Scene> validateDifference(Deque<Scene> difference) {
        if (difference.size() == 0) {
            //throw new IllegalStateException("Backstack validation error -- is the same Scene instance being added to the backstack more than once?");
        }
        return difference;
    }

    /**
     * A "scene block" is a group of associated scenes. A scene block is derived from a source list
     * by iterating (starting from the first/topmost scene) until a non-stacking scene is found
     * or the source list terminates.
     * @param difference the difference between the old backstack and the new backstack
     * @return a scene block to animate
     */
    private Deque<Scene> getLastSceneBlock(Deque<Scene> difference) {
        Deque<Scene> sceneBlock = new ArrayDeque<>();
        Iterator<Scene> sceneIterator = difference.iterator();
        while(sceneIterator.hasNext()) {
            Scene scene = sceneIterator.next();
            sceneBlock.add(scene);
            if (!scene.isStacking()) {
                break;
            }
        }
        return sceneBlock;
    }

    public void beginCut(SceneCut cut, Scene.Transformer transformer) {
        Iterator<Scene> decendingIterator = cut.incomingScenes.descendingIterator();
        while (decendingIterator.hasNext()) {
            Scene scene = decendingIterator.next();
            boolean isStarting = cut.direction == Direction.FORWARD || cut.direction == Direction.NONE;
            setUpScene(scene, isStarting);
            setUpComponents(scene, isStarting);
        }
        screenState = SceneState.TRANSITIONING;
        transformer.applyAnimations(cut, this);
    }

    /**
     * Called by the {@link com.davidstemmer.screenplay.scene.Scene.Transformer} after the scene
     * animation completes. Finishes pending layout operations and notifies the Flow.Callback.
     * @param cut contains the next and previous scene, and the flow direction
     */
    public void endCut(SceneCut cut) {
        Iterator<Scene> iterator = cut.outgoingScenes.iterator();
        while (iterator.hasNext()) {
            Scene scene = iterator.next();
            boolean isFinishing = cut.direction == Direction.BACKWARD || cut.direction == Direction.REPLACE;
            tearDownComponents(scene, isFinishing);
            tearDownScene(scene, isFinishing);
        }
        screenState = SceneState.NORMAL;
        cut.callback.onTraversalCompleted();
    }

    /**
     * @return TRANSITIONING if a transition is in process, NORMAL otherwise
     */
    public SceneState getScreenState() {
        return screenState;
    }

    public void exit(Deque<Scene> allScenes) {

        Deque<Scene> sceneBlock = getLastSceneBlock(allScenes);

        // the topmost scene is the first in the scene block
        // tear down the scenes in order, from top to bottom
        Iterator<Scene> sceneBlockIterator = sceneBlock.iterator();

        while(sceneBlockIterator.hasNext()) {
            Scene nextScene = sceneBlockIterator.next();
            if (nextScene.teardownOnConfigurationChange()) {
                tearDownComponents(nextScene, false);
                tearDownScene(nextScene, false);
            } else {
                removeFromParent(nextScene.getView());
            }
        }
    }

    private void attachToParent(View view) {
        container.addView(view);
    }

    private void removeFromParent(final View view) {

        // If we don't include the post() call, the following error occurs:
        // Attempt to read from field 'int android.view.View.mViewFlags' on a null object reference
        // So far I haven't been able to isolate the cause of this -- I think it might be related
        // to removing the view immediately after an animation ends, but I haven't been able to
        // reproduce it under controlled conditions.
        final ViewGroup parent = (ViewGroup) view.getParent();
        parent.post(new Runnable() {
            @Override
            public void run() {
                parent.removeView(view);
            }
        });
    }

    private void setUpScene(Scene scene, boolean isStarting) {
        View added = scene.setUp(activity, container, isStarting);
        attachToParent(added);
    }

    private void tearDownScene(Scene scene, boolean isFinishing) {
        View removed = scene.tearDown(activity, container, isFinishing);
        removeFromParent(removed);
    }

    private void setUpComponents(Scene scene, boolean isStarting) {
        for (Scene.Component component: scene.getComponents()) {
            component.afterSetUp(scene, isStarting);
        }
    }

    private void tearDownComponents(Scene scene, boolean isFinishing) {
        for (Scene.Component component: scene.getComponents()) {
            component.beforeTearDown(scene, isFinishing);
        }
    }
}
