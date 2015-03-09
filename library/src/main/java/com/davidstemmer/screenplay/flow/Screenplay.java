package com.davidstemmer.screenplay.flow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.scene.Scene;

import java.util.ArrayDeque;
import java.util.Iterator;

import flow.Backstack;
import flow.Flow;

public class Screenplay implements Flow.Listener {

    private final Director director;

    private Scene outgoingScene;
    private SceneState screenState = SceneState.NORMAL;

    public Screenplay(Director director) {
        this.director = director;
    }

    @Override
    public void go(Backstack nextBackstack, Flow.Direction direction, Flow.Callback callback) {

        Scene incomingScene = (Scene) nextBackstack.current().getScreen();

        ArrayDeque<Scene> incomingScenes;
        ArrayDeque<Scene> outgoingScenes;

        SceneCut.Builder sceneCut = new SceneCut.Builder()
                .setDirection(direction)
                .setCallback(callback);

        if (direction == Flow.Direction.BACKWARD) {
            if (outgoingScene.isStacking()) {
                incomingScenes = new ArrayDeque<>();
            } else {
                incomingScenes = getLastSceneBlock(nextBackstack);
            }
            outgoingScenes = new ArrayDeque<>();
            outgoingScenes.push(outgoingScene);
        }
        else {
            incomingScenes = new ArrayDeque<>();
            incomingScenes.push(incomingScene);
            // Forward on a stacked scene:
            // No outgoing scenes to animate or tear down.
            // Forward on a non-stacked scene:
            // Animate and tear down the current scene block.
            if (incomingScene.isStacking() || outgoingScene == null) {
                outgoingScenes = new ArrayDeque<>();
            } else if (nextBackstack.size() > 1) {
                outgoingScenes = getLastSceneBlock(trimBackstack(nextBackstack, 1));
            } else {
                outgoingScenes = new ArrayDeque<>();
                outgoingScenes.add(outgoingScene);
            }

        }

        Scene.Transformer delegatedTransformer = direction == Flow.Direction.BACKWARD ?
                outgoingScene.getTransformer() : incomingScene.getTransformer();

        for (Scene scene : incomingScenes) {
            sceneCut.addIncomingScene(scene);
        }

        for (Scene scene : outgoingScenes) {
            sceneCut.addOutgoingScene(scene);
        }

        outgoingScene = incomingScene;
        beginCut(sceneCut.build(), delegatedTransformer);
    }

    /**
     * Checks whether the scene present in the backstack and attached to the window.
     * More formally, returns <tt>true</tt> if and only if the backstack contains
     * at least one element <tt>e</tt> such that <tt>o.equals(e)</tt>, and its view is not null.
     * @param scene the scene to compare against
     * @param backstack a backstack of scenes
     * @return true if the scene is attached, false otherwise
     */
    public static boolean isSceneAttached(Scene scene, Backstack backstack) {
        return isSceneInBackstack(scene, backstack) && scene.getView() != null;
    }

    /**
     * Checks whether the scene present in the backstack.
     * More formally, returns <tt>true</tt> if and only if the backstack contains
     * at least one element <tt>e</tt> such that <tt>o.equals(e)</tt>.
     * @param scene the target scene
     * @param backstack a backstack of scenes
     * @return true if the scene is in the backstack, false otherwise
     */
    public static boolean isSceneInBackstack(Scene scene, Backstack backstack) {
        for (Backstack.Entry entry : backstack) {
            if (entry.getScreen().equals(scene)) {
                return true;
            }
        }
        return false;
    }

    private Backstack trimBackstack(Backstack backstack, int numItems) {
        Backstack.Builder builder = backstack.buildUpon();
        for (int i = 0; i< numItems; i++) {
            builder.pop();
        }
        return builder.build();
    }

    private static ArrayDeque<Scene> getLastSceneBlock(Backstack backstack) {
        Iterator<Backstack.Entry> iterator = backstack.iterator();
        ArrayDeque<Scene> stackedScenes = new ArrayDeque<>();

        while(iterator.hasNext()) {
            Scene scene = (Scene) iterator.next().getScreen();
            stackedScenes.push(scene);
            if (!scene.isStacking()) {
                return stackedScenes;
            }
        }
        throw new IllegalStateException("Modal view cannot be the root of a scene stack.");
    }

    public void beginCut(SceneCut cut, Scene.Transformer transformer) {
        for (Scene scene : cut.incomingScenes) {
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
        for (Scene scene : cut.outgoingScenes) {
            boolean isFinishing = cut.direction != Flow.Direction.FORWARD;
            tearDownComponents(scene, isFinishing);
            tearDownScene(scene, isFinishing);
        }
        screenState = SceneState.NORMAL;
        cut.callback.onComplete();
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
     * @param flow the current Flow
     */
    public void enter(Flow flow) {
        if (flow.getBackstack().size() == 0) {
            throw new IllegalStateException("Backstack is empty");
        }

        ArrayDeque<Scene> scenes = getLastSceneBlock(flow.getBackstack());
        Iterator<Scene> sceneIterator = scenes.iterator();
        Iterator<Scene> reverseSceneBlockIterator = scenes.descendingIterator();

        Scene firstScene = scenes.getFirst();
        if (scenes.size() == 1 && scenes.getFirst().getView() == null) {
            flow.replaceTo(firstScene);
        }
        else {
            while (reverseSceneBlockIterator.hasNext()) {
                Scene nextScene = reverseSceneBlockIterator.next();
                if (nextScene.teardownOnConfigurationChange()) {
                    tearDownComponents(nextScene, false);
                    tearDownScene(nextScene, false);
                } else {
                    removeFromParent(nextScene.getView());
                }
            }

            while(sceneIterator.hasNext()) {
                Scene nextScene = sceneIterator.next();
                if (nextScene.teardownOnConfigurationChange()) {
                    setUpScene(nextScene, false);
                    setUpComponents(nextScene, false);
                } else {
                    attachToParent(nextScene.getView());
                }
            }
        }
    }

    public interface Director {
        /**
         * @return the current Activity. Should be re-initialized after configuration changes.
         */
        public Activity getActivity();
        /**
         * @return the container for the Flow. Should be re-initialized after configuration changes.
         */
        public ViewGroup getContainer();
    }

    private void attachToParent(View view) {
        director.getContainer().addView(view);
    }

    private void removeFromParent(View view) {
        ViewGroup parent = (ViewGroup) view.getParent();
        parent.removeView(view);
    }

    private void setUpScene(Scene scene, boolean isStarting) {
        View added = scene.setUp(director.getActivity(), director.getContainer(), isStarting);
        attachToParent(added);
    }

    private void tearDownScene(Scene scene, boolean isFinishing) {
        View removed = scene.tearDown(director.getActivity(), director.getContainer(), isFinishing);
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
