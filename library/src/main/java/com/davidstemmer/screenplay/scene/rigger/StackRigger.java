package com.davidstemmer.screenplay.scene.rigger;

import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;

import flow.Flow;

/**
 * Created by weefbellington on 10/14/14.
 */
public class StackRigger implements Scene.Rigger {

    @Inject
    public StackRigger() {}

    @Override
    public boolean layoutIncoming(ViewGroup parent, Flow.Direction direction, View...incomingViews) {
        for (View incomingView : incomingViews) {
            // If the direction is FORWARD, attach the incoming view(s) to the parent
            // If the direction is BACKWARD, the incoming view(s) should already be  attached to
            // the parent.
            if (direction == Flow.Direction.FORWARD || direction == Flow.Direction.REPLACE) {
                parent.addView(incomingView);
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean layoutOutgoing(ViewGroup parent, Flow.Direction direction, View...outgoingViews) {
        // - If the direction is BACKWARD, the outgoing view(s) should be removed from the parent.
        // - If the direction is FORWARD or REPLACE the outgoing view should stay attached to the
        //   parent.
        if (direction == Flow.Direction.BACKWARD) {
            for (View outgoingView : outgoingViews) {
                parent.removeView(outgoingView);
            }
            return true;
        }
        return false;
    }
}
