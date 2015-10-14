package com.davidstemmer.screenplay.stage.rigger;

/**
 * Created by weefbellington on 4/2/15.
 */

import com.davidstemmer.screenplay.Screenplay;
import com.davidstemmer.screenplay.stage.Stage;

import javax.inject.Inject;


public class NoAnimationRigger implements Stage.Rigger {

    @Inject
    public NoAnimationRigger() {}

    @Override
    public void applyAnimations(Screenplay.Transition transition) {
        transition.end();
    }
}
