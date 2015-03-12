package com.davidstemmer.screenplay.sample.mortar.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.mortar.component.PresentationComponent;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.ActionDrawerTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.component.CallbackComponent;
import com.davidstemmer.screenplay.scene.component.ResultHandler;
import com.davidstemmer.screenplay.scene.component.SceneCallback;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Inject;

import butterknife.OnClick;
import flow.Flow;
import flow.Layout;

/**
 * Created by weefbellington on 10/21/14.
 */

@Layout(R.layout.action_drawer)
public class ActionDrawerScene extends StandardScene {

    public static enum Result {
        YES,
        NO,
        CANCELLED
    }

    private final ActionDrawerTransformer transformer;

    @Inject
    public ActionDrawerScene(ActionDrawerTransformer transformer,
                             DrawerLockingComponent lockingComponent,
                             CallbackComponent<Result> callbackComponent,
                             Presenter presenterComponent) {
        this.transformer = transformer;
        addComponents(lockingComponent, callbackComponent, presenterComponent);
    }

    @Override
    public boolean isStacking() {
        return true;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    public static interface Callback extends SceneCallback<Result> {}

    @ActionDrawerScene.Scope
    public static class Presenter extends PresentationComponent<View> {

        private final Flow flow;
        private final ResultHandler<Result> resultHandler;

        @Inject
        public Presenter(Flow flow, ResultHandler<Result> resultHandler) {
            this.flow = flow;
            this.resultHandler = resultHandler;
        }

        @Override
        public void afterSetUp(View view, boolean isInitializing) {}

        @Override
        public void beforeTearDown(View view, boolean isFinishing) {}

        @OnClick(R.id.yes) void yes() {
            resultHandler.setResult(Result.YES);
            flow.goBack();
        }

        @OnClick(R.id.no) void no() {
            resultHandler.setResult(Result.NO);
            flow.goBack();
        }
    }

    @javax.inject.Scope
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Scope {}
}
