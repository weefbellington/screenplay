package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.VerticalSlideTransformer;
import com.davidstemmer.screenplay.scene.Scene;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/17/14.
 */
public class ComplexPagedScene3 extends IndexedScene {

    private final VerticalSlideTransformer transformer;

    public ComplexPagedScene3() {
        super(PagedScene2.class.getName());
        this.transformer = new VerticalSlideTransformer(SampleApplication.getInstance());
        addComponents(new ClickBindingComponent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.complex_paged_scene_3;
    }

    @Override
    public Transformer getTransformer() {
        return transformer;
    }

    private static class ClickBindingComponent implements Component {

        private final Flow flow;

        public ClickBindingComponent() {
            this.flow = SampleApplication.getMainFlow();
        }

        @Override
        public void afterSetUp(Scene scene, boolean isInitializing) {

            View parent = scene.getView();
            View backTwoScenesButton = parent.findViewById(R.id.back_two_scenes);
            backTwoScenesButton.setOnClickListener(backTwoScenes);
        }

        @Override
        public void beforeTearDown(Scene scene, boolean isFinishing) {}

        private View.OnClickListener backTwoScenes = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.set(StaticScenes.COMPLEX_PAGED_SCENE_1);
            }
        };
    }
}
