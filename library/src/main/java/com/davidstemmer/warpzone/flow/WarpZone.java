package com.davidstemmer.warpzone.flow;

import android.content.Context;
import android.view.ViewGroup;

import com.davidstemmer.warpzone.WarpPipe;
import com.davidstemmer.warpzone.WarpState;
import com.davidstemmer.warpzone.stage.Stage;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/14/14.
 */
public class WarpZone implements Flow.Listener {

    private final Context context;
    private final ViewGroup container;

    private Stage previousStage;
    private WarpState warpState = WarpState.NORMAL;

    public WarpZone(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    @Override
    public void go(Backstack nextBackstack, Flow.Direction direction, Flow.Callback callback) {

        warpState = WarpState.WARPING;

        Stage nextStage = (Stage) nextBackstack.current().getScreen();

        WarpPipe pipe = new WarpPipe.Builder()
                .setDirection(direction)
                .setNextStage(nextStage)
                .setPreviousStage(previousStage)
                .setCallback(callback).build();

        if (direction == Flow.Direction.FORWARD || direction == Flow.Direction.REPLACE) {
            nextStage.getDirector().layoutIncomingStage(context, container, pipe);
            nextStage.getTransformer().applyAnimations(pipe, this);
        } else {
            previousStage.getDirector().layoutIncomingStage(context, container, pipe);
            previousStage.getTransformer().applyAnimations(pipe, this);
        }
        previousStage = nextStage;

    }

    public void notifyWarpComplete(WarpPipe pipe) {
        if (pipe.direction == Flow.Direction.BACKWARD) {
            pipe.previousStage.getDirector().layoutOutgoingStage(context, container, pipe);
        } else {
            pipe.nextStage.getDirector().layoutOutgoingStage(context, container, pipe);
        }
        pipe.callback.onComplete();
        warpState = WarpState.NORMAL;
    }

    public WarpState getWarpState() {
        return warpState;
    }
}
