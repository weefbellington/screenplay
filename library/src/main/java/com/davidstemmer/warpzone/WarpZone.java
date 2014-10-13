package com.davidstemmer.warpzone;

import android.content.Context;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.flow.PageFlow;
import com.davidstemmer.warpzone.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 9/24/14.
 */
public class WarpZone {

    public final Context context;
    public final ViewGroup container;
    private final Deque<Flow> flows = new ArrayDeque<Flow>();

    private StateMachine stateMachine = new StateMachine();

    public WarpZone(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    public WarpZone(Context context, ViewGroup container, Stage rootStage) {
        this(context, container);
        forkFlow(rootStage, new PageFlow.Whistle());
    }

    public void forkFlow(Stage stage, WarpWhistle warpWhistle) {

        Flow.Listener listener = warpWhistle.create(this, stateMachine);
        Flow flow;

        if (flows.isEmpty()) {
            Backstack backstack = Backstack.single(stage);
            flow = new Flow(backstack, listener);
            flow.resetTo(stage);
        }
        else {
            // make the last screen of the previous backstack the root of the new backstack
            Flow previousFlow = flows.peekLast();
            Backstack backstack = Backstack.single(previousFlow.getBackstack().current().getScreen());
            flow = new Flow(backstack, listener);
            flow.goTo(stage);
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

    public WarpState getWarpState() {
        return stateMachine.getState();
    }

    private boolean shouldPopToPreviousFlow() {
        Flow currentFlow = flows.peekLast();
        return !isRootFlow(currentFlow) && currentFlow.getBackstack().size() == 2;
    }

    private boolean isRootFlow(Flow flow) {
        return flows.peekFirst().equals(flow);
    }

    public static class StateMachine {

        private StateMachine() {}

        private WarpState warpState = WarpState.NORMAL;

        public WarpState getState() {
            return warpState;
        }

        public void setState(WarpState state) {
            warpState = state;
        }
    }

}
