package com.davidstemmer.screenplay.sample.scene;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transformer.NavigationDrawerTransformer;
import com.davidstemmer.screenplay.stage.StandardScene;
import com.davidstemmer.screenplay.stage.director.ModalDirector;

import javax.inject.Inject;

import flow.Layout;

/**
 * Created by weefbellington on 10/7/14.
 */

@Layout(R.layout.navigation_drawer)
public class NavigationDrawerScene extends StandardScene {

    private final ModalDirector director;
    private final NavigationDrawerTransformer transition;

    @Inject
    public NavigationDrawerScene(ModalDirector director, NavigationDrawerTransformer transition) {
        this.director = director;
        this.transition = transition;
    }

    @Override
    public Director getDirector() {
        return director;
    }

    @Override
    public Transformer getTransformer() {
        return transition;
    }
}
