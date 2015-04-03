package com.davidstemmer.screenplay;

import com.davidstemmer.screenplay.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import flow.Flow;

/**
 * @version 1.0.0
 * @author  David Stemmer
 * @since   1.0.0
 */
public class SceneCut {

    public final Flow.Direction direction;
    public final Flow.Callback callback;
    public final List<Scene> incomingScenes;
    public final List<Scene> outgoingScenes;

    public SceneCut(Builder builder) {
        direction = builder.direction;
        callback = builder.callback;
        incomingScenes = builder.incomingScenes;
        outgoingScenes = builder.outgoingScenes;
    }

    public static class Builder {

        Flow.Direction direction;
        Flow.Callback callback;
        final List<Scene> incomingScenes = new ArrayList<>();
        final List<Scene> outgoingScenes = new ArrayList<>();

        public Builder() {}

        public SceneCut build() {
            return new SceneCut(this);
        }

        public Builder setDirection(Flow.Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder setCallback(Flow.Callback callback) {
            this.callback = callback;
            return this;
        }


        public Builder addIncomingScene(Scene incomingScene) {
            incomingScenes.add(incomingScene);
            return this;
        }

        public Builder addOutgoingScene(Scene outgoingScene) {
            outgoingScenes.add(outgoingScene);
            return this;
        }

    }
}
