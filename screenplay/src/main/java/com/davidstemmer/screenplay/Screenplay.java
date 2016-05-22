package com.davidstemmer.screenplay;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.transition.Scene;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import com.davidstemmer.screenplay.internal.StageGroup;
import com.davidstemmer.screenplay.stage.Stage;
import com.davidstemmer.screenplay.util.CollectionUtils;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

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
        NONE,
        FORWARD,
        BACKWARD,
        REPLACE
    }

    public enum LifecycleEvent {
        SETUP,
        TEARDOWN
    }

    public void transition(Direction direction,
            StageGroup origin,
            StageGroup destination,
            TransitionCallback callback) {

        final Transition transition = new Transition(origin, destination, direction);

        //final List<Animation> animations;
        final List<Animator> animators;

        if (isInterGroupTransition(origin, destination)) {
            //animations = extractInterGroupAnimations(transitions, groupRigger);
            animators = extractInterGroupAnimators(transition);
        } else {
            //animations = extractIntraGroupAnimations(transitions);
            animators = extractIntraGroupAnimators(transition);
        }

        animateTransition(transition, animators, callback);
    }

    private List<Animation> extractInterGroupAnimations(StageGroup origin, StageGroup destination, Direction direction) {
        final Stage.Rigger groupRigger = direction == Direction.BACKWARD ?
                origin.getRoot().getRigger() :
                destination.getRoot().getRigger();
        final Deque<Stage> stagesIn = extractIncomingStages(origin, destination);
        final Deque<Stage> stagesOut = extractOutgoingStages(origin, destination);
        final List<Animation> animations = new ArrayList<>();

        for (Stage stage : stagesIn) {
            addIfNotNull(animations, groupRigger.animation(stage, direction, LifecycleEvent.SETUP));
        }
        for (Stage stage : stagesOut) {
            addIfNotNull(animations, groupRigger.animation(stage, direction, LifecycleEvent.TEARDOWN));
        }
        return animations;
    }

    private List<Animator> extractInterGroupAnimators(Transition transition) {
        final Stage.Rigger groupRigger = transition.direction == Direction.BACKWARD ?
                transition.originRoot.getRigger() :
                transition.destinationRoot.getRigger();
        final List<Animator> animators = new ArrayList<>();

        for (Stage stage : transition.stagesIn) {
            addIfNotNull(animators, groupRigger.animator(stage, transition.direction, LifecycleEvent.SETUP));
        }
        for (Stage stage : transition.stagesIn) {
            addIfNotNull(animators, groupRigger.animator(stage, transition.direction, LifecycleEvent.TEARDOWN));
        }
        return animators;
    }

    private List<Animation> extractIntraGroupAnimations(StageGroup origin, StageGroup destination, Direction direction) {
        final Deque<Stage> stagesIn = extractIncomingStages(origin, destination);
        final Deque<Stage> stagesOut = extractOutgoingStages(origin, destination);
        final List<Animation> animations = new ArrayList<>();

        for (Stage stage : stagesIn) {
            final Stage.Rigger delegatedRigger = stage.getRigger();
            addIfNotNull(animations, delegatedRigger.animation(stage, direction, LifecycleEvent.SETUP));
        }
        for (Stage stage : stagesOut) {
            final Stage.Rigger delegatedRigger = stage.getRigger();
            addIfNotNull(animations, delegatedRigger.animation(stage, direction, LifecycleEvent.TEARDOWN));
        }
        return animations;
    }

    private List<Animator> extractIntraGroupAnimators(Transition transition) {
        final List<Animator> animators = new ArrayList<>();
        for (Stage stage : transition.stagesIn) {
            final Stage.Rigger delegatedRigger = stage.getRigger();
            addIfNotNull(animators, delegatedRigger.animator(stage, transition.direction, LifecycleEvent.SETUP));
        }
        for (Stage stage : transition.stagesOut) {
            final Stage.Rigger delegatedRigger = stage.getRigger();
            addIfNotNull(animators, delegatedRigger.animator(stage, transition.direction, LifecycleEvent.TEARDOWN));
        }
        return animators;
    }

    private <T> void addIfNotNull(Collection<T> collection, T value) {
        if (value != null) {
            collection.add(value);
        }
    }

    private Deque<Stage> extractOutgoingStages(
            StageGroup origin,
            StageGroup destination) {

        final boolean isInterGroupTransition = isInterGroupTransition(origin, destination);
        if (!isInterGroupTransition) {
            final Deque<Stage> originStages = origin.getStages();
            final Deque<Stage> destinationStages = destination.getStages();
            boolean isSubtractive = destinationStages.size() < originStages.size();
            if (isSubtractive) {
                return CollectionUtils.difference(destinationStages, originStages, emptyStageQueue());
            } else {
                return emptyStageQueue();
            }
        } else {
            return origin.getStages();
        }
    }

    private Deque<Stage> extractIncomingStages(
            StageGroup origin,
            StageGroup destination) {

        final boolean isInterGroupTransition = isInterGroupTransition(origin, destination);
        if (!isInterGroupTransition) {
            final Deque<Stage> originStages = origin.getStages();
            final Deque<Stage> destinationStages = destination.getStages();
            boolean isAdditive = originStages.size() > destinationStages.size();
            if (isAdditive) {
                return CollectionUtils.difference(destinationStages, originStages, emptyStageQueue());
            } else {
                return emptyStageQueue();
            }
        } else {
            return destination.getStages();
        }
    }

    private boolean isInterGroupTransition(StageGroup origin, StageGroup destination) {
        return !origin.getRoot().equals(destination.getRoot());
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
        while (sceneIterator.hasNext()) {
            Stage stage = sceneIterator.next();
            stageBlock.add(stage);
            if (!stage.isModal()) {
                break;
            }
        }
        return stageBlock;
    }

    private void animateTransition(
            Transition transition,
            List<Animator> animators,
            TransitionCallback callback) {

        AnimatorSet animatorSet = new AnimatorSet();
        TransitionListener listener = new TransitionListener(transition.stagesIn, transition.stagesOut, transition.direction, callback);
        animatorSet.addListener(listener);
        animatorSet.playTogether(animators);
        animatorSet.start();
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

        //TODO parse stage group
        Deque<Stage> visibleStages = takeUntilNonStackingFound(stageQueue);

        // the topmost scene is the first in the scene block
        // tear down the scenes in order, from top to bottom
        Iterator<Stage> visibleSceneIterator = visibleStages.iterator();

        while (visibleSceneIterator.hasNext()) {
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
        for (Stage.Component component : stage.getComponents()) {
            component.afterSetUp(stage, isStarting);
        }
    }

    private void tearDownComponents(Stage stage, boolean isFinishing) {
        for (Stage.Component component : stage.getComponents()) {
            component.beforeTearDown(stage, isFinishing);
        }
    }

    private Deque<Stage> emptyStageQueue() {
        return CollectionUtils.emptyQueue(Stage.class);
    }

    public interface TransitionCallback {

        void onTransitionCompleted();
    }

    private class TransitionListener implements Animator.AnimatorListener {

        private final Deque<Stage> stagesIn;
        private final Deque<Stage> stagesOut;
        private final Direction direction;
        private final TransitionCallback callback;

        public TransitionListener(Deque<Stage> stagesIn, Deque<Stage> stagesOut, Direction direction, TransitionCallback callback) {
            this.stagesIn = new ArrayDeque<>(stagesIn);
            this.stagesOut = new ArrayDeque<>(stagesOut);
            this.direction = direction;
            this.callback = callback;
        }

        @Override
        public void onAnimationStart(Animator animation) {
            isTransitioning = true;
            for (Stage stage : stagesIn) {
                boolean isStarting = isSceneStarting(direction);
                setUpScene(stage, isStarting);
                setUpComponents(stage, isStarting);
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            isTransitioning = false;
            //TODO reverse teardown order
            for (Stage stage : stagesOut) {
                final boolean isFinishing = isSceneFinishing(direction);
                tearDownComponents(stage, isFinishing);
                tearDownScene(stage, isFinishing);
            }
            callback.onTransitionCompleted();
        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    }

    private class Transition {

        public final Deque<Stage> stagesIn;
        public final Deque<Stage> stagesOut;
        public final Stage originRoot;
        public final Stage destinationRoot;
        public final Direction direction;

        public Transition(StageGroup origin, StageGroup destination, Direction direction) {
            this.stagesIn = extractIncomingStages(origin, destination);
            this.stagesOut = extractOutgoingStages(origin, destination);
            this.originRoot = origin.getRoot();
            this.destinationRoot = destination.getRoot();
            this.direction = direction;
        }

    }
}
