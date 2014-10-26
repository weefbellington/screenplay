package com.davidstemmer.screenplay.sample.scene;

import android.os.Bundle;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.scene.transformer.ActionDrawerTransformer;
import com.davidstemmer.screenplay.sample.view.ActionDrawerView;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.rigger.ModalRigger;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/21/14.
 */

@Layout(R.layout.action_drawer)
@Singleton
public class ActionDrawerScene extends StandardScene {

    public static enum Result {
        YES,
        NO,
        CANCELLED
    }

    private Callback callback;

    private final ModalRigger rigger;
    private final ActionDrawerTransformer transformer;

    @Inject
    public ActionDrawerScene(DrawerLockingComponent component, ModalRigger rigger, ActionDrawerTransformer transformer) {
        super(component);
        this.rigger = rigger;
        this.transformer = transformer;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    public static interface Callback {
        public void onComplete(Result result);
    }

    @Singleton
    public static class Presenter extends ViewPresenter<ActionDrawerView> {

        @Inject ActionDrawerScene scene;
        @Inject Flow flow;

        private Result result = Result.CANCELLED;
        private boolean isPresenting = false;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
            isPresenting = true;
        }

        @Override
        protected void onSave(Bundle outState) {
            super.onSave(outState);
            isPresenting = false;
        }

        @OnClick(R.id.yes) void yes() {
            result = Result.YES;
            flow.goBack();
        }

        @OnClick(R.id.no) void no() {
            result = Result.NO;
            flow.goBack();
        }

        public void executeCallback() {
            if (isPresenting) {
                scene.callback.onComplete(result);
            }
            result = Result.CANCELLED;
        }
    }
}
