package com.davidstemmer.screenplay.sample.simple.scene;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeRigger;
import com.davidstemmer.screenplay.scene.Stage;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ModalViewsStage extends IndexedStage {

    private final Flow flow;
    private final CrossfadeRigger transformer;

    public ModalViewsStage() {
        super(ModalViewsStage.class.getName());
        this.flow = SampleApplication.getMainFlow();
        this.transformer = new CrossfadeRigger(SampleApplication.getInstance());

        Component clickBindingComponent = new ClickBindingComponent();

        addComponents(clickBindingComponent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.modal_views_scene;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }

    private class ClickBindingComponent implements Component {
        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {
            View parent = stage.getView();
            parent.findViewById(R.id.show_action_drawer).setOnClickListener(showActionDrawer);
            parent.findViewById(R.id.show_dialog).setOnClickListener(showDialog);
        }

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {

        }

        private View.OnClickListener showDialog = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.set(StaticScenes.DIALOG_SCENE);
            }
        };

        private View.OnClickListener showActionDrawer = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionDrawerCallback callback = new ActionDrawerCallback(getView().getContext());
                flow.set(new ActionDrawerStage(callback));
            }
        };

        private class ActionDrawerCallback implements ActionDrawerStage.Callback {

            private final Context context;

            public ActionDrawerCallback(Context context) {
                this.context = context;
            }

            @Override
            public void onExitScene(ActionDrawerResult result) {
                switch (result) {
                    case YES:
                        Toast.makeText(context, "Result is YES", Toast.LENGTH_LONG).show();
                        break;
                    case NO:
                        Toast.makeText(context, "Result is NO", Toast.LENGTH_LONG).show();
                        break;
                    case CANCELLED:
                        Toast.makeText(context, "Result is CANCELLED", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    }

}
