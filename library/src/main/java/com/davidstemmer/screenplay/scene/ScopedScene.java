package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import flow.Flow;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;
import mortar.Scoped;

/**
 * Created by weefbellington on 10/20/14.
 */
public abstract class ScopedScene extends StandardScene implements Scoped, Blueprint {

    private final MortarScope scope;
    private final Context scopedContext;
    private final List modules;

    protected ScopedScene(Context context, Object...modules) {
        this.modules = Arrays.asList(modules);
        this.scope = createScope(context);
        this.scopedContext = createMortarContext(context);
    }

    @Override
    public View setUp(Context context, ViewGroup parent, Flow.Direction direction) {
        return super.setUp(scopedContext, parent, direction);
    }

    @Override
    public View tearDown(Context context, ViewGroup parent, Flow.Direction direction) {
        View destroyed = super.tearDown(context, parent, direction);
        if (direction != Flow.Direction.FORWARD) {
            destroyScope(context);
        }
        return destroyed;
    }

    private MortarScope createScope(Context context) {
        MortarScope parentScope = Mortar.getScope(context);
        return parentScope.requireChild(this);
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

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return modules;
    }
}
