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

/**
 * @version 1.0.0
 * @author  David Stemmer
 * @since   1.0.0
 */
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
        Scene.Rigger delegatedRigger;

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

        /**
        if (direction == Flow.Direction.FORWARD || direction == Flow.Direction.REPLACE) {

            sceneCut.addIncomingScene(incomingScene);
            // If we're navigating from a block of scenes (one normal scene, one or more modals
            // stacked on top of it), add each the scenes in the block to the scene cut so they all
            // animate when the transition occurs.
            Backstack currentBackstack = trimBackstack(nextBackstack, 1);
            ArrayDeque<Scene> sceneBlock = getLastSceneBlock(currentBackstack);
            for (Scene scene : sceneBlock) {
                sceneCut.addOutgoingScene(scene);
            }
        }

        else if (incomingScene.getRigger()) {
            // Navigating backwards from a modal view...
            // - the incoming view is already attached to the scene
            // - we're not transitioning from a scene block
            // - we're not transitioning to a scene block
            delegatedTransformer = outgoingScene.getTransformer();
            sceneCut.addIncomingScene(incomingScene);
            sceneCut.addOutgoingScene(outgoingScene);
        }
        else {
            // Navigating backwards from a non-modal view:
            // - the incoming view does is not attached to the scene
            // - we may be transitioning to a scene block. Rewind to the most recent non-modal
            //   view in the backstack, then replay them, attaching the modal views in order.
            delegatedTransformer = outgoingScene.getTransformer();

            ArrayDeque<Scene> sceneBlock = getLastSceneBlock(nextBackstack);
            Iterator<Scene> sceneIterator = sceneBlock.iterator();

            while (sceneIterator.hasNext()) {
                Scene scene = sceneIterator.next();
                scene.setUp(director.getActivity(), director.getContainer());
                sceneCut.addIncomingScene(scene);
            }
            sceneCut.addOutgoingScene(outgoingScene);
        }
        **/
    }

    private Backstack trimBackstack(Backstack backstack, int numItems) {
        Backstack.Builder builder = backstack.buildUpon();
        for (int i = 0; i< numItems; i++) {
            builder.pop();
        }
        return builder.build();
    }

    private ArrayDeque<Scene> getLastSceneBlock(Backstack backstack) {
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
            scene.setUp(director.getActivity(), director.getContainer());
            attachToParent(scene.getView());
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
            View removed = scene.tearDown(director.getActivity(), director.getContainer());
            removeFromParent(removed);
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
        boolean isSceneAttached = false;
        Iterator<Backstack.Entry> iterator = flow.getBackstack().reverseIterator();
        while (iterator.hasNext()) {
            Scene nextScene = (Scene) iterator.next().getScreen();
            if (nextScene.getView() != null) {
                ViewGroup parent = (ViewGroup) nextScene.getView().getParent();
                parent.removeView(nextScene.getView());
                attachToParent(nextScene.getView());
                isSceneAttached = true;
            }
        }
        if (!isSceneAttached) {
            flow.replaceTo(flow.getBackstack().current().getScreen());
        }
    }

    /**
     * @version 1.0.0
     * @author  David Stemmer
     * @since   1.0.0
     */
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
        director.getContainer().removeView(view);
    }
}
