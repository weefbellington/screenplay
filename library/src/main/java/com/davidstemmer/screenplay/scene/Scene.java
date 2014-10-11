package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public interface Scene {

    public Director getDirector();
    public Transformer getTransformer();

    public static interface Director {
        public View create(Context context, Object screen, ViewGroup parent);
        public View destroy(Context context, Object screen, ViewGroup parent);
        public View getView();
    }

    public static interface Transformer {
        public void transform(Transition params);
    }

    public static interface TransitionListener {
        public void onTransitionComplete(Transition transition);
    }

    public static class Transition {

        public final Flow.Direction direction;
        public final Flow.Callback callback;
        public final TransitionListener listener;
        public final Scene nextScene;
        public final Scene previousScene;

        private Transition(Builder builder) {
            direction = builder.direction;
            callback = builder.callback;
            listener = builder.listener;
            nextScene = builder.nextScene;
            previousScene = builder.previousScene;
        }

        public static class Builder {

            public Flow.Direction direction;
            public Flow.Callback callback;
            public TransitionListener listener;
            public Scene nextScene;
            public Scene previousScene;

            public Builder() {}

            public Transition build() {
                return new Transition(this);
            }

            public Builder setDirection(Flow.Direction direction) {
                this.direction = direction;
                return this;
            }

            public Builder setCallback(Flow.Callback callback) {
                this.callback = callback;
                return this;
            }

            public Builder setListener(TransitionListener listener) {
                this.listener = listener;
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

            public Flow.Direction getDirection() {
                return direction;
            }

            public Flow.Callback getCallback() {
                return callback;
            }

            public TransitionListener getListener() {
                return listener;
            }

            public Scene getNextScene() {
                return nextScene;
            }

            public Scene getPreviousScene() {
                return previousScene;
            }
        }
    }
}
