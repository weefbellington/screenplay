package com.davidstemmer.screenplay;

import android.app.Activity;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.internal.StageGroup;
import com.davidstemmer.screenplay.stage.Stage;
import com.davidstemmer.screenplay.util.CollectionUtils;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.Set;

/**
 * The Screenplay object handles the navigation logic for a Screenplay application.
 */
public class Screenplay {

    private boolean isTransitioning = false;

    private final Activity activity;
    private final ViewGroup container;

    public Screenplay(Activity activity, ViewGroup container) {
        this.activity = activity;
        this.container = container;
    }

    public enum Direction {
        NONE, FORWARD, BACKWARD, REPLACE
    }

    public void transition(Direction direction,
                           StageGroup origin,
                           StageGroup destination,
                           TransitionCallback callback) {

        final Deque<Stage> scenesIn = extractIncomingScenes(origin, destination);
        final Deque<Stage> scenesOut = extractOutgoingScenes(origin, destination);
        final Stage.Rigger delegatedRigger = getDelegatedRigger(direction, scenesIn, scenesOut);

        final Transition.Builder transition = new Transition.Builder()
                .setScreenplay(this)
                .setDirection(direction)
                .setCallback(callback);

        transition.setIncomingStages(scenesIn);
        transition.setOutgoingStages(scenesOut);

        beginStageTransition(transition.build(), delegatedRigger);
    }

    private Deque<Stage> symmetricDifference(
            StageGroup origin,
            StageGroup destination) {
        return CollectionUtils.symmetricDifference(destination, origin, emptyStageQueue());
    }

    private Deque<Stage> extractOutgoingScenes(
            StageGroup origin,
            StageGroup destination) {
        if (origin.getRoot().equals(destination.getRoot())) {
            if (destination.getStages().size() < origin.getStages().size()) {
                return symmetricDifference(origin, destination);
            }
            else {
                return emptyStageQueue();
            }
        }
        else {
            return origin.getStages();
        }

    }


    private Deque<Stage> extractIncomingScenes(StageGroup origin,
                                               StageGroup destination) {

        if (origin.getRoot().equals(destination.getRoot())) {
            if (destination.getStages().size() > origin.getStages().size()) {
                return symmetricDifference(origin, destination);
            }
            else {
                return emptyStageQueue();
            }
        } else {
            return destination.getStages();
        }
    }

    private Map<Scene, Stage.Rigger getDelegatedRigger(Direction direction,
            StageGroup origin,
            StageGroup destination
            Deque<Stage> incomingStages,
            Deque<Stage> outgoingStages) {
        if (isInterGroupTransition(origin, destination)) {
            return direction == Direction.BACKWARD ?
                    origin.getRoot().getRigger():
                    destination.getRoot().getRigger();
        } else {

        }
    }

    /**
     * Derive a new queue from the source queue by taking scenes from the input queue until a
     * non-stacking scene is found.
     *
     * @param input the source list
     * @return the output list
     */
    private Deque<Stage> takeUntilNonStackingFound(Deque<Stage> input) {
        Deque<Stage> stageBlock = new ArrayDeque<>();
        Iterator<Stage> sceneIterator = input.iterator();
        while(sceneIterator.hasNext()) {
            Stage stage = sceneIterator.next();
            stageBlock.add(stage);
            if (!stage.isModal()) {
                break;
            }
        }
        return stageBlock;
    }

    private void beginStageTransition(Transition transition, Stage.Rigger rigger) {
        Iterator<Stage> decendingIterator = transition.incomingStages.descendingIterator();
        while (decendingIterator.hasNext()) {
            Stage stage = decendingIterator.next();
            boolean isStarting = isSceneStarting(transition.direction);
            setUpScene(stage, isStarting);
            setUpComponents(stage, isStarting);
        }
        isTransitioning = true;
        rigger.applyAnimations(transition);
    }

    /**
     * Called by the {@link Stage.Rigger} after the scene
     * animation completes. Finishes pending layout operations and notifies the Flow.Callback.
     * @param transition contains the next and previous scene, and the flow direction
     */
    public void endStageTransition(Transition transition) {
        Iterator<Stage> iterator = transition.outgoingStages.iterator();
        while (iterator.hasNext()) {
            Stage stage = iterator.next();
            boolean isFinishing = isSceneFinishing(transition.direction);
            tearDownComponents(stage, isFinishing);
            tearDownScene(stage, isFinishing);
        }
        isTransitioning = false;
        transition.callback.onTransitionCompleted();
    }

    private boolean isSceneStarting(Direction direction) {
        switch (direction) {
            case NONE:
            case FORWARD:
            case REPLACE:
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
    public boolean isTransitioning() {
        return isTransitioning;
    }

    public void teardownVisibleScenes(Deque<Stage> stageQueue) {

        Deque<Stage> visibleStages = takeUntilNonStackingFound(stageQueue);

        // the topmost scene is the first in the scene block
        // tear down the scenes in order, from top to bottom
        Iterator<Stage> visibleSceneIterator = visibleStages.iterator();

        while(visibleSceneIterator.hasNext()) {
            Stage nextStage = visibleSceneIterator.next();
            if (nextStage.teardownOnConfigurationChange()) {
                tearDownComponents(nextStage, false);
                tearDownScene(nextStage, false);
            } else {
                removeFromParent(nextStage.getView());
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

    private void setUpScene(Stage stage, boolean isStarting) {
        View added = stage.setUp(activity, container, isStarting);
        attachToParent(added);
    }

    private void tearDownScene(Stage stage, boolean isFinishing) {
        View removed = stage.tearDown(activity, container, isFinishing);
        removeFromParent(removed);
    }

    private void setUpComponents(Stage stage, boolean isStarting) {
        for (Stage.Component component: stage.getComponents()) {
            component.afterSetUp(stage, isStarting);
        }
    }

    private void tearDownComponents(Stage stage, boolean isFinishing) {
        for (Stage.Component component: stage.getComponents()) {
            component.beforeTearDown(stage, isFinishing);
        }
    }

    private Deque<Stage> emptyStageQueue() {
        return CollectionUtils.emptyQueue(Stage.class);
    }

    public interface TransitionCallback {
        void onTransitionCompleted();
    }

    public static class Transition {

        public final Screenplay.Direction direction;
        public final ArrayDeque<Stage> incomingStages;
        public final ArrayDeque<Stage> outgoingStages;

        private final Screenplay screenplay;
        private final Screenplay.TransitionCallback callback;

        private Transition(Builder builder) {

            screenplay = builder.screenplay;
            callback = builder.callback;

            direction = builder.direction;
            incomingStages = builder.incomingStages;
            outgoingStages = builder.outgoingStages;
        }

        public void end() {
            screenplay.endStageTransition(this);
        }

        public static class Builder {

            Screenplay screenplay;
            Screenplay.Direction direction;
            Screenplay.TransitionCallback callback;
            final ArrayDeque<Stage> incomingStages = new ArrayDeque<>();
            final ArrayDeque<Stage> outgoingStages = new ArrayDeque<>();

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


            public Builder setIncomingStages(Collection<Stage> incoming) {
                incomingStages.addAll(incoming);
                return this;
            }

            public Builder setOutgoingStages(Collection<Stage> outgoing) {
                outgoingStages.addAll(outgoing);
                return this;
            }

            public Builder setScreenplay(Screenplay screenplay) {
                this.screenplay = screenplay;
                return this;
            }
        }
    }

}
