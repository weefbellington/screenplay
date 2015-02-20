package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */

/**
 * @author  David Stemmer
 */
public interface Scene {

    /**
     * Create the View, using the layout parameters of the Parent. After this method is called,
     * getView() should return non-null value. The View should not be attached to the parent; that is the
     * responsibility of the Scene.Rigger.
     * @param context current context
     * @param parent the container view
     * @return the created view
     */
    public View setUp(Context context, ViewGroup parent);

    /**
     * Destroy the View. After this method is called, getView() should return null. The View should
     * not be detached from its parent; that is the responsibility of the Scene.Rigger.
     * @param context the current context
     * @param parent the container view
     * @return the destroyed view
     */
    public View tearDown(Context context, ViewGroup parent);

    /**
     * Get the View associated with the Scene
     * @return the view, or null of {@link #setUp setUp} has not yet been called
     */
    public View getView();


    public boolean isStacking();

    /**
     * @return a non-null {@link com.davidstemmer.screenplay.scene.Scene.Transformer}
     */
    public Transformer getTransformer();

    /**
     * @author  David Stemmer
     */
    public static interface Component {
        /**
         * Called after {@link Scene#setUp(android.content.Context, android.view.ViewGroup)}
         * @param context the current context
         * @param scene the current scene
         * @param view the view that was set up
         */
        public void afterSetUp(Context context, Scene scene, View view);
        /**
         * Called before {@link Scene#tearDown(android.content.Context, android.view.ViewGroup)}
         * @param context the current context
         * @param scene the current scene
         * @param view the view that will be torn down
         */
        public void beforeTearDown(Context context, Scene scene, View view);
    }

    /**
     * @author  David Stemmer
     */
    public static interface Rigger {
        /**
         * When this method returns, the incoming view(s) should be attached to the parent.
         * @param parent the parent that the view should be attached to
         * @param direction the direction of the scene transition
         * @param incomingViews the views that should be attached to the window
         */
        public void layoutIncoming(ViewGroup parent, Flow.Direction direction, View...incomingViews);

        /**
         * When this method returns, the outgoing view(s) may (optionally) be detached from the parent.
         * @param parent the parent that the view should be attached to
         * @param direction the direction of the scene transition
         * @param outgoingViews the view(s) that should be detached from the parent
         * @return true if any view was detached, false otherwise
         */
        public boolean layoutOutgoing(ViewGroup parent, Flow.Direction direction, View...outgoingViews);

    }

    /**
     * @author  David Stemmerw2xs
     */
    public static interface Transformer {
        /**
         * Apply the animation based on the Flow.Direction. When the animation completes, it is the
         * responsibility of the Transformer to call {@link Screenplay#endCut(com.davidstemmer.screenplay.SceneCut)}
         * @param cut
         * @param listener
         */
        public void applyAnimations(SceneCut cut, Screenplay listener);
    }

}
