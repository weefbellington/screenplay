package com.davidstemmer.screenplay.sample.scene;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transition.NavigationDrawerTransition;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.director.SimpleDirector;

import javax.inject.Inject;

import flow.Layout;

/**
 * Created by weefbellington on 10/7/14.
 */

@Layout(R.layout.navigation_drawer)
public class NavigationDrawerScene implements Scene {

    private final SimpleDirector director;
    private final NavigationDrawerTransition transition;

    @Inject
    public NavigationDrawerScene(SimpleDirector director, NavigationDrawerTransition transition) {
        this.director = director;
        this.transition = transition;
    }

    @Override
    public Director getDirector() {
        return director;
    }

    @Override
    public Transition getTransition() {
        return transition;
    }
}
