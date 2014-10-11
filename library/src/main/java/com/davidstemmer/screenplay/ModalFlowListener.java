package com.davidstemmer.screenplay;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.ScreenTransitions;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ModalFlowListener implements Flow.Listener, Scene.TransitionListener {

    private final Screenplay screenplay;
    private final boolean blockTouchesOutside;

    private Scene previousScene;

    public ModalFlowListener(Screenplay screenplay) {
        this(screenplay, true);
    }

    public ModalFlowListener(Screenplay screenplay, boolean blockTouchesOutside) {
        this.screenplay = screenplay;
        this.blockTouchesOutside = blockTouchesOutside;
    }


    @Override
    public final void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {
        screenplay.setSceneState(SceneState.TRANSITIONING);
        Scene nextScene = (Scene) entries.current().getScreen();

        if (direction == Flow.Direction.FORWARD) {
            if (entries.reverseIterator().hasNext()) {
                previousScene = (Scene) entries.reverseIterator().next().getScreen();
            }
            if (blockTouchesOutside) {
                applyOverlay(screenplay.container);
            }
            View newChild = nextScene.getDirector().create(screenplay.context, nextScene, screenplay.container);
            screenplay.container.addView(newChild);
        }

        Scene.Transition.Builder transitionBuilder = new Scene.Transition.Builder();
        if (previousScene != null) {
            transitionBuilder.setDirection(direction)
                    .setNextScene(nextScene)
                    .setPreviousScene(previousScene)
                    .setCallback(callback)
                    .setListener(this);
            ScreenTransitions.start(transitionBuilder.build());
        } else {
            transitionBuilder.setDirection(direction)
                    .setCallback(callback)
                    .setListener(this);
            onTransitionComplete(transitionBuilder.build());
        }

        if (direction == Flow.Direction.BACKWARD) {
            View oldChild = previousScene.getDirector().destroy(screenplay.context, previousScene, screenplay.container);
            screenplay.container.removeView(oldChild);
        }

        previousScene = nextScene;
    }

    private void applyOverlay(ViewGroup overlayContainer) {
        View overlay = new View(screenplay.context);
        overlay.setId(R.id.modal_overlay);
        overlay.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        overlay.setClickable(true);
        overlay.setBackgroundColor(0x60000000);
        overlayContainer.addView(overlay);
    }

    private void removeOverlay(ViewGroup overlayContainer) {
        overlayContainer.removeView(screenplay.container.findViewById(R.id.modal_overlay));
    }

    @Override
    public void onTransitionComplete(Scene.Transition transition) {
        if (transition.direction == Flow.Direction.BACKWARD) {
            if (blockTouchesOutside) {
                removeOverlay(screenplay.container);
            }
        }
        screenplay.setSceneState(SceneState.NORMAL);
        transition.callback.onComplete();
    }
}