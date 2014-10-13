package com.davidstemmer.warpzone.flow;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.davidstemmer.warpzone.R;
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
 * Created by weefbellington on 10/2/14.
 */
public class ModalFlow implements Flow.Listener, WarpListener {

    public static class Whistle implements WarpWhistle {

        private boolean blocksTouchesOutside = true;

        @Inject
        public Whistle() {}

        public WarpWhistle blocksTouchesOutside(boolean block) {
            this.blocksTouchesOutside = block;
            return this;
        }

        @Override
        public Flow.Listener create(WarpZone warpZone, WarpZone.StateMachine warpState) {
            return new ModalFlow(warpZone, warpState, blocksTouchesOutside);
        }
    }

    private final WarpZone warpZone;
    private final WarpZone.StateMachine warpMachine;
    private final boolean blockTouchesOutside;

    private Stage previousStage;

    public ModalFlow(WarpZone warpZone, WarpZone.StateMachine warpMachine, boolean blockTouchesOutside) {
        this.warpZone = warpZone;
        this.warpMachine = warpMachine;
        this.blockTouchesOutside = blockTouchesOutside;
    }


    @Override
    public final void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {
        warpMachine.setState(WarpState.WARPING);
        Stage nextStage = (Stage) entries.current().getScreen();

        if (direction == Flow.Direction.FORWARD) {
            if (entries.reverseIterator().hasNext()) {
                previousStage = (Stage) entries.reverseIterator().next().getScreen();
            }
            if (blockTouchesOutside) {
                applyOverlay(warpZone.container);
            }
            View newChild = nextStage.getDirector().create(warpZone.context, nextStage, warpZone.container);
            warpZone.container.addView(newChild);
        }

        WarpPipe.Builder plumber = new WarpPipe.Builder();
        if (previousStage != null) {
            WarpPipe pipe = plumber.setDirection(direction)
                    .setNextStage(nextStage)
                    .setPreviousStage(previousStage)
                    .setCallback(callback)
                    .build();
            WarpRouter.route(pipe, this);
        } else {
            WarpPipe pipe = plumber.setDirection(direction)
                    .setCallback(callback)
                    .build();
            onWarpComplete(pipe);
        }

        if (direction == Flow.Direction.BACKWARD) {
            View oldChild = previousStage.getDirector().destroy(warpZone.context, previousStage, warpZone.container);
            warpZone.container.removeView(oldChild);
        }

        previousStage = nextStage;
    }

    private void applyOverlay(ViewGroup overlayContainer) {
        View overlay = new View(warpZone.context);
        overlay.setId(R.id.modal_overlay);
        overlay.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        overlay.setClickable(true);
        overlay.setBackgroundColor(0x60000000);
        overlayContainer.addView(overlay);
    }

    private void removeOverlay(ViewGroup overlayContainer) {
        overlayContainer.removeView(warpZone.container.findViewById(R.id.modal_overlay));
    }

    @Override
    public void onWarpComplete(WarpPipe pipe) {
        if (pipe.direction == Flow.Direction.BACKWARD) {
            if (blockTouchesOutside) {
                removeOverlay(warpZone.container);
            }
        }
        warpMachine.setState(WarpState.NORMAL);
        pipe.callback.onComplete();
    }
}