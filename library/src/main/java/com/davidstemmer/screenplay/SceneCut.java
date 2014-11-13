package com.davidstemmer.screenplay;

import com.davidstemmer.screenplay.scene.Scene;

import flow.Flow;

/**
 * @version 1.0.0
 * @author  David Stemmer
 * @since   1.0.0
 */
public class SceneCut {

    public final Flow.Direction direction;
    public final Flow.Callback callback;
    public final Scene incomingScene;
    public final Scene outgoingScene;

    public SceneCut(Builder builder) {
        direction = builder.direction;
        callback = builder.callback;
        incomingScene = builder.incomingScene;
        outgoingScene = builder.outgoingScene;
    }

    public static class Builder {

        public Flow.Direction direction;
        public Flow.Callback callback;
        public Scene incomingScene;
        public Scene outgoingScene;

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


        public Builder setIncomingScene(Scene incomingScene) {
            this.incomingScene = incomingScene;
            return this;
        }

        public Builder setOutgoingScene(Scene outgoingScene) {
            this.outgoingScene = outgoingScene;
            return this;
        }
    }
}
