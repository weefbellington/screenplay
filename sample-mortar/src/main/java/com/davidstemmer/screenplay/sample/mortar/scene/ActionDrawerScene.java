package com.davidstemmer.screenplay.sample.mortar.scene;

import android.os.Bundle;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.mortar.module.ActivityModule;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.ActionDrawerTransformer;
import com.davidstemmer.screenplay.sample.mortar.view.ActionDrawerView;
import com.davidstemmer.screenplay.scene.ScopedScene;
import com.davidstemmer.screenplay.scene.component.CallbackComponent;
import com.davidstemmer.screenplay.scene.component.ResultHandler;
import com.davidstemmer.screenplay.scene.component.SceneCallback;
import com.davidstemmer.screenplay.scene.rigger.StackRigger;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Provides;
import flow.Flow;
import flow.Layout;
import mortar.MortarScope;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/21/14.
 */

@Layout(R.layout.action_drawer)
public class ActionDrawerScene extends ScopedScene {

    public static enum Result {
        YES,
        NO,
        CANCELLED
    }

    Module module;

    @Inject StackRigger rigger;
    @Inject ActionDrawerTransformer transformer;
    @Inject DrawerLockingComponent lockingComponent;
    @Inject CallbackComponent<Result> callbackComponent;

    public ActionDrawerScene(Callback callback) {
        this.module = new Module(callback);
    }

    @Override
    public void onCreateScope(MortarScope scope) {
        addComponent(lockingComponent);
        addComponent(callbackComponent);
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Override
    public String getMortarScopeName() {
        return getClass().getName();
    }

    @Override
    public Object getDaggerModule() {
        return module;
    }

    public static interface Callback extends SceneCallback<Result> {}

    @dagger.Module(addsTo = ActivityModule.class, injects = {
            ActionDrawerScene.class,
            ActionDrawerScene.Presenter.class,
            ActionDrawerView.class
    })
    public static class Module {

        private final Callback callback;

        public Module(Callback callback) {
            this.callback = callback;
        }

        @Provides @Singleton ResultHandler<Result> provideResultHandler() {
            return new ResultHandler<ActionDrawerScene.Result>(ActionDrawerScene.Result.CANCELLED);
        }

        @Provides @Singleton
        CallbackComponent<Result> provideCallbackComponent(ResultHandler<Result> resultHandler) {
            return new CallbackComponent<Result>(callback, resultHandler);
        }
    }

    @Singleton
    public static class Presenter extends ViewPresenter<ActionDrawerView> {

        @Inject Flow flow;
        @Inject ResultHandler<Result> resultHandler;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.yes) void yes() {
            resultHandler.setResult(Result.YES);
            flow.goBack();
        }

        @OnClick(R.id.no) void no() {
            resultHandler.setResult(Result.NO);
            flow.goBack();
        }
    }
}
