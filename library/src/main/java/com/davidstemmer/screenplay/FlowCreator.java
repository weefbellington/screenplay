package com.davidstemmer.screenplay;

import flow.Flow;

/**
 * Created by weefbellington on 10/10/14.
 */
public interface FlowCreator {
    public Flow.Listener create(Screenplay screenplay);
}
