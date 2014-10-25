package com.davidstemmer.screenplay.sample.presenter;

import android.app.Activity;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.R;

import javax.inject.Inject;
import javax.inject.Singleton;

import mortar.Mortar;
import mortar.MortarScope;
import mortar.Presenter;

/**
 * Created by weefbellington on 10/24/14.
 */
@Singleton
public class ActivityPresenter extends Presenter<Activity> implements Screenplay.Presenter {

    private ViewGroup container;

    @Inject public ActivityPresenter() {}

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
            container = (ViewGroup) getActivity().findViewById(R.id.main);
        }
        return container;
    }

    @Override
    protected MortarScope extractScope(Activity activity) {
        return Mortar.getScope(activity);
    }


}
