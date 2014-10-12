package com.davidstemmer.warpzone;

import com.davidstemmer.warpzone.stage.Stage;

import flow.Flow;

/**
* Created by weefbellington on 10/12/14.
*/
public class WarpPipe {

    public final Flow.Direction direction;
    public final Flow.Callback callback;
    public final Stage nextStage;
    public final Stage previousStage;

    public WarpPipe(Builder builder) {
        direction = builder.direction;
        callback = builder.callback;
        nextStage = builder.nextStage;
        previousStage = builder.previousStage;
    }

    public static class Builder {

        public Flow.Direction direction;
        public Flow.Callback callback;
        public Stage nextStage;
        public Stage previousStage;

        public Builder() {}

        public WarpPipe build() {
            return new WarpPipe(this);
        }

        public Builder setDirection(Flow.Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder setCallback(Flow.Callback callback) {
            this.callback = callback;
            return this;
        }


        public Builder setNextStage(Stage nextStage) {
            this.nextStage = nextStage;
            return this;
        }

        public Builder setPreviousStage(Stage previousStage) {
            this.previousStage = previousStage;
            return this;
        }

        public Flow.Direction getDirection() {
            return direction;
        }

        public Flow.Callback getCallback() {
            return callback;
        }

        public Stage getNextStage() {
            return nextStage;
        }

        public Stage getPreviousStage() {
            return previousStage;
        }
    }
}
