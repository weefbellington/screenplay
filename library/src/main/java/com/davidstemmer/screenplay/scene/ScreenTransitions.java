package com.davidstemmer.screenplay.scene;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ScreenTransitions {

    public static void start(Flow.Direction direction, Flow.Callback callback, Scene nextScene, Scene previousScene) {
        Scene.Transition transition = switchOnDirection(direction, nextScene, previousScene);
        transition.transform(direction, callback, nextScene, previousScene);
    }

    private static Scene.Transition switchOnDirection(Flow.Direction direction, Scene nextScene, Scene previousScene) {
        Scene incomingScene = direction == Flow.Direction.FORWARD ? nextScene : previousScene;
        return incomingScene.getTransition();
    }
}
