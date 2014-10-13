package com.davidstemmer.warpzone.sample.stage;

import com.davidstemmer.warpzone.sample.R;
import com.davidstemmer.warpzone.sample.stage.transformer.NavigationDrawerTransformer;
import com.davidstemmer.warpzone.stage.Stage;
import com.davidstemmer.warpzone.stage.director.SimpleDirector;

import javax.inject.Inject;

import flow.Layout;

/**
 * Created by weefbellington on 10/7/14.
 */

@Layout(R.layout.navigation_drawer)
public class NavigationDrawerStage implements Stage {

    private final SimpleDirector director;
    private final NavigationDrawerTransformer transition;

    @Inject
    public NavigationDrawerStage(SimpleDirector director, NavigationDrawerTransformer transition) {
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
