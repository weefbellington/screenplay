package com.davidstemmer.screenplay.flow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.scene.Scene;

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

        screenState = SceneState.TRANSITIONING;

        Scene incomingScene = (Scene) nextBackstack.current().getScreen();
        Scene.Rigger selectedRigger;
        Scene.Transformer selectedTransformer;

        SceneCut sceneCut = new SceneCut.Builder()
                .setDirection(direction)
                .setIncomingScene(incomingScene)
                .setOutgoingScene(outgoingScene)
                .setCallback(callback).build();

        if (incomingScene == outgoingScene) {
            callback.onComplete();
        }

        if (direction == Flow.Direction.BACKWARD && outgoingScene == null) {
            //Honestly, I have no idea how to reach this case. We might want to remove this line.
            callback.onComplete();
            return;
        }

        // Only call incomingScene.setUp if necessary. If we are exiting a modal scene, the View will
        // already exist.
        if (incomingScene.getView() == null) {
            incomingScene.setUp(director.getActivity(), director.getContainer());
        }

        if (direction == Flow.Direction.FORWARD || direction == Flow.Direction.REPLACE) {
            selectedRigger = incomingScene.getRigger();
            selectedTransformer = incomingScene.getTransformer();

        }
        else {
            selectedRigger = outgoingScene.getRigger();
            selectedTransformer = outgoingScene.getTransformer();
        }

        selectedRigger.layoutIncoming(director.getContainer(), incomingScene.getView(), direction);
        selectedTransformer.applyAnimations(sceneCut, this);

        outgoingScene = incomingScene;
    }


    /**
     * Called by the {@link com.davidstemmer.screenplay.scene.Scene.Transformer} after the scene
     * animation completes. Finishes pending layout operations and notifies the Flow.Callback.
     * @param cut contains the next and previous scene, and the flow direction
     */
    public void endCut(SceneCut cut) {

        if (cut.outgoingScene != null) {
            ViewGroup container = director.getContainer();
            View outgoingView = cut.outgoingScene.getView();
            Scene.Rigger rigger = cut.direction == Flow.Direction.BACKWARD ?
                    cut.outgoingScene.getRigger() :
                    cut.incomingScene.getRigger();
            boolean isViewDetached = rigger.layoutOutgoing(container, outgoingView, cut.direction);
            if (isViewDetached) {
                cut.outgoingScene.tearDown(director.getActivity(), container);
            }
        }

        cut.callback.onComplete();
        screenState = SceneState.NORMAL;
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
                nextScene.getRigger().layoutIncoming(director.getContainer(), nextScene.getView(), Flow.Direction.FORWARD);
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
}
