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

    private final MortarScope scope;
    private final Context scopedContext;

    protected ScopedScene(Context context, Blueprint blueprint) {
        this.scope = createScope(context, blueprint);
        this.scopedContext = createMortarContext(context);
    }

    @Override
    public View setUp(Context context, ViewGroup parent) {
        return super.setUp(scopedContext, parent);
    }

    @Override
    public View tearDown(Context context, ViewGroup parent, boolean isSceneFinishing) {
        View destroyed = super.tearDown(context, parent, isSceneFinishing);
        if (isSceneFinishing) {
            destroyScope(context);
        }
        return destroyed;
    }

    private MortarScope createScope(Context context, Blueprint blueprint) {
        MortarScope parentScope = Mortar.getScope(context);
        return parentScope.requireChild(blueprint);
    }

    private Context createMortarContext(Context context) {
        Context childContext = scope.createContext(context);
        Mortar.inject(childContext, this);
        scope.register(this);
        return childContext;
    }

    private void destroyScope(Context context) {
        MortarScope parentScope = Mortar.getScope(context);
        parentScope.destroyChild(scope);
    }


    @Override
    public void onExitScope() {}

    @Override
    public void onEnterScope(MortarScope scope) {}

}
