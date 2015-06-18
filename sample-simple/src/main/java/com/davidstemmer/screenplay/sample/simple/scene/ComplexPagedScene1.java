package com.davidstemmer.screenplay.sample.simple.scene;

import android.view.View;

import com.davidstemmer.screenplay.sample.simple.SampleApplication;
import com.davidstemmer.screenplay.sample.simple.scene.transformer.CrossfadeTransformer;
import com.davidstemmer.screenplay.scene.Scene;
import com.example.weefbellington.screenplay.sample.simple.R;

import flow.Backstack;
import flow.Flow;
import flow.Layout;

@Layout(R.layout.complex_paged_scene_1)
public class ComplexPagedScene1 extends IndexedScene {

    private final CrossfadeTransformer transformer;

    public ComplexPagedScene1() {
        super(ComplexPagedScene1.class.getName());
        this.transformer = new CrossfadeTransformer(SampleApplication.getInstance());
        addComponents(new ClickBindingComponent());
    }

    @Override
    public Scene.Transformer getTransformer() {
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

            View forwardTwoScenesButton = parent.findViewById(R.id.forward_two_scenes);
            View newBackstackButton     = parent.findViewById(R.id.new_backstack);

            forwardTwoScenesButton.setOnClickListener(forwardTwoScenes);
            newBackstackButton.setOnClickListener(forwardNewBackstack);
        }

        @Override
        public void beforeTearDown(Scene scene, boolean isFinishing) {}


        private final View.OnClickListener forwardTwoScenes = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backstack newBackstack = flow.getBackstack().buildUpon()
                        .push(StaticScenes.COMPLEX_PAGED_SCENE_2)
                        .push(StaticScenes.COMPLEX_PAGED_SCENE_3)
                        .build();
                flow.forward(newBackstack);
            }
        };

        private final View.OnClickListener forwardNewBackstack = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Backstack newBackstack = Backstack.emptyBuilder()
                        .push(StaticScenes.NEW_BACKSTACK_SCENE_1)
                        .push(StaticScenes.NEW_BACKSTACK_SCENE_2)
                        .build();
                flow.forward(newBackstack);
            }
        };
    }

}
