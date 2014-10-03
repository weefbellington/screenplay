package com.davidstemmer.flowcurrent.screen;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ScreenTransformations {

    public static void start(Flow.Direction direction, Flow.Callback callback, Screen nextScreen, Screen previousScreen) {
        Screen.Transformer transformer = switchOnDirection(direction, callback, nextScreen, previousScreen);
        transformer.transform(direction, callback, nextScreen, previousScreen);
    }

    private static Screen.Transformer switchOnDirection(Flow.Direction direction, Flow.Callback callback, Screen nextScreen, Screen previousScreen) {
        Screen incomingScreen = direction == Flow.Direction.FORWARD ? nextScreen : previousScreen;
        return incomingScreen.getTransformer();
    }
}
