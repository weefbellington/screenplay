package com.davidstemmer.screenplay;

import android.app.Activity;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.flow.Screenplay;

import javax.inject.Singleton;

import mortar.Mortar;
import mortar.MortarScope;
import mortar.Presenter;

/**
 * @version 1.0.0
 * @author  David Stemmer
 * @since   1.0.0
 */
@Singleton
public class MortarActivityDirector extends Presenter<Activity> implements Screenplay.Director {

    private final int containerId;
    private ViewGroup container;

    public MortarActivityDirector(int containerId) {
        this.containerId = containerId;
    }

    @Override
    public void dropView(Activity view) {
        super.dropView(view);
        container = null;
    }

    public Activity getActivity() {
        return getView();
    }

    @Override
    public ViewGroup getContainer() {
        if (container == null) {
            container = (ViewGroup) getActivity().findViewById(containerId);
        }
        return container;
    }

    @Override
    protected MortarScope extractScope(Activity activity) {
        return Mortar.getScope(activity);
    }


}
