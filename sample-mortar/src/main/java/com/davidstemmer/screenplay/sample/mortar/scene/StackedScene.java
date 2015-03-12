package com.davidstemmer.screenplay.sample.mortar.scene;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.davidstemmer.screenplay.sample.mortar.MainApplication;
import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.component.PresentationComponent;
import com.davidstemmer.screenplay.sample.mortar.module.ActionDrawerComponent;
import com.davidstemmer.screenplay.sample.mortar.module.ActionDrawerModule;
import com.davidstemmer.screenplay.sample.mortar.module.ApplicationComponent;
import com.davidstemmer.screenplay.sample.mortar.module.Dagger_ActionDrawerComponent;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import butterknife.OnClick;
import flow.Flow;
import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.stacked_scene)
public class StackedScene extends StandardScene {

    private final CrossfadeTransformer transformer;

    @Inject
    public StackedScene(Presenter presenter, CrossfadeTransformer transformer) {
        this.transformer = transformer;
        addComponents(presenter);
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends PresentationComponent<View> {

        private final Flow flow;
        private final Provider<DialogScene> dialogSceneFactory;

        @Inject
        public Presenter(Flow flow,
                         Provider<DialogScene> dialogSceneFactory) {
            this.flow = flow;
            this.dialogSceneFactory = dialogSceneFactory;
        }

        @OnClick(R.id.show_dialog) void dialogButtonClicked() {
            flow.goTo(dialogSceneFactory.get());
        }

        @OnClick(R.id.show_action_drawer) void showActionDrawer(View button) {
            ActionDrawerScene.Callback callback = new ActionDrawerCallback(button.getContext());
            ApplicationComponent parentComponent = MainApplication.getComponent();
            ActionDrawerComponent component = Dagger_ActionDrawerComponent.builder()
                    .applicationComponent(parentComponent)
                    .actionDrawerModule(new ActionDrawerModule(callback))
                    .build();
            flow.goTo(component.actionDrawerScene());
        }

        private class ActionDrawerCallback implements ActionDrawerScene.Callback {
            private final Context context;

            private ActionDrawerCallback(Context context) {
                this.context = context;
            }

            @Override
            public void onExitScene(ActionDrawerScene.Result result) {
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
