package com.davidstemmer.screenplay.flowlistener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.R;
import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.ScreenTransitions;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ModalFlowListener implements Flow.Listener {

    private final Context context;
    private final ViewGroup container;
    private final boolean blockTouchesOutside;

    private Scene previousScene;

    public ModalFlowListener(Screenplay screenplay) {
        this(screenplay.getContext(), screenplay.getContainer(), true);
    }

    public ModalFlowListener(Screenplay screenplay, boolean blockTouchesOutside) {
        this(screenplay.getContext(), screenplay.getContainer(), blockTouchesOutside);
    }

    public ModalFlowListener(Context context, ViewGroup container, boolean blockTouchesOutside) {
        this.context = context;
        this.container = container;
        this.blockTouchesOutside = blockTouchesOutside;
    }


    @Override
    public final void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {

        Scene nextScene = (Scene) entries.current().getScreen();

        if (direction == Flow.Direction.FORWARD) {
            if (entries.reverseIterator().hasNext()) {
                previousScene = (Scene) entries.reverseIterator().next().getScreen();
            }
            if (blockTouchesOutside) {
                applyOverlay(container);
            }
            View newChild = nextScene.getDirector().create(context, nextScene, container);
            container.addView(newChild);
        }

        if (previousScene != null) {
            ScreenTransitions.start(direction, callback, nextScene, previousScene);
        } else {
            callback.onComplete();
        }

        if (direction == Flow.Direction.BACKWARD) {
            if (blockTouchesOutside) {
                removeOverlay(container);
            }
            View oldChild = previousScene.getDirector().destroy(context, previousScene, container);
            container.removeView(oldChild);
        }

        previousScene = nextScene;
    }

    private void applyOverlay(ViewGroup overlayContainer) {
        View overlay = new View(context);
        overlay.setId(R.id.modal_overlay);
        overlay.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        overlay.setClickable(true);
        overlay.setBackgroundColor(0x60000000);
        overlayContainer.addView(overlay);
    }

    private void removeOverlay(ViewGroup overlayContainer) {
        overlayContainer.removeView(container.findViewById(R.id.modal_overlay));
    }
}