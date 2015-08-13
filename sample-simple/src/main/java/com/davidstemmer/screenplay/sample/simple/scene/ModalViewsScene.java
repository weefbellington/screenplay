package com.davidstemmer.screenplay.sample.simple.scene;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.scene.Scene;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/2/14.
 */
public class ModalViewsScene extends IndexedScene {

    private final Flow flow;
    private final CrossfadeTransformer transformer;

    public ModalViewsScene() {
        super(ModalViewsScene.class.getName());
        this.flow = SampleApplication.getMainFlow();
        this.transformer = new CrossfadeTransformer(SampleApplication.getInstance());

        Component clickBindingComponent = new ClickBindingComponent();

        addComponents(clickBindingComponent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.modal_views_scene;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    private class ClickBindingComponent implements Component {
        @Override
        public void afterSetUp(Scene scene, boolean isInitializing) {
            View parent = scene.getView();
            parent.findViewById(R.id.show_action_drawer).setOnClickListener(showActionDrawer);
            parent.findViewById(R.id.show_dialog).setOnClickListener(showDialog);
        }

        @Override
        public void beforeTearDown(Scene scene, boolean isFinishing) {

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
                flow.set(new ActionDrawerScene(callback));
            }
        };

        private class ActionDrawerCallback implements ActionDrawerScene.Callback {

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
