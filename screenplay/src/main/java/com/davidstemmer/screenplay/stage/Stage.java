package com.davidstemmer.screenplay.stage;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import com.davidstemmer.screenplay.Screenplay;

import java.util.List;

/**
 * @author  David Stemmer
 */
public interface Stage {

    /**
     * Create the View, using the layout parameters of the Parent. After this method is called,
     * getView() should return non-null value. The View should not be attached to the parent.
     * @param context current context
     * @param parent the container view
     * @param isInitializing true if this is the first time setUp has been called, false otherwise
     * @return the created view
     */
    View setUp(Context context, ViewGroup parent, boolean isInitializing);

    /**
     * Destroy the View. After this method is called, getView() should return null. The View should
     * not be detached from its parent.
     * @param context the current context
     * @param parent the container view
     * @param isFinishing true if this is the last time tearDown will be called, false otherwise
     * @return the destroyed view
     */
    View tearDown(Context context, ViewGroup parent, boolean isFinishing);

    List<Component> getComponents();

    /**
     * Get the View associated with the Scene
     * @return the view, or null of {@link #setUp setUp} has not yet been called
     */
    View getView();

    /**
     * Flag that specifies whether the view should be reattached on configuration change
     * @return true if the view should be reattached, false if it should be destroyed
     */
    boolean teardownOnConfigurationChange();
    /**
     * Flag that specifies whether or not the view is stacking (modal)
     * @return true if stacking, false otherwise
     */

    /**
     * Flag that specifies whether or not the view is modal (floating)
     * @return true if stacking, false otherwise
     */
    boolean isModal();

    /**
     * @return a non-null {@link Rigger}
     */
    public Rigger getRigger();

    public static interface Component {
        /**
         * Called after {@link Stage#setUp}
         * @param stage the stage that this component is attached to
         * @param isInitializing true if this is the first time setUp has been called, false otherwise
         */
        public void afterSetUp(Stage stage, boolean isInitializing);
        /**
         * Called before {@link Stage#tearDown}
         * @param stage the stage that this component is attached to
         * @param isFinishing true if this is the last time tearDown will be called, false otherwise
         */
        public void beforeTearDown(Stage stage, boolean isFinishing);
    }

    interface Rigger {
        Animator animator(Stage stage, Screenplay.Direction direction, Screenplay.LifecycleEvent event);
        Animation animation(Stage stage, Screenplay.Direction direction, Screenplay.LifecycleEvent event);
    }

}
