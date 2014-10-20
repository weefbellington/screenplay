package com.davidstemmer.screenplay.sample.scene;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transformer.NavigationDrawerTransformer;
import com.davidstemmer.screenplay.sample.view.NavigationDrawerView;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.director.ModalDirector;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.Layout;
import mortar.Blueprint;
import mortar.Mortar;
import mortar.MortarScope;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/7/14.
 */

@Layout(R.layout.navigation_drawer)
public class NavigationDrawerScene extends StandardScene implements Blueprint {

    private final ModalDirector director;
    private final NavigationDrawerTransformer transformer;

    private MortarScope scope;

    @Inject
    public NavigationDrawerScene(ModalDirector director, NavigationDrawerTransformer transformer) {
        this.director = director;
        this.transformer = transformer;
    }


    @Override
    public Director getDirector() {
        return director;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

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

    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return new Module();
    }

    @dagger.Module
    public static class Module {}

    public static class Presenter extends ViewPresenter<NavigationDrawerView> {

        private final Flow flow;
        private final WelcomeScene welcomeScene;
        private final PagedScene1 pagedScene;

        private Scene nextScene;

        @Inject
        public Presenter(Flow flow, WelcomeScene welcomeScene, PagedScene1 pagedScene) {
            this.flow = flow;
            this.welcomeScene = welcomeScene;
            this.pagedScene = pagedScene;

            this.nextScene = welcomeScene;
        }

        @OnClick(R.id.nav_item_welcome)
        void welcomeClicked() {
            nextScene = welcomeScene;
            flow.goBack();
        }

        @OnClick(R.id.nav_item_paged_scenes)
        void pagedScenesClicked() {
            nextScene = pagedScene;
            flow.goBack();
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @Override
        protected void onExitScope() {

            if (flow.getBackstack().current().getScreen() != nextScene) {
                flow.resetTo(nextScene);
            }
        }
    }


}
