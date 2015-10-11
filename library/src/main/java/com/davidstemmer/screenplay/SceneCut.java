package com.davidstemmer.screenplay;

import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.scene.Scene;

import java.util.ArrayDeque;
import java.util.Collection;

import flow.Flow;

/**
 * @version 1.0.0
 * @author  David Stemmer
 * @since   1.0.0
 */
public class SceneCut {

    public final Screenplay.Direction direction;
    public final Flow.TraversalCallback callback;
    public final ArrayDeque<Scene> incomingScenes;
    public final ArrayDeque<Scene> outgoingScenes;

    private final Screenplay screenplay;

    public SceneCut(Builder builder) {
        screenplay = builder.screenplay;
        direction = builder.direction;
        callback = builder.callback;
        incomingScenes = builder.incomingScenes;
        outgoingScenes = builder.outgoingScenes;
    }

    public void end() {
        screenplay.endStageTransition(this);
    }

    public static class Builder {

        Screenplay screenplay;
        Screenplay.Direction direction;
        Flow.TraversalCallback callback;
        final ArrayDeque<Scene> incomingScenes = new ArrayDeque<>();
        final ArrayDeque<Scene> outgoingScenes = new ArrayDeque<>();

        public Builder() {}

        public SceneCut build() {
            return new SceneCut(this);
        }

        public Builder setDirection(Screenplay.Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder setCallback(Flow.TraversalCallback callback) {
            this.callback = callback;
            return this;
        }


        public Builder setIncomingScenes(Collection<Scene> incoming) {
            incomingScenes.addAll(incoming);
            return this;
        }

        public Builder setOutgoingScenes(Collection<Scene> outgoing) {
            outgoingScenes.addAll(outgoing);
            return this;
        }

        public Builder setScreenplay(Screenplay screenplay) {
            this.screenplay = screenplay;
            return this;
        }
    }
}
