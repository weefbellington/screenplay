package com.davidstemmer.warpzone;

import com.davidstemmer.warpzone.stage.Stage;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class WarpRouter {

    public static void route(WarpPipe pipe, WarpListener listener) {
        Stage.Transformer transformer = switchOnDirection(pipe);
        transformer.transform(pipe, listener);
    }

    private static Stage.Transformer switchOnDirection(WarpPipe transition) {
        Stage incomingStage =
                transition.direction == Flow.Direction.FORWARD ?
                transition.nextStage :
                transition.previousStage;
        return incomingStage.getTransformer();
    }
}
