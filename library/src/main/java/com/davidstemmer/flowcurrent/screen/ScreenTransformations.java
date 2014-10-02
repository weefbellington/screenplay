package com.davidstemmer.flowcurrent.screen;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ScreenTransformations {

    public static void start(Flow.Direction direction, Screen nextScreen, Screen previousScreen) {
        Screen.Transformer transformer = choose(direction, nextScreen, previousScreen);
        transformer.transform(direction, nextScreen, previousScreen);
    }

    private static Screen.Transformer choose(Flow.Direction direction, Screen nextScreen, Screen previousScreen) {
        Screen incomingScreen = direction == Flow.Direction.FORWARD ? nextScreen : previousScreen;
        return incomingScreen.getTransformer();
    }
}
