package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.PopupRigger;
import com.davidstemmer.screenplay.scene.Stage;
import com.davidstemmer.screenplay.scene.XmlStage;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class DialogStage extends XmlStage {

    private final Flow flow;
    private final PopupRigger transformer;

    public DialogStage() {
        this.flow = SampleApplication.getMainFlow();
        this.transformer = new PopupRigger(SampleApplication.getInstance());
        addComponents(new DrawerLockingComponent(), new ClickBindingComponent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_scene;
    }

    @Override
    public boolean isStacking() {
        return true;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }

    private class ClickBindingComponent implements Component {
        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {
            View parent = stage.getView();
            View okButton = parent.findViewById(R.id.ok);
            View addSceneButton = parent.findViewById(R.id.add_scene);
            okButton.setOnClickListener(closeDialog);
            addSceneButton.setOnClickListener(addScene);
        }

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {

        }

        private final View.OnClickListener closeDialog = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.goBack();
            }
        };

        private final View.OnClickListener addScene = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.set(StaticScenes.POST_DIALOG_SCENE);
            }
        };
    }
}
