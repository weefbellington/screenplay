package com.davidstemmer.screenplay.flowlistener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.ScreenTransitions;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/1/14.
 */
public class PagedFlowListener implements Flow.Listener {

    private Scene previousScene;

    private final Context context;
    private final ViewGroup container;

    public PagedFlowListener(Screenplay screenplay) {
        this(screenplay.getContext(), screenplay.getContainer());
    }

    public PagedFlowListener(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    @Override
    public final void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {

        Scene nextScene = (Scene) entries.current().getScreen();

        View newChild = nextScene.getDirector().create(context, nextScene, container);

        if (previousScene != null) {
            ScreenTransitions.start(direction, callback, nextScene, previousScene);
            View oldChild = previousScene.getDirector().destroy(context, previousScene, container);
            container.removeView(oldChild);
        } else {
            callback.onComplete();
        }

        container.addView(newChild);
        previousScene = nextScene;

    }
}
