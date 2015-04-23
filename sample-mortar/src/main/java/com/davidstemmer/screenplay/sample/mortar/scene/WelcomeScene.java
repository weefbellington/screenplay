package com.davidstemmer.screenplay.sample.mortar.scene;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.transformer.NoAnimationTransformer;

import javax.inject.Inject;

import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */

@Layout(R.layout.welcome_scene)
public class WelcomeScene extends StandardScene {

    private final NoAnimationTransformer transformer;

    @Inject
    public WelcomeScene(NoAnimationTransformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

}
