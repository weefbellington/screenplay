package com.davidstemmer.screenplay.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;

/**
 * Created by weefbellington on 10/20/14.
 */
public abstract class MortarScene extends StandardScene implements Blueprint {

    private MortarScope scope;

    @Override
    public View setUp(Context context, ViewGroup parent) {
        MortarScope parentScope = Mortar.getScope(context);
        scope = parentScope.requireChild(this);
        Context childContext = scope.createContext(context);
        return super.setUp(childContext, parent);
    }

    @Override
    public View tearDown(Context context, ViewGroup parent) {
        MortarScope parentScope = Mortar.getScope(context);
        parentScope.destroyChild(scope);
        scope = null;
        return super.tearDown(context, parent);
    }
}
