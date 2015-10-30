package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.ActionDrawerRigger;
import com.davidstemmer.screenplay.stage.Stage;
import com.davidstemmer.screenplay.stage.XmlStage;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/21/14.
 */

public class ActionDrawerStage extends XmlStage {

    private final Flow flow;
    private final ActionDrawerRigger transformer;

    private Result result = Result.CANCELLED;

    public ActionDrawerStage(Callback callback) {

        this.flow = SampleApplication.getMainFlow();
        this.transformer = new ActionDrawerRigger(SampleApplication.getInstance());

        Component drawerLockingComponent = new DrawerLockingComponent();
        Component clickBindingComponent = new ClickBindingComponent();
        Component callbackComponent = new CallbackComponent(callback);

        addComponents(drawerLockingComponent, clickBindingComponent, callbackComponent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.action_drawer;
    }

    @Override
    public boolean isModal() {
        return true;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }

    public interface Callback {
        void onExitScene(Result result);
    }

    private class ClickBindingComponent implements Component {

        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {
            View parent = stage.getView();
            parent.findViewById(R.id.yes).setOnClickListener(yesListener);
            parent.findViewById(R.id.no).setOnClickListener(noListener);
        }

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {}

        private final View.OnClickListener yesListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = Result.YES;
                flow.goBack();
            }
        };

        private final View.OnClickListener noListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = Result.NO;
                flow.goBack();
            }
        };
    }

    private class CallbackComponent implements Component {

        private final Callback callback;

        public CallbackComponent(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {}

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {
            if (isFinishing) {
                callback.onExitScene(result);
            }
        }
    }

    public enum Result {
        YES,
        NO,
        CANCELLED
    }

}
