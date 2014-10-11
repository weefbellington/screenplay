package com.davidstemmer.screenplay.scene;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ScreenTransitions {

    public static void start(Scene.Transition transition) {
        Scene.Transformer transformer = switchOnDirection(transition);
        transformer.transform(transition);
    }

    private static Scene.Transformer switchOnDirection(Scene.Transition transition) {
        Scene incomingScene =
                transition.direction == Flow.Direction.FORWARD ?
                transition.nextScene :
                transition.previousScene;
        return incomingScene.getTransformer();
    }
}
