package com.davidstemmer.warpzone.flow;

import android.view.View;

import com.davidstemmer.warpzone.WarpListener;
import com.davidstemmer.warpzone.WarpPipe;
import com.davidstemmer.warpzone.WarpRouter;
import com.davidstemmer.warpzone.WarpState;
import com.davidstemmer.warpzone.WarpWhistle;
import com.davidstemmer.warpzone.WarpZone;
import com.davidstemmer.warpzone.stage.Stage;

import javax.inject.Inject;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/1/14.
 */
public class PageFlow implements Flow.Listener, WarpListener {


    public static class Whistle implements WarpWhistle {

        @Inject
        public Whistle(){}

        @Override
        public Flow.Listener create(WarpZone warpZone, WarpZone.StateMachine warpMachine) {
            return new PageFlow(warpZone, warpMachine);
        }
    }

    private final WarpZone warpZone;
    private final WarpZone.StateMachine warpMachine;

    private Stage previousStage;

    private PageFlow(WarpZone warpZone, WarpZone.StateMachine warpMachine) {
        this.warpZone = warpZone;
        this.warpMachine = warpMachine;
    }

    @Override
    public final void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {

        warpMachine.setState(WarpState.WARPING);

        Stage nextStage = (Stage) entries.current().getScreen();
        View newChild = nextStage.getDirector().create(warpZone.context, nextStage, warpZone.container);

        WarpPipe.Builder plumber = new WarpPipe.Builder();
        if (previousStage != null) {
            WarpPipe pipe = plumber.setDirection(direction)
                    .setNextStage(nextStage)
                    .setPreviousStage(previousStage)
                    .setCallback(callback)
                    .build();
            WarpRouter.route(pipe, this);
            View oldChild = previousStage.getDirector().destroy(
                    warpZone.context,
                    previousStage,
                    warpZone.container);
            warpZone.container.removeView(oldChild);
        } else {
            WarpPipe pipe = plumber.setDirection(direction)
                    .setCallback(callback)
                    .build();
            onWarpComplete(pipe);
        }

        warpZone.container.addView(newChild);
        previousStage = nextStage;

    }

    @Override
    public void onWarpComplete(WarpPipe transition) {
        transition.callback.onComplete();
        warpMachine.setState(WarpState.NORMAL);
    }
}
