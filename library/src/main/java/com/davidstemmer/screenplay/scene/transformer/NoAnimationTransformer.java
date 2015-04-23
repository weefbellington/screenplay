package com.davidstemmer.screenplay.scene.transformer;

/**
 * Created by weefbellington on 4/2/15.
 */

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.scene.Scene;

import javax.inject.Inject;


public class NoAnimationTransformer implements Scene.Transformer {

    @Inject
    public NoAnimationTransformer() {}

    @Override
    public void applyAnimations(SceneCut cut, Screenplay listener) {
        listener.endCut(cut);
    }
}
