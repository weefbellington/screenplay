package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;
import mortar.Scoped;

/**
 * Created by weefbellington on 10/20/14.
 */
public abstract class ScopedScene extends StandardScene implements Scoped {

    private final MortarScope parentScope;
    private final MortarScope childScope;
    private final Context childContext;

    protected ScopedScene(Context context, Blueprint blueprint) {

        this.parentScope = Mortar.getScope(context);
        this.childScope = parentScope.requireChild(blueprint);
        this.childContext = childScope.createContext(context);

        childScope.getObjectGraph().inject(this);
        childScope.register(this);

    }

    @Override
    public View setUp(Context context, ViewGroup parent, boolean isStarting) {
        return super.setUp(childContext, parent, isStarting);
    }

    @Override
    public View tearDown(Context context, ViewGroup parent, boolean isSceneFinishing) {
        View destroyed = super.tearDown(context, parent, isSceneFinishing);
        if (isSceneFinishing) {
            parentScope.destroyChild(childScope);
        }
        return destroyed;
    }

    @Override
    public void onExitScope() {}

    @Override
    public void onEnterScope(MortarScope scope) {}

}
