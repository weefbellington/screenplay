package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.flow.LayoutCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by weefbellington on 10/15/14.
 */
public abstract class StandardScene implements Scene {

    private final ArrayList<Component> components;
    private View view;

    public StandardScene() {
        this(new Component[] {});
    }

    public StandardScene(Component...components) {
        this.components = new ArrayList<Component>(Arrays.asList(components));
    }

    @Override
    public View setUp(Context context, ViewGroup parent) {
        view = LayoutCompat.createView(context, parent, this);
        return view;
    }

    @Override
    public View tearDown(Context context, ViewGroup parent, boolean isSceneFinishing) {
        View destroyed = view;
        view = null;
        return destroyed;
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public Collection<Component> getComponents() {
        return components;
    }

    @Override
    public boolean teardownOnConfigurationChange() {
        return true;
    }

    /**
     * Convenience method. Adds a Component to the component list. This should be called in the
     * Scene's constructor method.
     * @param component the component to add
     */
    public void addComponent(Component component) {
        components.add(component);
    }

    @Override
    public boolean isStacking() {
        return false;
    }
}
