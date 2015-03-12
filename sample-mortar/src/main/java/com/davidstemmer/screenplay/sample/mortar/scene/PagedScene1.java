package com.davidstemmer.screenplay.sample.mortar.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.component.PresentationComponent;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;

import javax.inject.Inject;
import javax.inject.Singleton;

import butterknife.OnClick;
import flow.Flow;
import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_1)
public class PagedScene1 extends StandardScene {

    private final CrossfadeTransformer transformer;

    @Inject
    public PagedScene1(Presenter presenter, CrossfadeTransformer transformer) {
        this.transformer = transformer;
        addComponents(presenter);
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    @Singleton
    public static class Presenter extends PresentationComponent<View> {

        private final PagedScene2 nextScene;
        private final Flow flow;

        @Inject
        public Presenter(PagedScene2 nextScene, Flow flow) {
            this.nextScene = nextScene;
            this.flow = flow;
        }

        @OnClick(R.id.next) void nextClicked() {
            flow.goTo(nextScene);
        }
    }
}
