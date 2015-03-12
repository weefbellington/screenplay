package com.davidstemmer.screenplay.sample.mortar.presenter;

import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by weefbellington on 3/12/15.
 */
public abstract class ViewPresenter<V extends View> implements PresentationDelegate<V>, View.OnAttachStateChangeListener {

    private V target;

    @Override
    @SuppressWarnings("unchecked")
    public void onViewAttachedToWindow(View view) {
        if (target == null) {
            target = (V) view;
            ButterKnife.inject(this, target);
            take(target);
        }
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        drop(target);
        ButterKnife.reset(this);
        target = null;
    }

    @Override
    public void take(V target) {}

    @Override
    public void drop(V target) {}

    @Override
    public V getTarget() {
        return target;
    }
}
