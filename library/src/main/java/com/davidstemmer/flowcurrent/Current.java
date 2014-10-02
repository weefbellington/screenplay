package com.davidstemmer.flowcurrent;

import android.content.Context;
import android.view.ViewGroup;

import com.davidstemmer.flowcurrent.flowlistener.PagedFlowListener;

import java.util.ArrayDeque;
import java.util.Deque;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 9/24/14.
 */
public class Current {

    private final Context context;
    private final ViewGroup container;

    private final Deque<Flow> flows = new ArrayDeque<Flow>();

    public Current(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    public void split(Object screen) {
        split(screen, new PagedFlowListener(this));
    }

    public void split(Object screen, Flow.Listener listener) {

        Backstack.Builder backstackBuilder = Backstack.emptyBuilder();
        // make the last screen of the previous backstack the root of the new backstack
        if (!flows.isEmpty()) {
            Flow previousFlow = flows.peekLast();
            backstackBuilder.push(previousFlow.getBackstack().current().getScreen());
        }
        backstackBuilder.push(screen);
        Backstack newBackstack = backstackBuilder.build();

        Flow flow = new Flow(newBackstack, listener);
        listener.go(newBackstack, Flow.Direction.FORWARD);

        flows.add(flow);
    }

    public void goForward(Object screen) {
        flows.peekLast().goTo(screen);
    }

    public boolean goBack() {
        while(!flows.isEmpty() ) {
            Flow activeFlow = flows.peekLast();
            if (activeFlow.goBack()) {
                return true;
            } else {
                flows.removeLast();
            }
        }
        return false;
    }

    public Context getContext() {
        return context;
    }

    public ViewGroup getContainer() {
        return container;
    }
}
