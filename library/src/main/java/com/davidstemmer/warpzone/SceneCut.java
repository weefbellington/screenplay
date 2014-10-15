package com.davidstemmer.warpzone;

import com.davidstemmer.warpzone.stage.Scene;

import flow.Flow;

/**
* Created by weefbellington on 10/12/14.
*/
public class SceneCut {

    public final Flow.Direction direction;
    public final Flow.Callback callback;
    public final Scene nextScene;
    public final Scene previousScene;

    public SceneCut(Builder builder) {
        direction = builder.direction;
        callback = builder.callback;
        nextScene = builder.nextScene;
        previousScene = builder.previousScene;
    }

    public static class Builder {

        public Flow.Direction direction;
        public Flow.Callback callback;
        public Scene nextScene;
        public Scene previousScene;

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


        public Builder setNextScene(Scene nextScene) {
            this.nextScene = nextScene;
            return this;
        }

        public Builder setPreviousScene(Scene previousScene) {
            this.previousScene = previousScene;
            return this;
        }
    }
}
