package com.davidstemmer.screenplay.scene.rigger;

/**
 * Created by weefbellington on 4/2/15.
 */

import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.scene.Stage;

import javax.inject.Inject;


public class NoAnimationRigger implements Stage.Rigger {

    @Inject
    public NoAnimationRigger() {}

    @Override
    public void applyAnimations(Screenplay.Transition transition) {
        transition.end();
    }
}
