package com.davidstemmer.flowcurrent.flowlistener;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.flowcurrent.Current;
import com.davidstemmer.flowcurrent.screen.Screen;
import com.davidstemmer.flowcurrent.screen.ScreenTransformations;

import flow.Backstack;
import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ModalFlowListener implements Flow.Listener {

    private final Context context;
    private final ViewGroup container;

    private Screen previousScreen;

    public ModalFlowListener(Current current) {
        this(current.getContext(), current.getContainer());
    }

    public ModalFlowListener(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    @Override
    public final void go(Backstack entries, Flow.Direction direction, Flow.Callback callback) {

        Screen nextScreen = (Screen) entries.current().getScreen();

        if (direction == Flow.Direction.FORWARD) {

            if (entries.reverseIterator().hasNext()) {
                previousScreen = (Screen) entries.reverseIterator().next().getScreen();
            }
            View newChild = nextScreen.getDirector().create(context, nextScreen, container);
            container.addView(newChild);
        }

        if (previousScreen != null) {
            ScreenTransformations.start(direction, callback, nextScreen, previousScreen);
        } else {
            callback.onComplete();
        }

        if (direction == Flow.Direction.BACKWARD) {
            View oldChild = previousScreen.getDirector().destroy(context, previousScreen, container);
            container.removeView(oldChild);
        }

        previousScreen = nextScreen;
    }
}