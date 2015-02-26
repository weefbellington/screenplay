package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;

/**
 * Created by weefbellington on 10/20/14.
 */
public abstract class ScopedScene extends StandardScene implements Blueprint {

    private MortarScope scope;

    @Override
    public View setUp(Context context, ViewGroup parent, Flow.Direction direction) {
        return super.setUp(createScope(context), parent, direction);
    }

    @Override
    public View tearDown(Context context, ViewGroup parent, Flow.Direction direction) {
        View destroyed = super.tearDown(context, parent, direction);
        destroyScope(context);
        return destroyed;
    }


    private Context createScope(Context context) {
        MortarScope parentScope = Mortar.getScope(context);
        scope = parentScope.requireChild(this);
        Context childContext = scope.createContext(context);

        Mortar.inject(childContext, this);
        onCreateScope(scope);
        return childContext;
    }

    private void destroyScope(Context context) {
        MortarScope parentScope = Mortar.getScope(context);
        parentScope.destroyChild(scope);
        scope = null;
        onDestroyScope();
    }

    public void onCreateScope(MortarScope scope) {}

    public void onDestroyScope() {}
}
