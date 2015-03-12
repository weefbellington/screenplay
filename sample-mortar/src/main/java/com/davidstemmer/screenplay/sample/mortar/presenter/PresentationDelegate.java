package com.davidstemmer.screenplay.sample.mortar.presenter;

/**
 * Created by weefbellington on 3/12/15.
 */
public interface PresentationDelegate<T> {

    public void take(T target);
    public void drop(T target);
    public T getTarget();
}
