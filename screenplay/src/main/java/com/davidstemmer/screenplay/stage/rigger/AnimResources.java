package com.davidstemmer.screenplay.stage.rigger;

import android.support.annotation.AnimRes;
import android.support.annotation.AnimatorRes;
import com.davidstemmer.screenplay.Screenplay;

/**
 * Created by weefbellington on 10/11/15.
 */
public class AnimResources {

    public int forwardIn = -1;
    public int forwardOut = -1;
    public int backIn = -1;
    public int backOut = -1;
    public int replaceIn = -1;
    public int replaceOut = -1;

    public @AnimatorRes int getAnimationIn(Screenplay.Direction direction) {
        switch (direction) {
            case FORWARD:
                return forwardIn;
            case BACKWARD:
                return backIn;
            case REPLACE:
                return replaceIn;
            default:
                return -1;
        }
    }

    public @AnimatorRes int getAnimationOut(Screenplay.Direction direction) {
        switch (direction) {
            case FORWARD:
                return forwardOut;
            case BACKWARD:
                return backOut;
            case REPLACE:
                return replaceOut;
            default:
                return -1;
        }
    }
}
