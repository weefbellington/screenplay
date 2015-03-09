package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.SceneCut;
import com.davidstemmer.screenplay.flow.Screenplay;

import java.util.Collection;

/**
 * @author  David Stemmer
 */
public interface Scene {

    /**
     * Create the View, using the layout parameters of the Parent. After this method is called,
     * getView() should return non-null value. The View should not be attached to the parent.
     * @param context current context
     * @param parent the container view
     * @return the created view
     */
    public View setUp(Context context, ViewGroup parent, boolean isInitializing);

    /**
     * Destroy the View. After this method is called, getView() should return null. The View should
     * not be detached from its parent.
     * @param context the current context
     * @param parent the container view
     * @return the destroyed view
     */
    public View tearDown(Context context, ViewGroup parent, boolean isStarting);

    public Collection<Component> getComponents();

    /**
     * Get the View associated with the Scene
     * @return the view, or null of {@link #setUp setUp} has not yet been called
     */
    public View getView();

    /**
     * Flag that specifies whether the view should be reattached on configuration change
     * @return true if the view should be reattached, false if it should be destroyed
     */
    public boolean teardownOnConfigurationChange();
    /**
     * Flag that specifies whether or not the view is stacking (modal)
     * @return true if stacking, false otherwise
     */
    public boolean isStacking();

    /**
     * @return a non-null {@link com.davidstemmer.screenplay.scene.Scene.Transformer}
     */
    public Transformer getTransformer();

    public static interface Component {
        /**
         * Called after {@link Scene#setUp}
         * @param scene the current scene
         * @param isInitializing true if this is the first time setUp has been called, false otherwise
         */
        public void afterSetUp(Scene scene, boolean isInitializing);
        /**
         * Called before {@link Scene#tearDown}
         * @param scene the current scene
         * @param isFinishing true if this is the last time tearDown will be called, false otherwise
         */
        public void beforeTearDown(Scene scene, boolean isFinishing);
    }

    public static interface Transformer {
        /**
         * Apply the animation based on the Flow.Direction. When the animation completes, it is the
         * responsibility of the Transformer to call {@link Screenplay#endCut}
         * @param cut contains information about the current transition
         * @param screenplay the screenplay object
         */
        public void applyAnimations(SceneCut cut, Screenplay screenplay);
    }

}
