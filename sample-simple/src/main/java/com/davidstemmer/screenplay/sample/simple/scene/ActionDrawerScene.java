package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.ActionDrawerTransformer;
import com.davidstemmer.screenplay.scene.Scene;
import com.davidstemmer.screenplay.scene.XmlScene;
import com.davidstemmer.screenplay.scene.component.SceneCallback;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/21/14.
 */

public class ActionDrawerScene extends XmlScene {

    private final Flow flow;
    private final ActionDrawerTransformer transformer;
    private final Callback callback;

    public ActionDrawerScene(Callback callback) {

        this.flow = SampleApplication.getMainFlow();
        this.transformer = new ActionDrawerTransformer(SampleApplication.getInstance());
        this.callback = callback;

        Component drawerLockingComponent = new DrawerLockingComponent();
        Component clickBindingComponent = new ClickBindingComponent();

        addComponents(drawerLockingComponent, clickBindingComponent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.action_drawer;
    }

    @Override
    public boolean isStacking() {
        return true;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    public interface Callback extends SceneCallback<ActionDrawerResult> {
        @Override void onExitScene(ActionDrawerResult result);
    }

    private class ClickBindingComponent implements Component {

        private ActionDrawerResult result = ActionDrawerResult.CANCELLED;

        @Override
        public void afterSetUp(Scene scene, boolean isInitializing) {
            View parent = scene.getView();
            parent.findViewById(R.id.yes).setOnClickListener(yesListener);
            parent.findViewById(R.id.no).setOnClickListener(noListener);
        }

        @Override
        public void beforeTearDown(Scene scene, boolean isFinishing) {
            if (isFinishing) {
                callback.onExitScene(result);
            }
        }

        private final View.OnClickListener yesListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = ActionDrawerResult.YES;
                flow.goBack();
            }
        };

        private final View.OnClickListener noListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = ActionDrawerResult.NO;
                flow.goBack();
            }
        };
    }

}
