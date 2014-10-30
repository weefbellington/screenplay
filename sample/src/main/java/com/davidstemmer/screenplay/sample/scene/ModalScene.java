package com.davidstemmer.screenplay.sample.scene;

import android.os.Bundle;
import android.widget.Toast;

import com.davidstemmer.screenplay.sample.R;
import com.davidstemmer.screenplay.sample.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.sample.view.ModalSceneView;
import com.davidstemmer.screenplay.scene.StandardScene;
import com.davidstemmer.screenplay.scene.rigger.PageRigger;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;
import flow.Layout;
import mortar.ViewPresenter;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.modal_scene)
@Singleton
public class ModalScene extends StandardScene {

    private final PageRigger rigger;
    private final CrossfadeTransformer transformer;

    @Inject
    public ModalScene(PageRigger rigger, CrossfadeTransformer transformer) {
        this.rigger = rigger;
        this.transformer = transformer;
    }

    @Override
    public Rigger getRigger() {
        return rigger;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends ViewPresenter<ModalSceneView> {

        @Inject Flow flow;
        @Inject DialogScene dialogScene;
        @Inject ActionDrawerScene actionDrawerScene;

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            ButterKnife.inject(this, getView());
        }

        @OnClick(R.id.show_dialog) void dialogButtonClicked() {
            flow.goTo(dialogScene);
        }
        @OnClick(R.id.show_action_drawer) void showActionDrawer() {
            actionDrawerScene.setCallback(new ActionDrawerCallback());
            flow.goTo(actionDrawerScene);
        }

        private class ActionDrawerCallback implements ActionDrawerScene.Callback {
            @Override
            public void onComplete(ActionDrawerScene.Result result) {
                switch (result) {
                    case YES:
                        Toast.makeText(getView().getContext(), "Result is YES", Toast.LENGTH_LONG).show();
                        break;
                    case NO:
                        Toast.makeText(getView().getContext(), "Result is NO", Toast.LENGTH_LONG).show();
                        break;
                    case CANCELLED:
                        Toast.makeText(getView().getContext(), "Result is CANCELLED", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }

    }
}
