package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.scene.Scene;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;
import flow.Layout;

/**
 * Created by weefbellington on 10/17/14.
 */

@Layout(R.layout.paged_scene_1)
public class PagedScene1 extends IndexedScene {

    private final Flow flow;
    private final CrossfadeTransformer transformer;

    public PagedScene1() {
        super(PagedScene1.class.getName());
        this.flow = SampleApplication.getMainFlow();
        this.transformer = new CrossfadeTransformer(SampleApplication.getInstance());

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
                flow.goTo(StaticScenes.SIMPLE_PAGED_SCENE_2);
            }
        };
    }
}
