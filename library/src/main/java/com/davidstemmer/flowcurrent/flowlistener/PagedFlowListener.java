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
 * Created by weefbellington on 10/1/14.
 */
public class PagedFlowListener implements Flow.Listener {

    private Screen previousScreen;

    private final Context context;
    private final ViewGroup container;

    public PagedFlowListener(Current current) {
        this(current.getContext(), current.getContainer());
    }

    public PagedFlowListener(Context context, ViewGroup container) {
        this.context = context;
        this.container = container;
    }

    @Override
    public final void go(Backstack entries, Flow.Direction direction) {

        Screen nextScreen = (Screen) entries.current().getScreen();

        View newChild = nextScreen.getDirector().create(context, nextScreen, container);

        if (previousScreen != null) {
            ScreenTransformations.start(direction, nextScreen, previousScreen);
            View oldChild = previousScreen.getDirector().destroy(context, previousScreen, container);
            container.removeView(oldChild);
        }

        container.addView(newChild);
        previousScreen = nextScreen;
    }
}
