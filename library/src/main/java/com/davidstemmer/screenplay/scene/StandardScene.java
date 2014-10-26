package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.flow.LayoutCompat;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by weefbellington on 10/15/14.
 */
public abstract class StandardScene implements Scene {

    private final Iterable<Component> components;
    private View view;

    public StandardScene() {
        this(new ArrayList<Component>());
    }

    public StandardScene(Iterable<Component> components) {
        this.components = components;
    }

    public StandardScene(Component...components) {
        this.components = Arrays.asList(components);
    }

    @Override
    public View setUp(Context context, ViewGroup parent) {
        view = LayoutCompat.createView(context, parent, this);
        for (Component component: components) {
            component.afterSetUp(context, this);
        }
        return view;
    }

    @Override
    public View tearDown(Context context, ViewGroup parent) {
        for (Component component: components) {
            component.beforeTearDown(context, this);
        }
        View destroyed = view;
        view = null;
        return destroyed;
    }

    @Override
    public View getView() {
        return view;
    }

}
