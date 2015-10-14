package com.davidstemmer.screenplay.flow;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.util.CollectionUtils;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;


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

    public void dispatch(Direction direction,
                         Deque<Scene> origin,
                         Deque<Scene> destination,
                         TransitionCallback callback) {

        final Deque<Scene> scenesIn = extractIncomingScenes(direction, origin, destination);
        final Deque<Scene> scenesOut = extractOutgoingScenes(direction, origin, destination);
        final Scene.Transformer delegatedTransformer = getDelegatedTransformer(direction, scenesIn, scenesOut);

        final Transition.Builder transition = new Transition.Builder()
                .setScreenplay(this)
                .setDirection(direction)
                .setCallback(callback);

        transition.setIncomingScenes(scenesIn);
        transition.setOutgoingScenes(scenesOut);

        beginStageTransition(transition.build(), delegatedTransformer);
    }

    private Deque<Scene> extractDifference(Direction direction,
                                           Deque<Scene> origin,
                                           Deque<Scene> destination) {
        return direction == Direction.BACKWARD ?
                CollectionUtils.difference(origin, destination, CollectionUtils.emptyQueue(Scene.class)) :
                CollectionUtils.difference(destination, origin, CollectionUtils.emptyQueue(Scene.class));
    }

    private Deque<Scene> extractOutgoingScenes(Direction direction,
                                               Deque<Scene> origin,
                                               Deque<Scene> destination) {
        Deque<Scene> changedScenes = extractDifference(direction, origin, destination);
        return direction == Direction.BACKWARD ?
                outgoingScenesBack(changedScenes) :
                outgoingScenesForward(changedScenes, origin);
    }


    private Deque<Scene> extractIncomingScenes(Direction direction,
                                               Deque<Scene> origin,
                                               Deque<Scene> destination) {
        Deque<Scene> changedScenes = extractDifference(direction, origin, destination);
        return direction == Direction.BACKWARD ?
                incomingScenesBack(changedScenes, destination) :
                incomingScenesForward(changedScenes);
    }

    private Scene.Transformer getDelegatedTransformer(Direction direction,
                                                      Deque<Scene> incomingScenes,
                                                      Deque<Scene> outgoingScenes) {
        return direction == Direction.BACKWARD ?
                outgoingScenes.getFirst().getTransformer():
                incomingScenes.getFirst().getTransformer();
    }

    private Deque<Scene> incomingScenesForward(Deque<Scene> addedScenes) {
        return takeUntilNonStackingFound(addedScenes);
    }

    private Deque<Scene> outgoingScenesForward(Deque<Scene> addedScenes, Deque<Scene> origin) {
        return areAllStacking(addedScenes) ?
                new ArrayDeque<Scene>() :
                takeUntilNonStackingFound(origin);
    }

    private Deque<Scene> incomingScenesBack(Deque<Scene> removedScenes, Deque<Scene> destination) {
        return areAllStacking(removedScenes) ?
                new ArrayDeque<Scene>() :
                takeUntilNonStackingFound(destination);
    }

    private Deque<Scene> outgoingScenesBack(Deque<Scene> removedScenes) {
        return takeUntilNonStackingFound(removedScenes);
    }

    private boolean areAllStacking(Deque<Scene> difference) {
        for (Scene scene : difference) {
            if (!scene.isStacking()) {
                return false;
            }
        }
        return true;
    }

    private Deque<Scene> validateDifference(Deque<Scene> difference) {
        if (difference.size() == 0) {
            //throw new IllegalStateException("Backstack validation error -- is the same Scene instance being added to the backstack more than once?");
        }
        return difference;
    }

    /**
     * Derive a new queue from the source queue by taking scenes from the input queue until a
     * non-stacking scene is found.
     *
     * @param input the source list
     * @return the output list
     */
    private Deque<Scene> takeUntilNonStackingFound(Deque<Scene> input) {
        Deque<Scene> sceneBlock = new ArrayDeque<>();
        Iterator<Scene> sceneIterator = input.iterator();
        while(sceneIterator.hasNext()) {
            Scene scene = sceneIterator.next();
            sceneBlock.add(scene);
            if (!scene.isStacking()) {
                break;
            }
        }
        return sceneBlock;
    }

    private void beginStageTransition(Transition transition, Scene.Transformer transformer) {
        Iterator<Scene> decendingIterator = transition.incomingScenes.descendingIterator();
        while (decendingIterator.hasNext()) {
            Scene scene = decendingIterator.next();
            boolean isStarting = isSceneStarting(transition.direction);
            setUpScene(scene, isStarting);
            setUpComponents(scene, isStarting);
        }
        screenState = SceneState.TRANSITIONING;
        transformer.applyAnimations(transition);
    }

    /**
     * Called by the {@link com.davidstemmer.screenplay.scene.Scene.Transformer} after the scene
     * animation completes. Finishes pending layout operations and notifies the Flow.Callback.
     * @param transition contains the next and previous scene, and the flow direction
     */
    public void endStageTransition(Transition transition) {
        Iterator<Scene> iterator = transition.outgoingScenes.iterator();
        while (iterator.hasNext()) {
            Scene scene = iterator.next();
            boolean isFinishing = isSceneFinishing(transition.direction);
            tearDownComponents(scene, isFinishing);
            tearDownScene(scene, isFinishing);
        }
        screenState = SceneState.NORMAL;
        transition.callback.onTransitionCompleted();
    }

    private boolean isSceneStarting(Direction direction) {
        switch (direction) {
            case NONE:
            case FORWARD:
                return true;
            default:
                return false;
        }
    }

    private boolean isSceneFinishing(Direction direction) {
        switch (direction) {
            case BACKWARD:
            case REPLACE:
                return true;
            default:
                return false;
        }
    }

    /**
     * @return TRANSITIONING if a transition is in process, NORMAL otherwise
     */
    public SceneState getScreenState() {
        return screenState;
    }

    public void teardownVisibleScenes(Deque<Scene> sceneQueue) {

        Deque<Scene> visibleScenes = takeUntilNonStackingFound(sceneQueue);

        // the topmost scene is the first in the scene block
        // tear down the scenes in order, from top to bottom
        Iterator<Scene> visibleSceneIterator = visibleScenes.iterator();

        while(visibleSceneIterator.hasNext()) {
            Scene nextScene = visibleSceneIterator.next();
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

    public interface TransitionCallback {
        void onTransitionCompleted();
    }

    public static class Transition {

        public final Screenplay.Direction direction;
        public final ArrayDeque<Scene> incomingScenes;
        public final ArrayDeque<Scene> outgoingScenes;

        private final Screenplay screenplay;
        private final Screenplay.TransitionCallback callback;

        private Transition(Builder builder) {

            screenplay = builder.screenplay;
            callback = builder.callback;

            direction = builder.direction;
            incomingScenes = builder.incomingScenes;
            outgoingScenes = builder.outgoingScenes;
        }

        public void end() {
            screenplay.endStageTransition(this);
        }

        public static class Builder {

            Screenplay screenplay;
            Screenplay.Direction direction;
            Screenplay.TransitionCallback callback;
            final ArrayDeque<Scene> incomingScenes = new ArrayDeque<>();
            final ArrayDeque<Scene> outgoingScenes = new ArrayDeque<>();

            public Builder() {}

            public Transition build() {
                return new Transition(this);
            }

            public Builder setDirection(Screenplay.Direction direction) {
                this.direction = direction;
                return this;
            }

            public Builder setCallback(Screenplay.TransitionCallback callback) {
                this.callback = callback;
                return this;
            }


            public Builder setIncomingScenes(Collection<Scene> incoming) {
                incomingScenes.addAll(incoming);
                return this;
            }

            public Builder setOutgoingScenes(Collection<Scene> outgoing) {
                outgoingScenes.addAll(outgoing);
                return this;
            }

            public Builder setScreenplay(Screenplay screenplay) {
                this.screenplay = screenplay;
                return this;
            }
        }
    }
}
