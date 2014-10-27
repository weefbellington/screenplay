package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;

/**
 * Created by weefbellington on 10/2/14.
 */

/**
 * @author  David Stemmer
 * @version 1.0.0
 * @since   1.0.0
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

    /**
     * @return the rigger associated with the scene
     */
    public Rigger getRigger();

    /**
     * @return the transformer associated with the scene
     */
    public Transformer getTransformer();

    /**
     * @author  David Stemmer
     * @version 1.0.0
     * @since   1.0.0
     */
    public static interface Component {
        /**
         * Called after {@link Scene#setUp(android.content.Context, android.view.ViewGroup)}
         * @param context the current context
         * @param scene the current scene
         */
        public void afterSetUp(Context context, Scene scene);
        /**
         * Called before {@link Scene#tearDown(android.content.Context, android.view.ViewGroup)}
         * @param context the current context
         * @param scene the current scene
         */
        public void beforeTearDown(Context context, Scene scene);
    }

    /**
     * @author  David Stemmer
     * @version 1.0.0
     * @since   1.0.0
     */
    public static interface Rigger {
        /**
         * Layout the next (incoming) scene. When this method returns, the next scene should be
         * attached to the parent.
         * @param context the current context
         * @param parent the parent that the view should be attached to
         * @param cut contains the next and previous scene, and the flow direction
         */
        public void layoutNext(Context context, ViewGroup parent, SceneCut cut);

        /**
         * Layout the previous (outgoing) scene. When this method returns, the previous scene may
         * (but is not necessarily) detached from the parent.
         * @param context the current context
         * @param parent the parent that the view should be detached from
         * @param cut contains the next and previous scene, and the flow direction
         */
        public void layoutPrevious(Context context, ViewGroup parent, SceneCut cut);
    }

    /**
     * @author  David Stemmer
     * @version 1.0.0
     * @since   1.0.0
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
