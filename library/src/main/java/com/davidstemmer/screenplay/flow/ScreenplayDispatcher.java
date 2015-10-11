package com.davidstemmer.screenplay.flow;

import android.app.Activity;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.scene.Scene;
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

        Deque<Scene> origin = toDeque(traversal.origin);
        Deque<Scene> destination = toDeque(traversal.destination);

        Screenplay.Direction direction = getDirection(traversal);
        Deque<Scene> difference = direction == Screenplay.Direction.BACKWARD ?
                CollectionUtils.difference(origin, destination, emptyQueue()) :
                CollectionUtils.difference(destination, origin, emptyQueue());

        if (isFirstDispatch) {
            Deque<Scene> empty = emptyQueue();
            screenplay.dispatch(empty, destination, Screenplay.Direction.NONE, callback);
        }
        else if (!difference.isEmpty()) {
            screenplay.dispatch(origin, destination, direction, callback);
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
        Deque<Scene> scenes = toDeque(flow.getHistory());
        screenplay.exit(scenes);
    }

    private Deque<Scene> toDeque(History history) {
        return CollectionUtils.fromIterator(history.iterator(), emptyQueue());
    }

    private Deque<Scene> emptyQueue() {
        return CollectionUtils.emptyQueue(Scene.class);
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
}
