package com.davidstemmer.screenplay.flow;

import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.Stage;
import com.davidstemmer.screenplay.scene.Scene;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import flow.Flow;


/**
 * The Screenplay object handles the navigation logic for a Screenplay application.
 */
public class Screenplay implements Flow.Dispatcher {

    private SceneState screenState = SceneState.NORMAL;

    private final Stage stage;

    public Screenplay(Stage stage) {
        this.stage = stage;
    }


    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {

        Deque<Scene> animatedScenesIn;
        Deque<Scene> animatedScenesOut;

        SceneCut.Builder sceneCut = new SceneCut.Builder()
                .setDirection(traversal.direction)
                .setCallback(callback);

        Deque<Scene> incoming = fromIterator(traversal.origin.iterator());
        Deque<Scene> outgoing = fromIterator(traversal.destination.iterator());

        if (traversal.direction == Flow.Direction.BACKWARD) {
            Deque<Scene> difference = difference(incoming, outgoing);
            animatedScenesOut = getLastSceneBlock(difference);
            animatedScenesIn = moveToNewSceneBlock(difference) ?
                    getLastSceneBlock(incoming) :
                    new ArrayDeque<Scene>();
        }
        else {
            Deque<Scene> difference = difference(incoming, outgoing);

            animatedScenesIn = getLastSceneBlock(difference);
            animatedScenesOut = moveToNewSceneBlock(difference) ?
                    getLastSceneBlock(outgoing) :
                    new ArrayDeque<Scene>();

        }

        Scene.Transformer delegatedTransformer = traversal.direction == Flow.Direction.BACKWARD ?
                animatedScenesOut.iterator().next().getTransformer():
                animatedScenesIn.iterator().next().getTransformer();

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

    private Deque<Scene> difference(Deque<Scene> incoming,
                                    Deque<Scene> outgoing) {
        ArrayDeque<Scene> difference = new ArrayDeque<>(incoming);
        difference.removeAll(outgoing);
        return validateDifference(difference);
    }

    private <T> Deque<T> fromIterator(Iterator iter) {
        ArrayDeque<T> deque = new ArrayDeque<T>();
        while (iter.hasNext()) {
            deque.addLast((T)iter.next());
        }
        return deque;
    }

    private Deque<Scene> validateDifference(Deque<Scene> difference) {
        if (difference.size() == 0) {
            throw new IllegalStateException("Backstack validation error -- is the same Scene instance being added to the backstack more than once?");
        }
        return difference;
    }

    /**
     * Checks whether the scene present in the backstack and attached to the window.
     * More formally, returns <tt>true</tt> if and only if the backstack contains
     * at least one element <tt>e</tt> such that <tt>o.equals(e)</tt>, and its view is not null.
     * @param scene the scene to compare against
     * @return true if the scene is attached, false otherwise
     */
    public boolean isSceneAttached(Scene scene) {
        return isSceneInBackstack(scene) && scene.getView() != null;
    }

    /**
     * Checks whether the scene present in the backstack.
     * More formally, returns <tt>true</tt> if and only if the backstack contains
     * at least one element <tt>e</tt> such that <tt>o.equals(e)</tt>.
     * @param scene the target scene
     * @return true if the scene is in the backstack, false otherwise
     */
    public boolean isSceneInBackstack(Scene scene) {
        for (Object entry : stage.getFlow().getHistory()) {
            if (entry.equals(scene)) {
                return true;
            }
        }
        return false;
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
            boolean isStarting = cut.direction != Flow.Direction.BACKWARD;
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
            boolean isFinishing = cut.direction != Flow.Direction.FORWARD;
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

    /**
     * Initialize the screen using the current Flow.Backstack. This is expected to be called in
     * Activity.onCreate(). Supports configuration changes.
     */
    public void enter() {
        Flow flow = stage.getFlow();

        if (stage.getFlow().getHistory().size() == 0) {
            throw new IllegalStateException("Backstack is empty");
        }

        Deque<Scene> allScenes = fromIterator(flow.getHistory().iterator());
        Deque<Scene> sceneBlock = getLastSceneBlock(allScenes);

        // the topmost scene is the first in the scene block
        // re-add the scenes in reverse order, from bottom top
        Iterator<Scene> sceneIterator = sceneBlock.descendingIterator();

        while(sceneIterator.hasNext()) {
            Scene nextScene = sceneIterator.next();
            if (nextScene.teardownOnConfigurationChange() || allScenes.isEmpty()) {
                setUpScene(nextScene, false);
                setUpComponents(nextScene, false);
            } else {
                attachToParent(stage, nextScene.getView());
            }
        }
    }

    public void exit() {

        Deque<Scene> allScenes = fromIterator(stage.getFlow().getHistory().iterator());
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

    private void attachToParent(Stage stage, View view) {
        stage.getContainer().addView(view);
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
        View added = scene.setUp(stage.getActivity(), stage.getContainer(), isStarting);
        attachToParent(stage, added);
    }

    private void tearDownScene(Scene scene, boolean isFinishing) {
        View removed = scene.tearDown(stage.getActivity(), stage.getContainer(), isFinishing);
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
