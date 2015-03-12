package com.davidstemmer.screenplay.sample.mortar.scene;

import android.view.View;

import com.davidstemmer.screenplay.SceneState;
import com.davidstemmer.screenplay.flow.Screenplay;
import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.component.DrawerLockingComponent;
import com.davidstemmer.screenplay.sample.mortar.component.PresentationComponent;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.PopupTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.OnClick;
import flow.Flow;
import flow.Layout;

/**
 * Created by weefbellington on 10/2/14.
 */
@Layout(R.layout.dialog_scene)
public class DialogScene extends StandardScene {

    private final PopupTransformer transformer;

    @Inject
    public DialogScene(PopupTransformer transformer, DrawerLockingComponent lock, Presenter presenter) {
        this.transformer = transformer;
        addComponents(lock, presenter);
    }

    @Override
    public boolean isStacking() {
        return true;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends PresentationComponent<View> {

        private final Flow flow;
        private final Screenplay screenplay;

        @Inject
        public Presenter(Flow flow, Screenplay screenplay) {
            this.flow = flow;
            this.screenplay = screenplay;
        }

        @OnClick(R.id.ok) void dismiss() {
            if (screenplay.getScreenState() != SceneState.TRANSITIONING) {
                flow.goBack();
            }
        }
    }
}
