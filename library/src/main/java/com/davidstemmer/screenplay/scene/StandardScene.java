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

    private final ArrayList<Component> components = new ArrayList<>();
    private View view;

    @Override
    public View setUp(Context context, ViewGroup parent, boolean isFinishing) {
        view = LayoutCompat.createView(context, parent, this);
        return view;
    }

    @Override
    public View tearDown(Context context, ViewGroup parent, boolean isStarting) {
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
     * Convenience method. Adds a components to the component list. This should be called in the
     * Scene's constructor method.
     * @param componentArray the components to add
     */
    public void addComponents(Component...componentArray) {
        this.components.addAll(Arrays.asList(componentArray));
    }

    public void addComponents(Collection<Component> componentCollection) {
        this.components.addAll(componentCollection);
    }

    @Override
    public boolean isStacking() {
        return false;
    }
}
