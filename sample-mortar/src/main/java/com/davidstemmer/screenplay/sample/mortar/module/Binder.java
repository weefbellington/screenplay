package com.davidstemmer.screenplay.sample.mortar.module;

import com.davidstemmer.screenplay.sample.mortar.presenter.PresentationDelegate;

import javax.inject.Inject;

/**
 * Created by weefbellington on 3/12/15.
 */
public class Binder<T> implements PresentationDelegate<T> {

    private T target;

    @Inject
    public Binder() {}

    @Override
    public void take(T target) {
        this.target = target;
    }

    @Override
    public void drop(T target) {
        this.target = null;
    }

    @Override
    public T getTarget() {
        return target;
    }
}
