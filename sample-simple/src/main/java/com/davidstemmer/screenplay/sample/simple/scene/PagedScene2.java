package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.HorizontalSlideTransformer;
import com.davidstemmer.screenplay.scene.Scene;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;
import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_2)
public class PagedScene2 extends IndexedScene {

    private final Flow flow;
    private final HorizontalSlideTransformer transformer;

    public PagedScene2() {
        super(PagedScene2.class.getName());
        this.flow = SampleApplication.getMainFlow();
        this.transformer = new HorizontalSlideTransformer(SampleApplication.getInstance());
        addComponents(new ClickBindingComponent());
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    private class ClickBindingComponent implements Component {
        @Override
        public void afterSetUp(Scene scene, boolean isInitializing) {
            View parent = scene.getView();
            View nextButton = parent.findViewById(R.id.next);
            nextButton.setOnClickListener(showNextScene);
        }

        @Override
        public void beforeTearDown(Scene scene, boolean isFinishing) {

        }

        private View.OnClickListener showNextScene = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.goTo(StaticScenes.SIMPLE_PAGED_SCENE_3);
            }
        };
    }
}
