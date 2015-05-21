package com.davidstemmer.screenplay.sample.simple.scene;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.ActionDrawerTransformer;
import com.davidstemmer.screenplay.sample.simple.view.ActionDrawerView;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.component.CallbackComponent;
import com.davidstemmer.screenplay.scene.component.ResultHandler;
import com.davidstemmer.screenplay.scene.component.SceneCallback;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Layout;

/**
 * Created by weefbellington on 10/21/14.
 */

@Layout(R.layout.action_drawer)
public class ActionDrawerScene extends StandardScene {

    private final ActionDrawerTransformer transformer;
    private final ResultHandler<ActionDrawerResult> resultHandler;

    public ActionDrawerScene(Callback callback) {

        this.transformer = new ActionDrawerTransformer(SampleApplication.getInstance());
        this.resultHandler = new ResultHandler<ActionDrawerResult>(ActionDrawerResult.CANCELLED);

        Component callbackComponent = new CallbackComponent<ActionDrawerResult>(callback, resultHandler);
        Component drawerLockingComponent = new DrawerLockingComponent();

        addComponents(callbackComponent, drawerLockingComponent);
    }

    @Override
    public View setUp(Context context, ViewGroup parent, boolean isStarting) {
        //TODO this bind should not be in setUp
        ActionDrawerView view = (ActionDrawerView) super.setUp(context, parent, isStarting);
        view.bind(resultHandler);
        return view;
    }

    @Override
    public boolean isStacking() {
        return true;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    public static interface Callback extends SceneCallback<ActionDrawerResult> {
        @Override void onExitScene(ActionDrawerResult result);
    }

}
