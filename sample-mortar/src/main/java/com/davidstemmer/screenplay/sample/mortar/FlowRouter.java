package com.davidstemmer.screenplay.sample.mortar;

import javax.inject.Inject;
import javax.inject.Singleton;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 4/23/15.
 */
@Singleton
public class FlowRouter implements Flow.Listener {

    private Flow.Listener delegate;

    @Inject
    public FlowRouter() {}

    public void bind(Flow.Listener delegate) {
        this.delegate = delegate;
    }

    public void unbind() {
        this.delegate = null;
    }

    @Override
    public void go(Backstack nextBackstack, Flow.Direction direction, Flow.Callback callback) {
        delegate.go(nextBackstack, direction, callback);
    }
}
