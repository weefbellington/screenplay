package com.davidstemmer.screenplay;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayDeque;
import java.util.Deque;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 9/24/14.
 */
public class Screenplay {

    public final Context context;
    public final ViewGroup container;
    private final Deque<Flow> flows = new ArrayDeque<Flow>();

    private SceneState sceneState;

    public Screenplay(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    public void changeFlow(Object screen) {
        changeFlow(screen, new PagedFlow.Creator());
    }

    public void changeFlow(Object screen, FlowListenerFactory flowListenerFactory) {

        Flow.Listener listener = flowListenerFactory.create(this);
        Flow flow;

        if (flows.isEmpty()) {
            Backstack backstack = Backstack.single(screen);
            flow = new Flow(backstack, listener);
            flow.resetTo(screen);
        }
        else {
            // make the last screen of the previous backstack the root of the new backstack
            Flow previousFlow = flows.peekLast();
            Backstack backstack = Backstack.single(previousFlow.getBackstack().current().getScreen());
            flow = new Flow(backstack, listener);
            flow.goTo(screen);
        }

        flows.add(flow);

    }

    public void goForward(Object screen) {
        flows.peekLast().goTo(screen);
    }

    public boolean goBack() {
        Flow delegate;
        if (shouldPopToPreviousFlow()) {
            delegate = flows.removeLast();
        } else {
            delegate = flows.peekLast();
        }
        return delegate.goBack();
    }

    public SceneState getSceneState() {
        return sceneState;
    }

    void setSceneState(SceneState state) {
        this.sceneState = state;
    }

    private boolean shouldPopToPreviousFlow() {
        Flow currentFlow = flows.peekLast();
        return !isRootFlow(currentFlow) && currentFlow.getBackstack().size() == 2;
    }

    private boolean isRootFlow(Flow flow) {
        return flows.peekFirst().equals(flow);
    }

}
