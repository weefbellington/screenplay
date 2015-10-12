package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.VerticalSlideRigger;
import com.davidstemmer.screenplay.scene.Stage;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Flow;

/**
 * Created by weefbellington on 10/17/14.
 */
public class ComplexPagedStage3 extends IndexedStage {

    private final VerticalSlideRigger transformer;

    public ComplexPagedStage3() {
        super(ComplexPagedStage3.class.getName());
        this.transformer = new VerticalSlideRigger(SampleApplication.getInstance());
        addComponents(new ClickBindingComponent());
    }

    @Override
    public int getLayoutId() {
        return R.layout.complex_paged_scene_3;
    }

    @Override
    public Rigger getRigger() {
        return transformer;
    }

    private static class ClickBindingComponent implements Component {

        private final Flow flow;

        public ClickBindingComponent() {
            this.flow = SampleApplication.getMainFlow();
        }

        @Override
        public void afterSetUp(Stage stage, boolean isInitializing) {

            View parent = stage.getView();
            View backTwoScenesButton = parent.findViewById(R.id.back_two_scenes);
            backTwoScenesButton.setOnClickListener(backTwoScenes);
        }

        @Override
        public void beforeTearDown(Stage stage, boolean isFinishing) {}

        private View.OnClickListener backTwoScenes = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flow.set(StaticScenes.COMPLEX_PAGED_SCENE_1);
            }
        };
    }
}
