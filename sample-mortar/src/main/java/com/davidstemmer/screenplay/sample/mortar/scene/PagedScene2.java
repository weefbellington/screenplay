package com.davidstemmer.screenplay.sample.mortar.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.mortar.R;
import com.davidstemmer.screenplay.sample.mortar.component.PresentationComponent;
import com.davidstemmer.screenplay.sample.mortar.scene.transformer.HorizontalSlideTransformer;
import com.davidstemmer.screenplay.scene.StandardScene;

import javax.inject.Inject;

import butterknife.OnClick;
import flow.Flow;
import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_2)
public class PagedScene2 extends StandardScene {

    private final HorizontalSlideTransformer transformer;

    @Inject
    public PagedScene2(Presenter presenter, HorizontalSlideTransformer transformer) {
        this.transformer = transformer;
        addComponents(presenter);
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    public static class Presenter extends PresentationComponent<View> {

        private final PagedScene3 nextScene;
        private final Flow flow;

        @Inject
        public Presenter(PagedScene3 nextScene, Flow flow) {
            this.nextScene = nextScene;
            this.flow = flow;
        }

        @OnClick(R.id.next) void nextClicked() {
            flow.goTo(nextScene);
        }
    }
}
