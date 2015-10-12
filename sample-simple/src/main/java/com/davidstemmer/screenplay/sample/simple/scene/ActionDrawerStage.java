package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.ActionDrawerRigger;
import com.davidstemmer.screenplay.scene.Stage;
import com.davidstemmer.screenplay.scene.XmlStage;
import com.davidstemmer.screenplay.scene.component.SceneCallback;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/21/14.
 */

public class ActionDrawerStage extends XmlStage {

    private final Flow flow;
    private final ActionDrawerRigger transformer;
    private final Callback callback;

    public ActionDrawerStage(Callback callback) {

        this.flow = SampleApplication.getMainFlow();
        this.transformer = new ActionDrawerRigger(SampleApplication.getInstance());
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
    public Rigger getRigger() {
        return transformer;
    }

    public interface Callback extends SceneCallback<ActionDrawerResult> {
        @Override void onExitScene(ActionDrawerResult result);
    }

    private class ClickBindingComponent implements Component {

        private ActionDrawerResult result = ActionDrawerResult.CANCELLED;

        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {
            View parent = stage.getView();
            parent.findViewById(R.id.yes).setOnClickListener(yesListener);
            parent.findViewById(R.id.no).setOnClickListener(noListener);
        }

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {
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
