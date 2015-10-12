package com.davidstemmer.screenplay.flow;

import android.app.Activity;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.stage.Stage;
import com.davidstemmer.screenplay.util.CollectionUtils;

import java.util.Deque;

import flow.Flow;
import flow.History;

/**
 * Created by weefbellington on 10/11/15.
 */
public class ScreenplayDispatcher implements Flow.Dispatcher {

    private final Screenplay screenplay;

    private boolean isFirstDispatch = true;

    public ScreenplayDispatcher(Activity activity, ViewGroup container) {
        this.screenplay = new Screenplay(activity, container);
    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {

        Deque<Stage> origin = toDeque(traversal.origin);
        Deque<Stage> destination = toDeque(traversal.destination);

        Screenplay.Direction direction = getDirection(traversal);
        Deque<Stage> difference = direction == Screenplay.Direction.BACKWARD ?
                CollectionUtils.difference(origin, destination, emptyQueue()) :
                CollectionUtils.difference(destination, origin, emptyQueue());

        if (isFirstDispatch) {
            Deque<Stage> empty = emptyQueue();
            screenplay.dispatch(Screenplay.Direction.NONE, empty, destination, new TransitionCallback(callback));
        }
        else if (!difference.isEmpty()) {
            screenplay.dispatch(direction,origin, destination, new TransitionCallback(callback));
        }
        else {
            callback.onTraversalCompleted();
        }
        isFirstDispatch = false;
    }

    public void setUp(Flow flow) {
        flow.setDispatcher(this);
    }

    public void tearDown(Flow flow) {
        Deque<Stage> stages = toDeque(flow.getHistory());
        screenplay.teardownVisibleScenes(stages);
    }

    private Deque<Stage> toDeque(History history) {
        return CollectionUtils.fromIterator(history.iterator(), emptyQueue());
    }

    private Deque<Stage> emptyQueue() {
        return CollectionUtils.emptyQueue(Stage.class);
    }

    private Screenplay.Direction getDirection(Flow.Traversal traversal) {
        switch (traversal.direction) {
            case FORWARD:
                return Screenplay.Direction.FORWARD;
            case BACKWARD:
                return Screenplay.Direction.BACKWARD;
            case REPLACE:
                return Screenplay.Direction.REPLACE;
            default:
                return Screenplay.Direction.NONE;
        }
    }

    private class TransitionCallback implements Screenplay.TransitionCallback {

        private final Flow.TraversalCallback wrappedCallback;

        public TransitionCallback(Flow.TraversalCallback wrappedCallback) {
            this.wrappedCallback = wrappedCallback;
        }

        @Override
        public void onTransitionCompleted() {
            wrappedCallback.onTraversalCompleted();
        }
    }

}
