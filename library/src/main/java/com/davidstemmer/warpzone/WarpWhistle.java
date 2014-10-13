package com.davidstemmer.warpzone;

import flow.Flow;

/**
 * Created by weefbellington on 10/10/14.
 */
public interface WarpWhistle {
    public Flow.Listener create(WarpZone warpZone, WarpZone.StateMachine warpState);
}
