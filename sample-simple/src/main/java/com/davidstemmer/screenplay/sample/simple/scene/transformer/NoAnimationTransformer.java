package com.davidstemmer.screenplay.sample.simple.scene.transformer;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.scene.Scene;

/**
 * Created by weefbellington on 10/17/14.
 */
public class NoAnimationTransformer implements Scene.Transformer {

    public NoAnimationTransformer() {}

    @Override
    public void applyAnimations(SceneCut cut, Screenplay listener) {
        listener.endCut(cut);
    }
}
